package com.gjiazhe.layoutswitch.view2;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;

/**
 * 基础功能的gridView，提供设置每行数量，adapter
 */
public abstract class CommonGridView extends ConstraintLayout {
    /**
     * 每行数量
     */
    protected int currSpanCount;
    /**
     * 记录条目数量
     */
    protected int mItemCount;

    GridAdapter mAdapter;
    //缓存view
    private CacheBin cacheBin = new CacheBin();
    //宽度测量参数，辅助子view高度等获取
    int mWidthMeasureSpec = 0;


    public CommonGridView(Context context) {
        super(context);
    }

    public CommonGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CommonGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setAdapter(GridAdapter gridAdapter) {
        this.mAdapter = gridAdapter;
        if (this.mAdapter != null) {
            //初始化
            int itemCount = gridAdapter.getItemCount();
            if (itemCount > 0) {
                requestLayout();
            }
        }
    }


    public GridAdapter getGridAdapter() {
        return mAdapter;
    }


    public View obtainView(int position) {
        View cacheView = cacheBin.getView(position);
        if (cacheView == null) {
            View view = mAdapter.onCreateView(position, this);
            cacheBin.addView(view);
            return view;
        }
        return cacheView;
    }

    public void clearCache() {
        cacheBin.clear();
    }

    MyObserver mDataSetObserver;

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (mAdapter != null && mDataSetObserver == null) {
            mDataSetObserver = new MyObserver();
            mAdapter.registerDataSetObserver(mDataSetObserver);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mAdapter != null && mDataSetObserver != null) {
            mAdapter.unregisterDataSetObserver(mDataSetObserver);
            mDataSetObserver = null;
        }
    }

    /**
     * 数据源变更
     */
    public void onChanged() {
        post(new Runnable() {
            @Override
            public void run() {
                clearCache();
                addAllView();
            }
        });
    }

    abstract void addAllView();

    class MyObserver extends android.database.DataSetObserver {
        @Override
        public void onChanged() {
            CommonGridView.this.onChanged();
            /**
             * 更新布局
             */
//            requestLayout();
        }
    }

    /**
     * view缓存池
     */
    class CacheBin {
        private ArrayList<View> viewBin = new ArrayList<>();

        public void addView(View view) {
            viewBin.add(view);
        }

        public View getView(int position) {
            if (position >= 0 && position < viewBin.size()) {
                return viewBin.get(position);
            }
            return null;
        }

        public void clear() {
            viewBin.clear();
        }
    }
}

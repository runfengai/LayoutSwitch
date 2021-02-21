package com.gjiazhe.layoutswitch;

import android.content.Context;
import android.util.AttributeSet;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 自定义网络布局管理器，两种状态：1.九宫格。2.放大指定一个，置于顶部，剩下的每排四个。
 */
public class MyGridLayoutManager extends GridLayoutManager {
    public static final int SPAN_COUNT_DEFAULT = 3;
    public static final int SPAN_COUNT_SCALE = 4;//放大状态下的数量

    private SpanSizeLookup mSpanSizeLookup;

    public MyGridLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public MyGridLayoutManager(Context context, int spanCount) {
        super(context, spanCount);
        setDefaultSpanSizeLookUp();
    }

    public MyGridLayoutManager(Context context, int spanCount, int orientation, boolean reverseLayout) {
        super(context, spanCount, orientation, reverseLayout);
    }


    private void setDefaultSpanSizeLookUp() {
        mSpanSizeLookup = new SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                int spanSize = 1;//默认是1个
                if (getSpanCount() == SPAN_COUNT_SCALE && position == 0) {
                    spanSize = SPAN_COUNT_SCALE;
                }
                return spanSize;
            }
        };
        setSpanSizeLookup(mSpanSizeLookup);
    }

    private int highlightPosition = -1;

    public void setHighLightPosition(int position) {
        highlightPosition = position;
    }

    public int getHighlightPosition() {
        return highlightPosition;
    }

    /**
     * 改变布局
     */
    public void switchSpanCount() {
        if (getSpanCount() == SPAN_COUNT_DEFAULT) {
            setSpanCount(SPAN_COUNT_SCALE);
        } else {
            setSpanCount(SPAN_COUNT_DEFAULT);
        }
    }

    public boolean isScaleMode() {
        return getSpanCount() == SPAN_COUNT_SCALE;
    }

    public void switchDefaultSpanCount() {
        setSpanCount(SPAN_COUNT_DEFAULT);
    }

    public void switchScaleSpanCount() {
        setSpanCount(SPAN_COUNT_SCALE);
    }


    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        super.onLayoutChildren(recycler, state);
    }
}

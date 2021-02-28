package com.gjiazhe.layoutswitch;


import android.graphics.Point;
import android.util.Size;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import static com.gjiazhe.layoutswitch.MyGridLayoutManager.SPAN_COUNT_DEFAULT;

/**
 * Created by gjz on 16/01/2017.
 */

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> implements View.OnClickListener {


    private static final int VIEW_TYPE_SMALL = 1;
    private static final int VIEW_TYPE_BIG = 2;

    private List<Item> mItems = new ArrayList<>();
    private GridLayoutManager mLayoutManager;

    public ItemAdapter(List<Item> items, GridLayoutManager layoutManager) {
        mItems.addAll(items);
        mLayoutManager = layoutManager;
    }

    /**
     * range改变
     */
    public void notifyItemRange(List<Item> items) {
        this.mItems.clear();
        this.mItems.addAll(items);
        notifyItemRangeChanged(0, getItemCount());
    }

    @Override
    public int getItemViewType(int position) {
        int spanCount = mLayoutManager.getSpanCount();
        if (spanCount == SPAN_COUNT_DEFAULT) {
            return VIEW_TYPE_SMALL;
        } else {
            return VIEW_TYPE_BIG;
        }
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == VIEW_TYPE_BIG) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_big, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_small, parent, false);
        }
        return new ItemViewHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        Item item = mItems.get(position);
        holder.iv.setImageResource(item.getImgResId());
        ViewGroup.LayoutParams ivLp = holder.root.getLayoutParams();
        if (position == 0 && getItemViewType(position) == VIEW_TYPE_BIG) {//第一个
            ivLp.height = (int) DensityUtil.dp2px(holder.iv.getContext(), 240);
//            ivLp.width = (int) DensityUtil.dp2px(holder.iv.getContext(), 240);
        } else {
            ivLp.height = (int) DensityUtil.dp2px(holder.iv.getContext(), 120);
//            ivLp.width = (int) DensityUtil.dp2px(holder.iv.getContext(), 120);
        }
    }

    public View getView(int position) {
        return mLayoutManager.findViewByPosition(position);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public void onClick(View v) {

    }

    public void notifyDataSet(List<Item> items, int i, int position) {
        this.mItems.clear();
        this.mItems.addAll(items);
        notifyDataSetChanged();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        ImageView iv;
        View root;

        ItemViewHolder(View itemView, int viewType) {
            super(itemView);
            root = itemView;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        if (onItemClickListener != null) {
                            onItemClickListener.onItemClick(v, position);
                        }
                    }

                }
            });

            getAdapterPosition();
            if (viewType == VIEW_TYPE_BIG) {
                iv = (ImageView) itemView.findViewById(R.id.image_big);
            } else {
                iv = (ImageView) itemView.findViewById(R.id.image_small);
            }
        }
    }

    /**
     * 定义RecyclerView选项单击事件的回调接口
     */
    public interface OnItemClickListener {
        //参数（父组件，当前单击的View,单击的View的位置，数据）
        void onItemClick(View view, int position);
    }
}

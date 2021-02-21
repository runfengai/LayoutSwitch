package com.gjiazhe.layoutswitch;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import static com.gjiazhe.layoutswitch.MyGridLayoutManager.SPAN_COUNT_DEFAULT;

/**
 * Created by gjz on 16/01/2017.
 */

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {


    private static final int VIEW_TYPE_SMALL = 1;
    private static final int VIEW_TYPE_BIG = 2;

    private List<Item> mItems;
    private GridLayoutManager mLayoutManager;

    public ItemAdapter(List<Item> items, GridLayoutManager layoutManager) {
        mItems = items;
        mLayoutManager = layoutManager;
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
        Item item = mItems.get(position % 4);
        holder.iv.setImageResource(item.getImgResId());
        ViewGroup.LayoutParams ivLp = holder.iv.getLayoutParams();
        if (position == 0 && getItemViewType(position) == VIEW_TYPE_BIG) {//第一个
            ivLp.height = (int) DensityUtil.dp2px(holder.iv.getContext(), 240);
        } else {
            ivLp.height = (int) DensityUtil.dp2px(holder.iv.getContext(), 120);
        }
    }

    @Override
    public int getItemCount() {
        return 9;
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        ImageView iv;


        ItemViewHolder(View itemView, int viewType) {
            super(itemView);
            if (viewType == VIEW_TYPE_BIG) {
                iv = (ImageView) itemView.findViewById(R.id.image_big);
            } else {
                iv = (ImageView) itemView.findViewById(R.id.image_small);
            }
        }
    }
}

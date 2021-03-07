package com.gjiazhe.layoutswitch.view2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.gjiazhe.layoutswitch.Item;
import com.gjiazhe.layoutswitch.R;

import java.util.ArrayList;
import java.util.List;

public class MyGridAdapter extends GridAdapter {
    private List<Item> list = new ArrayList<>();

    public MyGridAdapter(List<Item> list) {
        this.list = list;
    }

    @Override
    protected int getItemCount() {
        return 9;
    }

    @Override
    public int getItemId(int position) {
        return position;
    }

    @Override
    public View onCreateView(final int position, final ViewGroup viewGroup) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_const_big, viewGroup, false);
        Item item = (position > list.size() - 1) ? new Item(R.drawable.aaa) : list.get(position);
        ImageView viewById = view.findViewById(R.id.image_big);
        viewById.setImageResource(item.getImgResId());
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ScalableGridView) viewGroup).startScalePosition(position);
            }
        });
        return view;
    }

    @Override
    public void onBindView(View view) {

    }
}

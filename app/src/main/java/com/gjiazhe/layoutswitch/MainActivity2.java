package com.gjiazhe.layoutswitch;


import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.gjiazhe.layoutswitch.view2.MyGridAdapter;
import com.gjiazhe.layoutswitch.view2.ScalableGridView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        initItemsData();
        ScalableGridView scalableGridView = findViewById(R.id.gridView);
        final MyGridAdapter gridAdapter = new MyGridAdapter(items);
        scalableGridView.setAdapter(gridAdapter);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                initItemsData2();
                gridAdapter.notifyDataSetChanged(items);
            }
        }, 3000);
    }

    private List<Item> items;

    private void initItemsData() {
        items = new ArrayList<>();
        items.add(new Item(R.drawable.img1, "Image 1", 20, 33));
        items.add(new Item(R.drawable.img2, "Image 2", 10, 54));
        items.add(new Item(R.drawable.img3, "Image 3", 27, 20));
        items.add(new Item(R.drawable.img4, "Image 4", 45, 67));
        items.add(new Item(R.drawable.img1, "Image 5", 20, 33));
        items.add(new Item(R.drawable.img2, "Image 6", 10, 54));
        items.add(new Item(R.drawable.img3, "Image 7", 27, 20));
        items.add(new Item(R.drawable.img4, "Image 8", 45, 67));
    }

    private void initItemsData2() {
        items = new ArrayList<>();
        items.add(new Item(R.drawable.img4, "Image 8", 45, 67));
    }
}
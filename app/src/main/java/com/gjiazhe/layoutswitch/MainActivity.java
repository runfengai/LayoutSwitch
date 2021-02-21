package com.gjiazhe.layoutswitch;


import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.gjiazhe.layoutswitch.MyGridLayoutManager.SPAN_COUNT_DEFAULT;
import static com.gjiazhe.layoutswitch.MyGridLayoutManager.SPAN_COUNT_SCALE;


public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ItemAdapter itemAdapter;
    private MyGridLayoutManager gridLayoutManager;
    private List<Item> itemsOrigin;
    private List<Item> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        initItemsData();

        gridLayoutManager = new MyGridLayoutManager(this, SPAN_COUNT_DEFAULT);
        itemAdapter = new ItemAdapter(items, gridLayoutManager);
        recyclerView = (RecyclerView) findViewById(R.id.rv);
        recyclerView.setAdapter(itemAdapter);
        recyclerView.getItemAnimator();
        recyclerView.setLayoutManager(gridLayoutManager);
        itemAdapter.setOnItemClickListener(new ItemAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (gridLayoutManager.isScaleMode() && position == 0) {//还原默认
                    gridLayoutManager.setHighLightPosition(-1);
                    revertData();
                    gridLayoutManager.switchDefaultSpanCount();
                    notifyAdapter();
                } else if (gridLayoutManager.isScaleMode()) {//切换两个视图

//                    切换两个item之间的内容
                    changeDataBetween(position, 0);
                    itemAdapter.notifyItemMoved(0, position);
                    notifyAdapter();
                    gridLayoutManager.setHighLightPosition(position);
                } else {//默认切换到放大视图
                    //点击后，layoutManager处理
                    gridLayoutManager.setHighLightPosition(position);
                    //改变数据源
                    changeDataRange(position);
                    switchLayout();
                }

            }
        });
    }

    private void changeDataBetween(int position, int highlightPosition) {
        Collections.swap(items, position, highlightPosition);
    }

    private void revertData() {
        items.clear();
        items.addAll(itemsOrigin);
    }

    private void changeDataRange(int position) {
        Item pre = items.get(position);
        for (int i = 0; i <= position; i++) {
            Item curr = items.get(i);
            items.set(i, pre);
            pre = curr;
        }
    }

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
        items.add(new Item(R.drawable.img1, "Image 9", 20, 33));
        itemsOrigin = new ArrayList<>(items.size());
        itemsOrigin.addAll(items);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.meun_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_switch_layout) {
            switchLayout();
            switchIcon(item);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void switchLayout() {
        gridLayoutManager.switchSpanCount();
        notifyAdapter();
    }

    private void notifyAdapter() {
        itemAdapter.notifyItemRange(items);
    }

    private void switchIcon(MenuItem item) {
        if (gridLayoutManager.getSpanCount() == SPAN_COUNT_SCALE) {
            item.setIcon(getResources().getDrawable(R.drawable.ic_span_3));
        } else {
            item.setIcon(getResources().getDrawable(R.drawable.ic_span_1));
        }
    }
}

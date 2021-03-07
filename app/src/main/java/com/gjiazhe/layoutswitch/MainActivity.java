package com.gjiazhe.layoutswitch;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Path;
import android.os.Bundle;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.TranslateAnimation;

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
//        recyclerView.getItemAnimator();
//        recyclerView.setItemAnimator(null);
        recyclerView.setLayoutManager(gridLayoutManager);
        itemAdapter.setOnItemClickListener(new ItemAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, final int position) {
                if (gridLayoutManager.isScaleMode() && position == 0) {//还原默认
                    gridLayoutManager.setHighLightPosition(-1);
                    revertData();
                    gridLayoutManager.switchDefaultSpanCount();
                    notifyAdapter();
                } else if (gridLayoutManager.isScaleMode()) {//切换两个视图

//                    切换两个item之间的内容
//                    changeDataBetween(position, 0);
//                    itemAdapter.notifyItemMoved(0, position);
//                    notifyAdapter();
//                    gridLayoutManager.setHighLightPosition(position);
                    final View view1 = itemAdapter.getView(position);
                    view1.setPivotX(0);
                    view1.setPivotY(0);
                    view1.invalidate();
                    float view1X = view1.getX();
                    float view1Y = view1.getY();
                    final View view2 = itemAdapter.getView(0);
                    view2.setPivotX(0);
                    view2.setPivotY(0);
                    view2.invalidate();
                    float view2X = view2.getX();
                    float view2Y = view2.getY();
                    //位移
                    Path pathT1 = new Path();
                    pathT1.moveTo(0, 0);
                    pathT1.lineTo(view2X - view1X, view2Y - view1Y);
                    Path pathT2 = new Path();
                    pathT2.moveTo(0, 0);
                    pathT2.lineTo(view1X - view2X, view1Y - view2Y);
                    ObjectAnimator view1Translation = ObjectAnimator.ofFloat(view1, "translationX", "translationY",
                            pathT1);
                    ObjectAnimator view2Translation = ObjectAnimator.ofFloat(view2, "translationX", "translationY",
                            pathT2);
                    //缩放
                    Path pathS1 = new Path();
                    pathS1.moveTo(1, 1);
                    pathS1.lineTo((float) view2.getWidth() / view1.getWidth(), (float) view2.getHeight() / view1.getHeight());
                    Path pathS2 = new Path();
                    pathS2.moveTo(1, 1);
                    pathS2.lineTo((float) view1.getWidth() / view2.getWidth(), (float) view1.getHeight() / view2.getHeight());
//                    pathS2.lineTo(view1X / view2X, view1Y / view2Y);
                    ObjectAnimator view1Scale = ObjectAnimator.ofFloat(view1, "scaleX", "scaleY",
                            pathS1);
                    ObjectAnimator view2Scale = ObjectAnimator.ofFloat(view2, "scaleX", "scaleY",
                            pathS2);
                    AnimatorSet animate = new AnimatorSet();
                    if (unable) {
                        animate.playTogether(view1Translation, view2Translation);
                    } else {
                        animate.playTogether(view1Translation, view2Translation, view1Scale, view2Scale);
                    }
                    animate.setDuration(300);
                    animate.setInterpolator(new AccelerateInterpolator());
                    animate.start();
                    animate.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            //结束后(TODO 加了这句，第五个之后的都有问题。)
//                            itemAdapter.notifyDataSet(items,0,position);

//                            view1.setPivotX(view1.getWidth() / 2);
//                            view1.setPivotY(view1.getHeight() / 2);
//                            view2.setPivotX(view2.getWidth() / 2);
//                            view2.setPivotY(view2.getHeight() / 2);
//                            view1.invalidate();
//                            view2.invalidate();
                            //what???
                            Log.e("AAA", "1111111   x=" + view1.getX() + " y=" + view1.getY());
                            view1.post(new Runnable() {
                                @Override
                                public void run() {
                                    Log.e("AAA", "2222222   x=" + view1.getX() + " y=" + view1.getY());
                                }
                            });
                        }
                    });


                } else {//默认切换到放大视图
                    //点击后，layoutManager处理
                    gridLayoutManager.setHighLightPosition(position);
                    //改变数据源
//                    changeDataRange(position);
                    gridLayoutManager.switchSpanCount();

//                    notifyAdapter();
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
            gridLayoutManager.switchSpanCount();
            notifyAdapter();
            switchIcon(item);
            return true;
        }
        return super.onOptionsItemSelected(item);
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

    public boolean unable = false;

    public void aaa(View view) {
        unable = !unable;
    }
}

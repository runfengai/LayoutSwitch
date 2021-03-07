package com.gjiazhe.layoutswitch.view2;

import android.content.Context;
import android.content.res.TypedArray;

import androidx.transition.AutoTransition;
import androidx.transition.Transition;
import androidx.transition.TransitionManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.constraintlayout.widget.ConstraintSet;

import com.gjiazhe.layoutswitch.R;
import com.gjiazhe.layoutswitch.view2.animation.AbsConstraintStrategy;
import com.gjiazhe.layoutswitch.view2.animation.NormalConstraintStrategy;
import com.gjiazhe.layoutswitch.view2.animation.ScaleConstraintStrategy;
import com.gjiazhe.layoutswitch.view2.animation.ScaleSwitchConstraintStrategy;

import java.util.LinkedList;

/**
 * 可伸缩的gridView,九宫格
 */
public class ScalableGridView extends CommonGridView {
    private static final String TAG = "ScalableGridView";
    //
    private int normalSpanCount;
    //
    private int highlightSpanCount;
    /**
     * 高亮模式下的位置
     */
    private int highlightPosition = -1;

    /**
     * 状态
     */
    private Status status = Status.NORMAL;
    /**
     * 标记动画结束后的状态
     */
    private Status nextStatus = Status.NORMAL;
    /**
     * 动画
     */
    AutoTransition autoTransition = new AutoTransition();
    private Transition.TransitionListener transitionListener;
    private AbsConstraintStrategy scaleStrategy;
    private AbsConstraintStrategy normalStrategy;
//    private AbsConstraintStrategy switchStrategy;
    /**
     * 放大状态下逻辑上的索引
     */
    private LinkedList<Integer> logicIndexes = new LinkedList<>();

    /**
     * 三种状态：
     * 正常、动画中，放大
     */
    public enum Status {
        NORMAL, ANIMATING, SCALED
    }

    public ScalableGridView(Context context) {
        super(context);
    }

    public ScalableGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(attrs);
    }

    public ScalableGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(attrs);
    }

    /**
     * 初始化基本属性
     *
     * @param attrs
     */
    private void initView(AttributeSet attrs) {
        if (attrs == null) return;
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.ScalableGridView);
        normalSpanCount = typedArray.getInteger(R.styleable.ScalableGridView_normalSpanCount, 3);
        highlightSpanCount = typedArray.getInteger(R.styleable.ScalableGridView_highlightSpanCount, 4);
        typedArray.recycle();
        currSpanCount = normalSpanCount;
        autoTransition.setDuration(300);
        transitionListener = new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {
                status = Status.ANIMATING;
            }

            @Override
            public void onTransitionEnd(Transition transition) {
                //结束
                status = nextStatus;
            }

            @Override
            public void onTransitionCancel(Transition transition) {

            }

            @Override
            public void onTransitionPause(Transition transition) {

            }

            @Override
            public void onTransitionResume(Transition transition) {

            }
        };
        autoTransition.addListener(transitionListener);
        scaleStrategy = new ScaleConstraintStrategy(this);
        normalStrategy = new NormalConstraintStrategy(this);
//        switchStrategy = new ScaleConstraintStrategy(this);
    }

    /**
     * 添加view
     */
    @Override
    public void addAllView() {
        if (mAdapter != null) {
            int itemCount = mAdapter.getItemCount();
            if (this.getChildCount() > 0) {
                removeAllViews();
            }
            for (int i = 0; i < itemCount; i++) {
                View view = obtainView(i);
                view.setId(View.generateViewId());
                addView(view);
            }
            ConstraintSet generate = normalStrategy.generate(0);
            apply(generate);
        }
    }

    public void clearLogicIndexes() {
        logicIndexes.clear();
    }

    public void addLogicIndexes(int i) {
        logicIndexes.add(i);
    }

    /**
     * 开始放大某个指定view
     *
     * @param position 位置
     */
    public void startScalePosition(int position) {
        int itemCount = mAdapter.getItemCount();
        if (position < 0 || position > itemCount - 1) return;
        AbsConstraintStrategy constraintStrategy = null;
        //走切换到最大流程
        if (status == Status.NORMAL) {//正常状态
            Log.e(TAG, "startScalePosition status is normal scaledPosition:" + highlightPosition);
            if (highlightPosition == -1) {//走放大流程
                nextStatus = Status.SCALED;
                //记录放大索引
                this.highlightPosition = position;
                logicIndexes.remove((Integer) position);
                logicIndexes.add(0, position);
                constraintStrategy = scaleStrategy;
            } else {
                Log.e(TAG, "startScalePosition status is abnormal or scaledPosition is wrong");
            }
        } else if (status == Status.SCALED) {//走切换两个view流程
            Log.e(TAG, "startScalePosition status is SCALED  position=" + position + " highlightPosition=" + highlightPosition);
            if (position == highlightPosition) {//本尊，还原
                nextStatus = Status.NORMAL;
                this.highlightPosition = -1;
                //还原逻辑索引
                constraintStrategy = normalStrategy;
            } else {//切换两个不同的view
                nextStatus = Status.SCALED;
                replace(position, highlightPosition);
                this.highlightPosition = position;
                constraintStrategy = scaleStrategy;//这种情况，只是逻辑上的view索引改变而已，实际和scale一样
            }
        } else {//不处理
            Log.e(TAG, "startScalePosition status is animating");
        }


        if (constraintStrategy != null) {
            ConstraintSet generate = constraintStrategy.generate(position);
            apply(generate);
        }

    }

    private void replace(int position, int highlightPosition) {
        logicIndexes.set(logicIndexes.indexOf(position), highlightPosition);
        logicIndexes.set(logicIndexes.indexOf(highlightPosition), position);
    }


    public LinkedList<Integer> getLogicIndexes() {
        return logicIndexes;
    }

    private void apply(ConstraintSet set) {
        TransitionManager.beginDelayedTransition(this, autoTransition);
        set.applyTo(this);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        addAllView();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (transitionListener != null) {
            autoTransition.removeListener(transitionListener);
        }
        clearAnimation();
    }

    public void switchMode() {

    }

    public int getNormalSpanCount() {
        return normalSpanCount;
    }

    public void setNormalSpanCount(int normalSpanCount) {
        this.normalSpanCount = normalSpanCount;
    }

    public int getHighlightSpanCount() {
        return highlightSpanCount;
    }

    public void setHighlightSpanCount(int highlightSpanCount) {
        this.highlightSpanCount = highlightSpanCount;
    }

    public int getHighlightPosition() {
        return highlightPosition;
    }
}

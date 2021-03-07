package com.gjiazhe.layoutswitch.view2.animation;

import androidx.constraintlayout.widget.ConstraintSet;

import com.gjiazhe.layoutswitch.view2.ScalableGridView;

public abstract class AbsConstraintStrategy {
    protected ScalableGridView parentView;

    public AbsConstraintStrategy(ScalableGridView parentView) {
        this.parentView = parentView;
    }

    /**
     * @param position  处理的位置，放大用
     * @return
     */
    public abstract ConstraintSet generate(int position);
}

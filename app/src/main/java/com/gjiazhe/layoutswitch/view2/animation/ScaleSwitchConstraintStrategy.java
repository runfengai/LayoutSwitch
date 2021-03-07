package com.gjiazhe.layoutswitch.view2.animation;

import android.view.View;

import androidx.constraintlayout.widget.ConstraintSet;

import com.gjiazhe.layoutswitch.DensityUtil;
import com.gjiazhe.layoutswitch.view2.ScalableGridView;

/**
 * 放大状态下，两个view互换位置
 */
public class ScaleSwitchConstraintStrategy extends AbsConstraintStrategy {

    public ScaleSwitchConstraintStrategy(ScalableGridView parentView) {
        super(parentView);
    }

    @Override
    public ConstraintSet generate(int position) {
        ConstraintSet set = new ConstraintSet();
        set.clone(parentView);
        int itemCount = parentView.getChildCount();
        int highlightPosition = parentView.getHighlightPosition();
        if (highlightPosition == -1 || position == highlightPosition) {
            return set;
        }
        //
        int targetId = parentView.obtainView(position).getId();
        set.clear(targetId);
        int cachedHighlightId = parentView.obtainView(highlightPosition).getId();
        set.clear(cachedHighlightId);

        set.connect(targetId, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, 0);
        set.connect(targetId, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, 0);
        set.setHorizontalWeight(targetId, 1f);
        set.setHorizontalChainStyle(targetId, ConstraintSet.CHAIN_SPREAD);
        set.constrainHeight(targetId, DensityUtil.dp2px(parentView.getContext(), 100));



        return set;
    }
}

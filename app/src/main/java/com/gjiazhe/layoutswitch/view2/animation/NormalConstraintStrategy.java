package com.gjiazhe.layoutswitch.view2.animation;

import android.view.View;

import androidx.constraintlayout.widget.ConstraintSet;

import com.gjiazhe.layoutswitch.DensityUtil;
import com.gjiazhe.layoutswitch.view2.ScalableGridView;

/**
 * 缩小回原来形状
 */
public class NormalConstraintStrategy extends AbsConstraintStrategy {

    public NormalConstraintStrategy(ScalableGridView parentView) {
        super(parentView);
    }

    @Override
    public ConstraintSet generate(int position) {
        ConstraintSet set = new ConstraintSet();
        set.clone(parentView);
        int itemCount = parentView.getChildCount();
        parentView.clearLogicIndexes();
        for (int i = 0; i < itemCount; i++) {
            parentView.addLogicIndexes(i);
            View view = parentView.obtainView(i);
            set.constrainHeight(view.getId(), DensityUtil.dp2px(parentView.getContext(), 100));
            if (i % parentView.getNormalSpanCount() == 0) {
                set.connect(view.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, 0);
            } else {
                set.connect(view.getId(), ConstraintSet.START, parentView.obtainView(i - 1).getId(), ConstraintSet.END, 0);
            }
            if (i != 0 && (i + 1) % parentView.getNormalSpanCount() == 0) {
                set.connect(view.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END, 0);
            } else {
                set.connect(view.getId(), ConstraintSet.END, parentView.obtainView(i + 1).getId(), ConstraintSet.START, 0);
            }
            //当前行
            int column = i / parentView.getNormalSpanCount();
            //上一行的id
            if (column == 0) {
                set.connect(view.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, 0);
            } else {
                int lastColumnId = parentView.obtainView(i - parentView.getNormalSpanCount()).getId();
                set.connect(view.getId(), ConstraintSet.TOP, lastColumnId, ConstraintSet.BOTTOM, 0);
            }
            set.setHorizontalWeight(view.getId(), 1f);
            set.setHorizontalChainStyle(view.getId(), ConstraintSet.CHAIN_SPREAD);
        }
        return set;
    }
}

package com.gjiazhe.layoutswitch.view2.animation;

import android.view.View;

import androidx.constraintlayout.widget.ConstraintSet;

import com.gjiazhe.layoutswitch.DensityUtil;
import com.gjiazhe.layoutswitch.view2.ScalableGridView;

import java.util.LinkedList;

/**
 * 放大
 */
public class ScaleConstraintStrategy extends AbsConstraintStrategy {

    public ScaleConstraintStrategy(ScalableGridView parentView) {
        super(parentView);
    }

    @Override
    public ConstraintSet generate(int position) {
        ConstraintSet set = new ConstraintSet();
        set.clone(parentView);
        int itemCount = parentView.getChildCount();
        for (int i = 0; i < itemCount; i++) {
            set.clear(parentView.obtainView(i).getId());
        }
        View selected = parentView.obtainView(position);
        set.constrainHeight(selected.getId(), DensityUtil.dp2px(parentView.getContext(), 140));
        set.connect(selected.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, 0);
        set.connect(selected.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, 0);
        set.setHorizontalWeight(selected.getId(), 1f);
        //标记选中位置所在的行
        int addedCount = 0;
        //标记上一个view
        View lastView = null;
        //标记上一行首位view
        View lastColumnView = null;
        //
        LinkedList<Integer> logicIndexes = parentView.getLogicIndexes();
        if (logicIndexes == null) return set;
        int len = logicIndexes.size();
        for (int j = 0; j < len; j++) {
            int i = logicIndexes.get(j);
            if (position == i) continue;
            View view = parentView.obtainView(i);
            set.constrainHeight(view.getId(), DensityUtil.dp2px(parentView.getContext(), 100));
            //当前行
            int column = addedCount / parentView.getHighlightSpanCount();
            if (addedCount % parentView.getHighlightSpanCount() == 0) {//每行首位
                set.connect(view.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, 0);
            } else {
                set.connect(view.getId(), ConstraintSet.START, lastView.getId(), ConstraintSet.END, 0);
            }
            //判断每行最后一个
            if ((addedCount + 1) % parentView.getHighlightSpanCount() == 0) {//最后一位
                set.connect(view.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END, 0);
            } else {//
//                View nextView = parentView.obtainView(i + 1 == position ? i + 2 : i + 1);
                set.connect(view.getId(), ConstraintSet.END, parentView.obtainView(logicIndexes.get(j + 1)).getId(), ConstraintSet.START, 0);
            }
            if (column == 0) {
                set.connect(view.getId(), ConstraintSet.TOP, selected.getId(), ConstraintSet.BOTTOM, 0);
            } else {
                set.connect(view.getId(), ConstraintSet.TOP, lastColumnView.getId(), ConstraintSet.BOTTOM, 0);
            }
            set.setHorizontalWeight(view.getId(), 1f);
            set.setHorizontalChainStyle(view.getId(), ConstraintSet.CHAIN_SPREAD);
            addedCount++;
            lastView = view;
            //记录上一列的首位
            if (j % parentView.getHighlightSpanCount() == 0) {
                lastColumnView = view;
            }
        }
        return set;
    }
}

package com.gjiazhe.layoutswitch;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

public class DensityUtil {

    public static int dp2px(Context context, int dp) {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager windowMgr = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        windowMgr.getDefaultDisplay().getMetrics(dm);
        return (int) (dm.density * dp + 0.5f);

    }
}

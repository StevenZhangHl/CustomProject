package com.horen.base.util;

import android.content.Context;
import android.support.v7.widget.LinearSmoothScroller;
import android.util.DisplayMetrics;

/**
 * Author:Steven
 * Time:2018/8/9 16:07
 * Description:This isRecycleViewSmoothScroller
 */
public class RecycleViewSmoothScroller extends LinearSmoothScroller {

    public RecycleViewSmoothScroller(Context context) {
        super(context);
    }

    @Override
    protected int getVerticalSnapPreference() {
        return LinearSmoothScroller.SNAP_TO_START;
    }

    /**
     * 可以修改滚动速度，不修改可调用 super.calculateSpeedPerPixel(displayMetrics);
     *
     * @param displayMetrics
     * @return
     */
    @Override
    protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
        return 100f / displayMetrics.densityDpi;
    }
}

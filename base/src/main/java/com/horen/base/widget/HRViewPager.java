package com.horen.base.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * @author :ChenYangYi
 * @date :2018/08/03/10:54
 * @description :
 * @github :https://github.com/chenyy0708
 */
public class HRViewPager extends ViewPager {
    private boolean isCanScroll = true;

    public HRViewPager(Context context) {
        super(context);
    }

    public HRViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 设置其是否能滑动换页
     *
     * @param isCanScroll false 不能换页， true 可以滑动换页
     */
    public void setScanScroll(boolean isCanScroll) {
        this.isCanScroll = isCanScroll;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return false;

    }
}

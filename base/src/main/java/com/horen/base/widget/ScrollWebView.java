package com.horen.base.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.webkit.WebView;

/**
 * Author:Steven
 * Time:2018/8/28 9:19
 * Description:This isScrollWebView
 */
public class ScrollWebView extends WebView {

    public OnScrollChangeListener listener;

    public ScrollWebView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public ScrollWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollWebView(Context context) {
        super(context);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {

        super.onScrollChanged(l, t, oldl, oldt);

        float webcontent = getContentHeight() * getScale();// webview的高度
        float webnow = getHeight() + getScrollY();// 当前webview的高度
        Log.i("TAG1", "webview.getScrollY()====>>" + getScrollY());
        if (Math.abs(webcontent - webnow) < 1) {
            // 已经处于底端
            // Log.i("TAG1", "已经处于底端");
        } else if (getScrollY() == 0) {
            // Log.i("TAG1", "已经处于顶端");
        } else {
            listener.onScrollChanged(l, t, oldl, oldt);
        }

    }

    public void setOnScrollChangeListener(OnScrollChangeListener listener) {

        this.listener = listener;

    }

    public interface OnScrollChangeListener {
        public void onScrollChanged(int l, int t, int oldl, int oldt);
    }
}

package com.horen.base.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.horen.base.app.GlideApp;
/**
 * Author:Steven
 * Time:2018/9/25 9:05
 * Description:This isAutoLoadRecyclerView
 */
public class AutoLoadRecyclerView extends RecyclerView {
    private Context context;

    public AutoLoadRecyclerView(Context context) {
        super(context);
    }

    public AutoLoadRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public AutoLoadRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        addOnScrollListener(new AutoLoadScrollListener());
    }

    public void setOnScrollListener(Context context) {
        this.context = context;
        addOnScrollListener(new AutoLoadScrollListener());
    }

    private class AutoLoadScrollListener extends OnScrollListener {

        public AutoLoadScrollListener() {
            super();
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
        }

        //当屏幕停止滚动时为0；当屏幕滚动且用户使用的触碰或手指还在屏幕上时为1；由于用户的操作，屏幕产生惯性滑动时为2
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            switch (newState) {
                case RecyclerView.SCROLL_STATE_IDLE:
                    GlideApp.with(context).resumeRequests();
                    break;
                case RecyclerView.SCROLL_STATE_DRAGGING:
                    GlideApp.with(context).pauseRequests();
                    break;
                case RecyclerView.SCROLL_STATE_SETTLING:
                    GlideApp.with(context).pauseRequests();
                    break;
            }
        }
    }
}

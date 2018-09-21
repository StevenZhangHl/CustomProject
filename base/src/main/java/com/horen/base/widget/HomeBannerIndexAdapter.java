package com.horen.base.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.horen.base.R;
import com.horen.base.util.CUtils;
import com.horen.base.util.DisplayUtil;
import com.horen.base.util.NavigatorHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HOREN on 2017/12/15.
 * Banner 角标指示器 adapter
 * ConvenientBanner 不支持指示器定制开发，每个点距离10，固定
 * 见banner.setPageIndicator方法
 * <p>
 * 用法
 * banner.setOnPageChangeListener(new HomeBannerIndexAdapter(getContext(),banners.size(),layoutIndex));
 */
public class HomeBannerIndexAdapter implements ViewPager.OnPageChangeListener, NavigatorHelper.OnNavigatorScrollListener {
    private LinearLayout layout;
    private int size;
    private final int WIDTH_NORMAL = 11;
    private final int WIDTH_SELECT = 28;
    private Context context;
    private List<ImageView> views = new ArrayList<>();
    private NavigatorHelper navigatorHelper = new NavigatorHelper();

    /**
     * @param size   banner item 数量
     * @param layout 角标所属LinearLayout
     *               具体见platform fragment布局
     */
    public HomeBannerIndexAdapter(Context context, int size, LinearLayout layout) {
        this.context = context;
        layout.removeAllViews();
        this.layout = layout;
        this.size = size;
        navigatorHelper.setNavigatorScrollListener(this);
        navigatorHelper.setSkimOver(true);
        navigatorHelper.setTotalCount(size);
        if (size > 0) {
            for (int i = 0; i < size; i++) {//初始化
                View view = LayoutInflater.from(context).inflate(R.layout.fragment_platform_header_bannerindex, null, false);
                ImageView imageView = view.findViewById(R.id.iv_banner_index);
                if (i == 0) {
                    changedShapeColor(imageView, Color.WHITE);
                    LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) imageView.getLayoutParams();
                    params.width = DisplayUtil.dip2px(WIDTH_SELECT);
                    imageView.setLayoutParams(params);
                }
                layout.addView(view);//layout动态添加
                views.add(imageView);
            }
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        navigatorHelper.onPageScrolled(position % size, positionOffset, positionOffsetPixels);
    }

    @Override
    public void onPageSelected(int position) {
        navigatorHelper.onPageSelected(position % size);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        navigatorHelper.onPageScrollStateChanged(state);
    }

    @Override
    public void onEnter(int index, int totalCount, float enterPercent, boolean leftToRight) {
        ImageView imageView = views.get(index % size);
        CUtils.setWidgetWidth(imageView, DisplayUtil.dip2px((WIDTH_NORMAL + (WIDTH_SELECT - WIDTH_NORMAL) * enterPercent)));//逐渐变大
    }

    @Override
    public void onLeave(int index, int totalCount, float leavePercent, boolean leftToRight) {
        ImageView imageView = views.get(index % size);
        CUtils.setWidgetWidth(imageView, DisplayUtil.dip2px((WIDTH_SELECT - (WIDTH_SELECT - WIDTH_NORMAL) * leavePercent))); //逐渐变小
    }

    @Override
    public void onSelected(int index, int totalCount) {
        ImageView imageView = views.get(index % size);
        changedShapeColor(imageView, Color.WHITE);
    }

    @Override
    public void onDeselected(int index, int totalCount) {
        ImageView imageView = views.get(index % size);
        changedShapeColor(imageView, CUtils.getColor(context, R.color.alpha_55_white));
    }

    /**
     * 动画改变shape颜色
     *
     * @param view
     * @param color
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void changedShapeColor(View view, int color) {
        GradientDrawable shape = new GradientDrawable();
        shape.setCornerRadius(DisplayUtil.dip2px(5)); // 圆角
        shape.setColor(color);//设置颜色
        view.setBackground(shape);
    }
}
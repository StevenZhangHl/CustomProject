package com.horen.base.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by HOREN on 2018/1/15.
 */

public class CUtils {
    public static Drawable getDrawble(Context conetxt, @DrawableRes int id) {
        return ContextCompat.getDrawable(conetxt, id);
    }

    public static int getColor(Context conetxt, @ColorRes int id) {
        return ContextCompat.getColor(conetxt, id);
    }

    public static String getString(Context conetxt, @StringRes int id) {
        return conetxt.getResources().getString(id);
    }

    /**
     * 直接获取控件的宽、高
     *
     * @param view
     * @return int[]
     */
    public static int getViewHeight(final View view) {
        ViewTreeObserver vto2 = view.getViewTreeObserver();
        vto2.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                view.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });
        return view.getHeight();
    }

    /**
     * 直接获取控件的宽、高
     *
     * @param view
     * @return int[]
     */
    public static int getViewWidth(final View view) {
        ViewTreeObserver vto2 = view.getViewTreeObserver();
        vto2.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                view.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });
        return view.getWidth();
    }

    /**
     * 设置控件宽
     *
     * @param view
     * @param width
     */
    public static void setWidgetWidth(View view, int width) {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view.getLayoutParams();
        params.width = width;
        view.setLayoutParams(params);
    }

    /**
     * 设置控件高
     *
     * @param view
     * @param height
     */
    public static void setWidgetHeight(View view, int height) {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view.getLayoutParams();
        params.height = height;
        view.setLayoutParams(params);
    }

    public static <T extends View> T findViewById(View v, int id) {


        return (T) v.findViewById(id);
    }


    /**
     * 调用系统浏览器，打开指定url
     *
     * @param url      链接
     * @param activity 页面
     */
    public static void startUriForChrome(String url, Activity activity) {
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        activity.startActivity(intent);
    }

    /**
     * 时间格式化 年月日
     */
    public static String formatTime(String time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = format.parse(time);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }
        return format.format(date);
    }

    /**
     * 将数据保留两位小数
     */
    public static String getTwoDecimal(String num) {
        Double number = Double.valueOf(num);
        if (number == 0) return "0.00";
        DecimalFormat dFormat = new DecimalFormat("#.00");
        String yearString = dFormat.format(number);
        return yearString;
    }

}

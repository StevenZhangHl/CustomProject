package com.horen.base.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.horen.base.R;
import com.horen.base.util.DisplayUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author :ChenYangYi
 * @date :2018/08/30/12:46
 * @description :用于一行多个TextView，不同等分情况
 * @github :https://github.com/chenyy0708
 */
public class HRTabView extends LinearLayout {

    private Context mContext;

    /**
     * 字体颜色
     */
    @ColorInt
    private int mTabTextColor;
    /**
     * 显示文字集合  逗号,隔开
     */
    private String mTabTexts;
    /**
     * 每个TextView占比  逗号,隔开
     */
    private String mTabWeight;
    /**
     * 字体颜色
     */
    private int mTabTextSize;

    private List<TextView> mTextViews = new ArrayList<>();

    public HRTabView(Context context) {
        this(context, null);
        this.mContext = context;
    }

    public HRTabView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;

        setUp(attrs);
    }

    private void setUp(AttributeSet attrs) {
        TypedArray a = mContext.getTheme().obtainStyledAttributes(
                attrs, R.styleable.HRTabView, 0, 0);
        try {
            mTabTexts = a.getString(R.styleable.HRTabView_tab_strings);
            mTabWeight = a.getString(R.styleable.HRTabView_tab_weight);
            mTabTextColor = a.getColor(R.styleable.HRTabView_tab_text_color, ContextCompat.getColor(mContext, R.color.color_333));
            mTabTextSize = a.getDimensionPixelSize(R.styleable.HRTabView_tab_text_size,
                    13);
        } finally {
            a.recycle();
        }
        // 横向对齐
        setOrientation(HORIZONTAL);
        initTab(mTabTexts, mTabWeight);
    }

    /**
     * 初始化Tab
     *
     * @param mTabTexts  tab字符
     * @param mTabWeight 占比
     */
    public void initTab(String mTabTexts, String mTabWeight) {
        // 显示文字集合
        String[] textStrings = mTabTexts.split(",");
        // 占比结婚
        String[] weights = mTabWeight.split(",");
        setData(textStrings, weights);
    }

    /**
     * 设置Tab数据
     *
     * @param textStrings tab文字
     * @param weights     tab占比
     */
    public void setData(String[] textStrings, String[] weights) {
        removeAllViews();
        // 动态添加TextView
        for (int i = 0; i < textStrings.length; i++) {
            TextView textView = new TextView(mContext);
            // 设置宽为0，高为WRAP_CONTENT
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT);
            // 对齐位置
            layoutParams.gravity = Gravity.CENTER;
            // 占比
            layoutParams.weight = Float.valueOf(weights[i]);
            textView.setLayoutParams(layoutParams);
            // 字体颜色大小
            textView.setText(textStrings[i]);
            textView.setTextColor(mTabTextColor);
            textView.setTextSize(DisplayUtil.px2dip(mContext, mTabTextSize));
            textView.setGravity(Gravity.CENTER);
            // 添加到线性布局里
            addView(textView);
            mTextViews.add(textView);
        }
    }

    /**
     * 设置多个文字内容
     *
     * @param strins 显示文字
     */
    public void setTabText(String... strins) {
        String[] tabStrings = strins.clone();
        for (int i = 0; i < tabStrings.length; i++) {
            mTextViews.get(i).setText(tabStrings[i]);
        }
    }
}

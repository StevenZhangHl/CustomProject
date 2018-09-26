package com.horen.base.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.horen.base.R;
import com.horen.base.util.DisplayUtil;
import com.jaeger.library.StatusBarUtil;

/**
 * @author :ChenYangYi
 * @date :2018/07/30/15:08
 * @description : 通用Toolbar  左右标题、中间标题、左右图片设置
 * @github :https://github.com/chenyy0708
 */

public class HRToolbar extends FrameLayout {
    private Toolbar toolbar;
    private ImageView ivRight, ivLeft;
    private TextView tvTitle, tvRight, tvLeft;
    private Context mContent;
    private String title, leftTitle, rightTitle;
    /**
     * 是否是白色背景Toolbar
     */
    private boolean isWhite = false;
    /**
     * 默认2dp阴影
     */
    private int elevation = 0;
    /**
     * Toolbar背景颜色
     */
    @ColorInt
    private int backgroundColor;
    /**
     * Toolbar 左右边图片
     */
    private Drawable rightIcon, leftIcon;

    public HRToolbar(Context context) {
        this(context, null);
        this.mContent = context;
    }

    public HRToolbar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContent = context;
        setUp(attrs);
    }

    /**
     * 设置属性
     *
     * @param attrs 自定义属性
     */
    private void setUp(AttributeSet attrs) {
        // 默认背景色为主题色
        backgroundColor = ContextCompat.getColor(mContent, R.color.color_main);
        TypedArray a = mContent.getTheme().obtainStyledAttributes(
                attrs, R.styleable.HRToolbar, 0, 0);
        try {
            title = a.getString(R.styleable.HRToolbar_title);
            leftTitle = a.getString(R.styleable.HRToolbar_left_title);
            rightTitle = a.getString(R.styleable.HRToolbar_right_title);
            elevation = a.getInteger(R.styleable.HRToolbar_title_elevation, 0);
            isWhite = a.getBoolean(R.styleable.HRToolbar_is_white, false);
            backgroundColor = a.getColor(R.styleable.HRToolbar_bg_color, ContextCompat.getColor(mContent, R.color.color_main_color));
            rightIcon = a.getDrawable(R.styleable.HRToolbar_right_icon);
            leftIcon = a.getDrawable(R.styleable.HRToolbar_left_icon);
        } finally {
            a.recycle();
        }
        if (isWhite) {
            // 没有设置背景色，默认白色标题栏背景色为#F5F5F5
            if (backgroundColor == ContextCompat.getColor(mContent, R.color.color_main_color)) {
                setBackgroundColor(ContextCompat.getColor(mContent, R.color.color_f5));
            } else {
                setBackgroundColor(backgroundColor);
            }
            View.inflate(mContent, R.layout.horen_toolbar_layout_white, this);
            toolbar = findViewById(R.id.tool_bar_balck);
            tvTitle = findViewById(R.id.tool_bar_title_tv_black);
            tvRight = findViewById(R.id.right_tv_black);
            tvLeft = findViewById(R.id.left_tv_black);
            ivRight = findViewById(R.id.right_iv_black);
            ivLeft = findViewById(R.id.left_iv_black);
        } else {
            // 主题色背景
            setBackgroundColor(backgroundColor);
            View.inflate(mContent, R.layout.horen_toolbar_layout, this);
            toolbar = findViewById(R.id.tool_bar_white);
            tvTitle = findViewById(R.id.tool_bar_title_tv_white);
            tvRight = findViewById(R.id.right_tv_white);
            tvLeft = findViewById(R.id.left_tv_white);
            ivRight = findViewById(R.id.right_iv_white);
            ivLeft = findViewById(R.id.left_iv_white);
        }
        if (rightIcon != null) {
            ivRight.setVisibility(VISIBLE);
            ivRight.setImageDrawable(rightIcon);
        }
        if (leftIcon != null) {
            ivLeft.setVisibility(VISIBLE);
            ivLeft.setImageDrawable(leftIcon);
        }
        tvTitle.setText(title);
        // 设置右标题
        if (!TextUtils.isEmpty(rightTitle)) {
            setRightText(rightTitle);
        }
        // 设置左标题
        if (!TextUtils.isEmpty(leftTitle)) {
            setLeftText(leftTitle);
        }
        // 添加分割线
        setToolbarElevation(elevation);
    }

    /**
     * 获取Toolbar
     *
     * @return
     */
    public Toolbar getToolbar() {
        return toolbar;
    }

    /**
     * 设置高度阴影
     *
     * @param elevation 阴影
     */
    public void setToolbarElevation(int elevation) {
        // 高于5.0版本系统
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setElevation(0);
        }
        if (elevation != 0) {
            // 不使用阴影，统一使用线，白色背景使用 #F5F5F5颜色分割线,其他颜色使用#F1F1F1分割线
            if (backgroundColor == ContextCompat.getColor(mContent, R.color.white)) {
                View diver = new View(mContent);
                diver.setBackgroundResource(R.color.color_f5);
                LayoutParams lpDriver = new LayoutParams(LayoutParams.MATCH_PARENT, DisplayUtil.dip2px(mContent, 1));
                lpDriver.gravity = Gravity.BOTTOM;
                addView(diver, lpDriver);
            } else {
                View diver = new View(mContent);
                diver.setBackgroundResource(R.color.color_f1);
                LayoutParams lpDriver = new LayoutParams(LayoutParams.MATCH_PARENT, DisplayUtil.dip2px(mContent, 1));
                lpDriver.gravity = Gravity.BOTTOM;
                addView(diver, lpDriver);
            }
        }
    }

    /**
     * Toolbar标题
     *
     * @param string 标题
     */
    public void setTitle(String string) {
        tvTitle.setText(string);
    }

    /**
     * 用于BaseActivity中统一Toolbar设置右标题
     * 布局使用可以使用属性right_title
     *
     * @param rightText 标题名
     */
    public void setRightText(String rightText) {
        tvRight.setVisibility(VISIBLE);
        tvRight.setText(rightText);
    }

    /**
     * 用于BaseActivity中统一Toolbar设置左标题
     * 布局使用可以使用属性left_title
     *
     * @param leftTitle 标题名
     */
    public void setLeftText(String leftTitle) {
        tvLeft.setVisibility(VISIBLE);
        tvLeft.setText(leftTitle);
    }

    /**
     * 隐藏右标题
     *
     * @param visible 是否隐藏
     */
    public void setTvRightVisible(boolean visible) {
        tvRight.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    /**
     * 设置右图片ID
     *
     * @param id 图片id
     */
    public void setRightImageRes(@DrawableRes int id) {
        ivRight.setVisibility(View.VISIBLE);
        ivRight.setImageResource(id);
    }


    /**
     * 右图片点击事件
     *
     * @param listener 点击
     */
    public void setOnRightImgListener(OnClickListener listener) {
        ivRight.setOnClickListener(listener);
    }

    /**
     * 中间标题的点击事件
     * @param listener
     */
    public void setOnTitleListener(OnClickListener listener) {
        tvTitle.setOnClickListener(listener);
    }

    /**
     * 右标题点击事件
     *
     * @param listener 点击
     */
    public void setOnRightTextListener(OnClickListener listener) {
        tvRight.setOnClickListener(listener);
    }

    /**
     * 初始化Toolbar
     */
    public void bindActivity(final AppCompatActivity activity) {
        initToolbar(activity);
    }

    /**
     * 白色状态栏
     */
    public void bindActivityForWhite(final AppCompatActivity activity) {
        bindActivity(activity, R.color.white);
    }

    /**
     * 设置白色或者灰色状态栏
     */
    public void bindActivity(final AppCompatActivity activity, @ColorRes int statusColor) {
        // 版本大于6.0 改变状态栏文字颜色
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            StatusBarUtil.setColor(activity, ContextCompat.getColor(activity, statusColor), 0);
        } else {
            StatusBarUtil.setColor(activity, ContextCompat.getColor(activity, R.color.black), 0);
        }
        initToolbar(activity);
    }

    /**
     * 初始化Toolbar
     */
    private void initToolbar(final AppCompatActivity activity) {
        activity.setSupportActionBar(toolbar);
        // 开启返回键
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // 隐藏标题
        activity.getSupportActionBar().setDisplayShowTitleEnabled(false);
        // 返回退出
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.onBackPressed();
            }
        });
    }

}

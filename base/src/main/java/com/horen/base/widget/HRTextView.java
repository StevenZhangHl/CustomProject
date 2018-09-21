package com.horen.base.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.horen.base.R;

/**
 * @author :ChenYangYi
 * @date :2018/08/30/15:08
 * @description : 适用于左右都有文字，介绍  库存量:  20 (控制两个文字之间的宽度，增加中间文字:)
 * @github :https://github.com/chenyy0708
 */
public class HRTextView extends AppCompatTextView {

    private Context mContext;
    /**
     * 左边文字
     */
    private String mLeftText;
    /**
     * 是否追加冒号
     */
    private boolean isAddColon = true;
    /**
     * 冒号，占位符
     */
    private String colonText = ": ";

    public HRTextView(Context context) {
        this(context, null);
    }

    public HRTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        setUp(attrs);
    }

    private void setUp(AttributeSet attrs) {
        TypedArray a = mContext.getTheme().obtainStyledAttributes(
                attrs, R.styleable.HRTextView, 0, 0);
        try {
            mLeftText = a.getString(R.styleable.HRTextView_left_text);
            isAddColon = a.getBoolean(R.styleable.HRTextView_is_add_colon, true);
        } finally {
            a.recycle();
        }
        // AS预览使用
        if (isInEditMode()) {
            setText(getText());
        }
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        super.setText(getBuilder().append(text.toString()).toString(), type);
    }

    private StringBuilder getBuilder() {
        StringBuilder builder = new StringBuilder();
        builder.append(mLeftText);
        if (isAddColon) {
            builder.append(colonText);
        }
        return builder;
    }

}

package com.horen.base.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextPaint;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.horen.base.R;

/**
 * Created by HOREN on 2018/2/4.
 */
public class PinEntryEditText extends AppCompatEditText implements
        VerificationAction, TextWatcher {
    private int mFigures;//需要输入的位数
    private int mVerCodeMargin;//验证码之间的间距
    private int mBottomSelectedColor;//底部选中的颜色
    private int mBottomNormalColor;//未选中的颜色
    private float mBottomLineHeight;//底线的高度
    private int mSelectedBackgroundColor;//选中的背景颜色

    private OnVerificationCodeChangedListener onCodeChangedListener;
    private int mCurrentPosition = 0;
    private int mEachRectLength = 0;//每个矩形的边长
    private Paint mSelectedBackgroundPaint;
    private Paint mNormalBackgroundPaint;
    private Paint mBottomSelectedPaint;
    private Paint mBottomNormalPaint;

    private Paint mCursorPaint;
//    private Shader shader;
//    private Matrix matrix;
    public PinEntryEditText(Context context) {
        this(context, null);
    }

    public PinEntryEditText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PinEntryEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(attrs);
        setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent));//防止出现下划线
        initPaint();
        setFocusableInTouchMode(true);
        super.addTextChangedListener(this);
    }

    /**
     * 初始化paint
     */
    private void initPaint() {
        mSelectedBackgroundPaint = new Paint();
        mSelectedBackgroundPaint.setColor(mSelectedBackgroundColor);
        mNormalBackgroundPaint = new Paint();
        mNormalBackgroundPaint.setColor(getColor(android.R.color.transparent));

        mBottomSelectedPaint = new Paint();
        mBottomNormalPaint = new Paint();
        mBottomSelectedPaint.setColor(mBottomSelectedColor);
        mBottomNormalPaint.setColor(mBottomNormalColor);
        mBottomSelectedPaint.setStrokeWidth(mBottomLineHeight);
        mBottomNormalPaint.setStrokeWidth(mBottomLineHeight);

        mCursorPaint = new Paint();
        mCursorPaint.setColor(mBottomSelectedColor);
        mCursorPaint.setStrokeWidth(mBottomLineHeight);
        mCursorPaint.setAntiAlias(true);
        mCursorPaint.setDither(true);
    }

    /**
     * 初始化Attrs
     */
    private void initAttrs(AttributeSet attrs) {
        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.VerCodeEditText);
        mFigures = ta.getInteger(R.styleable.VerCodeEditText_figures, 4);
        mVerCodeMargin = (int) ta.getDimension(R.styleable.VerCodeEditText_verCodeMargin, 0);
        mBottomSelectedColor = ta.getColor(R.styleable.VerCodeEditText_bottomLineSelectedColor,
                getCurrentTextColor());
        mBottomNormalColor = ta.getColor(R.styleable.VerCodeEditText_bottomLineNormalColor,
                getColor(android.R.color.darker_gray));
        mBottomLineHeight = ta.getDimension(R.styleable.VerCodeEditText_bottomLineHeight,
                dp2px(5));
        mSelectedBackgroundColor = ta.getColor(R.styleable.VerCodeEditText_selectedBackgroundColor,
                getColor(android.R.color.transparent));
        ta.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthResult = 0, heightResult = 0;
        //最终的宽度
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        if (widthMode == MeasureSpec.EXACTLY) {
            widthResult = widthSize;
        } else {
            widthResult = getScreenWidth(getContext());
        }
        //每个矩形形的宽度
        mEachRectLength = (widthResult - (mVerCodeMargin * (mFigures - 1))) / mFigures;
        //最终的高度
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        if (heightMode == MeasureSpec.EXACTLY) {
            heightResult = heightSize;
        } else {
            heightResult = mEachRectLength;
        }
        setMeasuredDimension(widthResult, heightResult);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            requestFocus();
            setSelection(getText().length());
            showKeyBoard(getContext());
            return false;
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mCurrentPosition = getText().length();
        int width = mEachRectLength - getPaddingLeft() - getPaddingRight();
        int height = getMeasuredHeight() - getPaddingTop() - getPaddingBottom();
        for (int i = 0; i < mFigures; i++) {
            canvas.save();
            int start = width * i + i * mVerCodeMargin;
            int end = width + start;
            //画一个矩形
            if (i == mCurrentPosition) {//选中的下一个状态
                canvas.drawRect(start, 0, end, height, mSelectedBackgroundPaint);
            } else {
                canvas.drawRect(start, 0, end, height, mNormalBackgroundPaint);
            }
            canvas.restore();
        }
        //绘制文字
        String value = getText().toString();
        for (int i = 0; i < value.length(); i++) {
            canvas.save();
            int start = width * i + i * mVerCodeMargin;
            float x = start + width / 2;
            TextPaint paint = getPaint();
            paint.setTextAlign(Paint.Align.CENTER);
            paint.setColor(getCurrentTextColor());
            Paint.FontMetrics fontMetrics = paint.getFontMetrics();
            float baseline = (height - fontMetrics.bottom + fontMetrics.top) / 2
                    - fontMetrics.top;
            canvas.drawText(String.valueOf(value.charAt(i)), x, baseline, paint);
            canvas.restore();
        }
        //绘制底线
        for (int i = 0; i < mFigures; i++) {
            canvas.save();
            float lineY = height - mBottomLineHeight / 2;
            int start = width * i + i * mVerCodeMargin;
            int end = width + start;

            float startC = width * i + width/2 + i * mVerCodeMargin;

            if (i == mCurrentPosition) {
                canvas.drawLine(start, lineY, end, lineY, mBottomSelectedPaint);

                if (mCursorPaint.getColor() == getColor(R.color.color_main_color)){
                    mCursorPaint.setColor(getColor(android.R.color.transparent));
                } else {
                    mCursorPaint.setColor(getColor(R.color.color_main_color));
                }//模拟光标效果，设置颜色变换
                //在当前正在输入验证码上绘制一个光标
                canvas.drawLine(startC, lineY/2-20, startC, lineY/2+20, mCursorPaint);

                //在当前正在输入验证码上绘制一个圆点
                //canvas.drawCircle(startC,lineY/2,5,mCursorPaint);
            } else {
                canvas.drawLine(start, lineY, end, lineY, mBottomNormalPaint);
            }
            canvas.restore();
        }
        requestFocus();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        mCurrentPosition = getText().length();
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        mCurrentPosition = getText().length();
        if (onCodeChangedListener != null) {
            onCodeChangedListener.onVerCodeChanged(getText(), start, before, count);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
//        matrix = new Matrix();
//        shader = new LinearGradient(0, 0, getMeasuredWidth(), 0, Color.RED, Color.WHITE, Shader.TileMode.CLAMP);
    }

    @Override
    public void afterTextChanged(Editable s) {
        mCurrentPosition = getText().length();
        if (getText().length() == mFigures) {
            if (onCodeChangedListener != null) {
                onCodeChangedListener.onInputCompleted(getText());
            }
        } else if (getText().length() > mFigures) {
            getText().delete(mFigures, getText().length());
        }
    }

    @Override
    public void setFigures(int figures) {
        mFigures = figures;
    }

    @Override
    public void setVerCodeMargin(int margin) {
        mVerCodeMargin = margin;
    }

    @Override
    public void setBottomSelectedColor(@ColorRes int bottomSelectedColor) {
        mBottomSelectedColor = getColor(bottomSelectedColor);
    }

    @Override
    public void setBottomNormalColor(@ColorRes int bottomNormalColor) {
        mBottomSelectedColor = getColor(bottomNormalColor);
    }

    @Override
    public void setSelectedBackgroundColor(@ColorRes int selectedBackground) {
        mSelectedBackgroundColor = getColor(selectedBackground);
    }

    @Override
    public void setBottomLineHeight(int bottomLineHeight) {
        this.mBottomLineHeight = bottomLineHeight;
    }

    @Override
    public void setOnVerificationCodeChangedListener(OnVerificationCodeChangedListener listener) {
        this.onCodeChangedListener = listener;
    }

    /**
     * 返回颜色
     */
    private int getColor(@ColorRes int color) {
        return ContextCompat.getColor(getContext(), color);
    }

    /**
     * dp转px
     */
    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }

    /**
     * 获取手机屏幕的宽度
     */
    static int getScreenWidth(Context context) {
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(metrics);
        return metrics.widthPixels;
    }
    public void sss(){
        requestFocus();
        setSelection(getText().length());
        showKeyBoard(getContext());
    }
    public void showKeyBoard(Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(this, InputMethodManager.SHOW_FORCED);
    }
}
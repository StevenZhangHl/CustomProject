package com.horen.base.widget;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import com.horen.base.R;
import com.xw.repo.XEditText;

/**
 * author  :ChenYangYi
 * time    :2018/2/4
 * desc    :密码输入框 （删除图标 + 显示隐藏密码图标）
 */

public class PWEditText extends AppCompatEditText {


    private String mSeparator; //mSeparator，default is "".
    private boolean disableClear; // disable clear drawable.
    private Drawable mClearDrawable;
    private Drawable mTogglePwdDrawable;
    private boolean disableEmoji; // disable emoji and some special symbol input.
    private int mShowPwdResId;
    private int mHidePwdResId;

    private XEditText.OnXTextChangeListener mXTextChangeListener;
    private TextWatcher mTextWatcher;
    private int mPreLength;
    private boolean hasFocused;
    private int[] pattern; // pattern to separate. e.g.: mSeparator = "-", pattern = [3,4,4] -> xxx-xxxx-xxxx
    private int[] intervals; // indexes of separators.
    /* When you set pattern, it will automatically compute the max length of characters and separators,
     so you don't need to set 'maxLength' attr in your xml any more(it won't work).*/
    private int mMaxLength = Integer.MAX_VALUE;
    private boolean hasNoSeparator; // true, the same as EditText.
    private boolean isPwdInputType;
    private boolean isPwdShow;
    private Bitmap mBitmap;
    // 增大删除隐藏密码按钮的点击范围
    private int dp10 = dp2px(10);

    public PWEditText(Context context) {
        this(context, null);
    }

    public PWEditText(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.editTextStyle); // Attention here !
    }

    public PWEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initAttrs(context, attrs, defStyleAttr);

        if (disableEmoji) {
            setFilters(new InputFilter[]{new PWEditText.EmojiExcludeFilter()});
        }
        mTextWatcher = new PWEditText.MyTextWatcher();
        this.addTextChangedListener(mTextWatcher);

        setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                hasFocused = hasFocus;
                markerFocusChangeLogic();
            }
        });
    }

    private void initAttrs(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray a = context.obtainStyledAttributes(attrs, com.xw.repo.R.styleable.XEditText, defStyleAttr, 0);

        mSeparator = a.getString(com.xw.repo.R.styleable.XEditText_x_separator);
        if (mSeparator == null) {
            mSeparator = "";
        }
        if (mSeparator.length() > 0) {
            int inputType = getInputType();
            if (inputType == 2 || inputType == 8194 || inputType == 4098) { // if inputType is number, it can't insert mSeparator.
                setInputType(InputType.TYPE_CLASS_PHONE);
            }
        }

        disableClear = a.getBoolean(com.xw.repo.R.styleable.XEditText_x_disableClear, false);
        boolean disableTogglePwdDrawable = a.getBoolean(com.xw.repo.R.styleable.XEditText_x_disableTogglePwdDrawable, false);

        if (!disableClear) {
            int cdId = a.getResourceId(com.xw.repo.R.styleable.XEditText_x_clearDrawable, -1);
            if (cdId == -1)
                cdId = R.drawable.ic_clear;
            mClearDrawable = AppCompatResources.getDrawable(context, cdId);
            if (mClearDrawable != null) {
                mClearDrawable.setBounds(0, 0, mClearDrawable.getIntrinsicWidth(),
                        mClearDrawable.getIntrinsicHeight());
                if (cdId == R.drawable.ic_clear)
                    DrawableCompat.setTint(mClearDrawable, getCurrentHintTextColor());
            }
        }

        int inputType = getInputType();
        if (!disableTogglePwdDrawable && (inputType == 129 || inputType == 145 || inputType == 18 || inputType == 225)) {
            isPwdInputType = true;
            isPwdShow = inputType == 145;
            mMaxLength = 20;

            mShowPwdResId = a.getResourceId(com.xw.repo.R.styleable.XEditText_x_showPwdDrawable, -1);
            mHidePwdResId = a.getResourceId(com.xw.repo.R.styleable.XEditText_x_hidePwdDrawable, -1);
            if (mShowPwdResId == -1)
                mShowPwdResId = com.xw.repo.R.drawable.x_et_svg_ic_show_password_24dp;
            if (mHidePwdResId == -1)
                mHidePwdResId = com.xw.repo.R.drawable.x_et_svg_ic_hide_password_24dp;

            int tId = isPwdShow ? mShowPwdResId : mHidePwdResId;
            mTogglePwdDrawable = ContextCompat.getDrawable(context, tId);
            if (mShowPwdResId == com.xw.repo.R.drawable.x_et_svg_ic_show_password_24dp ||
                    mHidePwdResId == com.xw.repo.R.drawable.x_et_svg_ic_hide_password_24dp) {
                DrawableCompat.setTint(mTogglePwdDrawable, getCurrentHintTextColor());
            }
            mTogglePwdDrawable.setBounds(0, 0, mTogglePwdDrawable.getIntrinsicWidth(),
                    mTogglePwdDrawable.getIntrinsicHeight());

            int cdId = a.getResourceId(com.xw.repo.R.styleable.XEditText_x_clearDrawable, -1);
            if (cdId == -1)
                cdId = com.xw.repo.R.drawable.x_et_svg_ic_clear_24dp;
            if (!disableClear) {
                mBitmap = getBitmapFromVectorDrawable(context, cdId,
                        cdId == com.xw.repo.R.drawable.x_et_svg_ic_clear_24dp); // clearDrawable
            }
        }

        disableEmoji = a.getBoolean(com.xw.repo.R.styleable.XEditText_x_disableEmoji, false);

        a.recycle();
    }

    private Bitmap getBitmapFromVectorDrawable(Context context, int drawableId, boolean tint) {
        Drawable drawable = AppCompatResources.getDrawable(context, drawableId);
        if (drawable == null)
            return null;

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            drawable = (DrawableCompat.wrap(drawable)).mutate();
        }
        if (tint)
            DrawableCompat.setTint(drawable, getCurrentHintTextColor());

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (hasFocused && mBitmap != null && isPwdInputType && !isTextEmpty()) {
            int left = getMeasuredWidth() - getPaddingRight() -
                    mTogglePwdDrawable.getIntrinsicWidth() - mBitmap.getWidth() - dp2px(25); // dp2px(20)删除图标距离密码图标距离
            int top = (getMeasuredHeight() - mBitmap.getHeight()) >> 1;
            canvas.drawBitmap(mBitmap, left, top, null);
        }
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            performClick();
        }

        if (hasFocused && isPwdInputType && event.getAction() == MotionEvent.ACTION_UP) {
            int w = mTogglePwdDrawable.getIntrinsicWidth();
            int h = mTogglePwdDrawable.getIntrinsicHeight();
            int top = (getMeasuredHeight() - h) >> 1;
            int right = getMeasuredWidth() - getPaddingRight();
            boolean isAreaX = event.getX() <= right + dp10 && event.getX() >= right - w - dp10;
            boolean isAreaY = event.getY() >= top - dp10 && event.getY() <= top + h + dp10;
//            boolean isAreaX = event.getX() <= right  && event.getX() >= right - w ;
//            boolean isAreaY = event.getY() >= top  && event.getY() <= top + h;
            if (isAreaX && isAreaY) {
                isPwdShow = !isPwdShow;
                if (isPwdShow) {
                    setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                setSelection(getSelectionStart(), getSelectionEnd());
                mTogglePwdDrawable = ContextCompat.getDrawable(getContext(), isPwdShow ?
                        mShowPwdResId : mHidePwdResId);
                if (mShowPwdResId == com.xw.repo.R.drawable.x_et_svg_ic_show_password_24dp ||
                        mHidePwdResId == com.xw.repo.R.drawable.x_et_svg_ic_hide_password_24dp) {
                    DrawableCompat.setTint(mTogglePwdDrawable, getCurrentHintTextColor());
                }
                mTogglePwdDrawable.setBounds(0, 0, mTogglePwdDrawable.getIntrinsicWidth(),
                        mTogglePwdDrawable.getIntrinsicHeight());

                setCompoundDrawables(getCompoundDrawables()[0], getCompoundDrawables()[1],
                        mTogglePwdDrawable, getCompoundDrawables()[3]);

                invalidate();
            }

            if (!disableClear) {
                // 减dp10 为了扩大点击范围  dp25是两个图标之间的距离
                right -= w + dp2px(25) - dp2px(5);
                isAreaX = event.getX() <= right && event.getX() >= right - mBitmap.getWidth() - dp2px(15);
                if (isAreaX && isAreaY) {
                    setError(null);
                    setText("");
                }
            }
        }

        if (hasFocused && !disableClear && !isPwdInputType && event.getAction() == MotionEvent.ACTION_UP) {
            Rect rect = mClearDrawable.getBounds();
            int rectW = rect.width();
            int rectH = rect.height();
            int top = (getMeasuredHeight() - rectH) >> 1;
            int right = getMeasuredWidth() - getPaddingRight();
            float eventX = event.getX();
            boolean isAreaX = eventX <= right + dp10 && event.getX() >= right - dp10 - rectW;  // dp10 为增大点击的范围大小
            boolean isAreaY = event.getY() >= top - dp10 && event.getY() <= (top + rectH) + dp10;
            if (isAreaX && isAreaY) {
                setError(null);
                setText("");
            }
        }

        return super.onTouchEvent(event);
    }

    @Override
    public boolean onTextContextMenuItem(int id) {
        ClipboardManager clipboardManager = (ClipboardManager) getContext()
                .getSystemService(Context.CLIPBOARD_SERVICE);
        if (clipboardManager != null) {
            if (id == 16908320 || id == 16908321) { // catch CUT or COPY ops
                super.onTextContextMenuItem(id);

                ClipData clip = clipboardManager.getPrimaryClip();
                ClipData.Item item = clip.getItemAt(0);
                if (item != null && item.getText() != null) {
                    String s = item.getText().toString().replace(mSeparator, "");
                    clipboardManager.setPrimaryClip(ClipData.newPlainText(null, s));

                    return true;
                }
            } else if (id == 16908322) { // catch PASTE ops
                ClipData clip = clipboardManager.getPrimaryClip();
                ClipData.Item item = clip.getItemAt(0);
                if (item != null && item.getText() != null) {
                    setTextToSeparate((getText().toString() + item.getText().toString()).replace(mSeparator, ""));

                    return true;
                }
            }
        }

        return super.onTextContextMenuItem(id);
    }

    // =========================== MyTextWatcher ================================
    private class MyTextWatcher implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            mPreLength = s.length();
            if (mXTextChangeListener != null) {
                mXTextChangeListener.beforeTextChanged(s, start, count, after);
            }
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (mXTextChangeListener != null) {
                mXTextChangeListener.onTextChanged(s, start, before, count);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
            if (mXTextChangeListener != null) {
                mXTextChangeListener.afterTextChanged(s);
            }

            int currLength = s.length();
            if (hasNoSeparator) {
                mMaxLength = currLength;
            }

            markerFocusChangeLogic();

            if (currLength > mMaxLength) {
                getText().delete(currLength - 1, currLength);
                return;
            }
            if (pattern == null) {
                return;
            }

            for (int i = 0; i < pattern.length; i++) {
                if (currLength - 1 == intervals[i]) {
                    if (currLength > mPreLength) { // inputting
                        if (currLength < mMaxLength) {
                            removeTextChangedListener(mTextWatcher);
                            getText().insert(currLength - 1, mSeparator);
                        }
                    } else if (mPreLength <= mMaxLength) { // deleting
                        removeTextChangedListener(mTextWatcher);
                        getText().delete(currLength - 1, currLength);
                    }

                    addTextChangedListener(mTextWatcher);

                    break;
                }
            }
        }
    }

    private void markerFocusChangeLogic() {
        if (!hasFocused || (isTextEmpty() && !isPwdInputType)) {
            setCompoundDrawables(getCompoundDrawables()[0], getCompoundDrawables()[1],
                    null, getCompoundDrawables()[3]);

            if (!isTextEmpty() && isPwdInputType) {
                invalidate();
            }
        } else {
            if (isPwdInputType) {
                if (mShowPwdResId == com.xw.repo.R.drawable.x_et_svg_ic_show_password_24dp ||
                        mHidePwdResId == com.xw.repo.R.drawable.x_et_svg_ic_hide_password_24dp) {
                    DrawableCompat.setTint(mTogglePwdDrawable, getCurrentHintTextColor());
                }
                setCompoundDrawables(getCompoundDrawables()[0], getCompoundDrawables()[1],
                        mTogglePwdDrawable, getCompoundDrawables()[3]);
            } else if (!isTextEmpty() && !disableClear) {
                setCompoundDrawables(getCompoundDrawables()[0], getCompoundDrawables()[1],
                        mClearDrawable, getCompoundDrawables()[3]);
            }
        }
    }

    private boolean isTextEmpty() {
        return getText().toString().trim().length() == 0;
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                Resources.getSystem().getDisplayMetrics());
    }

    /**
     * set customize separator
     */
    public void setSeparator(@NonNull String separator) {
        this.mSeparator = separator;

        if (mSeparator.length() > 0) {
            int inputType = getInputType();
            if (inputType == 2 || inputType == 8194 || inputType == 4098) { // if inputType is number, it can't insert mSeparator.
                setInputType(InputType.TYPE_CLASS_PHONE);
            }
        }
    }

    /**
     * set customize pattern
     *
     * @param pattern   e.g. pattern:{4,4,4,4}, separator:"-" to xxxx-xxxx-xxxx-xxxx
     * @param separator separator
     */
    public void setPattern(@NonNull int[] pattern, @NonNull String separator) {
        setSeparator(separator);
        setPattern(pattern);
    }

    /**
     * set customize pattern
     *
     * @param pattern e.g. pattern:{4,4,4,4}, separator:"-" to xxxx-xxxx-xxxx-xxxx
     */
    public void setPattern(@NonNull int[] pattern) {
        this.pattern = pattern;

        intervals = new int[pattern.length];
        int count = 0;
        int sum = 0;
        for (int i = 0; i < pattern.length; i++) {
            sum += pattern[i];
            intervals[i] = sum + count;
            if (i < pattern.length - 1) {
                count += mSeparator.length();
            }
        }
        mMaxLength = intervals[intervals.length - 1];

        InputFilter[] filters = new InputFilter[1];
        filters[0] = new InputFilter.LengthFilter(mMaxLength);
        setFilters(filters);
    }

    /**
     * set CharSequence to separate
     */
    public void setTextToSeparate(@NonNull CharSequence c) {
        if (c.length() == 0) {
            return;
        }

        setText("");
        for (int i = 0; i < c.length(); i++) {
            append(c.subSequence(i, i + 1));
        }
    }

    /**
     * Get text without separators.
     * <p>
     * Deprecated, use {@link #getTrimmedString()} instead.
     */
    @Deprecated
    public String getNonSeparatorText() {
        return getText().toString().replaceAll(mSeparator, "");
    }

    /**
     * Get text String having been trimmed.
     */
    public String getTrimmedString() {
        if (hasNoSeparator) {
            return getText().toString().trim();
        } else {
            return getText().toString().replaceAll(mSeparator, "").trim();
        }
    }

    /**
     * @return has separator or not
     */
    public boolean hasNoSeparator() {
        return hasNoSeparator;
    }

    /**
     * @param hasNoSeparator true, has no separator, the same as EditText
     */
    public void setHasNoSeparator(boolean hasNoSeparator) {
        this.hasNoSeparator = hasNoSeparator;
        if (hasNoSeparator) {
            mSeparator = "";
        }
    }

    /**
     * set true to disable Emoji and special symbol
     *
     * @param disableEmoji true: disable emoji;
     *                     false: enable emoji
     */
    public void setDisableEmoji(boolean disableEmoji) {
        this.disableEmoji = disableEmoji;
        if (disableEmoji) {
            setFilters(new InputFilter[]{new PWEditText.EmojiExcludeFilter()});
        } else {
            setFilters(new InputFilter[0]);
        }
    }

    /**
     * the same as EditText.addOnTextChangeListener(TextWatcher textWatcher)
     */
    public void setOnXTextChangeListener(XEditText.OnXTextChangeListener listener) {
        this.mXTextChangeListener = listener;
    }

    public interface OnXTextChangeListener {

        void beforeTextChanged(CharSequence s, int start, int count, int after);

        void onTextChanged(CharSequence s, int start, int before, int count);

        void afterTextChanged(Editable s);
    }

    @Override
    public Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable("save_instance", super.onSaveInstanceState());
        bundle.putString("separator", mSeparator);
        bundle.putIntArray("pattern", pattern);
        bundle.putBoolean("hasNoSeparator", hasNoSeparator);

        return bundle;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            mSeparator = bundle.getString("separator");
            pattern = bundle.getIntArray("pattern");
            hasNoSeparator = bundle.getBoolean("hasNoSeparator");

            if (pattern != null) {
                setPattern(pattern);
            }
            super.onRestoreInstanceState(bundle.getParcelable("save_instance"));

            return;
        }

        super.onRestoreInstanceState(state);
    }

    /**
     * disable emoji and special symbol input
     */
    private class EmojiExcludeFilter implements InputFilter {

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            for (int i = start; i < end; i++) {
                int type = Character.getType(source.charAt(i));
                if (type == Character.SURROGATE || type == Character.OTHER_SYMBOL) {
                    return "";
                }
            }
            return null;
        }
    }
}

package com.horen.base.util;

import android.os.CountDownTimer;
import android.widget.TextView;


/**
 * TimeCountUtil
 */
public class TimeCountUtil extends CountDownTimer {

    private TextView mButton;
    private OnTimeOutListener listener;

    public TimeCountUtil(TextView button, long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
        this.mButton = button;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        // 按钮不可用
        mButton.setEnabled(false);
        String showText = millisUntilFinished / 1000 + "s";
        mButton.setText(showText);
    }

    public void setOnTimeOutListener(OnTimeOutListener listener) {
        this.listener = listener;
    }

    @Override
    public void onFinish() {
        // 按钮设置可用
        mButton.setEnabled(true);
        mButton.setText("获取验证码");
        if (listener != null) {
            listener.timeOut();
        }
    }

    public interface OnTimeOutListener {
        void timeOut();
    }
}
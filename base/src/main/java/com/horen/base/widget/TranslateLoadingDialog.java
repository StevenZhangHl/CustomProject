package com.horen.base.widget;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.horen.base.R;

/**
 * Author:Steven
 * Time:2018/8/28 16:22
 * Description:This isTranslateLoadingDialog
 */
public class TranslateLoadingDialog {
    /**
     * 加载数据对话框
     */
    private Dialog mLoadingDialog;

    /**
     * 显示加载对话框
     *
     * @param context    上下文
     * @param cancelable 对话框是否可以取消
     */
    public Dialog showDialogForLoading(Context context, boolean cancelable) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_loading, null);
        TextView loadingText = (TextView) view.findViewById(R.id.id_tv_loading_dialog_text);
        loadingText.setVisibility(View.INVISIBLE);
        mLoadingDialog = new Dialog(context, R.style.CustomProgressDialog);
        mLoadingDialog.setCancelable(cancelable);
        mLoadingDialog.setCanceledOnTouchOutside(false);
        mLoadingDialog.setContentView(view, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));

        //这些是设置对话框大小，位置，下面activity的亮度
        WindowManager.LayoutParams params = mLoadingDialog.getWindow()
                .getAttributes();
        // 这个是设置activity的亮度的dimAmount在0.0f和1.0f之间，0.0f完全不暗，即背景是可见的 ，1.0f时候，背景全部变黑暗。
        params.dimAmount = 0.0f;
        // 设置对话框的布局参数为居中
        mLoadingDialog.getWindow().setAttributes(params);
        mLoadingDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        mLoadingDialog.show();
        return mLoadingDialog;
    }

    /**
     * 关闭加载对话框
     */
    public void cancelDialogForLoading() {
        if (mLoadingDialog != null) {
            mLoadingDialog.cancel();
        }
    }
}

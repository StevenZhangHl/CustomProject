package com.horen.base.callback;

import android.content.Context;
import android.view.View;

import com.horen.base.R;
import com.kingja.loadsir.callback.Callback;

/**
 * @author :ChenYangYi
 * @date :2018/09/07/10:15
 * @description :无数据页面
 * @github :https://github.com/chenyy0708
 */
public class EmptyCallBack extends Callback {
    @Override
    protected int onCreateView() {
        return R.layout.widget_empty_page;
    }

    @Override
    protected boolean onReloadEvent(Context context, View view) {
        return true;
    }
}

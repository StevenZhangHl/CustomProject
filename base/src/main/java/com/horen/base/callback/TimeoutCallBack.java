package com.horen.base.callback;

import android.content.Context;
import android.view.View;

import com.horen.base.R;
import com.kingja.loadsir.callback.Callback;

/**
 * @author :ChenYangYi
 * @date :2018/09/07/10:15
 * @description :服务器超时
 * @github :https://github.com/chenyy0708
 */
public class TimeoutCallBack extends Callback {
    @Override
    protected int onCreateView() {
        return R.layout.widget_nonetwork_page;
    }

    @Override
    protected boolean onReloadEvent(Context context, View view) {
        return true;
    }

    @Override
    protected void onViewCreate(Context context, View view) {
        // 再试一次
        view.findViewById(R.id.no_network_reload_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onReloadListener.onReload(view);
            }
        });
    }
}

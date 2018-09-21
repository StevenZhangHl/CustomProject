package com.horen.base.callback;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.horen.base.R;
import com.kingja.loadsir.callback.Callback;

import org.simple.eventbus.Subscriber;

/**
 * @author :ChenYangYi
 * @date :2018/09/07/10:15
 * @description :无网络页面
 * @github :https://github.com/chenyy0708
 */
public class NoNetCallBack extends Callback {
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
        super.onViewCreate(context, view);
        // 网络无法连接
        TextView textView = view.findViewById(R.id.no_network_text);
        ImageView image = view.findViewById(R.id.no_network_img);
        textView.setText(context.getString(R.string.cont_connection_network));
        // 再试一次
        view.findViewById(R.id.no_network_reload_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onReloadListener.onReload(view);
            }

        });
        image.setImageResource(R.drawable.icon_no_net_work);
    }
}

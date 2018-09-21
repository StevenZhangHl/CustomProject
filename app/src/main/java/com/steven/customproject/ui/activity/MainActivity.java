package com.steven.customproject.ui.activity;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.horen.base.base.BaseActivity;
import com.horen.base.constant.ARouterPath;
import com.steven.customproject.R;

@Route(path = ARouterPath.HOST_MAIN_ACTIVITY)
public class MainActivity extends BaseActivity {

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView(Bundle savedInstanceState) {

    }
}

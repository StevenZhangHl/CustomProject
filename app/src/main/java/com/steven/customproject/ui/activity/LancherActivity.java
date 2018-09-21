package com.steven.customproject.ui.activity;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.horen.base.base.BaseActivity;
import com.horen.base.constant.ARouterPath;
import com.horen.base.rx.RxHelper;
import com.horen.base.util.AnimationUtils;
import com.horen.base.util.DisplayUtil;
import com.steven.customproject.R;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;


public class LancherActivity extends BaseActivity {
    private int duration = 2000;
    private ImageView iv_spash;

    @Override
    public int getLayoutId() {
        return R.layout.activity_lancher;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        removeStatusBar();
        int width = DisplayUtil.getScreenWidth(this);
        int height = DisplayUtil.getScreenHeight(this);
        iv_spash = (ImageView) findViewById(R.id.iv_spash);
        ScaleAnimation animation = new ScaleAnimation(1.0f, 1.8f, 1.0f, 1.8f, width / 2f, height / 2f);
        animation.setDuration(duration);
        animation.setFillAfter(true);
        animation.setInterpolator(new LinearInterpolator());
        animation.setRepeatCount(1);
        iv_spash.startAnimation(animation);
        mRxManager.add(Observable.timer(duration, TimeUnit.MILLISECONDS).compose(RxHelper.<Long>applySchedulers())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        ARouter.getInstance().build(ARouterPath.LOVESNOW_MAIN_ACTIVITY).navigation();
                        finish();
                    }
                }));
    }
}

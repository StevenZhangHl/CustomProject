package com.steven.snowhome.ui.fragment;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.horen.base.base.BaseFragment;
import com.horen.base.widget.HRToolbar;
import com.steven.lovesnow.R;

/**
 * Author:Steven
 * Time:2018/9/21 13:19
 * Description:This isSnowHomeFragment
 */
public class SnowHomeFragment extends BaseFragment {
    private ImageView lovesnow_view;
    private TextView tv_love_wife;
    private TextView tv_wife_hard;
    private HRToolbar tool_bar;

    @Override
    public int getLayoutId() {
        return R.layout.snow_fragment_snow_home;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        tool_bar = (HRToolbar) rootView.findViewById(R.id.tool_bar);
        lovesnow_view = (ImageView) rootView.findViewById(R.id.lovesnow_view);
        tv_love_wife = (TextView) rootView.findViewById(R.id.tv_love_wife);
        tv_wife_hard = (TextView) rootView.findViewById(R.id.tv_wife_hard);
        initToolbar(tool_bar.getToolbar());
        tool_bar.setTitle("爱老婆");
        tool_bar.getToolbar().setNavigationIcon(null);
        YoYo.with(Techniques.ZoomIn)
                .repeat(1000)
                .duration(2000)
                .repeatMode(ValueAnimator.REVERSE)
                .playOn(lovesnow_view);
        YoYo.with(Techniques.BounceIn)
                .repeat(1000)
                .duration(2000)
                .repeatMode(ValueAnimator.REVERSE)
                .playOn(tv_love_wife);
        YoYo.with(Techniques.FadeInLeft)
                .repeat(1000)
                .duration(2000)
                .repeatMode(ValueAnimator.REVERSE)
                .playOn(tv_wife_hard);
    }
}

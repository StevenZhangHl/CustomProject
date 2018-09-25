package com.steven.snowhome.ui.fragment;

import android.graphics.drawable.Animatable;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.horen.base.base.BaseFragment;
import com.horen.base.widget.HRToolbar;
import com.steven.lovesnow.R;

/**
 * Author:Steven
 * Time:2018/9/21 13:35
 * Description:This isMeFragment
 */
public class MeFragment extends BaseFragment implements View.OnClickListener {
    private HRToolbar tool_bar;
    private ImageView iv_love_snow;
    private ImageView image_heart;
    private Button bt_start_anim;

    public static MeFragment newInstance(String title) {
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        MeFragment meFragment = new MeFragment();
        meFragment.setArguments(bundle);
        return meFragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.snow_fragment_me;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        tool_bar = (HRToolbar) rootView.findViewById(R.id.tool_bar);
        iv_love_snow = (ImageView) rootView.findViewById(R.id.iv_love_snow);
        image_heart = (ImageView) rootView.findViewById(R.id.image_heart);
        bt_start_anim = (Button) rootView.findViewById(R.id.bt_start_anim);
        initToolbar(tool_bar.getToolbar());
        tool_bar.setTitle("宠老婆");
        tool_bar.getToolbar().setNavigationIcon(null);
        bt_start_anim.setOnClickListener(this);
        startAnim();
    }

    @Override
    public void onClick(View view) {
        if (view == bt_start_anim) {
            startAnim();
        }
    }

    private void startAnim() {
        ((Animatable) iv_love_snow.getDrawable()).start();
        ((Animatable) image_heart.getDrawable()).start();
    }
}

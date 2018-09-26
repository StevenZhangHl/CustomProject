package com.steven.snowhome.ui.fragment;

import android.graphics.drawable.Animatable;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.horen.base.base.BaseFragment;
import com.horen.base.widget.HRToolbar;
import com.scwang.smartrefresh.header.BezierCircleHeader;
import com.scwang.smartrefresh.header.DeliveryHeader;
import com.scwang.smartrefresh.header.DropboxHeader;
import com.scwang.smartrefresh.header.FlyRefreshHeader;
import com.scwang.smartrefresh.header.FunGameBattleCityHeader;
import com.scwang.smartrefresh.header.PhoenixHeader;
import com.scwang.smartrefresh.header.StoreHouseHeader;
import com.scwang.smartrefresh.header.TaurusHeader;
import com.scwang.smartrefresh.header.WaterDropHeader;
import com.scwang.smartrefresh.header.WaveSwipeHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.steven.lovesnow.R;

/**
 * Author:Steven
 * Time:2018/9/21 13:35
 * Description:This isMeFragment
 */
public class MeFragment extends BaseFragment implements View.OnClickListener, OnRefreshListener {
    private ImageView iv_love_snow;
    private ImageView image_heart;
    private SmartRefreshLayout refresh_me;

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
        iv_love_snow = (ImageView) rootView.findViewById(R.id.iv_love_snow);
        image_heart = (ImageView) rootView.findViewById(R.id.image_heart);
        refresh_me = (SmartRefreshLayout) rootView.findViewById(R.id.refresh_me);
        startAnim();
        initRefresh();
    }

    private void initRefresh() {
        refresh_me.setEnableLoadMore(false);
        refresh_me.setRefreshHeader(new PhoenixHeader(_mActivity));
        refresh_me.setPrimaryColorsId(R.color.color_main_color);
        refresh_me.setOnRefreshListener(this);
    }

    @Override
    public void onClick(View view) {

    }

    private void startAnim() {
        ((Animatable) iv_love_snow.getDrawable()).start();
        ((Animatable) image_heart.getDrawable()).start();
    }

    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        startAnim();
        refreshLayout.finishRefresh();
    }
}

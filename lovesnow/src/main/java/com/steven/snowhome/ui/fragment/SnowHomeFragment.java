package com.steven.snowhome.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.horen.base.base.BaseFragment;
import com.horen.base.widget.HRToolbar;
import com.horen.base.widget.HomeBannerIndexAdapter;
import com.kevin.delegationadapter.DelegationAdapter;
import com.scwang.smartrefresh.header.BezierCircleHeader;
import com.scwang.smartrefresh.header.FlyRefreshHeader;
import com.scwang.smartrefresh.header.TaurusHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.steven.lovesnow.R;
import com.steven.snowhome.adapter.HomeAdapter;
import com.steven.snowhome.adapter.HomeBannerAdapter;
import com.steven.snowhome.adapter.HomeTagAdapter;
import com.steven.snowhome.bean.SnowHomeBean;
import com.steven.snowhome.mvp.contract.SnowHomeContract;
import com.steven.snowhome.mvp.presenter.SnowHomePresenter;

import java.util.List;

/**
 * Author:Steven
 * Time:2018/9/21 13:19
 * Description:This isSnowHomeFragment
 */
public class SnowHomeFragment extends BaseFragment<SnowHomePresenter, SnowHomeContract.Model> implements SnowHomeContract.View, OnRefreshListener {
    private HRToolbar tool_bar;
    private ConvenientBanner banner_home;
    private RecyclerView recyclerview_home_photos;
    private SmartRefreshLayout refresh_home;
    private LinearLayout layoutIndex;
    private HomeAdapter homeAdapter;
    private int autoTurningTime = 4000;

    @Override
    public int getLayoutId() {
        return R.layout.snow_fragment_snow_home;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        tool_bar = (HRToolbar) rootView.findViewById(R.id.tool_bar);
        initToolbar(tool_bar.getToolbar());
        tool_bar.setTitle("爱老婆");
        tool_bar.getToolbar().setNavigationIcon(null);
        banner_home = (ConvenientBanner) rootView.findViewById(R.id.banner_home);
        recyclerview_home_photos = (RecyclerView) rootView.findViewById(R.id.recyclerview_home_photos);
        refresh_home = (SmartRefreshLayout) rootView.findViewById(R.id.refresh_home);
        layoutIndex = (LinearLayout) rootView.findViewById(R.id.ll_home_banner_index);
        initRecyclerView();
        initRefreshLayout();
    }

    private LinearLayoutManager linearLayoutManager;

    private void initRecyclerView() {
        linearLayoutManager = new LinearLayoutManager(_mActivity);
        linearLayoutManager.setSmoothScrollbarEnabled(true);
        recyclerview_home_photos.setLayoutManager(linearLayoutManager);
        homeAdapter = new HomeAdapter(_mActivity);
        recyclerview_home_photos.setAdapter(homeAdapter);
        recyclerview_home_photos.setHasFixedSize(true);
        recyclerview_home_photos.setNestedScrollingEnabled(false);
    }

    private void initRefreshLayout() {
        refresh_home.setRefreshHeader(new TaurusHeader(_mActivity));
        refresh_home.setPrimaryColorsId(R.color.color_main_color);
        refresh_home.setEnableLoadMore(false);
        refresh_home.setOnRefreshListener(this);
        refresh_home.autoRefresh();
    }

    @Override
    public void setBannerData(List<SnowHomeBean.Banner> bannerData) {
        banner_home.setPages(new CBViewHolderCreator() {
            @Override
            public Object createHolder() {
                return new HomeBannerAdapter();
            }
        }, bannerData);
        banner_home.startTurning(autoTurningTime);
        banner_home.setOnPageChangeListener(new HomeBannerIndexAdapter(getContext(), bannerData.size(), layoutIndex));
    }

    @Override
    public void setHomeArticlesData(List<SnowHomeBean.Article> articlesData) {
        homeAdapter.setmData(articlesData);
    }

    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        mPresenter.getHomeData();
        refreshLayout.finishRefresh();
    }
}

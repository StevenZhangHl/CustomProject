package com.steven.snowhome.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.horen.base.base.BaseFragment;
import com.horen.base.ui.BigImagePagerActivity;
import com.horen.base.util.RecycleViewSmoothScroller;
import com.horen.base.widget.AutoLoadRecyclerView;
import com.horen.base.widget.HRToolbar;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.steven.lovesnow.R;
import com.steven.snowhome.adapter.WifePhotosAdapter;
import com.steven.snowhome.ui.activity.BigImageActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Author:Steven
 * Time:2018/9/21 13:35
 * Description:This isWifeFragment
 */
public class WifeFragment extends BaseFragment implements OnRefreshLoadMoreListener {
    private HRToolbar tool_bar;
    private AutoLoadRecyclerView recyclerview_photos;
    private WifePhotosAdapter photosAdapter;
    private SmartRefreshLayout refresh_photos;
    private List<Integer> serverImages = new ArrayList<>();
    private StaggeredGridLayoutManager staggeredGridLayoutManager;

    public static WifeFragment newInstance(String title) {
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        WifeFragment wifeFragment = new WifeFragment();
        wifeFragment.setArguments(bundle);
        return wifeFragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.snow_fragment_wife;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        tool_bar = (HRToolbar) rootView.findViewById(R.id.tool_bar);
        recyclerview_photos = (AutoLoadRecyclerView) rootView.findViewById(R.id.recyclerview_photos);
        refresh_photos = (SmartRefreshLayout) rootView.findViewById(R.id.refresh_photos);
        initToolbar(tool_bar.getToolbar());
        tool_bar.setTitle("疼老婆");
        tool_bar.getToolbar().setNavigationIcon(null);
        tool_bar.setOnTitleListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (serverImages.size()!=0){
                    scrollToTop();
                }
            }
        });
        initRecyclerView();
        initRefreshView();
    }

    /**
     * 视图滚动到顶部
     */
    private void scrollToTop() {
        RecyclerView.SmoothScroller smoothScroller = new RecycleViewSmoothScroller(_mActivity);
        smoothScroller.setTargetPosition(0);
        staggeredGridLayoutManager.startSmoothScroll(smoothScroller);
    }

    private void initRefreshView() {
        refresh_photos.setEnableLoadMoreWhenContentNotFull(false);
        refresh_photos.setOnRefreshLoadMoreListener(this);
        refresh_photos.autoRefresh();
    }


    private void initRecyclerView() {
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerview_photos.setHasFixedSize(true);
        recyclerview_photos.setLayoutManager(staggeredGridLayoutManager);
        photosAdapter = new WifePhotosAdapter(R.layout.snow_item_wife_photos, new ArrayList<Integer>());
        recyclerview_photos.setAdapter(photosAdapter);
        recyclerview_photos.setOnScrollListener(_mActivity);
        recyclerview_photos.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                int itemViewId = view.getId();
                ImageView iv_wife = (ImageView) view.findViewById(R.id.iv_wife_photo);
                if (itemViewId == R.id.iv_wife_photo) {
                    BigImageActivity.startImageForResouceId(_mActivity, serverImages.get(position), iv_wife);
                }
            }
        });
    }

    private void getData() {
        int imageId = R.mipmap.icon_photo_item;
        List<Integer> imageIds = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            imageIds.add(imageId);
        }
        photosAdapter.setItemHeights(imageIds.size());
        photosAdapter.setNewData(imageIds);
        serverImages.addAll(imageIds);
    }

    private int pageNum = 0;

    private void loadMoreData() {
        int imageId = R.mipmap.icon_photo_item;
        List<Integer> imageIds = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            imageIds.add(imageId);
        }
        photosAdapter.setItemHeights(imageIds.size());
        photosAdapter.addData(imageIds);
        serverImages.addAll(imageIds);
    }

    @Override
    public void onLoadMore(RefreshLayout refreshLayout) {
        pageNum++;
        loadMoreData();
        if (pageNum == 2) {
            refreshLayout.finishLoadMoreWithNoMoreData();
            return;
        }
        refreshLayout.finishLoadMore();
    }

    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        serverImages.clear();
        refresh_photos.setNoMoreData(false);
        pageNum = 0;
        getData();
        refreshLayout.finishRefresh();
    }
}

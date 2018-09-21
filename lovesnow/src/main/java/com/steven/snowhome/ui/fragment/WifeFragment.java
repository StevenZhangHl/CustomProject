package com.steven.snowhome.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.horen.base.base.BaseFragment;
import com.horen.base.widget.HRToolbar;
import com.steven.lovesnow.R;
import com.steven.snowhome.adapter.WifePhotosAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Author:Steven
 * Time:2018/9/21 13:35
 * Description:This isWifeFragment
 */
public class WifeFragment extends BaseFragment {
    private HRToolbar tool_bar;
    private RecyclerView recyclerview_photos;
    private WifePhotosAdapter photosAdapter;

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
        recyclerview_photos = (RecyclerView) rootView.findViewById(R.id.recyclerview_photos);
        initToolbar(tool_bar.getToolbar());
        tool_bar.setTitle("疼老婆");
        tool_bar.getToolbar().setNavigationIcon(null);
        initRecyclerView();
    }

    private void initRecyclerView() {
        recyclerview_photos.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        photosAdapter = new WifePhotosAdapter(R.layout.snow_item_wife_photos, new ArrayList<Integer>());
        recyclerview_photos.setAdapter(photosAdapter);
        getData();
    }

    private void getData() {
        int imageId = R.mipmap.icon_photo_item;
        List<Integer> imageIds = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            imageIds.add(imageId);
        }
        photosAdapter.setNewData(imageIds);
    }
}

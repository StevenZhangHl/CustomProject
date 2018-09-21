package com.steven.snowhome.ui.fragment;

import android.os.Bundle;

import com.horen.base.base.BaseFragment;
import com.horen.base.widget.HRToolbar;
import com.steven.lovesnow.R;

/**
 * Author:Steven
 * Time:2018/9/21 13:35
 * Description:This isWifeFragment
 */
public class WifeFragment extends BaseFragment {
    private HRToolbar tool_bar;
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
        initToolbar(tool_bar.getToolbar());
        tool_bar.setTitle("疼老婆");
        tool_bar.getToolbar().setNavigationIcon(null);
    }
}

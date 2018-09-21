package com.horen.base.base;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.horen.base.R;
import com.horen.base.listener.OnPageChangerLitener;
import com.horen.base.widget.HRToolbar;

import java.util.ArrayList;
import java.util.List;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * @author ChenYangYi
 * @date 2018/8/2
 * Tablayout + Viewpager + Fragment页面
 */
public abstract class BaseTabVPFragment extends BaseFragment {

    protected HRToolbar toolbar;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;


    private String[] mTitles;
    private List<SupportFragment> mFragments = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.fragment_base_tab_vp;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        toolbar = rootView.findViewById(R.id.tool_bar);
        toolbar.setVisibility(View.GONE);
        mTabLayout = rootView.findViewById(R.id.tl_hundred_order);
        mViewPager = rootView.findViewById(R.id.vp_hundred_order);
        mTitles = getTitles();
        mFragments = getFragments();
        initTabAndViewPager();
    }

    private void initTabAndViewPager() {
        mViewPager.setOffscreenPageLimit(mTitles.length);
        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.setAdapter(new BaseFragmentAdapter(getChildFragmentManager(), mFragments, mTitles));
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                onPageChangerLitener.getCuttentPosition(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * 标题集合
     *
     * @return TabLayout标题
     */
    protected abstract String[] getTitles();

    /**
     * fragment集合
     *
     * @return
     */
    protected abstract List<SupportFragment> getFragments();

    /**
     * 选择器的监听事件
     */
    private OnPageChangerLitener onPageChangerLitener;

    public void setOnPageChangerLitener(OnPageChangerLitener litener) {
        this.onPageChangerLitener = litener;
    }
}

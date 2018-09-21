package com.horen.base.base;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.horen.base.R;
import com.horen.base.widget.HRToolbar;

import java.util.ArrayList;
import java.util.List;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * @author ChenYangYi
 * @date 2018/8/2
 * Tablayout + Viewpager + Fragment页面
 */
public abstract class BaseTabVPActivity extends BaseActivity {

    private HRToolbar toolbar;
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
        toolbar = findViewById(R.id.tool_bar);
        mTabLayout = findViewById(R.id.tl_hundred_order);
        mViewPager = findViewById(R.id.vp_hundred_order);
        toolbar.setTitle(title());
        mTitles = getTitles();
        mFragments = getFragments();
        initTabAndViewPager();
    }

    private void initTabAndViewPager() {
        initToolbar(toolbar.getToolbar(), false);
        mViewPager.setOffscreenPageLimit(mTitles.length);
        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.setAdapter(new BaseFragmentAdapter(getSupportFragmentManager(), mFragments, mTitles));
    }

    /**
     * Title
     *
     * @return 页面title
     */
    protected abstract String title();

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
}

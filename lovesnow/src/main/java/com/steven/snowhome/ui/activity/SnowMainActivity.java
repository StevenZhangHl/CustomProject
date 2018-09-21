package com.steven.snowhome.ui.activity;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.horen.base.app.BaseApp;
import com.horen.base.base.AppManager;
import com.horen.base.bean.TabEntity;
import com.horen.base.constant.ARouterPath;
import com.horen.base.constant.MsgEvent;
import com.horen.base.util.ToastUitl;
import com.steven.lovesnow.R;
import com.steven.snowhome.ui.fragment.MeFragment;
import com.steven.snowhome.ui.fragment.SnowHomeFragment;
import com.steven.snowhome.ui.fragment.WifeFragment;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.util.ArrayList;

import me.yokeyword.fragmentation.SupportActivity;
import me.yokeyword.fragmentation.SupportFragment;

@Route(path = ARouterPath.LOVESNOW_MAIN_ACTIVITY)
public class SnowMainActivity extends SupportActivity {
    /**
     * 导航栏数据集合
     */
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    /**
     * 导航栏标题数据源
     */
    private String[] mTabTitles = BaseApp.getAppResources().getStringArray(R.array.parter_tab_items);
    /**
     * 未选中时显示的图片集合
     */
    private int[] mIconUnselectIds = new int[mTabTitles.length];
    /**
     * 选中时显示的图片集合
     */
    private int[] mIconSelectIds = new int[mTabTitles.length];
    private TypedArray unselectArray = BaseApp.getAppResources().obtainTypedArray(R.array.parter_tab_icon_unselect);
    private TypedArray selectArray = BaseApp.getAppResources().obtainTypedArray(R.array.parter_tab_icon_select);

    /**
     * fragment集合
     */
    private SupportFragment[] mFragments = new SupportFragment[mTabTitles.length];
    public static final int FIRST = 0;
    public static final int SECOND = 1;
    public static final int THIRD = 2;
    /**
     * 当前选中的位置
     */
    private int currentPosition = FIRST;
    /**
     * 我爱
     */
    private SnowHomeFragment snowHomeFragment;
    /**
     * 老婆
     */
    private WifeFragment wifeFragment;
    /**
     * 大人
     */
    private MeFragment meFragment;

    /**
     * 底部导航栏
     */
    private CommonTabLayout common_tab_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.snow_activity_snow_main);
        init();
        if (savedInstanceState == null) {
            snowHomeFragment = new SnowHomeFragment();
            wifeFragment = WifeFragment.newInstance(mTabTitles[SECOND]);
            meFragment = MeFragment.newInstance(mTabTitles[THIRD]);
            setmFragmentsData();
            loadMultipleRootFragment(R.id.ll_fragment_container, FIRST, mFragments);
        } else {
            snowHomeFragment = findFragment(SnowHomeFragment.class);
            wifeFragment = findFragment(WifeFragment.class);
            meFragment = findFragment(MeFragment.class);
            setmFragmentsData();
        }
        AppManager.getAppManager().addActivity(this);
    }
    /**
     * 设置mFragments数据
     */
    private void setmFragmentsData() {
        mFragments[FIRST] = snowHomeFragment;
        mFragments[SECOND] = wifeFragment;
        mFragments[THIRD] = meFragment;
    }

    public void init() {
        common_tab_layout = findViewById(R.id.common_tab_layout);
        initTabData();
        initTabLayout();
        EventBus.getDefault().register(this);
    }

    /**
     * 转换图片资源数据
     */
    private void initTabData() {
        for (int i = 0; i < unselectArray.length(); i++) {
            mIconUnselectIds[i] = unselectArray.getResourceId(i, 0);
        }
        for (int j = 0; j < selectArray.length(); j++) {
            mIconSelectIds[j] = selectArray.getResourceId(j, 0);
        }
    }

    private void initTabLayout() {
        for (int i = 0; i < mTabTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTabTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }
        common_tab_layout.setTabData(mTabEntities);
        common_tab_layout.setCurrentTab(FIRST);
        common_tab_layout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                switch (position) {
                    case 0:
                        currentPosition = FIRST;
                        break;
                    case 1:
                        currentPosition = SECOND;
                        break;
                    case 2:
                        currentPosition = THIRD;
                        break;
                }
                showHideFragment(mFragments[position]);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
    }

    @Subscriber
    public void onRecieveEvent(MsgEvent event) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        AppManager.getAppManager().removeActivity(this);
    }

    private long clickTime;

    @Override
    public void onBackPressedSupport() {
        long currentTime = System.currentTimeMillis();
        long time = 2000;
        if ((currentTime - clickTime) > time) {
            ToastUitl.showShort(R.string.hint_exit);
            clickTime = System.currentTimeMillis();
        } else {
            super.onBackPressedSupport();
        }
    }
}

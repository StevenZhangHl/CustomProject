package com.horen.base.app;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.multidex.MultiDexApplication;

import com.alibaba.android.arouter.launcher.ARouter;
import com.baidu.mapapi.SDKInitializer;
import com.horen.base.BuildConfig;
import com.horen.base.R;
import com.horen.base.callback.EmptyCallBack;
import com.horen.base.callback.NoNetCallBack;
import com.horen.base.callback.TimeoutCallBack;
import com.horen.base.net.Url;
import com.horen.base.util.CircularAnim;
import com.horen.base.util.LogUtils;
import com.horen.base.widget.CortpFooter;
import com.horen.base.widget.CortpHeader;
import com.kingja.loadsir.callback.SuccessCallback;
import com.kingja.loadsir.core.LoadSir;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;

import me.jessyan.retrofiturlmanager.RetrofitUrlManager;

/**
 * @author ChenYangYi
 * @date 2018/7/31
 * Application
 */
public class BaseApp extends MultiDexApplication {

    private static BaseApp baseApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        baseApplication = this;
        // 动画时间
        CircularAnim.init(350, 200, 0);
        ZXingLibrary.initDisplayOpinion(this);
        ARouter.openLog();
        ARouter.openDebug();
        ARouter.init(this);
        LogUtils.init(BuildConfig.LOG_DEBUG);
        SDKInitializer.initialize(getApplicationContext());
        RetrofitUrlManager.getInstance().putDomain(Url.UPLOAD, Url.UPLOAD_URL);
        RetrofitUrlManager.getInstance().putDomain(Url.BASE_PALTFORM, Url.BASE_PALTFORM_URL);
        initRefreshLayout();
        initLoadingLayout();
//        // 性能卡顿监测
//        BlockCanary.install(this, new AppContext()).start();
//        if (LeakCanary.isInAnalyzerProcess(this)) {
//            return;
//        }
//        // 内存泄露监测
//        LeakCanary.install(this);
    }

    /**
     * 多状态布局初始化
     */
    private void initLoadingLayout() {
        LoadSir.beginBuilder()
                .addCallback(new EmptyCallBack())//添加各种状态页
                .addCallback(new TimeoutCallBack())
                .addCallback(new NoNetCallBack())
                .setDefaultCallback(SuccessCallback.class)//设置默认状态页
                .commit();
    }

    /**
     * 设置默认的刷新头部和脚部
     */
    private void initRefreshLayout() {
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @NonNull
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                // 自动上啦加载
                layout.setEnableAutoLoadMore(false);
                // 上啦加载完成滚动到内容
                layout.setEnableScrollContentWhenLoaded(false);
                return new CortpHeader(context);
            }
        });
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @NonNull
            @Override
            public RefreshFooter createRefreshFooter(@NonNull Context context, @NonNull RefreshLayout layout) {
                // 自动上啦加载
                layout.setEnableAutoLoadMore(false);
                // 上啦加载完成滚动到内容
                layout.setEnableScrollContentWhenLoaded(false);
                return new CortpFooter(context);
            }
        });
    }

    public static Context getAppContext() {
        return baseApplication;
    }

    public static Resources getAppResources() {
        return baseApplication.getResources();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

}

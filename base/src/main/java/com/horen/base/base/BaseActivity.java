package com.horen.base.base;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.horen.base.R;
import com.horen.base.app.BaseApp;
import com.horen.base.callback.EmptyCallBack;
import com.horen.base.callback.NoNetCallBack;
import com.horen.base.callback.TimeoutCallBack;
import com.horen.base.mvp.BaseModel;
import com.horen.base.mvp.BasePresenter;
import com.horen.base.mvp.BaseView;
import com.horen.base.rx.RxManager;
import com.horen.base.util.NetWorkUtils;
import com.horen.base.util.TUtil;
import com.horen.base.util.ToastUitl;
import com.horen.base.widget.HRToolbar;
import com.jaeger.library.StatusBarUtil;
import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.callback.SuccessCallback;
import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;
import com.kingja.loadsir.core.Transport;

import org.simple.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author ChenYangYi
 * @date 2018/7/31
 * BaseActivity
 */
public abstract class BaseActivity<T extends BasePresenter, E extends BaseModel> extends AppCompatActivity implements BaseView, Callback.OnReloadListener {
    public T mPresenter;
    public E mModel;
    public Context mContext;
    public RxManager mRxManager;
    Unbinder unbinder;

    private FrameLayout flBaseContent;
    private HRToolbar tl_white;
    private HRToolbar tl;
    private LinearLayout llBaseLayout;
    protected LoadService mBaseLoadService;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRxManager = new RxManager();
        doBeforeSetContentView();
        getDelegate().setContentView(R.layout.activity_base_content);
        initFindViewById();
        setContentView(getLayoutId());
        // 注册对象
        EventBus.getDefault().register(this);
        unbinder = ButterKnife.bind(this);
        ButterKnife.bind(this);
        mContext = this;
        mPresenter = TUtil.getT(this, 0);
        mModel = TUtil.getT(this, 1);
        if (mPresenter != null) {
            mPresenter.mContext = this;
        }
        // 是否检查网络连接
        if (isCheckNetWork()) {
            isNetConnected();
        }
        this.initPresenter();
        this.initView(savedInstanceState);
    }

    @Override
    public void setContentView(int layoutResID) {
        flBaseContent.removeAllViews();
        getLayoutInflater().inflate(layoutResID, flBaseContent, true);
    }

    private void initFindViewById() {
        tl_white = findViewById(R.id.tl_white);
        tl = findViewById(R.id.tl);
        flBaseContent = findViewById(R.id.fl_base_content);
        llBaseLayout = findViewById(R.id.ll_base_layout);
        setFitsSystemWindows();
        // 多状态管理类
        if (!isCustomLoadingLayout()) {
            mBaseLoadService = LoadSir.getDefault().register(flBaseContent, this);
        }
    }

    /**
     * 返回当前正在显示的标题栏
     *
     * @return
     */
    public HRToolbar getTitleBar() {
        return tl_white.getVisibility() == View.VISIBLE ? tl_white : tl;
    }

    /**
     * 适配4.4系统toolbar显示不全
     */
    protected void setFitsSystemWindows() {
        llBaseLayout.setFitsSystemWindows(true);
    }

    /**
     * 主题色标题栏
     *
     * @param title 标题
     */
    public void showTitle(String title) {
        tl.setVisibility(View.VISIBLE);
        tl_white.setVisibility(View.GONE);
        initToolbar(tl.getToolbar(), false);
        tl.setTitle(title);
    }

    /**
     * 白色标题栏
     *
     * @param title 标题
     */
    public void showWhiteTitle(String title) {
        tl.setVisibility(View.GONE);
        tl_white.setVisibility(View.VISIBLE);
        initToolbar(tl_white.getToolbar(), true);
        tl_white.setTitle(title);
    }

    /**
     * 白色标题栏
     *
     * @param title       标题
     * @param statusColor 状态栏颜色
     */
    public void showWhiteTitle(String title, @ColorRes int statusColor) {
        tl.setVisibility(View.GONE);
        tl_white.setVisibility(View.VISIBLE);
        initToolbar(tl_white.getToolbar(), true, statusColor);
        tl_white.setTitle(title);
        tl_white.setBackgroundResource(statusColor);
    }

    /**
     * 白色背景toolbar
     *
     * @param toolbar
     * @param isWhite 是否白色状态栏
     */
    protected void initToolbar(@NonNull Toolbar toolbar, boolean isWhite) {
        // 白色状态栏
        if (isWhite) {
            setWhiteStatusBar(R.color.color_f5);
        }
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    /**
     * 自定义颜色状态栏toolbar
     *
     * @param toolbar
     * @param isWhite 是否白色状态栏
     */
    protected void initToolbar(@NonNull Toolbar toolbar, boolean isWhite, @ColorRes int statusColor) {
        // 白色状态栏
        if (isWhite) {
            setWhiteStatusBar(statusColor);
        }
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    /**
     * 白色状态栏
     * 6.0以上状态栏修改为白色，状态栏字体为黑色
     * 6.0以下状态栏为黑色
     */
    protected void setWhiteStatusBar(@ColorRes int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            StatusBarUtil.setColor(this, BaseApp.getAppResources().getColor(color), 0);
        } else {
            StatusBarUtil.setColor(this, getResources().getColor(R.color.black), 0);
        }
    }

    /**
     * 设置layout前配置
     */
    private void doBeforeSetContentView() {
        // 把activity放到application栈中管理
        AppManager.getAppManager().addActivity(this);
        // 无标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 设置竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setStatusBar();
    }

    /**
     * 沉浸式状态栏
     */
    protected void setStatusBar() {
        StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.color_main), 0);
    }

    /**
     * 获取布局文件
     *
     * @return 布局Id
     */
    @LayoutRes
    public abstract int getLayoutId();

    /**
     * 简单页面无需mvp就不用管此方法即可,完美兼容各种实际场景的变通
     */
    public abstract void initPresenter();

    /**
     * 初始化view
     *
     * @param savedInstanceState 保存数据
     */
    public abstract void initView(Bundle savedInstanceState);

    /**
     * 通过Class跳转界面
     **/
    public void startActivity(Class<?> cls) {
        startActivity(cls, null);
    }

    /**
     * 通过Class跳转界面
     **/
    public void startActivityForResult(Class<?> cls, int requestCode) {
        startActivityForResult(cls, null, requestCode);
    }

    /**
     * 含有Bundle通过Class跳转界面
     **/
    public void startActivityForResult(Class<?> cls, Bundle bundle,
                                       int requestCode) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    /**
     * 含有Bundle通过Class跳转界面
     **/
    public void startActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.onDestroy();
        }
        mRxManager.clear();
        unbinder.unbind();
        AppManager.getAppManager().finishActivity(this);
    }

    /**
     * 短暂显示Toast提示(来自String)
     **/
    public void showToast(String text) {
        if (text.equals(getString(R.string.error_please_check_network))) { // 无网络
            showNoNetwork();
        } else if (text.equals(getString(R.string.connection_network_error))) { // 服务器错误
            showServiceError();
        } else {
            ToastUitl.showShort(text);
        }
    }


    @Override
    public void onError(String msg) {
        showToast(msg);
    }

    /**
     * 数据加载成功
     */
    public void showSuccess() {
        mBaseLoadService.showCallback(SuccessCallback.class);
    }

    /**
     * 数据为空
     */
    public void showEmpty() {
        mBaseLoadService.showCallback(EmptyCallBack.class);
    }

    /**
     * 数据为空,自定义空数据信息
     */
    public void showEmpty(final String emptyText) {
        mBaseLoadService.setCallBack(EmptyCallBack.class, new Transport() {
            @Override
            public void order(Context context, View view) {
                TextView mTvEmpty = view.findViewById(R.id.empty_text);
                mTvEmpty.setText(emptyText);
            }
        });
        mBaseLoadService.showCallback(EmptyCallBack.class);
    }

    /**
     * 服务器异常
     */
    public void showServiceError() {
        mBaseLoadService.showCallback(TimeoutCallBack.class);
    }

    /**
     * 无网络
     */
    public void showNoNetwork() {
        mBaseLoadService.showCallback(NoNetCallBack.class);
    }

    @Override
    public void onReload(View v) {
        // 网络已经链接
        if (NetWorkUtils.isNetConnected(BaseApp.getAppContext())) {
            showSuccess();
            reLoadData();
        } else {
            showToast(getString(R.string.error_please_check_network));
        }
    }

    /**
     * 用于子页面重新加载数据
     */
    protected void reLoadData() {
    }

    /**
     * 是否检查网络设置，默认不检查
     */
    protected boolean isCheckNetWork() {
        return false;
    }

    /**
     * 网络链接状态
     */
    protected void isNetConnected() {
        //网络
        if (!NetWorkUtils.isNetConnected(BaseApp.getAppContext())) {
            showNoNetwork();
        }
    }

    /**
     * 是否使用自定义LoadingLayout，适用于头部不是常规Toolbar的页面
     *
     * @return boolean
     */
    protected boolean isCustomLoadingLayout() {
        return false;
    }
}
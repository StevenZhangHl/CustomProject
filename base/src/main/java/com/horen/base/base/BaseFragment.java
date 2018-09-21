package com.horen.base.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.callback.SuccessCallback;
import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;

import org.simple.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * @author ChenYangYi
 * @date 2018/7/31
 * 基类fragment
 */
public abstract class BaseFragment<T extends BasePresenter, E extends BaseModel> extends SupportFragment implements BaseView, Callback.OnReloadListener {
    protected View rootView;
    public T mPresenter;
    public E mModel;
    public RxManager mRxManager;

    Unbinder unbinder;

    protected LoadService mBaseLoadService;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(getLayoutId(), container, false);
        }
        // 注册对象
        EventBus.getDefault().register(this);
        mRxManager = new RxManager();
        unbinder = ButterKnife.bind(this, rootView);
        mPresenter = TUtil.getT(this, 0);
        mModel = TUtil.getT(this, 1);
        if (mPresenter != null) {
            mPresenter.mContext = this.getActivity();
        }
        // 多状态管理类
        if (!isCustomLoadingLayout()) {
            mBaseLoadService = LoadSir.getDefault().register(rootView, this);
        }
        return mBaseLoadService != null ? mBaseLoadService.getLoadLayout() : rootView;
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        initPresenter();
        initView(savedInstanceState);
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
     * 初始化toolbar
     */
    protected void initToolbar(@NonNull Toolbar toolbar) {
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
    }

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
        intent.setClass(_mActivity, cls);
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
        intent.setClass(_mActivity, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        EventBus.getDefault().unregister(this);
        super.onDestroyView();
        if (mPresenter != null) {
            mPresenter.onDestroy();
        }
        unbinder.unbind();
        mRxManager.clear();
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

    @Override
    public void onReload(View v) {
        // 网络已经链接
        if (NetWorkUtils.isNetConnected(BaseApp.getAppContext())) {
            showSuccess();
            reLoadData();
        } else {
            ToastUitl.showShort(getString(R.string.error_please_check_network));
        }
    }

    /**
     * 用于子页面重新加载数据
     */
    protected void reLoadData() {
    }

    /**
     * 数据加载成功
     */
    public void showSuccess() {
        if (isCheckNetWork())
            mBaseLoadService.showCallback(SuccessCallback.class);
    }

    /**
     * 数据为空
     */
    public void showEmpty() {
        if (isCheckNetWork())
            mBaseLoadService.showCallback(EmptyCallBack.class);
    }

    /**
     * 服务器异常
     */
    public void showServiceError() {
        if (isCheckNetWork())
            mBaseLoadService.showCallback(TimeoutCallBack.class);
    }

    /**
     * 无网络
     */
    public void showNoNetwork() {
        if (isCheckNetWork())
            mBaseLoadService.showCallback(NoNetCallBack.class);
    }

    protected boolean isCustomLoadingLayout() {
        return false;
    }

    /**
     * 是否检查网络设置，默认检查
     */
    protected boolean isCheckNetWork() {
        return true;
    }
}
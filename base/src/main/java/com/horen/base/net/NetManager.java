package com.horen.base.net;

import java.util.concurrent.TimeUnit;

import me.jessyan.retrofiturlmanager.RetrofitUrlManager;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author :ChenYangYi
 * @date :2018/07/18/10:43
 * @description :单例Retrofit管理类
 * @github :https://github.com/chenyy0708
 */
public class NetManager {
    private OkHttpClient mOkHttpClient;
    private Retrofit mRetrofit;
    private UploadApi uploadApi;

    private static final int CONNECT_TIME_OUT = 1000 * 30;

    private static class NetManagerHolder {
        private static final NetManager INSTANCE = new NetManager();
    }

    public static NetManager getInstance() {
        return NetManagerHolder.INSTANCE;
    }

    private NetManager() {
        //开启Log
        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor();
        logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        mOkHttpClient = RetrofitUrlManager.getInstance().with(new OkHttpClient.Builder())
                .readTimeout(CONNECT_TIME_OUT, TimeUnit.MILLISECONDS)
                .connectTimeout(CONNECT_TIME_OUT, TimeUnit.MILLISECONDS)
                .writeTimeout(CONNECT_TIME_OUT, TimeUnit.MILLISECONDS)
                .addInterceptor(logInterceptor)
                .build();
        this.mRetrofit = new Retrofit.Builder()
                .baseUrl(Url.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(mOkHttpClient)
                .build();
        uploadApi = mRetrofit.create(UploadApi.class);
    }

    public OkHttpClient getOkHttpClient() {
        return mOkHttpClient;
    }

    public UploadApi getUploadApi() {
        return uploadApi;
    }

    public Retrofit getRetrofit() {
        return mRetrofit;


    }
}

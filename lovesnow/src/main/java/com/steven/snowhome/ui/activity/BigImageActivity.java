package com.steven.snowhome.ui.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.horen.base.base.BaseActivity;
import com.jaeger.library.StatusBarUtil;
import com.steven.lovesnow.R;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

public class BigImageActivity extends BaseActivity {
    public static final String INTENT_LIST = "imageResouceId";

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void startImageForResouceId(Activity activity, Integer imageResouceId, ImageView imageView) {
        Intent intent = new Intent(activity, BigImageActivity.class);
        Bundle options = ActivityOptions.makeSceneTransitionAnimation(
                activity, imageView, "shareImage").toBundle();
        intent.putExtra(INTENT_LIST, imageResouceId);
        activity.startActivity(intent, options);
    }

    @Override
    public int getLayoutId() {
        return R.layout.snow_activity_big_image;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        // 隐藏系统状态栏
        StatusBarUtil.setTranslucentForImageViewInFragment(this, 0, null);
        PhotoView imageView = (PhotoView) findViewById(R.id.image);
        // 点击图片
        imageView.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
            @Override
            public void onPhotoTap(View view, float x, float y) {
                onBackPressed();
            }
        });
        Integer imageId = getIntent().getIntExtra(INTENT_LIST, 0);
        Glide.with(this)
                .load(imageId)
                .thumbnail(0.1f)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(imageView);
    }
}

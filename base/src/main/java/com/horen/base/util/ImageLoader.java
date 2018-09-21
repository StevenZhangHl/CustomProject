package com.horen.base.util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.horen.base.R;
import com.horen.base.app.GlideApp;


/**
 * @author :ChenYangYi
 * @date :2018/08/06/10:39
 * @description :图片加载工具类
 * @github :https://github.com/chenyy0708
 */
public class ImageLoader {

    /**
     * Glide加载图片
     *
     * @param context 上下文
     * @param url     图片地址
     * @param iv      ImageView
     */
    public static void load(Context context, Object url, ImageView iv) {
        GlideApp.with(context)
                .load(url)
                .error(R.drawable.ic_error_normal)
                .placeholder(R.drawable.ic_error_normal)
                .into(iv);
    }

    /**
     * Glide加载图片
     *
     * @param context 上下文
     * @param url     图片地址
     * @param iv      ImageView
     */
    public static void loadCenter(Context context, String url, ImageView iv) {
        GlideApp.with(context)
                .load(url)
                .error(R.drawable.ic_error_normal)
                .placeholder(R.drawable.ic_error_normal)
                .centerCrop()
                .into(iv);
    }

    /**
     * 加载drawable里的图片
     *
     * @param context
     * @param imageId
     * @param iv
     */
    public static void loadDrawable(Context context, int imageId, ImageView iv) {
        GlideApp.with(context)
                .load(imageId)
                .error(R.drawable.ic_error_normal)
                .placeholder(R.drawable.ic_error_normal)
                .centerCrop()
                .into(iv);
    }

    public static void loadRoundIV(Context context, String url, ImageView iv, int dp) {
        Glide.with(context)
                .load(url)
                .apply(new RequestOptions().placeholder(R.drawable.icon_error_normal))
                .apply(new RequestOptions().error(R.drawable.icon_error_normal))
//                .transform(new GlideRoundTransform(context, dp)
//                )
                .into(iv);
    }

    public static void loadBanner(Context context, String url, ImageView iv) {
        Glide.with(context)
                .load(url)
                .apply(new RequestOptions().placeholder(R.drawable.icon_error_normal))
                .apply(new RequestOptions().error(R.drawable.icon_error_banner))
                .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE)).into(iv);
    }
}

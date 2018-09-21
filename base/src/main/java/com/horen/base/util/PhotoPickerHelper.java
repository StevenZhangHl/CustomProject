package com.horen.base.util;

import android.app.Activity;

import com.horen.base.R;
import com.horen.base.app.HRConstant;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

public class PhotoPickerHelper {

    /**
     * 选择图片
     */
    public static final int PHOTO_REQUEST_CODE = 100;

    /**
     * 打开相册选取照片
     *
     * @param activity      当前页面
     * @param maxSelectable 最大选择个数
     * @param requestCode   请求码
     */
    public static void start(Activity activity, int maxSelectable, int requestCode) {
        init(activity, maxSelectable, requestCode);
    }

    /**
     * 打开相册选取照片
     *
     * @param activity      当前页面
     * @param maxSelectable 最大选择个数
     */
    public static void start(Activity activity, int maxSelectable) {
        init(activity, maxSelectable, PHOTO_REQUEST_CODE);
    }

    /**
     * 打开相册选取照片
     *
     * @param activity      当前页面
     * @param maxSelectable 最大选择个数
     * @param requestCode   请求码
     */
    private static void init(Activity activity, int maxSelectable, int requestCode) {
        // 相机相册
        Matisse.from(activity)
                .choose(MimeType.ofImage())
                // 是否开启相机
                .capture(true)
                // 7.0权限
                .captureStrategy(new CaptureStrategy(true, HRConstant.FILE_PROVIDER))
                .countable(true)
                // 自定义主题
                .theme(R.style.Matisse_HOREN)
                // 最多选择多少张
                .maxSelectable(maxSelectable)
                .thumbnailScale(0.85f)
                .imageEngine(new Glide4Engine())
                .forResult(requestCode);
    }
}

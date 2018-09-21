package com.horen.base.ui;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.horen.base.R;
import com.horen.base.util.DisplayUtil;
import com.uuzuche.lib_zxing.activity.CaptureFragment;
import com.uuzuche.lib_zxing.activity.CodeUtils;

/**
 * Created by Administrator on 2016/12/13.
 */
public class ScanActivity extends FragmentActivity implements View.OnClickListener, SensorEventListener {
    public final static int REQUEST = 66;
    // 是否开启闪关灯，默认是关闭的
    private boolean isLightEnable = false;
    private ImageView ivLight;
    private TextView tvLight;
    private LinearLayout llLight;
    private ImageView ivScanLine;
    private SensorManager sensorManager;

    public static void startActivityForResult(Activity activity) {
        Intent intent = new Intent();
        intent.setClass(activity, ScanActivity.class);
        activity.startActivityForResult(intent, REQUEST);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 设置竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_qr_scan);
        // 透明状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        llLight = findViewById(R.id.zxing_light);
        ivLight = findViewById(R.id.iv_scan_open_light);
        ivScanLine = findViewById(R.id.iv_scan_line);
        tvLight = findViewById(R.id.tv_scan_open_light);
        llLight.setOnClickListener(this);
        findViewById(R.id.zxing_back).setOnClickListener(this);
        scanLineAnim(ivScanLine, 0, DisplayUtil.dip2px(239), 2000);
        /**
         * 初始化界面之后>>>
         * 执行扫面Fragment的初始化操作
         */
        CaptureFragment captureFragment = new CaptureFragment();
        // 为二维码扫描界面设置定制化界面
        CodeUtils.setFragmentArgs(captureFragment, R.layout.my_camera);
        captureFragment.setAnalyzeCallback(analyzeCallback);
        /**
         * 替换我们的扫描控件
         */
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_my_container, captureFragment).commit();

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    /**
     * 二维码解析回调函数
     */
    CodeUtils.AnalyzeCallback analyzeCallback = new CodeUtils.AnalyzeCallback() {
        @Override
        public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
            setResult(CodeUtils.RESULT_SUCCESS, result);
        }

        @Override
        public void onAnalyzeFailed() {
            setResult(CodeUtils.RESULT_FAILED, "");
        }
    };

    private void setResult(int type, String code) {
        Intent resultIntent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putInt(CodeUtils.RESULT_TYPE, type);
        bundle.putString(CodeUtils.RESULT_STRING, code);
        resultIntent.putExtras(bundle);
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.zxing_back) {
            finish();
        } else if (v.getId() == R.id.zxing_light) {
            if (isLightEnable) { // 开启闪关灯状态，关闭
                CodeUtils.isLightEnable(false);
                isLightEnable = !isLightEnable;
                ivLight.setImageResource(R.drawable.zxing_light);
                tvLight.setText("轻触开启");
                tvLight.setTextColor(getResources().getColor(R.color.white));
            } else { // 关闭闪光灯状态，开启
                CodeUtils.isLightEnable(true);
                isLightEnable = !isLightEnable;
                ivLight.setImageResource(R.drawable.zxing_light_open);
                tvLight.setText("轻触关闭");
                tvLight.setTextColor(getResources().getColor(R.color.color_main));
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
//        float value = sensorEvent.values[0];
//        if (value < 50){//光线变暗情况下
//            llLight.setVisibility(View.VISIBLE);//手电筒按钮始终显示
//        } else {//光线变强情况下
//            if (isLightEnable){//如果灯光是开启状态
//                llLight.setVisibility(View.VISIBLE);//手电筒按钮始终显示
//            } else {
//                llLight.setVisibility(View.GONE);//手电筒按钮消失
//            }
//        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }

    public void scanLineAnim(View view, float fromY, float toY, int duration) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationY", fromY, toY);
        animator.setDuration(duration);
        animator.start();
        animator.setRepeatCount(-1);
    }
}
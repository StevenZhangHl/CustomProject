package com.steven.snowhome.ui.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import com.horen.base.util.DisplayUtil;
import com.steven.lovesnow.R;

/**
 * Author:Steven
 * Time:2018/9/21 13:48
 * Description:This isPathAnimView
 */
public class PathAnimView extends View {
    private static final int INVALIDATE_TIMES = 100; //总共执行100次
    private int width = DisplayUtil.dip2px(20);
    private int height = DisplayUtil.dip2px(10);
    private Paint mPaint;
    private Bitmap mBitmap;
    private int mOffsetX, mOffsetY; // 图片的中间位置

    private Path mAnimPath;
    private PathMeasure mPathMeasure;
    private float mPathLength;

    private double mStep;            //distance each step
    private float mDistance;        //distance moved

    private float[] mPos;
    private float[] mTan;

    private Matrix mMatrix;

    public PathAnimView(Context context) {
        this(context, null);
    }

    public PathAnimView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public PathAnimView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPathView();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = measureLength(widthMeasureSpec, width);
        height = measureLength(heightMeasureSpec, height);
        setMeasuredDimension(width, height);
    }

    private int measureLength(int measureSpec, int length) {
        int measureLength = 0;
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);
        switch (mode) {
            case MeasureSpec.AT_MOST:
                //wrap_content时size其实为父控件能给的最大长度,length相关于一个默认值的作用 Math.min
                measureLength = Math.min(size, length);
                break;
            case MeasureSpec.EXACTLY:
                //match_parent或指定如30dp时 测出来多大就多大,考虑的情况是有时候测量出的确没有空间放置此控件了
                measureLength = size;
                break;
            case MeasureSpec.UNSPECIFIED:
                measureLength = length;
                break;
        }
        return measureLength;
    }

    public void start() {
        mDistance = 0;
        invalidate();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void initPathView() {
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(10);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setAntiAlias(true);

        mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.icon_light);

        mOffsetX = mBitmap.getWidth() / 2;
        mOffsetY = mBitmap.getHeight() / 2;

        mAnimPath = new Path();
        mAnimPath.addArc(width / 2, 100, 400, 100, -225, 225);
        mAnimPath.arcTo(width / 2, 200, 600, 400, -180, 225, false);
        mAnimPath.lineTo(width / 2, 542);
        mAnimPath.close();

        mPathMeasure = new PathMeasure(mAnimPath, false);
        mPathLength = mPathMeasure.getLength();

        mStep = mPathLength / INVALIDATE_TIMES;
        mDistance = mPathLength;
        mPos = new float[2];
        mTan = new float[2];

        mMatrix = new Matrix();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mAnimPath == null || mPaint == null) {
            return;
        }
        canvas.drawPath(mAnimPath, mPaint);

        if (mDistance < mPathLength) {
            mPathMeasure.getPosTan(mDistance, mPos, mTan);
            mMatrix.reset();
            mMatrix.postTranslate(-mOffsetX, -mOffsetY);
            float degrees = (float) (Math.atan2(mTan[1], mTan[0]) * 180.0 / Math.PI);
            mMatrix.postRotate(degrees);
            mMatrix.postTranslate(mPos[0], mPos[1]);
            canvas.drawBitmap(mBitmap, mMatrix, null);
            mDistance += mStep;
            invalidate();
        } else {
            canvas.drawBitmap(mBitmap, mMatrix, null);
        }
    }
}

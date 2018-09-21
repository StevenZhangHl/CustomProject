package com.horen.base.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import com.horen.base.util.DisplayUtil;

/**
 * Author:Steven
 * Time:2018/9/7 16:49
 * Description:This isTrigonView
 */
public class TrigonView extends View {
    private int width = DisplayUtil.dip2px(20);
    private int height = DisplayUtil.dip2px(10);

    private Paint whitePaint;
    private Paint linePain;
    private Path path;

    public TrigonView(Context context) {
        this(context, null);
    }

    public TrigonView(Context context, AttributeSet attrs) {
        super(context, attrs);
        whitePaint = new Paint();
        whitePaint.setAntiAlias(true);
        whitePaint.setColor(Color.WHITE);
        whitePaint.setAlpha(50);
        whitePaint.setStyle(Paint.Style.FILL);
        linePain = new Paint();
        linePain.setColor(Color.WHITE);
        linePain.setAntiAlias(true);
        linePain.setStyle(Paint.Style.FILL);
        linePain.setStrokeWidth(1f);
        path = new Path();
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

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawTriangle(canvas);
        drawLine(canvas);
    }

    private void drawLine(Canvas canvas) {
        canvas.drawLine(0, height, width, 0, linePain);
    }

    private void drawTriangle(Canvas canvas) {
        //用路径来画出这个三角形
        path.moveTo(0, height);//起始点
        path.lineTo(width, 0);//从起始点画一根线到 相应的坐标
        path.lineTo((float) width, 0);
        path.lineTo(width, height);
        path.close();//闭合
        canvas.drawPath(path, whitePaint);
    }

    public void postInvalidate() {
        invalidate();
    }
}

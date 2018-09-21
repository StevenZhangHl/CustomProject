package com.horen.base.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.horen.base.util.DisplayUtil;

/**
 * Author:Steven
 * Time:2018/9/7 17:26
 * Description:This isBiasLineView
 */
public class BiasLineView extends View {
    private int width = DisplayUtil.dip2px(20);//将dp转成px的方法...
    private int height = DisplayUtil.dip2px(10);
    private Path linePath;
    private Paint linePain;

    public BiasLineView(Context context) {
        super(context);
    }

    public BiasLineView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        linePain = new Paint();
        linePain.setColor(Color.BLACK);
        linePain.setAntiAlias(true);
        linePain.setStyle(Paint.Style.FILL);
        linePath = new Path();
    }

    public BiasLineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
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
        drawLine(canvas);
    }

    private void drawLine(Canvas canvas) {
        //再画线
        linePath.moveTo(0, height);
        linePath.lineTo(width, 0);
        canvas.drawPath(linePath, linePain);
    }
}

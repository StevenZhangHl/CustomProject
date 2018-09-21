package com.horen.base.widget;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.horen.base.R;
import com.horen.base.listener.TimePickerListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Author:Steven
 * Time:2018/8/20 11:30
 * Description:This isCustomTimePickerView
 */
public class TimePickerViewHelper {
    private TimePickerView mTimePickerView;
    private TimePickerListener timePickerListener;
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");

    public TimePickerViewHelper(Context mContext) {
        //初始化时间选择器
        Calendar selectedDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();// 开始时间为当前系统时间 - 12个月
        Calendar endDate = Calendar.getInstance(); // 结束时间为系统当前时间
        startDate.set(endDate.get(GregorianCalendar.YEAR) - 1,
                (endDate.get(GregorianCalendar.MONTH)), endDate.get(GregorianCalendar.DAY_OF_MONTH));
        initTimePicker(mContext, startDate, endDate, selectedDate);
    }

    public TimePickerViewHelper(Context mContext, Calendar startDate, Calendar endDate, Calendar selectedDate) {
        initTimePicker(mContext, startDate, endDate, selectedDate);
    }

    /**
     * 初始化时间选择器
     *
     * @param mContext     上下文
     * @param startDate    开始时间
     * @param endDate      结束时间
     * @param selectedDate 当前时间
     */
    private void initTimePicker(Context mContext, Calendar startDate, Calendar endDate, Calendar selectedDate) {
        mTimePickerView = new TimePickerBuilder(mContext, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                String time = format.format(date);
                if (timePickerListener != null) {
                    timePickerListener.onTimePicker(time);
                }
            }
        }).setDate(selectedDate)
                .setRangDate(startDate, endDate)
                .setLayoutRes(R.layout.partner_pickerview_time, new CustomListener() {
                    @Override
                    public void customLayout(View v) {
                        TextView tvtitle = v.findViewById(R.id.tv_title);
                        tvtitle.setText("查询月份");
                        final Button tvSubmit = (Button) v.findViewById(R.id.tv_confirm_click);
                        tvSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mTimePickerView.returnData();
                                mTimePickerView.dismiss();
                            }
                        });
                    }
                })
                .setContentTextSize(18)
                .setType(new boolean[]{true, true, false, false, false, false})
                .setLabel("年", "月", "日", "时", "分", "秒")
                .setLineSpacingMultiplier(1.2f)
                .setTextXOffset(0, 0, 0, 40, 0, -40)
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setDividerColor(Color.parseColor("#87CD25"))
                .build();
    }

    public void show() {
        mTimePickerView.show();
    }

    public void setTimePickerListener(TimePickerListener timePickerListener) {
        this.timePickerListener = timePickerListener;
    }
}

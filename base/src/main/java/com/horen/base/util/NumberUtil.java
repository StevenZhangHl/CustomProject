package com.horen.base.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;

/**
 * Author:Steven
 * Time:2018/8/31 10:46
 * Description:This isNumberUtil
 */
public class NumberUtil {
    /**
     * 保留2位小数且带千位符
     * @param number
     * @return
     */
    public static String formitNumber(double number) {
        DecimalFormat df = new DecimalFormat("###,###.##");
        NumberFormat nf = NumberFormat.getInstance();
        String result = "";
        try {
            result = df.format((nf.parse(String.valueOf(number))));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (!result.contains(".")) {
            result = result + ".00";
        }
        return result;
    }

    /**
     * 保留2位小数不带千位符
     * @param number
     * @return
     */
    public static String formitNumberTwoPoint(double number) {
        String result = "";
        DecimalFormat df = new DecimalFormat("###.##");
        NumberFormat nf = NumberFormat.getInstance();
        try {
            result = df.format((nf.parse(String.valueOf(number))));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (!result.contains(".")) {
            result = result + ".00";
        }
        return result;
    }

    /**
     * 带千位符但是不保留小数
     * @param number
     * @return
     */
    public static String formitNumberNoPoint(double number){
        String result = "";
        DecimalFormat df = new DecimalFormat("###,###");
        NumberFormat nf = NumberFormat.getInstance();
        try {
            result = df.format((nf.parse(String.valueOf(number))));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }
}

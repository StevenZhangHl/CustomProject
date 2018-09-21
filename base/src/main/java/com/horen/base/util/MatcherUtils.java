package com.horen.base.util;

import android.text.TextUtils;

import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Author:Steven
 * Time:2018/8/17 8:42
 * Description:This isMatcherUtils
 */
public class MatcherUtils {
    /**
     * 判断是否为手机号
     *
     * @param mobiles
     * @return
     */
    public static boolean isMobilePhone(String mobiles) {
        if (!TextUtils.isEmpty(mobiles)) {
            String telRegex = "^1[3|4|5|7|8]\\d{9}$";//"[1]"代表第1位为数字1，"[3458]"代表第二位可以为3、4、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
            Pattern pattern = Pattern.compile(telRegex);
            Matcher matcher = pattern.matcher(mobiles);
            return matcher.matches();
        }
        return false;
    }

    /**
     * 判断是否为邮箱
     *
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);
        return m.matches();
    }

    /**
     * 格式化小数保留2位小数
     * @param number
     * @return
     */
    public static String formatNumber(float number) {
        DecimalFormat decimalFormat = new DecimalFormat("0.00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
        String s = decimalFormat.format(number);//format 返回的是字符串
        return s;
    }
}

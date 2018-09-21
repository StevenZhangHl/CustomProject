package com.horen.base.util;

import android.text.TextUtils;

import com.horen.base.app.BaseApp;
import com.horen.base.app.HRConstant;

/**
 * Author:Steven
 * Time:2018/8/27 17:06
 * Description:This isPlatformUtil
 */
public class PlatformUtil {
    public static String getPlanUrlH5(String solution_h5_url) {
        String url = solution_h5_url + "&visit_devicetoken=" + AppUtil.getDeviceId();
        url = addUserid(url);
        return url;
    }

    private static String addUserid(String url) {
        String app_token = SPUtils.getSharedStringData(BaseApp.getAppContext(), HRConstant.TOKEN);
        if (TextUtils.isEmpty(app_token)) {
            return url;
        }
        if (!TextUtils.isEmpty(SPUtils.getSharedStringData(BaseApp.getAppContext(), HRConstant.USER_ID))) {
            url = url + "&user_id=" + SPUtils.getSharedStringData(BaseApp.getAppContext(), HRConstant.USER_ID);
        }
        return url;
    }
}

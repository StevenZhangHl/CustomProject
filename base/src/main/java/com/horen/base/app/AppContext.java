package com.horen.base.app;

/**
 * @author :ChenYangYi
 * @date :2018/08/31/11:44
 * @description :
 * @github :https://github.com/chenyy0708
 */
//public class AppContext extends BlockCanaryContext {
//    private static final String TAG = "AppContext";
//
//    @Override
//    public String provideQualifier() {
//        String qualifier = "";
//        try {
//            PackageInfo info = BaseApp.getAppContext().getPackageManager()
//                    .getPackageInfo(BaseApp.getAppContext().getPackageName(), 0);
//            qualifier += info.versionCode + "_" + info.versionName + "_YYB";
//        } catch (PackageManager.NameNotFoundException e) {
//            LogUtils.e(e.toString());
//        }
//        return qualifier;
//    }
//
//    @Override
//    public String provideUid() {
//        return "87224330";
//    }
//
//    @Override
//    public String provideNetworkType() {
//        return "wifi";
//    }
//
//    @Override
//    public int provideMonitorDuration() {
//        return 9999;
//    }
//
//    @Override
//    public int provideBlockThreshold() {
//        return 500;
//    }
//
//    @Override
//    public boolean displayNotification() {
//        return BuildConfig.LOG_DEBUG;
//    }
//
//    @Override
//    public List<String> concernPackages() {
//        List<String> list = super.provideWhiteList();
//        list.add("com.horen");
//        return list;
//    }
//
//    @Override
//    public List<String> provideWhiteList() {
//        return new ArrayList<>();
//    }
//
//    @Override
//    public boolean stopWhenDebugging() {
//        return true;
//    }
//}

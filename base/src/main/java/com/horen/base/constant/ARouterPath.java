package com.horen.base.constant;

/**
 * @author :ChenYangYi
 * @date :2018/07/31/13:39
 * @description :路由跳转链接
 * @github :https://github.com/chenyy0708
 */
public class ARouterPath {
    //---------------------------------服务端--------------------------------

    /**
     * 服务端MainActivity
     */
    public static final String SERVICE_MAIN_ACTIVITY = "/service/servicemainactivity";


    //---------------------------------合伙人--------------------------------

    /**
     * 测试Fragment2
     */
    public static final String PARTNER_CUSROMER_CENTER_FRAGMENT = "/partner/customercenterfragment";
    public static final String PARNNER_MAIN_ACTIVITY = "/partner/businessmainActivity";
    //下单页面
    public static final String CREATE_ORDER_FRAME_ACTIVITY = "/companyorder/CreateOrderFrameActivity";
    //万箱合伙人提交成功
    public static final String PLATFORM_ACTIVITY_SUCCESS = "/companyorder/PLATFORM_ACTIVITY_SUCCESS";

    //---------------------------------个人中心--------------------------------

    // 登陆
    public static final String USER_LOGIN = "/user/loginactivity";
    public static final String USER_PLATFORM = "/user/platformactivity";
    // 个人中心
    public static final String USER_INFO = "/user/userinfoactivity";
    // 业务范围
    public static final String BUSINESS_SCOPE = "/user/businessscopeactivity";
    // 平台支持
    public static final String PLATFORM_SUPPORT = "/user/platformsupportactivity";


}

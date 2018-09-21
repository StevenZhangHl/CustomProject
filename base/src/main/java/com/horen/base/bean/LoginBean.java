package com.horen.base.bean;


import android.text.TextUtils;

/**
 * @author :ChenYangYi
 * @date :2018/05/15/15:11
 * @description :
 * @github :https://github.com/chenyy0708
 */
public class LoginBean {


    /**
     * loginInfo : {"app_token":"bff1c4736d49332ca2ef863594680178389e6d14","company_class":"3","company_id":"CN302101","company_name":"箱箱上海合伙人","facility_type":"3","flag_bindmail":"0","flag_bindmobile":"0","flag_data":"1","flag_orgadmin":"1","lock_status":"0","nums":0,"role_id":"PARTNER_SA_ADMIN","status":"1","user_id":"25757023295504384","user_name":"xxshhhr"}
     */

    private LoginInfoBean loginInfo;

    public LoginInfoBean getLoginInfo() {
        return loginInfo == null ? new LoginInfoBean() : loginInfo;
    }

    public void setLoginInfo(LoginInfoBean loginInfo) {
        this.loginInfo = loginInfo;
    }

    public static class LoginInfoBean {
        /**
         * app_token : bff1c4736d49332ca2ef863594680178389e6d14
         * company_class : 3
         * company_id : CN302101
         * company_name : 箱箱上海合伙人
         * facility_type : 3
         * flag_bindmail : 0
         * flag_bindmobile : 0
         * flag_data : 1
         * flag_orgadmin : 1
         * lock_status : 0
         * nums : 0
         * role_id : PARTNER_SA_ADMIN
         * status : 1
         * user_id : 25757023295504384
         * user_name : xxshhhr
         */

        private String app_token;
        private String company_class;
        private String company_id;
        private String company_name;
        private String facility_type;
        private String flag_bindmail;
        private String flag_bindmobile;
        private String flag_data;
        private String flag_orgadmin;
        private String lock_status;
        private int nums;
        private String role_id;
        private String status;
        private String user_id;
        private String user_name;
        private String user_mail;
        private String user_mobile;
        private String user_nickname;
        private String photo;

        public String getPhoto() {
            return photo == null ? "" : photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public String getUser_mail() {
            return user_mail;
        }

        public void setUser_mail(String user_mail) {
            this.user_mail = user_mail;
        }

        public String getUser_mobile() {
            return user_mobile;
        }

        public void setUser_mobile(String user_mobile) {
            this.user_mobile = user_mobile;
        }

        public String getUser_nickname() {
            return user_nickname;
        }

        public void setUser_nickname(String user_nickname) {
            this.user_nickname = user_nickname;
        }

        public String getApp_token() {
            return app_token;
        }

        public void setApp_token(String app_token) {
            this.app_token = app_token;
        }

        public String getCompany_class() {
            return TextUtils.isEmpty(company_class) ? "" : company_class;
        }

        public void setCompany_class(String company_class) {
            this.company_class = company_class;
        }

        public String getCompany_id() {
            return company_id;
        }

        public void setCompany_id(String company_id) {
            this.company_id = company_id;
        }

        public String getCompany_name() {
            return company_name;
        }

        public void setCompany_name(String company_name) {
            this.company_name = company_name;
        }

        public String getFacility_type() {
            return facility_type;
        }

        public void setFacility_type(String facility_type) {
            this.facility_type = facility_type;
        }

        public String getFlag_bindmail() {
            return flag_bindmail;
        }

        public void setFlag_bindmail(String flag_bindmail) {
            this.flag_bindmail = flag_bindmail;
        }

        public String getFlag_bindmobile() {
            return flag_bindmobile;
        }

        public void setFlag_bindmobile(String flag_bindmobile) {
            this.flag_bindmobile = flag_bindmobile;
        }

        public String getFlag_data() {
            return flag_data;
        }

        public void setFlag_data(String flag_data) {
            this.flag_data = flag_data;
        }

        public String getFlag_orgadmin() {
            return flag_orgadmin;
        }

        public void setFlag_orgadmin(String flag_orgadmin) {
            this.flag_orgadmin = flag_orgadmin;
        }

        public String getLock_status() {
            return lock_status;
        }

        public void setLock_status(String lock_status) {
            this.lock_status = lock_status;
        }

        public int getNums() {
            return nums;
        }

        public void setNums(int nums) {
            this.nums = nums;
        }

        public String getRole_id() {
            return role_id;
        }

        public void setRole_id(String role_id) {
            this.role_id = role_id;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }
    }
}

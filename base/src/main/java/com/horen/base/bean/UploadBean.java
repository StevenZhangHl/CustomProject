package com.horen.base.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Author:Steven
 * Time:2018/8/8 8:29
 * Description:This isUploadBean
 */
public class UploadBean implements Serializable {


    /**
     * code : 000000
     * data : [{"uploadUrl":"http://47.98.235.3:8901/download?fileName=2018-05-02-41c93f00-26e3-4468-a5e8-05990193ade6.jpg&filePath=photo","watchUrl":"http://47.98.235.3:8901/upload/photo/2018-05-02-41c93f00-26e3-4468-a5e8-05990193ade6.jpg"}]
     * message : 接口调用成功
     */

    private String code;
    private String message;
    private List<DataBean> data;

    /**
     * 返回结果是否成功
     */
    public boolean success() {
        return "000000".equals(code);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * uploadUrl : http://47.98.235.3:8901/download?fileName=2018-05-02-41c93f00-26e3-4468-a5e8-05990193ade6.jpg&filePath=photo
         * watchUrl : http://47.98.235.3:8901/upload/photo/2018-05-02-41c93f00-26e3-4468-a5e8-05990193ade6.jpg
         */

        private String uploadUrl;
        private String watchUrl;

        public String getUploadUrl() {
            return uploadUrl;
        }

        public void setUploadUrl(String uploadUrl) {
            this.uploadUrl = uploadUrl;
        }

        public String getWatchUrl() {
            return watchUrl;
        }

        public void setWatchUrl(String watchUrl) {
            this.watchUrl = watchUrl;
        }
    }
}

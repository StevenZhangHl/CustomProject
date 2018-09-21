package com.horen.base.bean;

import java.io.Serializable;

/**
 * @author ChenYangYi
 * @date 2018/7/31
 * 封装服务器返回数据
 */
public class BaseEntry<T> implements Serializable {

    /**
     * header : {"ret_message":"获取数据成功.","ret_status":"1","ret_value":"消息","srv_desc":"","srv_id":""}
     */

    private HeaderBean header;
    private T data;

    public T getDataResponse() {
        return data;
    }

    public HeaderBean getHeader() {
        return header;
    }

    public void setHeader(HeaderBean header) {
        this.header = header;
    }

    public boolean success() {
        return header.getRet_status() > 0 ? true : false;
    }

    public static class HeaderBean implements Serializable {
        /**
         * ret_message : 获取数据成功.
         * ret_status : 1
         * ret_value : 消息
         * srv_desc :
         * srv_id :
         */

        private String ret_message;
        private int ret_status;
        private String ret_value;
        private String srv_desc;
        private String srv_id;

        public String getRet_message() {
            return ret_message;
        }

        public void setRet_message(String ret_message) {
            this.ret_message = ret_message;
        }

        public int getRet_status() {
            return ret_status;
        }

        public void setRet_status(int ret_status) {
            this.ret_status = ret_status;
        }

        public String getRet_value() {
            return ret_value;
        }

        public void setRet_value(String ret_value) {
            this.ret_value = ret_value;
        }

        public String getSrv_desc() {
            return srv_desc;
        }

        public void setSrv_desc(String srv_desc) {
            this.srv_desc = srv_desc;
        }

        public String getSrv_id() {
            return srv_id;
        }

        public void setSrv_id(String srv_id) {
            this.srv_id = srv_id;
        }
    }
}

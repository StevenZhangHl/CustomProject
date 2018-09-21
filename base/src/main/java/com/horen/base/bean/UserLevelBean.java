package com.horen.base.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Author:Steven
 * Time:2018/9/6 13:41
 * Description:This isUserLevelBean
 */
public class UserLevelBean implements Serializable {

    /**
     * liquid : {"next":"970987","tend":["＜300万","300≤B＜500","500≤B＜1000","≥1000"],"level":"4","tend_data":["一级:租赁收入＜300万，租赁收入分成5%,耗材收入分成0.5%","二级:租赁收入300≤B＜500，租赁收入分成6%,耗材收入分成0.6%","三级:租赁收入500≤B＜1000，租赁收入分成7%,耗材收入分成0.7%","四级:租赁收入≥1000，租赁收入分成8%,耗材收入分成0.8%"]}
     * parts : {"next":"970987","tend":["＜400万","400≤B＜700","700≤B＜1000","≥1000"],"level":"3","tend_data":["一级:租赁收入＜400万，租赁收入分成5%,耗材收入分成0.5%","二级:租赁收入400≤B＜700，租赁收入分成6%,耗材收入分成0.6%","三级:租赁收入700≤B＜1000，租赁收入分成7%,耗材收入分成0.7%","四级:租赁收入≥1000，租赁收入分成8%,耗材收入分成0.8%"]}
     * fresh : {"next":"970987","tend":["＜300万","300≤B＜600","≥600"],"level":"1","tend_data":["一级:租赁收入＜300万，租赁收入分成5%,耗材收入分成0.6%","二级:租赁收入300≤B＜600，租赁收入分成6%,耗材收入分成0.7%","三级:租赁收入≥600，租赁收入分成8%,耗材收入分成0.8%"]}
     */

    private LiquidBean liquid;
    private PartsBean parts;
    private FreshBean fresh;

    public LiquidBean getLiquid() {
        return liquid;
    }

    public void setLiquid(LiquidBean liquid) {
        this.liquid = liquid;
    }

    public PartsBean getParts() {
        return parts;
    }

    public void setParts(PartsBean parts) {
        this.parts = parts;
    }

    public FreshBean getFresh() {
        return fresh;
    }

    public void setFresh(FreshBean fresh) {
        this.fresh = fresh;
    }

    public static class LiquidBean implements Serializable {
        /**
         * next : 970987
         * tend : ["＜300万","300≤B＜500","500≤B＜1000","≥1000"]
         * level : 4
         * tend_data : ["一级:租赁收入＜300万，租赁收入分成5%,耗材收入分成0.5%","二级:租赁收入300≤B＜500，租赁收入分成6%,耗材收入分成0.6%","三级:租赁收入500≤B＜1000，租赁收入分成7%,耗材收入分成0.7%","四级:租赁收入≥1000，租赁收入分成8%,耗材收入分成0.8%"]
         */

        private String next;
        private String level;
        private List<String> tend;
        private List<String> tend_data;

        public String getNext() {
            return next;
        }

        public void setNext(String next) {
            this.next = next;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public List<String> getTend() {
            return tend;
        }

        public void setTend(List<String> tend) {
            this.tend = tend;
        }

        public List<String> getTend_data() {
            return tend_data;
        }

        public void setTend_data(List<String> tend_data) {
            this.tend_data = tend_data;
        }
    }

    public static class PartsBean implements Serializable {
        /**
         * next : 970987
         * tend : ["＜400万","400≤B＜700","700≤B＜1000","≥1000"]
         * level : 3
         * tend_data : ["一级:租赁收入＜400万，租赁收入分成5%,耗材收入分成0.5%","二级:租赁收入400≤B＜700，租赁收入分成6%,耗材收入分成0.6%","三级:租赁收入700≤B＜1000，租赁收入分成7%,耗材收入分成0.7%","四级:租赁收入≥1000，租赁收入分成8%,耗材收入分成0.8%"]
         */

        private String next;
        private String level;
        private List<String> tend;
        private List<String> tend_data;

        public String getNext() {
            return next;
        }

        public void setNext(String next) {
            this.next = next;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public List<String> getTend() {
            return tend;
        }

        public void setTend(List<String> tend) {
            this.tend = tend;
        }

        public List<String> getTend_data() {
            return tend_data;
        }

        public void setTend_data(List<String> tend_data) {
            this.tend_data = tend_data;
        }
    }

    public static class FreshBean implements Serializable {
        /**
         * next : 970987
         * tend : ["＜300万","300≤B＜600","≥600"]
         * level : 1
         * tend_data : ["一级:租赁收入＜300万，租赁收入分成5%,耗材收入分成0.6%","二级:租赁收入300≤B＜600，租赁收入分成6%,耗材收入分成0.7%","三级:租赁收入≥600，租赁收入分成8%,耗材收入分成0.8%"]
         */

        private String next;
        private String level;
        private List<String> tend;
        private List<String> tend_data;

        public String getNext() {
            return next;
        }

        public void setNext(String next) {
            this.next = next;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public List<String> getTend() {
            return tend;
        }

        public void setTend(List<String> tend) {
            this.tend = tend;
        }

        public List<String> getTend_data() {
            return tend_data;
        }

        public void setTend_data(List<String> tend_data) {
            this.tend_data = tend_data;
        }
    }
}

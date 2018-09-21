package com.horen.base.bean;

/**
 * @author :ChenYangYi
 * @date :2018/08/17/16:46
 * @description :多选Bean
 * @github :https://github.com/chenyy0708
 */
public class MultipleSelectBean {

    public MultipleSelectBean(String tabName) {
        this.tabName = tabName;
    }

    public MultipleSelectBean(String tabName, boolean isSelect) {
        this.tabName = tabName;
        this.isSelect = isSelect;
    }

    /**
     * 属性名
     */
    private String tabName;
    /**
     * 是否选择
     */
    private boolean isSelect;

    public String getTabName() {
        return tabName;
    }

    public void setTabName(String tabName) {
        this.tabName = tabName;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }
}

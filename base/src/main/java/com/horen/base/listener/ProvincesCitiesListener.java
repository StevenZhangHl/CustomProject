package com.horen.base.listener;

/**
 * @author :ChenYangYi
 * @date :2018/09/05/09:07
 * @description :省市区选择监听
 * @github :https://github.com/chenyy0708
 */
public interface ProvincesCitiesListener {
    /**
     * 选择省市区
     *
     * @param province 省
     * @param city     市
     * @param area     区
     */
    void onPickerCityListener(String province, String city, String area);
}

package com.horen.base.listener;

import java.util.List;

/**
 * @author :ChenYangYi
 * @date :2018/08/20/15:29
 * @description :多选框
 * @github :https://github.com/chenyy0708
 */
public interface MultipleSelectListener {
    /**
     * 多选集合位置
     */
    void onMultipleSelect(List<Integer> mSelect);
}

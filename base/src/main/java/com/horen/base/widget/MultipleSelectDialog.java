package com.horen.base.widget;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.allen.library.SuperButton;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.flyco.dialog.widget.base.BottomBaseDialog;
import com.horen.base.R;
import com.horen.base.bean.MultipleSelectBean;
import com.horen.base.listener.MultipleSelectListener;
import com.scwang.smartrefresh.layout.util.DensityUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author :ChenYangYi
 * @date :2018/08/17/13:10
 * @description :通用多选弹框
 * @github :https://github.com/chenyy0708
 */
public class MultipleSelectDialog extends BottomBaseDialog<MultipleSelectDialog> {
    private RecyclerView recyclerView;
    /**
     * 多选数据
     */
    private List<MultipleSelectBean> mData;
    /**
     * 标题
     */
    private TextView tvTitle;
    private String title;
    /**
     * 提交按钮
     */
    private SuperButton stb_submit;

    /**
     * 记录多选
     */
    HashMap<Integer, Boolean> mSelected = new LinkedHashMap<>();

    private MultipleSelectListener multipleSelectListener;
    private BaseQuickAdapter<MultipleSelectBean, BaseViewHolder> quickAdapter;

    public MultipleSelectDialog(Context context, List<MultipleSelectBean> mData, String title) {
        super(context);
        this.mData = mData;
        this.title = title;
        // 循环，取出是否已经有已选择集合，记录
        for (int i = 0; i < mData.size(); i++) {
            if (mData.get(i).isSelect()) {
                mSelected.put(i, true);
            }
        }
    }

    @Override
    public View onCreateView() {
        View view = View.inflate(getContext(), R.layout.dialog_multiple_select, null);
        recyclerView = view.findViewById(R.id.recycler_view);
        tvTitle = view.findViewById(R.id.tv_title);
        stb_submit = view.findViewById(R.id.stb_submit);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        tvTitle.setText(title);
        init(mData);
        checkSubmitButton(mSelected);
        return view;
    }

    @Override
    public void setUiBeforShow() {
        if (mData.size() > 7) {
            ViewGroup.LayoutParams lp = recyclerView.getLayoutParams();
            lp.height = DensityUtil.dp2px(50 * 7);
            recyclerView.setLayoutParams(lp);
        }
    }

    public void init(List<MultipleSelectBean> mData) {
        quickAdapter = new BaseQuickAdapter<MultipleSelectBean, BaseViewHolder>(R.layout.item_multiple_select, mData) {

            @Override
            protected void convert(final BaseViewHolder helper, final MultipleSelectBean item) {
                helper.setText(R.id.tv_type, item.getTabName());
                final AppCompatCheckBox checkBox = helper.getView(R.id.cb_select_item);
                // 选择
                helper.setOnClickListener(R.id.fl_multiple_select, new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        // 改变CheckBox选择状态
                        checkBox.setChecked(!item.isSelect());
                        // 记录被选择
                        mData.get(helper.getLayoutPosition())
                                .setSelect(!item.isSelect());
                        // 判断是否选择
                        if (mData.get(helper.getLayoutPosition()).isSelect()) {
                            mSelected.put(helper.getLayoutPosition(), true);
                        } else {
                            mSelected.remove(helper.getLayoutPosition());
                        }
                        // 多选个数是否为0,
                        checkSubmitButton(mSelected);
                    }
                });
                // 回显选择
                checkBox.setChecked(item.isSelect());
                checkSubmitButton(mSelected);
            }
        };

        recyclerView.setAdapter(quickAdapter);
        stb_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                if (multipleSelectListener != null) {
                    // 选择位置
                    multipleSelectListener.onMultipleSelect(getSelect());
                }
            }
        });

    }

    /**
     * 获取选择位置
     */
    public List<Integer> getSelect() {
        List<Integer> mSelect = new ArrayList<>();
        for (Map.Entry<Integer, Boolean> integerBooleanEntry : mSelected.entrySet()) {
            mSelect.add(integerBooleanEntry.getKey());
        }
        return mSelect;
    }

    /**
     * 设置新数据
     */
    public void setData(List<MultipleSelectBean> mData) {
        quickAdapter.setNewData(mData);
        // 清空
        mSelected.clear();
    }

    /**
     * 根据多选个数，判断确定按钮是否亮起
     *
     * @param mSelected 选择集合
     */
    private void checkSubmitButton(HashMap<Integer, Boolean> mSelected) {
        if (mSelected.size() > 0) {
            stb_submit.setEnabled(true);
            stb_submit.setTextColor(ContextCompat.getColor(mContext, R.color.white));
        } else {
            stb_submit.setEnabled(false);
            stb_submit.setTextColor(ContextCompat.getColor(mContext, R.color.color_999));
        }
    }

    public void setMultipleSelectListener(MultipleSelectListener multipleSelectListener) {
        this.multipleSelectListener = multipleSelectListener;
    }
}

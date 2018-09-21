package com.horen.base.widget;

import android.content.Context;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.flyco.dialog.widget.base.BottomBaseDialog;
import com.horen.base.R;
import com.horen.base.bean.RadioSelectBean;
import com.horen.base.listener.RadioSelectListener;

import java.util.List;

/**
 * @author :ChenYangYi
 * @date :2018/08/17/13:10
 * @description :通用单选弹框
 * @github :https://github.com/chenyy0708
 */
public class RadioSelectDialog extends BottomBaseDialog<RadioSelectDialog> {
    private RecyclerView recyclerView;
    /**
     * 单选数据
     */
    private List<RadioSelectBean> mData;
    /**
     * 标题
     */
    private TextView tvTitle;
    private String title;

    /**
     * 当前选择条目位置
     */
    private int currentSelectPosition = -1;

    private RadioSelectListener radioSelectListener;

    public RadioSelectDialog(Context context, List<RadioSelectBean> mData, String title) {
        this(context, mData, title, -1);
    }

    public RadioSelectDialog(Context context, List<RadioSelectBean> mData, String title, int currentSelectPosition) {
        super(context);
        this.mData = mData;
        this.title = title;
        this.currentSelectPosition = currentSelectPosition;
    }

    @Override
    public View onCreateView() {
        View view = View.inflate(getContext(), R.layout.dialog_radio_select, null);
        recyclerView = view.findViewById(R.id.recycler_view);
        tvTitle = view.findViewById(R.id.tv_title);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        return view;
    }

    @Override
    public void setUiBeforShow() {
        tvTitle.setText(title);
        setData(mData);
    }

    public void setData(List<RadioSelectBean> mData) {
        BaseQuickAdapter adapter = new BaseQuickAdapter<RadioSelectBean, BaseViewHolder>(R.layout.item_radio_select, mData) {

            @Override
            protected void convert(final BaseViewHolder helper, final RadioSelectBean item) {
                helper.setText(R.id.tv_type, item.getTabName());
                final AppCompatCheckBox checkBox = helper.getView(R.id.cb_select_item);
                // 选择
                helper.setOnClickListener(R.id.fl_multiple_select, new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        // 返回数据
                        if (radioSelectListener != null) {
                            checkBox.setChecked(true);
                            // 记录选择位置
                            currentSelectPosition = helper.getLayoutPosition();
                            radioSelectListener.onSelected(mData.get(helper.getLayoutPosition()));
                            notifyDataSetChanged();
                            dismiss();
                        }
                    }
                });
                // 指定位置显示选中
                checkBox.setChecked(currentSelectPosition == helper.getLayoutPosition());
            }
        };
        recyclerView.setAdapter(adapter);
    }

    public RadioSelectDialog setRadioSelectListener(RadioSelectListener radioSelectListener) {
        this.radioSelectListener = radioSelectListener;
        return this;
    }
}

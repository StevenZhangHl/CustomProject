package com.steven.snowhome.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.horen.base.util.ImageLoader;
import com.kevin.delegationadapter.AdapterDelegate;
import com.steven.lovesnow.R;
import com.steven.snowhome.bean.SnowHomeBean;

import java.util.List;

/**
 * Author:Steven
 * Time:2018/9/26 14:29
 * Description:This isHomeTagAdapter
 */
public class HomeTagAdapter extends BaseQuickAdapter<SnowHomeBean.TagItem, BaseViewHolder> {

    public HomeTagAdapter(int layoutResId, @Nullable List<SnowHomeBean.TagItem> data) {
        super(layoutResId, data);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    protected void convert(BaseViewHolder helper, SnowHomeBean.TagItem item) {
        helper.getItemViewType();
        ImageLoader.load(mContext, item.getImg(), (ImageView) helper.getView(R.id.iv_tag));
        helper.setText(R.id.tv_tag, item.getTitle());
    }
}

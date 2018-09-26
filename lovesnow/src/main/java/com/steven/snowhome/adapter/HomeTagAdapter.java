package com.steven.snowhome.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.horen.base.util.ImageLoader;
import com.kevin.delegationadapter.AdapterDelegate;
import com.steven.lovesnow.R;
import com.steven.snowhome.bean.SnowHomeBean;

/**
 * Author:Steven
 * Time:2018/9/26 14:29
 * Description:This isHomeTagAdapter
 */
public class HomeTagAdapter extends AdapterDelegate<SnowHomeBean.TagItem, HomeTagAdapter.ViewHolder> {
    private Context context;

    public HomeTagAdapter(Context context) {
        this.context = context;
    }

    @Override
    protected ViewHolder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.snow_item_home_tag, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    protected void onBindViewHolder(ViewHolder holder, int position, SnowHomeBean.TagItem item) {
        ImageLoader.load(context, item.getImg(), holder.iv_tag);
        holder.tv_tag.setText(item.getTitle());
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView iv_tag;
        private TextView tv_tag;

        public ViewHolder(View itemView) {
            super(itemView);
            iv_tag = (ImageView) itemView.findViewById(R.id.iv_tag);
            tv_tag = (TextView) itemView.findViewById(R.id.tv_tag);
        }
    }
}

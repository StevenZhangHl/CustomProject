package com.steven.snowhome.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.horen.base.util.ImageLoader;
import com.kevin.delegationadapter.AdapterDelegate;
import com.steven.lovesnow.R;
import com.steven.snowhome.bean.SnowHomeBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Author:Steven
 * Time:2018/9/26 13:16
 * Description:This isHomeAdapter
 */
public class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<SnowHomeBean.Article> mData = new ArrayList<>();

    public HomeAdapter(Context context) {
        this.context = context;
    }

    public void setmData(List<SnowHomeBean.Article> newData) {
        mData.addAll(newData);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.snow_item_article, parent, false);
        HomeAdapter.HomeViewHolder viewHolder = new HomeAdapter.HomeViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        HomeViewHolder homeViewHolder = (HomeViewHolder) holder;
        ImageLoader.load(context, mData.get(position).getPic(), homeViewHolder.iv_article);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class HomeViewHolder extends RecyclerView.ViewHolder {
        public ImageView iv_article;

        public HomeViewHolder(View itemView) {
            super(itemView);
            iv_article = (ImageView) itemView.findViewById(R.id.iv_article);
        }
    }
}

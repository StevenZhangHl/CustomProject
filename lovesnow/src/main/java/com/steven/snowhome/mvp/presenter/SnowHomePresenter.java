package com.steven.snowhome.mvp.presenter;

import com.google.gson.Gson;
import com.horen.base.util.AssetUtil;
import com.steven.snowhome.bean.SnowHomeBean;
import com.steven.snowhome.mvp.contract.SnowHomeContract;

/**
 * Author:Steven
 * Time:2018/9/26 9:56
 * Description:This isSnowHomePresenter
 */
public class SnowHomePresenter extends SnowHomeContract.Presenter {
    @Override
    public void getHomeData() {
        String jsonData = AssetUtil.getStringFormAsset(mContext, "goods.json");
        SnowHomeBean snowHomeBean = new Gson().fromJson(jsonData, SnowHomeBean.class);
        mView.setBannerData(snowHomeBean.getBanner());
        mView.setHomeArticlesData(snowHomeBean.getArticles());
    }
}

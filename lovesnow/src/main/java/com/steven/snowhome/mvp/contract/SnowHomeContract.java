package com.steven.snowhome.mvp.contract;

import com.horen.base.mvp.BaseModel;
import com.horen.base.mvp.BasePresenter;
import com.horen.base.mvp.BaseView;
import com.steven.snowhome.bean.SnowHomeBean;

import java.util.List;

/**
 * Author:Steven
 * Time:2018/9/26 9:58
 * Description:This isSnowHomeContract
 */
public interface SnowHomeContract {
    interface Model extends BaseModel {

    }

    interface View extends BaseView {
        void setBannerData(List<SnowHomeBean.Banner> bannerData);

        void setHomeArticlesData(List<SnowHomeBean.Article> articlesData);
    }

    abstract class Presenter extends BasePresenter<View, Model> {
        public abstract void getHomeData();
    }
}

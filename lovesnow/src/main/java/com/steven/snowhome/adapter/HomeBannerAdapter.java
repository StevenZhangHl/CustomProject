package com.steven.snowhome.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.holder.Holder;
import com.horen.base.util.ImageLoader;
import com.horen.base.util.ToastUitl;
import com.steven.snowhome.bean.SnowHomeBean;

/**
 * Author:Steven
 * Time:2018/9/26 15:47
 * Description:This isHomeBannerAdapter
 */
public class HomeBannerAdapter implements Holder<SnowHomeBean.Banner> {
    private ImageView imageView;

    @Override
    public View createView(Context context) {
        //你可以通过layout文件来创建，不一定是Image，任何控件都可以进行翻页
        imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        return imageView;
    }

    @Override
    public void UpdateUI(final Context context, int position, final SnowHomeBean.Banner data) {
        ImageLoader.loadBanner(context, data.getImg(), imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(data.getLink())) { // 链接不为空
                    ToastUitl.showShort(data.getLink());
                }
            }
        });
    }
}

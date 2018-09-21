package com.steven.snowhome.adapter;

import android.support.annotation.Nullable;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.horen.base.util.ImageLoader;
import com.steven.lovesnow.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Author:Steven
 * Time:2018/9/21 16:48
 * Description:This isWifePhotosAdapter
 */
public class WifePhotosAdapter extends BaseQuickAdapter<Integer, BaseViewHolder> {
    public WifePhotosAdapter(int layoutResId, @Nullable List<Integer> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Integer item) {
        ImageView imageView = helper.getView(R.id.iv_wife_photo);
        //改变holder.button的高度
        int height = (int) (Math.random() * 400 + 200);
        ViewGroup.LayoutParams lp = imageView.getLayoutParams();
        lp.height = height;
        imageView.setLayoutParams(lp);
        ImageLoader.loadDrawable(mContext, item, imageView);
    }
}

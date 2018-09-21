package com.horen.base.util;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.google.gson.Gson;
import com.horen.base.R;
import com.horen.base.bean.CityJsonBean;
import com.horen.base.listener.ProvincesCitiesListener;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * @author :ChenYangYi
 * @date :2018/09/05/08:55
 * @description :省市区联动选择器
 * @github :https://github.com/chenyy0708
 */
public class CityPickerViewHelper {

    //  省市区开始
    private ArrayList<CityJsonBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();

    private static final int MSG_LOAD_DATA = 0x0001;
    private static final int MSG_LOAD_SUCCESS = 0x0002;
    private static final int MSG_LOAD_FAILED = 0x0003;

    private boolean isLoaded = false;

    private Context mContext;

    private ProvincesCitiesListener listener;

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_LOAD_DATA:
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            // 写子线程中的操作,解析省市区数据
                            initJsonData();
                        }
                    }).start();
                    break;
                case MSG_LOAD_SUCCESS:
                    isLoaded = true;
                    show();
                    break;
                case MSG_LOAD_FAILED:
                    isLoaded = false;
                    break;

            }
        }
    };
    private OptionsPickerView pvOptions;

    public CityPickerViewHelper(Context mContext, ProvincesCitiesListener listener) {
        this.mContext = mContext;
        this.listener = listener;
        initJsonData();
    }

    /**
     * 显示省市区选择弹框
     */
    public void show() {
        if (pvOptions == null) {
            //设置选中项文字颜色
            pvOptions = new OptionsPickerBuilder(mContext, new OnOptionsSelectListener() {
                @Override
                public void onOptionsSelect(int options1, int options2, int options3, View v) {
                    if (listener != null) {
                        listener.onPickerCityListener(options1Items.get(options1).getPickerViewText(), options2Items.get(options1).get(options2), options3Items.get(options1).get(options2).get(options3));
                    }
                }
            }).setTitleText("城市选择")
                    .setDividerColor(Color.BLACK)
                    .setCancelColor(ContextCompat.getColor(mContext, R.color.color_main_color))
                    .setSubmitColor(ContextCompat.getColor(mContext, R.color.color_main_color))
                    .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                    .setContentTextSize(20)
                    .build();
            pvOptions.setPicker(options1Items, options2Items, options3Items);//三级选择器
        }
        pvOptions.show();
    }

    /**
     * 省市区元数据处理
     */
    private void initJsonData() {

        /**
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         *
         * */
        String JsonData = getJson(mContext, "province.json");//获取assets目录下的json文件数据

        ArrayList<CityJsonBean> jsonBean = parseData(JsonData);//用Gson 转成实体

        /**
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        options1Items = jsonBean;

        for (int i = 0; i < jsonBean.size(); i++) {//遍历省份
            ArrayList<String> CityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> Province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）

            for (int c = 0; c < jsonBean.get(i).getCityList().size(); c++) {//遍历该省份的所有城市
                String CityName = jsonBean.get(i).getCityList().get(c).getName();
                CityList.add(CityName);//添加城市

                ArrayList<String> City_AreaList = new ArrayList<>();//该城市的所有地区列表

                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                if (jsonBean.get(i).getCityList().get(c).getArea() == null
                        || jsonBean.get(i).getCityList().get(c).getArea().size() == 0) {
                    City_AreaList.add("");
                } else {

                    for (int d = 0; d < jsonBean.get(i).getCityList().get(c).getArea().size(); d++) {//该城市对应地区所有数据
                        String AreaName = jsonBean.get(i).getCityList().get(c).getArea().get(d);

                        City_AreaList.add(AreaName);//添加该城市所有地区数据
                    }
                }
                Province_AreaList.add(City_AreaList);//添加该省所有地区数据
            }

            /**
             * 添加城市数据
             */
            options2Items.add(CityList);

            /**
             * 添加地区数据
             */
            options3Items.add(Province_AreaList);
        }

//        mHandler.sendEmptyMessage(MSG_LOAD_SUCCESS);

    }

    public String getJson(Context context, String fileName) {

        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager assetManager = context.getAssets();
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    /**
     * Gson 解析
     *
     * @param result
     * @return
     */
    public ArrayList<CityJsonBean> parseData(String result) {
        ArrayList<CityJsonBean> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = GsonUtil.getGson();
            for (int i = 0; i < data.length(); i++) {
                CityJsonBean entity = gson.fromJson(data.optJSONObject(i).toString(), CityJsonBean.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
            mHandler.sendEmptyMessage(MSG_LOAD_FAILED);
        }
        return detail;
    }
}

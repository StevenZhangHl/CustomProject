package com.horen.maplib;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ZoomControls;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.LogoPosition;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.Overlay;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolygonOptions;
import com.baidu.mapapi.map.Stroke;
import com.baidu.mapapi.map.TextureMapView;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.district.DistrictResult;
import com.baidu.mapapi.search.district.DistrictSearch;
import com.baidu.mapapi.search.district.DistrictSearchOption;
import com.baidu.mapapi.search.district.OnGetDistricSearchResultListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @author :ChenYangYi
 * @date :2018/08/01/12:48
 * @description :百度地图 工具类
 * @github :https://github.com/chenyy0708
 */
public class MapHelper implements IMapView, OnGetDistricSearchResultListener {
    private MapView mapView;
    private BaiduMap mBaiduMap;
    private Context mContext;
    /**
     * 默认层级
     */
    public static final int NORMAL_ZINDEX = 0;
    /**
     * 被选中层级
     */
    public static final int SELECT_ZINDEX = 1;
    /**
     * 国家级别
     */
    public static final int COUNTRY_ZINDEX = 5;
    /**
     * 上一个位置默认值
     */
    public static final int NORMAL = -1;
    /**
     * 上一个点击的Marker位置
     */
    private int previousPosition = NORMAL;
    /**
     * Marker集合
     */
    private List<Marker> markers = new ArrayList<>();
    /**
     * 城市搜索
     */
    private DistrictSearch mDistrictSearch;
    /**
     * 边框宽度
     */
    private int strokeWidth;
    /**
     * 边框颜色
     */
    private int strokeColor;
    /**
     * 填充颜色
     */
    private int fillColor;
    private Overlay cityOverLay;

    /**
     * 初始化百度地图
     *
     * @param mContext 上下文
     * @param mapView  百度地图
     */
    public MapHelper(Context mContext, MapView mapView) {
        this.mContext = mContext;
        this.mapView = mapView;
        this.mBaiduMap = mapView.getMap();
        mapView.setLogoPosition(LogoPosition.logoPostionRightBottom);
        //关闭显示比例尺
        mapView.showScaleControl(false);
        //关闭缩放按钮
        mapView.showZoomControls(false);
        // 隐藏logo
        View child = mapView.getChildAt(1);
        if (child != null && (child instanceof ImageView || child instanceof ZoomControls)) {
            child.setVisibility(View.INVISIBLE);
        }
        initBaiduMap(mBaiduMap);
    }

    /**
     * 初始化TextureMapView百度地图,解决黑屏的问题
     *
     * @param mContext 上下文
     * @param mapView  百度地图
     */
    public MapHelper(Context mContext, TextureMapView mapView) {
        this.mContext = mContext;
        this.mBaiduMap = mapView.getMap();
        mapView.setLogoPosition(LogoPosition.logoPostionRightBottom);
        //关闭显示比例尺
        mapView.showScaleControl(false);
        //关闭缩放按钮
        mapView.showZoomControls(false);
        // 隐藏logo
        View child = mapView.getChildAt(1);
        if (child != null && (child instanceof ImageView || child instanceof ZoomControls)) {
            child.setVisibility(View.INVISIBLE);
        }
        initBaiduMap(mBaiduMap);
    }

    /**
     * 初始化公共操作
     */
    private void initBaiduMap(BaiduMap mBaiduMap) {
        //普通地图
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        //关闭显示指南针
        mBaiduMap.getUiSettings().setCompassEnabled(false);
        //屏蔽双指下拉时变成3D地图
        mBaiduMap.getUiSettings().setOverlookingGesturesEnabled(false);
        //创建行政区域查询的实例
        mDistrictSearch = DistrictSearch.newInstance();
        mDistrictSearch.setOnDistrictSearchListener(this);
    }

    /**
     * 根据点集合，显示缩放比例
     *
     * @param list 点集合
     */
    @Override
    public void setZoomWithLatLngs(List<LatLng> list) {
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (LatLng latLng : list) {
            builder.include(latLng);
        }
        LatLngBounds bounds = builder.build();
        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newLatLngBounds(bounds));
    }

    /**
     * 移动地图到指定经纬度
     *
     * @param lat        经度
     * @param lng        纬度
     * @param latLngZoom 缩放比率
     */
    @Override
    public void moveTo(double lat, double lng, float latLngZoom) {
        MapStatusUpdate msu = MapStatusUpdateFactory.newLatLngZoom(new LatLng(lat, lng), latLngZoom);
        mBaiduMap.animateMapStatus(msu);
    }

    /**
     * 移动中心点到Marker位置
     *
     * @param lat 经度
     * @param lng 纬度
     */
    @Override
    public void moveTo(double lat, double lng) {
        MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(new LatLng(lat, lng));
        mBaiduMap.animateMapStatus(msu);
    }

    /**
     * 添加Marker标记
     *
     * @param latitude   经度
     * @param longitude  纬度
     * @param drawableId 资源id
     * @param index      marker在集合中位置
     * @param zIndex     层级
     */
    @Override
    public void addMarker(double latitude, double longitude, int drawableId, int index, int zIndex) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("index", index);
        LatLng latLng = new LatLng(latitude, longitude);
        BitmapDescriptor icon = BitmapDescriptorFactory
                .fromResource(drawableId);
        MarkerOptions option = new MarkerOptions()
                .position(latLng)
                .extraInfo(bundle)
                .zIndex(zIndex)
                .icon(icon);
        Marker marker = (Marker) mBaiduMap.addOverlay(option);
        markers.add(marker);
    }

    /**
     * 添加Marker标记
     *
     */
    public void addMarkers(List<OverlayOptions> options) {
        //在地图上批量添加
        mBaiduMap.addOverlays(options);
    }

    /**
     * 修改Marker标记Icon
     *
     * @param position   Marker位置
     * @param drawableId 资源id
     * @param zIndex     层级
     */
    @Override
    public void changeMarkerIcon(int position, int drawableId, int zIndex) {
        // 防止空指针
        if (position > markers.size() - 1) {
            return;
        }
        BitmapDescriptor icon = BitmapDescriptorFactory
                .fromResource(drawableId);
        markers.get(position).setZIndex(zIndex);
        markers.get(position).setIcon(icon);
    }

    /**
     * 清除MapView，Marker点和Listener
     */
    @Override
    public void clearMap() {
        for (Marker marker : markers) {
            marker.remove();
        }
        markers.clear();
        previousPosition = NORMAL;
    }

    public List<Marker> getMarkers() {
        return markers;
    }

    /**
     * Mapview
     */
    public MapView getMapView() {
        return mapView;
    }

    /**
     * BaiduMap
     */
    public BaiduMap getBaiduMap() {
        return mBaiduMap;
    }

    /**
     * 地图点击事件
     *
     * @param onMapClickListener 点击监听
     */
    @Override
    public void setOnMapClickListener(final MapListener.MapClickListener onMapClickListener) {
        mBaiduMap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                onMapClickListener.onMapClickListener(latLng.latitude, latLng.longitude);
            }

            @Override
            public boolean onMapPoiClick(MapPoi mapPoi) {
                return false;
            }
        });
    }

    /**
     * Maker点击事件
     *
     * @param onMarkerClickListenertener 点击监听
     */
    @Override
    public void setOnMarkerClickListener(final MapListener.MarkerClickListener onMarkerClickListenertener) {
        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                int currentPosition = (int) marker.getExtraInfo().getSerializable("index");
                // 重复点击Marker 拦截
                if (currentPosition == previousPosition) {
                    return false;
                }
                if (onMarkerClickListenertener != null) {
                    onMarkerClickListenertener.onMarkerClickListener(currentPosition, previousPosition);
                }
                previousPosition = currentPosition;
                return false;
            }
        });
    }

    /**
     * 获取上一个点击的Marker点
     *
     * @return 位置
     */
    public int getPreviousPosition() {
        return previousPosition == NORMAL ? 0 : previousPosition;
    }

    /**
     * 用于点击地图重置Marker的点击状态
     *
     * @param previousPosition 位置
     */
    public void setPreviousPosition(int previousPosition) {
        this.previousPosition = previousPosition;
    }

    /**
     * 高亮某一城市区域
     *
     * @param cityName    城市名
     * @param strokeWidth 边框宽度
     * @param strokeColor 边框颜色
     * @param fillColor   填充颜色
     */
    @Override
    public void setHighlightCity(String cityName, int strokeWidth, int strokeColor, int fillColor) {
        this.strokeWidth = strokeWidth;
        this.strokeColor = strokeColor;
        this.fillColor = fillColor;
        //建立搜索条件
        DistrictSearchOption option = new DistrictSearchOption().cityName(cityName).districtName(cityName);
        //执行行政区域的搜索
        mDistrictSearch.searchDistrict(option);
    }

    /**
     * 搜索城市监听
     *
     * @param districtResult 结果
     */
    @Override
    public void onGetDistrictResult(DistrictResult districtResult) {
        if (districtResult.error == SearchResult.ERRORNO.NO_ERROR) {
            // 搜索城市点集合
            List<List<LatLng>> pointsList = districtResult.getPolylines();
            // 搜索不到结果
            if (pointsList == null) {
                return;
            }
            //地理边界对象
            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            // 将点集合聚合到Overlay 绘制覆盖物
            for (List<LatLng> polyline : pointsList) {
                // 填充颜色
                OverlayOptions ooPolygon = new PolygonOptions().points(polyline)
                        .stroke(new Stroke(strokeWidth, strokeColor)).fillColor(fillColor);
                cityOverLay = mBaiduMap.addOverlay(ooPolygon);
                for (LatLng latLng : polyline) {
                    //包含这些点
                    builder.include(latLng);
                }
            }
            // 显示区域
            mBaiduMap.animateMapStatus(MapStatusUpdateFactory
                    .newLatLngBounds(builder.build()));
        }
    }

    /**
     * 清除城市覆盖物
     */
    public void clearCityOverLay() {
        if (cityOverLay != null) {
            cityOverLay.remove();
        }
    }
}

package com.horen.base.net;

import com.horen.base.bean.UploadBean;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

import static me.jessyan.retrofiturlmanager.RetrofitUrlManager.DOMAIN_NAME_HEADER;

/**
 * @author :ChenYangYi
 * @date :2018/08/08/12:43
 * @description :
 * @github :https://github.com/chenyy0708
 */
public interface UploadApi {
    /**
     * 上传文件
     */
    @Headers({DOMAIN_NAME_HEADER + Url.UPLOAD})
    @Multipart
    @POST("uploads/batch")
    Observable<UploadBean> uploadFile(@PartMap Map<String, RequestBody> map, @Part("file") MultipartBody body);
}

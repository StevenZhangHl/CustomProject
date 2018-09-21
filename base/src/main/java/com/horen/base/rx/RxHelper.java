package com.horen.base.rx;


import com.horen.base.app.BaseApp;
import com.horen.base.bean.BaseEntry;
import com.horen.base.bean.UploadBean;
import com.horen.base.net.NetManager;
import com.horen.base.util.LogUtils;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import top.zibin.luban.Luban;

/**
 * @author :ChenYangYi
 * @date :2018/4/17
 * @description : RxJava2工具类，用于线程切换等
 */
public class RxHelper {

    /**
     * 用于订阅转换，返回CompositeDisposable便于取消订阅，同意管理
     *
     * @param observable 被订阅者
     * @param observer   订阅者
     */
    public static <T> Disposable subscribe(Observable<T> observable, final Observer<T> observer) {
        return observable.subscribe(
                new Consumer<T>() {
                    @Override
                    public void accept(T t) throws Exception {
                        observer.onNext(t);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        observer.onError(throwable);
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        observer.onComplete();
                    }
                }, new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        observer.onSubscribe(disposable);
                    }
                }
        );
    }

    /**
     * 线程切换
     *
     * @param <T> 对象
     */
    public static <T> ObservableTransformer<T, T> applySchedulers() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    /**
     * 上传图片
     *
     * @param files   压缩图片集合
     * @param map     上传参数
     * @param builder 图片参数
     */
    public static ObservableTransformer<List<String>, UploadBean> uploadFile(final List<File> files, final Map<String, RequestBody> map, final MultipartBody.Builder builder) {
        // 压缩图片
        return new ObservableTransformer<List<String>, UploadBean>() {
            @Override
            public Observable<UploadBean> apply(Observable<List<String>> upstream) {
                return upstream.flatMap(new Function<List<String>, Observable<UploadBean>>() {
                    @Override
                    public Observable<UploadBean> apply(List<String> paths) throws Exception {
                        // 子线程循环压缩
                        for (String filePath : paths) {
                            File file = Luban.with(BaseApp.getAppContext()).load(filePath).get(filePath);
                            builder.addFormDataPart("file", file.getName(), RequestBody.create(MultipartBody.FORM, file));
                            // 添加压缩图片到集合中，用于完成删除本地压缩文件
                            files.add(file);
                        }
                        return NetManager.getInstance().getUploadApi().uploadFile(map, builder.build()).compose(RxHelper.<UploadBean>applySchedulers());
                    }
                }).compose(RxHelper.<UploadBean>applySchedulers());

            }
        };
    }

    /**
     * 上传图片
     *
     * @param files 压缩图片集合
     */
    public static ObservableTransformer<List<String>, UploadBean> uploadFile(final List<File> files) {
        // 上传图片参数
        final Map<String, RequestBody> map = new HashMap<>();
        map.put("type", RequestBody.create(MediaType.parse("form-data"), "photo"));
        final MultipartBody.Builder builder = new MultipartBody.Builder();
        // 压缩图片
        return new ObservableTransformer<List<String>, UploadBean>() {
            @Override
            public Observable<UploadBean> apply(Observable<List<String>> upstream) {
                return upstream.flatMap(new Function<List<String>, Observable<UploadBean>>() {
                    @Override
                    public Observable<UploadBean> apply(List<String> paths) throws Exception {
                        LogUtils.d("Thread:" + Thread.currentThread().getName());
                        // 子线程循环压缩
                        for (String filePath : paths) {
                            File file = Luban.with(BaseApp.getAppContext()).load(filePath).get(filePath);
                            builder.addFormDataPart("file", file.getName(), RequestBody.create(MultipartBody.FORM, file));
                            // 添加压缩图片到集合中，用于完成删除本地压缩文件
                            files.add(file);
                        }
                        return NetManager.getInstance().getUploadApi().uploadFile(map, builder.build()).compose(RxHelper.<UploadBean>applySchedulers());
                    }
                }).compose(RxHelper.<UploadBean>applySchedulers());

            }
        };
    }


    /**
     * Rx优雅处理服务器返回
     *
     * @param <T>
     * @return
     */
    public static <T extends BaseEntry> ObservableTransformer<T, T> handleResult() {
        return new ObservableTransformer<T, T>() {
            @Override
            public Observable<T> apply(Observable<T> upstream) {
                return upstream.flatMap(new Function<T, Observable<T>>() {
                    @Override
                    public Observable<T> apply(T entry) throws Exception {
                        if (entry.success()) {
                            return createData(entry);
                        } else {
                            return Observable.error(new HRException(entry.getHeader().getRet_message()));
                        }
                    }
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());

            }
        };
    }

    /**
     * Rx优雅处理服务器返回
     *
     * @param <T>
     * @return
     */
    public static <T> ObservableTransformer<BaseEntry<T>, T> getResult() {
        return new ObservableTransformer<BaseEntry<T>, T>() {
            @Override
            public Observable<T> apply(Observable<BaseEntry<T>> upstream) {
                return upstream.flatMap(new Function<BaseEntry<T>, Observable<T>>() {
                    @Override
                    public Observable<T> apply(BaseEntry<T> entry) throws Exception {
                        if (entry.success()) {
                            return createData(entry.getDataResponse());
                        } else {
                            return Observable.error(new HRException(entry.getHeader().getRet_message()));
                        }
                    }
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());

            }
        };
    }

    /**
     * 创建成功的数据
     *
     * @param data
     * @param <T>
     * @return
     */
    public static <T> Observable<T> createData(final T data) {
        return Observable.create(new ObservableOnSubscribe<T>() {
            @Override
            public void subscribe(ObservableEmitter<T> emitter) throws Exception {
                {
                    try {
                        emitter.onNext(data);
                        emitter.onComplete();
                    } catch (Exception e) {
                        emitter.onError(e);
                    }
                }
            }
        });

    }
}

package com.horen.base.util;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;

/**
 * @author :ChenYangYi
 * @date :2018/08/13/10:43
 * @description :Glide保存图片
 * @github :https://github.com/chenyy0708
 */

public class GlideFileHelper {

    private Context context;

    public GlideFileHelper() {
    }

    public GlideFileHelper(Context context) {
        super();
        this.context = context;
    }

    /**
     * Glide保存图片
     *
     * @param fileName 文件名
     * @param url      图片地址
     */
    public void savePicture(final String fileName, String url) {

    }

    /**
     * Glide保存本地资源图片
     *
     * @param fileName 文件名
     * @param imgRId   本地资源id
     */
    public void savePicture(final String fileName, Integer imgRId) {
    }

    //往SD卡写入文件的方法
    public void savaFileToSD(String filename, byte[] bytes) throws Exception {
        //如果手机已插入sd卡,且app具有读写sd卡的权限
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            String filePath = Environment.getExternalStorageDirectory().getCanonicalPath() + "/budejie";
            File dir1 = new File(filePath);
            if (!dir1.exists()) {
                dir1.mkdirs();
            }
            filename = filePath + "/" + filename;
            //这里就不要用openFileOutput了,那个是往手机内存中写数据的
            FileOutputStream output = new FileOutputStream(filename);
            output.write(bytes);
            //将bytes写入到输出流中
            output.close();
            //关闭输出流
//            Toast.makeText(context, context.getString(R.string.save_success) + filePath, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);


            Uri uri = Uri.fromFile(dir1);
            intent.setData(uri);
            //这个广播的目的就是更新图库，发了这个广播进入相册就可以找到你保存的图片了！，记得要传你更新的file哦
            context.sendBroadcast(intent);
        } else Toast.makeText(context, "SD卡不存在或者不可读写", Toast.LENGTH_SHORT).show();
    }


    public static Bitmap convertViewToBitmap(View view) {
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();

        return bitmap;
    }


    public void saveForImageView(String filename, View view) throws Exception {
        //如果手机已插入sd卡,且app具有读写sd卡的权限
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            Bitmap bitmap = convertViewToBitmap(view);
            String filePath = Environment.getExternalStorageDirectory().getCanonicalPath() + "/budejie";
            File dir1 = new File(filePath);
            if (!dir1.exists()) {
                dir1.mkdirs();
            }
            filename = filePath + "/" + filename;
            FileOutputStream fos = new FileOutputStream(filename);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, fos);
            fos.flush();
            fos.close();
            //关闭输出流
//            Toast.makeText(context, context.getString(R.string.save_success) + filePath, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            Uri uri = Uri.fromFile(dir1);
            intent.setData(uri);
            //这个广播的目的就是更新图库，发了这个广播进入相册就可以找到你保存的图片了！，记得要传你更新的file哦
            context.sendBroadcast(intent);
        } else Toast.makeText(context, "SD卡不存在或者不可读写", Toast.LENGTH_SHORT).show();
    }
}

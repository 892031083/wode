package com.example.wode.api;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.wode.AppManager;
import com.example.wode.api.result.OnResultListener;
import com.example.wode.api.result.Result;
import com.example.wode.api.result.ResultProduct;
import com.example.wode.utils.OkHttpUtils;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutionException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ImageTool {
    //1.创建对应的MediaType
    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
    private final OkHttpClient client = new OkHttpClient();

    public static String getImgUrl(String url) {
        if (url==null || url.equals("") || url.length()<10){
          //  return  ApiUrl.BASE_URL + "upload/photo/1586258485839.jpg";
            return "defus";
        }
        if (url.substring(0, 4).equals("http")) {
            return url;
        } else {
            return ApiUrl.BASE_URL + "upload/" + url;
        }
    }

    /**
     * @param userName
     * @param file
     */
    public void uploadImage(String userName, File file) {


        RequestBody fileBody = RequestBody.create(MEDIA_TYPE_PNG, file);


        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", "testImage.png", fileBody)
                .addFormDataPart("userName", userName)
                .build();


    }

    /**
     * base64上传方式
     *
     * @param path
     * @param onResultListener
     */
    public void uploadBase64Img(String path, OnResultListener<Result> onResultListener) {
        //3.构建MultipartBody

        //4.构建请求
        RequestBody formBody = new FormBody.Builder()   //创建表单请求体，具体的参数设置也要去看源码才更清楚
                .add("img", imageToBase64(path))      //自己设的，不要盗用我的名儿！
                .add("filename", System.currentTimeMillis() + "")
                .build();

        //4.构建请求
        Request request = new Request.Builder()
                .url(ApiUrl.BASE_URL + "user/user/uploadimg")
                .post(formBody)
                .build();

        //5.发送请求
        AppManager.getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String str = response.body().string();
                Log.i("RESULT", str);
            }
        });
    }
    /**
     * base64上传方式 (直接传过来batmap)
     *
     * @param
     * @param onResultListener
     */
    public void uploadBitmapBase64Img(Bitmap b, final OnResultListener<Result> onResultListener) {

        RequestBody formBody = new FormBody.Builder()   //
                .add("img", bitmapToBase64(b))      //！
                .add("filename", System.currentTimeMillis() + "")
                .build();

        //构建请求
        Request request = new Request.Builder()
                .url(ApiUrl.BASE_URL + "user/user/uploadimg")
                .post(formBody)
                .build();

        //.发送请求
        AppManager.getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String str = response.body().string();
                JSONObject jsonObject= null;
                Log.i("RESULT",str);
                try {
                    jsonObject = new JSONObject(str);
                    Result result=AppManager.getGson().fromJson(jsonObject.toString(), Result.class);
                    onResultListener.onSuccess(result);
                    Log.i("RESULT", str);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }
    /**
     * 将图片转换成Base64编码的字符串
     */
    public static String imageToBase64(String path) {
        if (TextUtils.isEmpty(path)) {
            return null;
        }
        InputStream is = null;
        byte[] data = null;
        String result = null;
        try {
            is = new FileInputStream(path);
            //创建一个字符流大小的数组。
            data = new byte[is.available()];
            //写入数组
            is.read(data);
            //用默认的编码格式进行编码
            result = Base64.encodeToString(data, Base64.NO_CLOSE);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return result;
    }
    private static String bitmapToBase64(Bitmap bitmap) {
        String result = null;
        ByteArrayOutputStream baos = null;
        try {
            if (bitmap != null) {
                baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

                baos.flush();
                baos.close();

                byte[] bitmapBytes = baos.toByteArray();
                result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.flush();
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}

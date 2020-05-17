package com.example.wode.utils;

import com.example.wode.AppManager;

import java.io.IOException;

import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;

public class OkHttpUtils {
    /**
     * http请求接口
     * @param url
     * @return
     */
    public static final MediaType FORM_CONTENT_TYPE
            = MediaType.parse("application/x-www-form-urlencoded; charset=utf-8");

    public static Request buildRequest(String url){

        if (AppManager.isNetWorkAvaiable()){
            Request request=new Request.Builder()
                    .tag("okhttp")
                    .url(url)
                    .build();

            return request;
        }
        return null;
    }


//    public static void postexcute(String url,)

    /**
     * get请求
     * @param url
     * @param callback
     */
    public static void excute(String url, Callback callback){
        Request request=buildRequest(url);
        excute(request,callback);
    }



    public static void excute(Request request, Callback callback){
        AppManager.getOkHttpClient().newCall(request).enqueue(callback);

    }
    public static Response Synexcute(String url) {
        Request request=buildRequest(url);
        try {
          return  AppManager.getOkHttpClient().newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}

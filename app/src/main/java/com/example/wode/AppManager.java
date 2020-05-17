package com.example.wode;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.google.gson.Gson;

import okhttp3.OkHttpClient;

public class AppManager extends Application {
//    private static Gson
    private static Gson gson;
    private static OkHttpClient okHttpClient;
    private static Context context;

    public static Gson getGson() {
        return gson;
    }

    public static OkHttpClient getOkHttpClient() {
        return okHttpClient;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        gson=new Gson();
        okHttpClient=new OkHttpClient();
        context=this;
    }

    public static Context getContext() {
        return context;
    }

    public static boolean isNetWorkAvaiable(){
        //判断网络是否可用
        ConnectivityManager connectivityManager= (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();

        return networkInfo!=null && networkInfo.isConnected();//当前网络是否可用
    }
}

package com.example.wode.api;

import android.util.Log;

import com.example.wode.AppManager;
import com.example.wode.api.result.OnResultListener;
import com.example.wode.api.result.Result;
import com.example.wode.api.result.ResultNews;
import com.example.wode.utils.OkHttpUtils;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * 新闻请求接口
 */
public class NewsApi {
    /**
     * 用的是天行云数据API 我的账号最多只能请求 10*10条数据
     */
    public static String API_KEY="8a95cd39d4c4aef915f4718c3f91812b";

    public static String API_UTL="http://api.tianapi.com/caijing/index?";

    public static int NUM=10;//每次15条数据

    public void OnGetNews(int page, final OnResultListener<ResultNews> onResultListener){
        String url=getNewsUrl(page);
        OkHttpUtils.excute(url, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                //错误
               // Log.i("1==============",call.toString());
            }
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                //请求成功
                String str=response.body().string();
                ResultNews result= AppManager.getGson().fromJson(str,ResultNews.class);
                onResultListener.onSuccess(result);
                Log.i("2==============",str);
            }
        });
    }

    private String getNewsUrl(int page) {

        return API_UTL+"key="+API_KEY+"&+page="+page+"&+num="+NUM+"&rand=1";
    }

}

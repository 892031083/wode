package com.example.wode.api;

import android.util.Log;

import com.example.wode.AppManager;
import com.example.wode.UserUtilsBean;
import com.example.wode.api.result.OnResultListener;
import com.example.wode.api.result.ResultLogin;
import com.example.wode.api.result.ResultProduct;
import com.example.wode.util.User;
import com.example.wode.utils.OkHttpUtils;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class LoginApi {
    /**
     * 登陆数据请求的api
     */
    public static String REGISTERED_URL="user/user/registered?mobile=%s&user_pass=%s";
    public static String LOGIN_URL="user/user/login?mobile=%s&user_pass=%s";

    public static String LOGIN_USERINFO="user/user/setuserinfo?useravatar=%s&username=%s&user_id=%s";
   // public static String REGISTERED_URL="user/user/registered";

    public void toRegistered(String name, String pass, final OnResultListener<ResultLogin> onResultListener){
        String url=ApiUrl.BASE_URL+String.format(REGISTERED_URL,name,pass);
        OkHttpUtils.excute(url, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String str=response.body().string();
                Log.i("==============",str);

                try {
                    JSONObject jsonObject=new JSONObject(str);
                    Log.i("==============",jsonObject.toString());
                    ResultLogin resultLogin=  AppManager.getGson().fromJson(jsonObject.toString(), ResultLogin.class);
                    onResultListener.onSuccess(resultLogin);
                } catch (JSONException e) {
                    onResultListener.onSuccess(new ResultLogin(0,"json解析错误",null));

                    e.printStackTrace();
                }
            }
        });
    }

    public void toLogin(String name, String pass, final OnResultListener<ResultLogin> onResultListener){
        String url=ApiUrl.BASE_URL+String.format(LOGIN_URL,name,pass);
        OkHttpUtils.excute(url, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String str=response.body().string();
                Log.i("==============",str);

                try {
                    JSONObject jsonObject=new JSONObject(str);
                    Log.i("==============",jsonObject.toString());
                    ResultLogin resultLogin=  AppManager.getGson().fromJson(jsonObject.toString(), ResultLogin.class);
                    onResultListener.onSuccess(resultLogin);
                } catch (JSONException e) {
                    onResultListener.onSuccess(new ResultLogin(0,"json解析错误",null));

                    e.printStackTrace();
                }
            }
        });
    }

    public void setUserInfo(String avatar,String nickname,final OnResultListener<ResultLogin> onResultListener){
        String url=ApiUrl.BASE_URL+String.format(LOGIN_USERINFO,avatar,nickname, UserUtilsBean.getInstance().getUser().getUserId());
        OkHttpUtils.excute(url, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    String str=response.body().string();
                try {
                    JSONObject jsonObject=new JSONObject(str);
                    ResultLogin resultLogin=AppManager.getGson().fromJson(jsonObject.toString(),ResultLogin.class);
                    onResultListener.onSuccess(resultLogin);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}

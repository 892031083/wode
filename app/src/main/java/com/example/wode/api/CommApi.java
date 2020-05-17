package com.example.wode.api;

import android.util.Log;

import com.example.wode.AppManager;
import com.example.wode.api.result.OnResultListener;
import com.example.wode.api.result.ResultComm;
import com.example.wode.util.Comment;
import com.example.wode.utils.OkHttpUtils;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CommApi {
    public static String ADDCOMM_URL="product/product/addcomment";

    public static String FIND_URL="product/product/getcomment?product_id=%s";

    public void OnGetComment(int pdouctid, final OnResultListener<ResultComm> onResultListener){
        String url=ApiUrl.BASE_URL+String.format(FIND_URL,pdouctid);
        OkHttpUtils.excute(url, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String str=response.body().string();
                try {
                    JSONObject jsonObject=new JSONObject(str);
                    ResultComm resultComm= AppManager.getGson().fromJson(jsonObject.toString(),ResultComm.class);
                    onResultListener.onSuccess(resultComm);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void SaveComment(Comment comment, final OnResultListener<ResultComm> onResultListener){
        String url=ApiUrl.BASE_URL+String.format(ADDCOMM_URL,comment.getUserId(),comment.getUserName(),comment.getUserurl(),comment.getCommentText(),comment.getProid());
        RequestBody formBody = new FormBody.Builder()
                .add("userid", String.valueOf(comment.getUserId()))      //！
                .add("username", comment.getUserName())
                .add("useravatar",comment.getUserurl())
                .add("pid",comment.getProid()+"")
                .add("data",comment.getCommentText())
                .build();

        //构建请求
        Request request = new Request.Builder()
                .url(ApiUrl.BASE_URL + ADDCOMM_URL)
                .post(formBody)
                .build();

        //.发送请求

        Log.i("===================",url);
        AppManager.getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String str=response.body().string();
                try {
                    JSONObject jsonObject=new JSONObject(str);
                    ResultComm resultComm= AppManager.getGson().fromJson(jsonObject.toString(),ResultComm.class);
                    onResultListener.onSuccess(resultComm);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

}

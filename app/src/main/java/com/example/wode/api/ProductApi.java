package com.example.wode.api;

import android.util.Log;
import android.widget.Toast;

import com.example.wode.AppManager;
import com.example.wode.api.ApiUrl;
import com.example.wode.api.result.OnResultListener;
import com.example.wode.api.result.Result;
import com.example.wode.api.result.ResultNews;
import com.example.wode.api.result.ResultProduct;
import com.example.wode.util.Product;
import com.example.wode.utils.OkHttpUtils;
import com.google.gson.JsonObject;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ProductApi {
    public static String PRODUCT_FINDS_URL="product/product/finds?type=%s";

    public static String PRODUCT_RECOMM_URL="product/product/recomm";//推荐

    public static String PRODUCT_FINDID_URL="product/product/findS?id=%s";

    public static String ADD_PRODUCT_URL="product/product/addproduct?product_name=%s&product_type=%s&product_photo=%s&product_price=%s&product_num=%s&" +
            "create_time=%s&user_id=%s&user_name=%s&user_mobile=%s";

    public void OnGetProductList(int userId, final OnResultListener<ResultProduct> onResultListener){//我的发布 可以简化，不过写这个的时候没考虑到。。
        String url= ApiUrl.BASE_URL+String.format(PRODUCT_FINDID_URL,userId);
        OkHttpUtils.excute(url, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String str=response.body().string();
                try {
                    JSONObject jsonObject=new JSONObject(str);
                    ResultProduct result= AppManager.getGson().fromJson(jsonObject.toString(), ResultProduct.class);
                    onResultListener.onSuccess(result);
                    //      Log.i("=========",jsonObject.toString());
                } catch (JSONException e) {
                    onResultListener.onSuccess(new ResultProduct(0,"json解析错误",null));

                    e.printStackTrace();
                }

            }
        });
    }

    public void OnGetProductList(String type, final OnResultListener<ResultProduct> onResultListener){//查询所有
        String url= ApiUrl.BASE_URL+String.format(PRODUCT_FINDS_URL,type);
        OkHttpUtils.excute(url, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String str=response.body().string();
                try {
                    JSONObject jsonObject=new JSONObject(str);
                    ResultProduct result= AppManager.getGson().fromJson(jsonObject.toString(), ResultProduct.class);
                    onResultListener.onSuccess(result);
              //      Log.i("=========",jsonObject.toString());
                } catch (JSONException e) {
                    onResultListener.onSuccess(new ResultProduct(0,"json解析错误",null));

                    e.printStackTrace();
                }

            }
        });
    }

    public void OnGetProductRecomm(final OnResultListener<ResultProduct> onResultListener){
        OkHttpUtils.excute(ApiUrl.BASE_URL + PRODUCT_RECOMM_URL, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String str=response.body().string();

                try {
                    JSONObject jsonObject=new JSONObject(str);
                    ResultProduct result= AppManager.getGson().fromJson(jsonObject.toString(), ResultProduct.class);
                    onResultListener.onSuccess(result);
                    //      Log.i("=========",jsonObject.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                    onResultListener.onSuccess(new ResultProduct(0,"json解析错误",null));

                }
            }
        });
    }
//    public static String ADD_PRODUCT_URL="product/product/addproduct?product_name=%s&product_type=%s&product_photo=%s&product_price=%s&product_num=%s&" +
//            "create_time=%s&user_id=%s&user_nackname=%s&user_mobile=%s";
    public void SaveProduct(Product product, final OnResultListener<ResultProduct> onResultListener){
        String url=ApiUrl.BASE_URL+String.format(ADD_PRODUCT_URL,product.getProductName(),product.getType(),product.getImageUrl(),product.getPrive(),product.getNum(),
                System.currentTimeMillis(),product.getUserId(),product.getUserNickName(),product.getMobile());
        OkHttpUtils.excute(url, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String str=response.body().string();
                Log.i("RESULT",str);
                try {
                    JSONObject jsonObject=new JSONObject(str);
                    ResultProduct result= AppManager.getGson().fromJson(jsonObject.toString(), ResultProduct.class);
                    onResultListener.onSuccess(result);

                } catch (JSONException e) {
                    e.printStackTrace();
                    onResultListener.onSuccess(new ResultProduct(0,"json解析错误",null));
                }
            }
        });
    }
}

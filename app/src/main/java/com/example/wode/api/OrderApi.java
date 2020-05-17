package com.example.wode.api;

import android.util.Log;

import com.example.wode.AppManager;
import com.example.wode.api.result.OnResultListener;
import com.example.wode.api.result.ResultOrder;
import com.example.wode.api.result.ResultProduct;
import com.example.wode.util.OrderForm;
import com.example.wode.utils.OkHttpUtils;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class OrderApi {

    public static String ADDORDER_URL="order/order/topay?user_id=%s&user_name=%s&user_mobile=%s&price=%s&ctime=%s&order_data=%s&id=%s";
    public static String GETORDER_URL="order/order/findorder?user_id=%s";


    public void SaveOrder(OrderForm orderForm, final OnResultListener<ResultOrder> orderOnResultListener) {
        String url = ApiUrl.BASE_URL + String.format(ADDORDER_URL, orderForm.getUser_id(), orderForm.getUser_name(), orderForm.getUser_mobile(), orderForm.getPrive(),
                orderForm.getCreate_time(), orderForm.getDataJson(),System.currentTimeMillis());
        OkHttpUtils.excute(url, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String str = response.body().string();
                Log.i("RESULT", str);
                try {
                    JSONObject jsonObject = new JSONObject(str);
                    ResultOrder result = AppManager.getGson().fromJson(jsonObject.toString(), ResultOrder.class);
//                    AppManager.getGson().
                    orderOnResultListener.onSuccess(result);

                } catch (JSONException e) {
                    e.printStackTrace();
                    orderOnResultListener.onSuccess(new ResultOrder(0, "json解析错误", null));
                }
            }
        });
    }

    public void getOrders(int userId, final OnResultListener<ResultOrder> onResultListener){
        String url=ApiUrl.BASE_URL+String.format(GETORDER_URL,userId);
        OkHttpUtils.excute(url, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String str = response.body().string();
                Log.i("RESULT", str);
                try {
                    JSONObject jsonObject = new JSONObject(str);
                    ResultOrder result = AppManager.getGson().fromJson(jsonObject.toString(), ResultOrder.class);
                    onResultListener.onSuccess(result);

                } catch (JSONException e) {
                    e.printStackTrace();
                    onResultListener.onSuccess(new ResultOrder(0, "json解析错误", null));
                }
            }
        });
    }


 }
package com.example.wode.home.fragment;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.wode.UserUtilsBean;
import com.example.wode.api.ApiUrl;
import com.example.wode.api.NewsApi;
import com.example.wode.api.OrderApi;
import com.example.wode.api.ProductApi;
import com.example.wode.api.result.OnResultListener;
import com.example.wode.api.result.Result;
import com.example.wode.api.result.ResultNews;
import com.example.wode.api.result.ResultOrder;
import com.example.wode.api.result.ResultProduct;
import com.example.wode.util.Order;
import com.example.wode.util.Product;

import java.util.ArrayList;
import java.util.List;

public class FragmentPraenter implements FragmentContract.FragmentParenter {

    private FragmentContract.FragmentView fragmentView;
    public static int ResultType_SHOW=3;
    public static int ResultType_FIND_LOAD=4;
    public static int ResultType_FIND_REFRESH=5;
    public static int ERROR=5;
    String errorString="";
    private List list;//返回的list
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (fragmentView!=null) {
               if (msg.what==ResultType_SHOW){
                   fragmentView.showData(list);
               }
               if (msg.what==ResultType_FIND_LOAD){
                   fragmentView.onLoad(list);
               }
                if (msg.what==ResultType_FIND_REFRESH){
                    fragmentView.onRefresh(list);
                }
                if (msg.what==ERROR){

                }
            }
        }
    };

    public FragmentPraenter(FragmentContract.FragmentView fragmentView){
        this.fragmentView=fragmentView;
    }

    @Override
    public void getRecommends() {//获取首页推荐数据
        //TODO model
        if (ApiUrl.ISHTTP){
            ProductApi productApi=new ProductApi();
            productApi.OnGetProductRecomm(new OnResultListener<ResultProduct>() {
                @Override
                public void onSuccess(ResultProduct listResult) {
                    if (listResult.getCode()==200){
                        list=listResult.getData();
                        handler.sendEmptyMessage(ResultType_SHOW);
                    }else {
                        errorString=listResult.getTitle();
                        handler.sendEmptyMessage(ERROR);
                    }
                }

                @Override
                public void onError() {

                }
            });
        }else {
            fragmentView.showData(Product.getProductList(4));
        }
    }

    @Override
    public void getOrders() {//获取订单页面数据
        if (ApiUrl.ISHTTP){
            //去请求
            OrderApi orderApi=new OrderApi();
            orderApi.getOrders(UserUtilsBean.getInstance().getUser().getUserId(), new OnResultListener<ResultOrder>() {
                @Override
                public void onSuccess(ResultOrder resultOrder) {
                    if (resultOrder.getCode()==200){
                        list=resultOrder.getList();
                        handler.sendEmptyMessage(ResultType_SHOW);
                    }else {
                        errorString=resultOrder.getMsg();
                        handler.sendEmptyMessage(ERROR);
                    }
                }

                @Override
                public void onError() {

                }
            });
        }else {
            fragmentView.showData(Order.getOrderList(10));
        }
    }

    @Override
    public void getFinds() {
        toFindModel(ResultType_SHOW,1);
    }



    @Override
    public void getUserInfo() {

    }

    @Override
    public void onFindLoad(int page) {//发现也加载
        toFindModel(ResultType_FIND_LOAD,page);
    }

    @Override
    public void onFindRefresh(int page) {//发i西安也刷新
        toFindModel(ResultType_FIND_REFRESH,page);
    }

    private void toFindModel(final int type,int page) {
        NewsApi newsApi=new NewsApi();
        newsApi.OnGetNews(page, new OnResultListener<ResultNews>() {//参数page 页数 及 请求回掉
            @Override
            public void onSuccess(final ResultNews resultNews) {
                //Log.i("22============",resultNews.getCode()+"");
                if (resultNews.getCode()==200){//代表成功
                    list=resultNews.getList();
                    handler.sendEmptyMessage(type);//发送消息
                }
            }
            @Override
            public void onError() {

            }
        });
    }
}

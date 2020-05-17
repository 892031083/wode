package com.example.wode.home;

import android.os.Handler;
import android.os.Message;

import com.example.wode.api.ProductApi;
import com.example.wode.api.ApiUrl;
import com.example.wode.api.result.OnResultListener;
import com.example.wode.api.result.Result;
import com.example.wode.api.result.ResultProduct;
import com.example.wode.util.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductPraenter implements ProductContract.HomePraenter {
    /**
     */
    private ProductContract.HomeView homeView;
    private ProductApi productApi;//请求数据的model
    private int APITYPE_GETFINDS=100;
    private int APITYPE_LOAD=200;
    private List<Product> products;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==100){
                toShowView();
            }
            homeView.hideLoad();
        }
    };

    public ProductPraenter(ProductContract.HomeView homeView) {
        this.homeView=homeView;
        productApi=new ProductApi();
    }
    private boolean isSowAll0=false,isSowAll1=false,isSowAll2=false,isSowAll3=false;//是否已经显示全部
    private boolean isLoading[]={false,false,false,false};//是否正在加载 防止反复请求
    @Override
    public void getProducts() {
        homeView.showLoad();//显示加载
        //TODO
        if (ApiUrl.ISHTTP){
            //这里去api请求数据
            productApi.OnGetProductList("全部", new OnResultListener<ResultProduct>() {
                @Override
                public void onSuccess(ResultProduct listResult) {

                    if (listResult.getCode()==200){
                        products=listResult.getData();
                        handler.sendEmptyMessage(APITYPE_GETFINDS);
                    }else {
                        //TODO
                    }
                }

                @Override
                public void onError() {

                }
            });


        }else {//测试数据
            homeView.initViewPagerItem(Product.getProductList(20),0);
            homeView.initViewPagerItem(Product.getProductList(20),1);
            homeView.initViewPagerItem(Product.getProductList(20),2);
            homeView.initViewPagerItem(Product.getProductList(20),3);
            homeView.hideLoad();
        }

    }

    @Override
    public void toLoad(int ItemId, int pageon, int page) {
        //TODO
        if (ItemId==0 && isSowAll0) return;
        if (ItemId==1 && isSowAll1) return;
        if (ItemId==2 && isSowAll2) return;
        if (ItemId==3 && isSowAll3) return;
        if (isLoading[ItemId]) return;;
        //TODO model
        homeView.OnLoading();
        isLoading[ItemId]=true;
    }

    public void toShowView(){
        List<Product> list1=new ArrayList<>();
        List<Product> list2=new ArrayList<>();
        List<Product> list3=new ArrayList<>();
        List<Product> list4=new ArrayList<>();
        for (Product product:products){
            if (product.getType().equals("电子产品")) list1.add(product);
            if (product.getType().equals("学习用品")) list2.add(product);
            if (product.getType().equals("体育用品")) list3.add(product);
            if (product.getType().equals("生活用品")) list4.add(product);
        }
        homeView.initViewPagerItem(list1,0);
        homeView.initViewPagerItem(list2,1);
        homeView.initViewPagerItem(list3,2);
        homeView.initViewPagerItem(list4,3);
    }
}

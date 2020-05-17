package com.example.wode.home.fragment;

import com.example.wode.base.MainListener;
import com.example.wode.util.Product;

import java.util.List;

public interface FragmentContract  {
    interface FragmentModel extends MainListener.BaseModel{

    }
    interface FragmentParenter extends MainListener.BasePraenter{
        void getRecommends();//获取首页推荐
        void getOrders();//获取订单
        void getFinds();//获取发现数据
        void getUserInfo();//获取用户信息
        void onFindLoad(int page);//发现页面加载
        void onFindRefresh(int page);//发现界面刷新
    }
    interface FragmentView extends MainListener.BaseView{
        void showData(List list);//展示数据
        void onLoad(List list);
        void onRefresh(List list);
    }

}

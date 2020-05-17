package com.example.wode.home;

import com.example.wode.base.MainListener;
import com.example.wode.util.Product;

import java.util.List;

public interface ProductContract {


    interface HomeModel extends MainListener.BaseModel{

    }

    interface HomePraenter extends MainListener.BasePraenter{
        void getProducts();
        void toLoad(int ItemId,int pageon,int page);//上拉加载更多 //分页 pageon代表第几条开始 page代表共几条数据
    }

    interface HomeView extends MainListener.BaseView{
        void initViewPagerItem(List<Product> list, int i);//初始化数据
        void updatePagerItem(List<Product> list,int i);
        void showLoad();
        void hideLoad();
    }


}

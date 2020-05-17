package com.example.wode.base;

/**
 * 采用 mvp 模式   所有接口的父亲
 */
public interface MainListener {
    interface BaseView{
        void OnLoading();//设置布局加载中

    }
    interface BaseModel{

    }
    interface BasePraenter{

    }
}

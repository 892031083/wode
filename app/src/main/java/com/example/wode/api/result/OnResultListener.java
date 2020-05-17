package com.example.wode.api.result;

/**
 * 请求数据时回掉接口
 */
public interface OnResultListener<T> {
    void onSuccess(T t);

    void onError();
}

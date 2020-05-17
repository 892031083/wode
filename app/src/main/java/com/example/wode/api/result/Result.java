package com.example.wode.api.result;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result<T> {
    @Expose
    int code;//200代表成功
    @SerializedName("msg")
    @Expose
    String title;
    @SerializedName("info")
    @Expose
    T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Result(int code, String title, T data) {
        this.code = code;
        this.title = title;
        this.data = data;
    }
}

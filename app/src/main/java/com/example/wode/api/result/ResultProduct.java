package com.example.wode.api.result;

import com.example.wode.util.Product;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResultProduct {
    @Expose
    int code;//200代表成功
    @SerializedName("msg")
    @Expose
    String title;
    @SerializedName("info")
    @Expose
    List<Product> data;

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

    public List<Product> getData() {
        return data;
    }

    public void setData(List<Product> data) {
        this.data = data;
    }

    public ResultProduct(int code, String title, List<Product> data) {
        this.code = code;
        this.title = title;
        this.data = data;
    }
}

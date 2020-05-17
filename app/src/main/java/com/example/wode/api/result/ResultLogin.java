package com.example.wode.api.result;

import com.example.wode.util.Product;
import com.example.wode.util.User;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResultLogin {
    @Expose
    int code;//200代表成功
    @SerializedName("msg")
    @Expose
    String title;
    @SerializedName("info")
    @Expose
    User data;

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

    public void setData(User data) {
        this.data = data;
    }

    public User getData() {
        return data;
    }

    public ResultLogin(int code, String title, User data) {
        this.code = code;
        this.title = title;
        this.data = data;
    }
}

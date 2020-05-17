package com.example.wode.api.result;

import com.example.wode.util.News;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResultNews {
    @Expose
    private int code;
    @SerializedName("msg")//表示jion对应的字段
    @Expose
    private String msg;
    @SerializedName("newslist")//表示jion对应的字段
    @Expose
    private List<News> list;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<News> getList() {
        return list;
    }

    public void setList(List<News> list) {
        this.list = list;
    }

    public ResultNews(int code, String msg, List<News> list) {
        this.code = code;
        this.msg = msg;
        this.list = list;
    }

    @Override
    public String toString() {
        return "ResultNews{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", list=" + list +
                '}';
    }
}

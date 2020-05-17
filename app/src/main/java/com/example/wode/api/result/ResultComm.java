package com.example.wode.api.result;

import com.example.wode.util.Comment;
import com.example.wode.util.OrderForm;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResultComm {
    @Expose
    private int code;
    @SerializedName("msg")//表示jion对应的字段
    @Expose
    private String msg;
    @SerializedName("info")//表示jion对应的字段
    @Expose
    private List<Comment> list;


    public ResultComm(int code, String msg, List<Comment> list) {
        this.code = code;
        this.msg = msg;
        this.list = list;
    }

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

    public List<Comment> getList() {
        return list;
    }

    public void setList(List<Comment> list) {
        this.list = list;
    }
}

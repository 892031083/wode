package com.example.wode.util;

import java.io.Serializable;

public class CartKeep extends BaseUtil implements Serializable {
    int type;//1 购物车 2.收藏
    int Id;//id
    long createTime;//创建时间 毫秒数
    String productName;//商品名称
    int productId;//商品ID
    String productImg;//商品图片链接
    int num;//数量
    int selected;//是否选中 1选择 2未选中
    double money;//价格金钱

    public CartKeep(int type) {
        this.type = type;
    }

    public CartKeep(int type, long createTime, String productName, int productId, String productImg, int num, int selected, double money) {
        this.type = type;

        this.createTime = createTime;
        this.productName = productName;
        this.productId = productId;
        this.productImg = productImg;
        this.num = num;
        this.selected = selected;
        this.money = money;
    }

    public static CartKeep ProductToCart(Product product,int num,int type){//商品类转购物车类
        return new CartKeep(type,System.currentTimeMillis(),product.getProductName(),product.getProductID(),product.getImageUrl(),num,1,product.getPrive());
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductImg() {
        return productImg;
    }

    public void setProductImg(String productImg) {
        this.productImg = productImg;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getSelected() {
        return selected;
    }

    public void setSelected(int selected) {
        this.selected = selected;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }
}

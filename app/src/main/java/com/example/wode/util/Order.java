package com.example.wode.util;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 订单类
 */
public class Order extends BaseUtil implements Parcelable {
    int productId;
    int id;
    int UserID;
    int ProductNum;
    int ProductUserId;
    double sumPrice;//总价
    double productMoney;//商品单价
    String ProductImgUrl;
    String ProductType;
    String ProductName;
    String ProductUserName;
    long createTime;//创建时间


    protected Order(Parcel in) {
        productId = in.readInt();
        id = in.readInt();
        UserID = in.readInt();
        ProductNum = in.readInt();
        ProductUserId = in.readInt();
        sumPrice = in.readDouble();
        productMoney = in.readDouble();
        ProductImgUrl = in.readString();
        ProductType = in.readString();
        ProductName = in.readString();
        ProductUserName = in.readString();
        createTime = in.readLong();
    }

    public static final Creator<Order> CREATOR = new Creator<Order>() {
        @Override
        public Order createFromParcel(Parcel in) {
            return new Order(in);
        }

        @Override
        public Order[] newArray(int size) {
            return new Order[size];
        }
    };

    public static List<OrderForm> getOrderList(int num){
        List<OrderForm> list=new ArrayList<>();
        for (int i=0;i<num;i++){
            CartKeep cartKeep=new CartKeep(0,System.currentTimeMillis(),"摄像机",10,"https://ns-strategy.cdn.bcebos.com/ns-strategy/upload/fc_big_pic/part-00715-2870.jpg",
                    10,1,10);
            List<CartKeep> list1=new ArrayList<>();
            list1.add(cartKeep);
            OrderForm orderForm=new OrderForm(0,100,System.currentTimeMillis(),1,"刘**","11111","");
            orderForm.setDataJson(orderForm.getOrderData(list1));
            list.add(orderForm);
        }

        return list;
    }

    public static String getTime(long millSecond){
        Date time = new Date(millSecond);
        SimpleDateFormat formats = new SimpleDateFormat("yyyy-mm-dd HH:mm");
        return formats.format(time);
    }

    public Order(int productId, int id, int userID, int productNum, int productUserId, double sumPrice, double productMoney, String productImgUrl,String pt, String productName, String productUserName, long createTime) {
        this.productId = productId;
        this.id = id;
        UserID = userID;
        ProductNum = productNum;
        ProductUserId = productUserId;
        this.sumPrice = sumPrice;
        this.ProductType=pt;
        this.productMoney = productMoney;
        ProductImgUrl = productImgUrl;
        ProductName = productName;
        ProductUserName = productUserName;
        this.createTime = createTime;
    }

    public String getProductType() {
        return ProductType;
    }

    public void setProductType(String productType) {
        ProductType = productType;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int userID) {
        UserID = userID;
    }

    public int getProductNum() {
        return ProductNum;
    }

    public void setProductNum(int productNum) {
        ProductNum = productNum;
    }

    public int getProductUserId() {
        return ProductUserId;
    }

    public void setProductUserId(int productUserId) {
        ProductUserId = productUserId;
    }

    public double getSumPrice() {
        return sumPrice;
    }

    public void setSumPrice(double sumPrice) {
        this.sumPrice = sumPrice;
    }

    public double getProductMoney() {
        return productMoney;
    }

    public void setProductMoney(double productMoney) {
        this.productMoney = productMoney;
    }

    public String getProductImgUrl() {
        return ProductImgUrl;
    }

    public void setProductImgUrl(String productImgUrl) {
        ProductImgUrl = productImgUrl;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getProductUserName() {
        return ProductUserName;
    }

    public void setProductUserName(String productUserName) {
        ProductUserName = productUserName;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    /**
     * Describe the kinds of special objects contained in this Parcelable
     * instance's marshaled representation. For example, if the object will
     * include a file descriptor in the output of {@link #writeToParcel(Parcel, int)},
     * the return value of this method must include the
     * {@link #CONTENTS_FILE_DESCRIPTOR} bit.
     *
     * @return a bitmask indicating the set of special object types marshaled
     * by this Parcelable object instance.
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Flatten this object in to a Parcel.
     *
     * @param dest  The Parcel in which the object should be written.
     * @param flags Additional flags about how the object should be written.
     *              May be 0 or {@link #PARCELABLE_WRITE_RETURN_VALUE}.
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(productId);
        dest.writeInt(id);
        dest.writeInt(UserID);
        dest.writeInt(ProductNum);
        dest.writeInt(ProductUserId);
        dest.writeDouble(sumPrice);
        dest.writeDouble(productMoney);
        dest.writeString(ProductImgUrl);
        dest.writeString(ProductType);
        dest.writeString(ProductName);
        dest.writeString(ProductUserName);
        dest.writeLong(createTime);
    }
}

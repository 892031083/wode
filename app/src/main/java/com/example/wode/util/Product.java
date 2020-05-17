package com.example.wode.util;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.wode.api.ApiUrl;
import com.example.wode.api.ImageTool;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Product extends BaseUtil implements Parcelable {
    @SerializedName("id")
    @Expose
    private  int ProductID;//商品id
    @SerializedName("product_name")
    @Expose
    private  String productName;
    @SerializedName("user_mobile")
    @Expose
    private  String mobile;//联系方式
    private  String productData;//物品信息

    private  User user;//发起人
    @SerializedName("user_id")
    @Expose
    private int userId;
    @SerializedName("user_nackname")
    @Expose
    private String userNickName;
    @SerializedName("product_photo")
    @Expose
    private String imageUrl;//商品图片
    @SerializedName("product_num")
    @Expose
    private  int num;//捐赠数量
    @SerializedName("product_price")
    @Expose
    private double prive;//价格

    @SerializedName("product_type")
    @Expose
    private String type;//类型
    /*
    这个位置不做数据库了  直接固定死 0代表电子产品 1.学习用品 2.体育用品 3.生活用品
     */
    private int sortId=1;//分类id

    public Product() {
    }

    public Product(int productID, String productName, String mobile, String productData, User user, String imageUrl, int num, double prive, int sortId) {
        ProductID = productID;
        this.productName = productName;
        this.mobile = mobile;
        this.productData = productData;
        this.user = user;
        this.imageUrl = ImageTool.getImgUrl(imageUrl);
        this.num = num;
        this.prive = prive;
        this.sortId=0;

    }

    public int getSortId() {
        return sortId;
    }

    public void setSortId(int sortId) {
        this.sortId = sortId;
    }

    protected Product(Parcel in) {
        ProductID = in.readInt();
        productName = in.readString();
        mobile = in.readString();
        productData = in.readString();
        imageUrl = in.readString();
        num = in.readInt();
        prive = in.readDouble();
        sortId=in.readInt();
        userId = in.readInt();
        userNickName=in.readString();

    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    //测试用的
    public static List<Product> getProductList(int num){
        List<Product> list=new ArrayList<>();

        for (int i=0;i<num;i++){
            list.add(new Product(i+1,"摄像机","18766666666","商品的描述",
                    new User(10,"刘**"),"https://ns-strategy.cdn.bcebos.com/ns-strategy/upload/fc_big_pic/part-00715-2870.jpg",10,11.5,0));

        }
        return list;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserNickName() {
        return userNickName;
    }

    public void setUserNickName(String userNickName) {
        this.userNickName = userNickName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getPrive() {
        return prive;
    }

    public void setPrive(double prive) {
        this.prive = prive;
    }

    public int getProductID() {
        return ProductID;
    }

    public void setProductID(int productID) {
        ProductID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getMobile() {
        return mobile;
    }

    public String getImageUrl() {
        return ImageTool.getImgUrl(imageUrl);
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getProductData() {
        return productData;
    }

    public void setProductData(String productData) {
        this.productData = productData;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public String toString() {
        return "Product{" +
                "ProductID=" + ProductID +
                ", productName='" + productName + '\'' +
                ", mobile='" + mobile + '\'' +
                ", productData='" + productData + '\'' +
                ", user=" + user +
                ", imageUrl='" + imageUrl + '\'' +
                ", num=" + num +
                ", prive=" + prive +
                ", sortId=" + sortId +
                '}';
    }

    /**
     * 序列化过程
     * @param dest
     * @param flags
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(ProductID);
        dest.writeString(productName);
        dest.writeString(mobile);
        dest.writeString(productData);
        dest.writeString(imageUrl);
        dest.writeInt(num);
        dest.writeDouble(prive);
        dest.writeInt(sortId);
        dest.writeInt(userId);
        dest.writeString(userNickName);
    }
}

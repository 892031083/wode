package com.example.wode.util;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.wode.api.ImageTool;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class User implements Parcelable {
    @SerializedName("id")
    @Expose
    private int UserId;
    @SerializedName("user_nickname")
    @Expose
    private String nickName;
    @SerializedName("mobile")
    @Expose
    private String userMobile;
    @SerializedName("user_pass")
    @Expose
    private String userPass;
    @SerializedName("avatar")
    @Expose
    private String avatarUrl;//头像地址

    private String QQValue;//qq

    private String sex1;//性别

    private String WXValue;//微信

    public User(int userId, String nickName, String userMobile, String userPass, String avatarUrl) {
        UserId = userId;
        this.nickName = nickName;
        this.userMobile = userMobile;
        this.userPass = userPass;
        this.avatarUrl = avatarUrl;
    }

    /**
     * 复制一个
     * @param user
     */
    public User(User user){
        this.UserId = user.getUserId();
        this.nickName = user.getNickName();
        this.userMobile = user.getUserMobile();
        this.userPass = user.getUserPass();
        this.avatarUrl = user.getAvatarUrl();
        this.QQValue=user.getQQValue();
        this.sex1=user.getSex();
        this.WXValue=user.getWXValue();
    }
    public User copuUser(User user){
       return new User(user);
    }



    public User(int userId, String nickName) {
        UserId = userId;
        this.nickName = nickName;
    }

    protected User(Parcel in) {
        UserId = in.readInt();
        nickName = in.readString();
        userMobile = in.readString();
        userPass = in.readString();
        avatarUrl = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public String getNickName() {
        return nickName==null?"":nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public String getUserPass() {
        return userPass;
    }

    public void setUserPass(String userPass) {
        this.userPass = userPass;
    }

    public String getAvatarUrl() {

        return ImageTool.getImgUrl(avatarUrl);
    }
    public String getAvatarUrl2(){
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(UserId);
        dest.writeString(nickName);
        dest.writeString(userMobile);
        dest.writeString(userPass);
        dest.writeString(avatarUrl);
    }

    public String getQQValue() {
        return QQValue==null?"":QQValue;
    }

    public void setQQValue(String QQValue) {
        this.QQValue = QQValue;
    }

    public String getSex() {
        String oustr=(sex1==null?"":sex1);
        if (!oustr.equals("男")&&!oustr.equals("女")){
            return "男";
        }
        return oustr;
    }

    public void setSex(String sex) {
        this.sex1 = sex;
    }

    public String getWXValue() {
        return WXValue==null?"":WXValue;
    }

    public void setWXValue(String WXValue) {
        this.WXValue = WXValue;
    }

    @Override
    public String toString() {
        return "User{" +
                "UserId=" + UserId +
                ", nickName='" + nickName + '\'' +
                ", userMobile='" + userMobile + '\'' +
                ", userPass='" + userPass + '\'' +
                ", avatarUrl='" + avatarUrl + '\'' +
                '}';
    }

    public static User getTestUser(){
        return new User(1,"测试用户","18763485251","","https://dss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=218375221,1552855610&fm=111&gp=0.jpg");
    }
 }

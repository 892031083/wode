package com.example.wode.util;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Comment extends BaseUtil implements Parcelable {
    @SerializedName("id")
    @Expose
    int commentID;
    User user;
    @SerializedName("username")
    @Expose
    String userName;
    @SerializedName("useravatar")
    @Expose
    String userurl;
    @SerializedName("userid")
    @Expose
    int userId;
    @SerializedName("ctime")
    @Expose
    String date;//评论时间
    @SerializedName("data")
    @Expose
    String commentText;//评论内容
    int goNum;//点赞数量
    @SerializedName("pid")
    @Expose
    int proid;//商品id

    public int getProid() {
        return proid;
    }

    public void setProid(int proid) {
        this.proid = proid;
    }

    public Comment() {

    }

    public static List<Comment> getCommentList(int num){
        List<Comment> list=new ArrayList<>();
        for (int i=0;i<num;i++){
            list.add(new Comment(1,"刘**","",10,"2020.4.3","好速度啊是大师傅电风扇地方公交卡的卡号的空间啊撒犯得上反对顶顶顶顶顶顶顶顶顶顶顶顶啊实打实打算大苏打大苏打",
                            10));
        }
        return list;
    }
    public Comment(int commentID, User user, String date, String commentText, int goNum) {
        this.commentID = commentID;
        this.user = user;
        this.date = date;
        this.commentText = commentText;
        this.goNum = goNum;
    }

    public Comment(int commentID, String userName, String userurl, int userId, String date, String commentText, int goNum) {
        this.commentID = commentID;
        this.userName = userName;
        this.userurl = userurl;
        this.userId = userId;
        this.date = date;
        this.commentText = commentText;
        this.goNum = goNum;
    }

    protected Comment(Parcel in) {
        commentID = in.readInt();
        user = in.readParcelable(User.class.getClassLoader());
        date = in.readString();
        commentText = in.readString();
        goNum = in.readInt();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserurl() {
        return userurl;
    }

    public void setUserurl(String userurl) {
        this.userurl = userurl;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public static final Creator<Comment> CREATOR = new Creator<Comment>() {
        @Override
        public Comment createFromParcel(Parcel in) {
            return new Comment(in);
        }

        @Override
        public Comment[] newArray(int size) {
            return new Comment[size];
        }
    };

    public int getCommentID() {
        return commentID;
    }

    public void setCommentID(int commentID) {
        this.commentID = commentID;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public int getGoNum() {
        return goNum;
    }

    public void setGoNum(int goNum) {
        this.goNum = goNum;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "commentID=" + commentID +
                ", user=" + user +
                ", date='" + date + '\'' +
                ", commentText='" + commentText + '\'' +
                ", goNum=" + goNum +
                '}';
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
        dest.writeInt(commentID);
        dest.writeParcelable(user, flags);
        dest.writeString(date);
        dest.writeString(commentText);
        dest.writeInt(goNum);
    }
}

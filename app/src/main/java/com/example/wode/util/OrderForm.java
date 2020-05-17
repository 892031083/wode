package com.example.wode.util;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.example.wode.AppManager;
import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.util.List;

/**
 * 订单类
 */
public class OrderForm extends BaseUtil implements Parcelable {
    @Expose
    long id;
    @SerializedName("sum")
    @Expose
    double prive;//总价
    @Expose
    long create_time;//创建时间
    @Expose
    int user_id;//用户id
    @Expose
    String user_name;
    @Expose
    String user_mobile;
    @SerializedName("order_data")
    @Expose
    String dataJson;//订单内容

    protected OrderForm(Parcel in) {
        id = in.readLong();
        prive = in.readDouble();
        create_time = in.readLong();
        user_id = in.readInt();
        user_name = in.readString();
        user_mobile = in.readString();
        dataJson = in.readString();
    }

    public static final Creator<OrderForm> CREATOR = new Creator<OrderForm>() {
        @Override
        public OrderForm createFromParcel(Parcel in) {
            return new OrderForm(in);
        }

        @Override
        public OrderForm[] newArray(int size) {
            return new OrderForm[size];
        }
    };

    public List<CartKeep> getOrderData(String data){//获取订单的商品信息
        Gson gson=new Gson();
        data=data.replace("&quot;","\"");
        Log.i("AAAAAAAAAA",data);

        List<CartKeep> list= AppManager.getGson().fromJson(data, new TypeToken<List<CartKeep>>() {}.getType());
        return list;
    }

    public String  getOrderData(List<CartKeep> list){
//        JSONObject jsonObject =new JSONObject(list);
//        String str="[";
//        for (CartKeep cartKeep:list){
//            str+=new Gson().toJson(cartKeep);
//            str+=",";
//        }
//        str=str.substring(0,str.length()-1);
//        str+="]";
        return AppManager.getGson().toJson(list);
        //return  gson.toJson(list);
    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getPrive() {
        return prive;
    }

    public void setPrive(double prive) {
        this.prive = prive;
    }

    public long getCreate_time() {
        return create_time;
    }

    public void setCreate_time(long create_time) {
        this.create_time = create_time;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_mobile() {
        return user_mobile;
    }

    public void setUser_mobile(String user_mobile) {
        this.user_mobile = user_mobile;
    }

    public String getDataJson() {
        return dataJson;
    }

    public void setDataJson(String dataJson) {
        this.dataJson = dataJson;
    }

    public OrderForm(long id, double prive, long create_time, int user_id, String user_name, String user_mobile, String dataJson) {

        this.id = id;
        this.prive = prive;
        this.create_time = create_time;
        this.user_id = user_id;
        this.user_name = user_name;
        this.user_mobile = user_mobile;
        this.dataJson = dataJson;
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
        dest.writeLong(id);
        dest.writeDouble(prive);
        dest.writeLong(create_time);
        dest.writeInt(user_id);
        dest.writeString(user_name);
        dest.writeString(user_mobile);
        dest.writeString(dataJson);
    }
}

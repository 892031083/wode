package com.example.wode.api;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.util.Log;

import com.example.wode.AppManager;
import com.example.wode.base.DataBaseHelper;
import com.example.wode.util.CartKeep;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据库管理类
 */
public class DataBaseTool  {
    public static DataBaseTool dataBaseTool;
    public static final String DATABASENAME="sqlWode";
    private DataBaseHelper dataBaseHelper;
    private DataBaseTool(){
        dataBaseHelper=new DataBaseHelper(AppManager.getContext(),DATABASENAME,1);
    }
    //单利模式
    public static DataBaseTool getInstance(){
        if (dataBaseTool==null){
            synchronized (DataBaseTool.class){
                if (dataBaseTool==null){
                    dataBaseTool=new DataBaseTool();
                }
            }
        }
        return dataBaseTool;
    }

  public boolean addCartKeepData(CartKeep cartKeep) {
   //   dataBaseHelper=new DataBaseHelper(AppManager.getContext(),DATABASENAME,1);
      if (cartKeep.getType()==1){
          //购物车
          addCartData(cartKeep);
      }else {
          return addKeepData(cartKeep);
      }
      return true;
   }

    private boolean addKeepData(CartKeep cartKeep) {
        List<CartKeep> list = getCartKeepList(cartKeep.getType(), cartKeep.getProductId());//先查询是否存在 如果存在则数量加1
        ContentValues values = new ContentValues();

        if (list == null || list.size() <= 0) {
            values.put("seletedis", cartKeep.getSelected());
            values.put("productName", cartKeep.getProductName());
            values.put("imgurl", cartKeep.getProductImg());
            values.put("money", cartKeep.getMoney());
            values.put("productNum", cartKeep.getNum());
            values.put("createTime", cartKeep.getCreateTime());
            values.put("productId", cartKeep.getProductId());
            values.put("type", cartKeep.getType());
            SQLiteDatabase sqLiteDatabase = dataBaseHelper.getWritableDatabase();
            sqLiteDatabase.insert("cartandkeep", null, values);
            return true;

        }else {

            DeleteCartData(list.get(0).getId());// //如果有 则取消收藏(删除)
            return false;
        }
    }
    private void addCartData(CartKeep cartKeep) {

        ContentValues values = new ContentValues();

        List<CartKeep> list = getCartKeepList(cartKeep.getType(), cartKeep.getProductId());//先查询是否存在 如果存在则数量加1
        if (list == null || list.size() <= 0) {
            values.put("seletedis", cartKeep.getSelected());
            values.put("productName", cartKeep.getProductName());
            values.put("imgurl", cartKeep.getProductImg());
            values.put("money", cartKeep.getMoney());
            values.put("productNum", cartKeep.getNum());
            values.put("createTime", cartKeep.getCreateTime());
            values.put("productId", cartKeep.getProductId());
            values.put("type", cartKeep.getType());
            SQLiteDatabase sqLiteDatabase = dataBaseHelper.getWritableDatabase();
            sqLiteDatabase.insert("cartandkeep", null, values);
        }else{//修改数量
            upDateCartNum(list.get(0).getNum()+1,list.get(0).getProductId(),list.get(0).getType());
        }
    }

    private void upDateCartNum(int num,int productId,int type) {

        SQLiteDatabase sqLiteDatabase=dataBaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("productNum", num);

        int update = sqLiteDatabase.update("cartandkeep", values, " type=? and productId=?",new String[]{type+"",""+productId});
    }

    public List<CartKeep> getCartKeepList(int type,int productid){
        SQLiteDatabase sqLiteDatabase=dataBaseHelper.getWritableDatabase();
       Cursor cursor=sqLiteDatabase.rawQuery("select * from cartandkeep where type="+type+(productid==0?"":" and productId="+productid) ,null);
        //Cursor cursor=sqLiteDatabase.rawQuery("select * from cartandkeep " ,null);

        List<CartKeep> list=new ArrayList<>();
        while (cursor.moveToNext()){
            CartKeep cartKeep=new CartKeep(type);
            cartKeep.setId(cursor.getInt(cursor.getColumnIndex("id")));
            cartKeep.setProductName(cursor.getString(cursor.getColumnIndex("productName")));
            cartKeep.setSelected(cursor.getInt(cursor.getColumnIndex("seletedis")));
            cartKeep.setProductImg(cursor.getString(cursor.getColumnIndex("imgurl")));
            cartKeep.setMoney(cursor.getDouble(cursor.getColumnIndex("money")));
            cartKeep.setNum(cursor.getInt(cursor.getColumnIndex("productNum")));
            cartKeep.setCreateTime(cursor.getLong(cursor.getColumnIndex("createTime")));
            cartKeep.setProductId(cursor.getInt(cursor.getColumnIndex("productId")));
            list.add(cartKeep);
        }
        Log.i("===============",list.size()+"ss");
        return list;
    }

    public void DeleteCartData(int id){
        SQLiteDatabase sqLiteDatabase=dataBaseHelper.getWritableDatabase();
        sqLiteDatabase.execSQL("DELETE FROM cartandkeep WHERE id = "+id);
    }

    public void asyupdateCartNum(final List<int[]> list){
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                SQLiteDatabase sqLiteDatabase=dataBaseHelper.getWritableDatabase();
                for (int [] ints:list){
                    ContentValues values = new ContentValues();//批量修改
                    values.put("productNum", ints[1]);
                    sqLiteDatabase.update("cartandkeep", values, " id=?",new String[]{ints[0]+""});
                }

            }
        });
    }
}

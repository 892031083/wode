package com.example.wode.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;



public  class Power {
    public static int userID=0;

    public static void getDialog(String string,String mesg,Context context){
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        builder.setTitle(string);
        builder.setMessage(mesg);
        builder.setCancelable(false);//是否莫泰
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        builder.create().show();
    }

}

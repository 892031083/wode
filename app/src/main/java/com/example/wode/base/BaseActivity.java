package com.example.wode.base;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.wode.R;
import com.example.wode.api.ApiUrl;
import com.example.wode.widget.LoadDialog;
import com.example.wode.widget.ToastDialog;

/**
 * 这个类为所有Activity的第一级父类
 * 功能为UI的参数
 */
public abstract class BaseActivity extends AppCompatActivity {
    private ImageView outbtn;
    private boolean IsTitle=false;
    private ViewGroup onload;
    private LoadDialog loadDialog;//加载布局
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT){
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }


        if (IsShowTitle()){//是否带有标题栏以及加载Layout
            //加载带有标题栏的布局
            ViewGroup baseView= (ViewGroup) getLayoutInflater().inflate(R.layout.activity_base,null);//带有标题栏的父布局
            baseView.findViewById(R.id.outbtn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
            onload=baseView.findViewById(R.id.onloading);//加载中布局
            baseView.addView(getLayoutInflater().inflate(getLayoutId(),null));
            setContentView(baseView);
           // showOnloading(false);
            IsTitle=true;//是否拥有标题栏标识

        }else{//不带有加载布局以及标题栏

            setContentView(getLayoutId());
        }

        initView();
        initData();

    }

    protected abstract boolean IsShowTitle();//是否显示标题栏

    protected void showOnloading(boolean is){//弹出加载布局
       if (is){
           loadDialog=new LoadDialog(this,"正在加载");
           loadDialog.setCancelable(false);
           loadDialog.show();
       }else {
           if (loadDialog!=null )loadDialog.cancel();
       }
    }

    protected void setTitleText(String title){
        ((TextView)findViewById(R.id.titleText)).setText(title);
    }
    public void showMsg(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    //是否联网的开关
    public void testInfo(){
        final ToastDialog toastDialog=new ToastDialog(this,"是否联网模式?点击取消是静态数据");
        toastDialog.setCancelable(false);
        toastDialog.setOnClickDialog(new ToastDialog.OnClickDialog() {
            @Override
            public void onFixClick() {
                //是
                toastDialog.dismiss();
                ApiUrl.ISHTTP=true;
            }

            @Override
            public void onCancelClick() {
                //否
                ApiUrl.ISHTTP=false;
                toastDialog.dismiss();
            }
        });

        toastDialog.show();
    }

    protected abstract void initData();

    protected abstract void initView();

    protected abstract int getLayoutId();

}

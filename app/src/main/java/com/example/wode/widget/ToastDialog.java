package com.example.wode.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.wode.R;

public class ToastDialog extends Dialog {
    public OnClickDialog getOnClickDialog() {
        return onClickDialog;
    }

    public void setOnClickDialog(OnClickDialog onClickDialog) {
        this.onClickDialog = onClickDialog;
    }

    private OnClickDialog onClickDialog;
    public ToastDialog(Context context, String content) {
        super(context, R.style.CustomDialog);
        initView(context,content);
    }

    private void initView(Context context,String title) {

        //View imgview= LayoutInflater.from(context).inflate(R.layout.layout_toastdialog,null);

        setContentView(R.layout.layout_toastdialog);
        ((TextView)findViewById(R.id.title)).setText(title);
        findViewById(R.id.yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickDialog.onFixClick();
            }
        });
        findViewById(R.id.no).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickDialog.onCancelClick();
            }
        });
        setCanceledOnTouchOutside(true);
        // setCancelable(false);//屏蔽回退
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
       // attributes.alpha=0.8f;
        getWindow().setAttributes(attributes);
        setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {//屏蔽回退键

                return true;
            }
        });
    }
    public interface OnClickDialog{
        void onFixClick();//确定
        void onCancelClick();//取消
    }
}

package com.example.wode.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.wode.R;

public class LoadDialog extends Dialog {
    private String content;

    public LoadDialog(Context context, String content) {
        super(context, R.style.CustomDialog);
        this.content=content;
        initView();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode){
            case KeyEvent.KEYCODE_BACK:
                if(LoadDialog.this.isShowing())
                    LoadDialog.this.dismiss();
                break;
        }
        return true;
    }

    private void initView(){
        setContentView(R.layout.layout_loading);
        ((TextView)findViewById(R.id.tvcontent)).setText(content);
        setCanceledOnTouchOutside(true);
       // setCancelable(false);//屏蔽回退
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.alpha=0.8f;
        getWindow().setAttributes(attributes);
       setOnKeyListener(new OnKeyListener() {
           @Override
           public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {//屏蔽回退键
               return true;
           }
       });

    }

}

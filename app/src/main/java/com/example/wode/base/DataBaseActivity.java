package com.example.wode.base;

import android.os.Bundle;

import androidx.annotation.Nullable;

public abstract  class DataBaseActivity<T extends MainListener.BasePraenter> extends BaseActivity {
    protected T praenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // onClick();
        //ViewId();
 //       praenter=getPraenter();
    }

    /**
     * 处理OnClick注解
     */

}

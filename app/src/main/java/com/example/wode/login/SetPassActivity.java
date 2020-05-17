package com.example.wode.login;

import android.view.View;
import android.widget.TextView;

import com.example.wode.R;
import com.example.wode.base.DataBaseActivity;
import com.example.wode.widget.LoginItemEditText;

public class SetPassActivity extends DataBaseActivity<LoginContract.SetPassPraenter> implements LoginContract.SetPassView {
    public static String TAG="修改密码";
    @Override
    protected boolean IsShowTitle() {
        return true;
    }

    @Override
    protected void initData() {
        praenter=new SetPassPraenter(this);

    }

    @Override
    protected void initView() {
        setTitleText(TAG);
        final LoginItemEditText mobile=findViewById(R.id.codeMobile);
        final LoginItemEditText pass=findViewById(R.id.pass);
        final LoginItemEditText newpass=findViewById(R.id.newpass);
        final LoginItemEditText newpass2=findViewById(R.id.newpass2);
        findViewById(R.id.setBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (newpass.getEditText().toString().equals(newpass2.getEditText().toString())){
                    praenter.toSetPass(mobile.getEditText().toString(),pass.getEditText().toString(),newpass.getEditText().toString());
                }
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setpass;
    }

    @Override
    public void setSuccess() {

    }
}

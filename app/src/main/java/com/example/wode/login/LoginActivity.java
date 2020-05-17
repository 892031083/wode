package com.example.wode.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wode.R;
import com.example.wode.api.ApiUrl;
import com.example.wode.base.DataBaseActivity;
import com.example.wode.home.HomeActivity;
import com.example.wode.widget.LoadDialog;
import com.example.wode.widget.LoginItemEditText;
import com.example.wode.widget.ToastDialog;

/**
 * 登陆注册界面
 */
public class LoginActivity extends DataBaseActivity<LoginContract.LoginPraenter> implements LoginContract.LoginView, View.OnClickListener {

    public static int PassLoginMode=1;//密码登陆
    public static int CodeLoginMode=2;//验证码登陆

   // protected LoadDialog loadDialog;//自定义的 加载弹出dialog //
    protected Button loginBtn;//登陆界面的登陆按钮
    protected EditText userNameView;//登陆界面的用户名view
    protected EditText passView;//登陆界面的密码view

    protected LoginItemEditText userNamemLin,passmLin,codeLin;//注册界面的 密码框 验证码框等

    protected LoginItemEditText codeLUserName,CodeL;//验证码登录相关

    private View reLayout;//注册的布局
    private View codeLayout;//验证码登录的布局
    @Override
    protected boolean IsShowTitle() {//登陆界面不需要显示标题栏
        return false;
    }

    @Override
    protected void initData() {
        praenter=new LoginPraenter(this);//new 一个praenter
        /**
         * 查看是否第一次登陆
         */
        SharedPreferences sharedPerference=getSharedPreferences("login",MODE_PRIVATE);
        String mobile=sharedPerference.getString("mobile","");
        String pass=sharedPerference.getString("pass","");
        if (!mobile.equals("")) userNameView.setText(mobile);
        if (!pass.equals("")) passView.setText(pass);
    }

    @Override
    protected void initView() {

        super.testInfo();//启动测试
        findViewById(R.id.toregisteredBtn).setOnClickListener(this);
        findViewById(R.id.toReBtn).setOnClickListener(this);
        reLayout=findViewById(R.id.include_reL);
        codeLayout=findViewById(R.id.include_code);
        userNameView=findViewById(R.id.mobileView);
        passView=findViewById(R.id.passView);
        //注册界面相关控件初始化
        userNamemLin=findViewById(R.id.registeredMobile);
        passmLin=findViewById(R.id.registeredPass);
        codeLin=findViewById(R.id.registeredCode);
       // 验证码登录界面相关初始化
        codeLUserName=findViewById(R.id.codeMobile);
        CodeL=findViewById(R.id.Code);
        /**
         * 当发送验证码按钮点击时候
         */
        codeLin.setOnClickByCodeButton(new mOnClickByCodeButton(userNamemLin));//注册界面监听验证码按钮点击 去发验证码 参数:手机号的输入框
        CodeL.setOnClickByCodeButton(new mOnClickByCodeButton(codeLUserName));//验证码登录监听发送验证码按钮点击 参数 手机号的输入框 获取手机号

        //touch占用 不设置view无法遮盖
        reLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        codeLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        /**
         * 标题栏位置
         */
        ((TextView)reLayout.findViewById(R.id.titleText)).setText(R.string.registeredText);
        (reLayout.findViewById(R.id.outbtn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        ((TextView)codeLayout.findViewById(R.id.titleText)).setText(R.string.codeloginText);
        (codeLayout.findViewById(R.id.outbtn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.toregisteredBtn://去注册界面
                showLayout(reLayout);
                break;
            case R.id.toReBtn://去验证码登录界面
                showLayout(codeLayout);
                break;
        }
    }


    public void toLogin(final View view){//点击登陆 去登陆
        //view.setEnabled(false);
        switch (view.getId()){
            case R.id.loginBtn://登陆界面登陆按钮点击
                praenter.toLogin(PassLoginMode,//密码登陆
                        userNameView.getText().toString(),passView.getText().toString());//参数 账号 密码
                break;
            case R.id.registeredBtn://注册按钮点击
                praenter.toRegistered(userNamemLin.getEditText(),passmLin.getEditText(),codeLin.getEditText());//参数 账号 验证码 密码
                break;
            case R.id.codeLoginBtn://验证码登录按钮点击
             //   Log.i("SSSS",codeLUserName.getEditText());
                praenter.toLogin(CodeLoginMode,codeLUserName.getEditText(),CodeL.getEditText());
                break;

        }

    }

//    public void sendCode(){
//
//    }

    public void showLayout(View view) {//显示布局
        //显示注册的布局
        view.setVisibility(View.VISIBLE);
        Animation animation= AnimationUtils.loadAnimation(this, R.anim.anim_start_activityright);//播放动画
        view.setAnimation(animation);
        animation.start();
    }
    public void hideLayout(View view) {//隐藏布局
        //隐藏注册的布局
        view.setVisibility(View.GONE);
        Animation animation= AnimationUtils.loadAnimation(this, R.anim.anim_out_activityright);//播放动画
        view.setAnimation(animation);
        animation.start();
    }


    @Override
    public void goHome() {//登陆成功

        showOnloading(false);
        startActivity(new Intent(this, HomeActivity.class));
        this.finish();
    }

    @Override
    public void sendCodeLayout(boolean IsSuccess, String title) {
        if (IsSuccess){//如果发送成功
            sendCodeView.CodeOpenStart(10);

        }else{
            showMsg(title);
        }
    }

    @Override
    public void showMsg(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }



    @Override
    public void OnLoading() {//设置正在加载
           // showOnloading(true);
        showOnloading(true);
    }

    @Override
    public void hideLoad() {
        showOnloading(false);
    }

    @Override
    public void onBackPressed() {
        if (reLayout.getVisibility()==View.VISIBLE){
            //回到登陆界面
          hideLayout(reLayout);
        }else if (codeLayout.getVisibility()==View.VISIBLE){
            hideLayout(codeLayout);
        }

        else {
            super.onBackPressed();
        }


    }

    LoginItemEditText sendCodeView=null;//因为同一个界面中有两个发送按钮 避免冲突 做一个标识
    class mOnClickByCodeButton implements LoginItemEditText.OnClickByCodeButton{//实现验证码点击接口
        LoginItemEditText mLinText;
        mOnClickByCodeButton(LoginItemEditText mLinText){
            this.mLinText=mLinText;
        }
        @Override
        public void onClick(LoginItemEditText view) {
            sendCodeView=view;
            praenter.sendCode(mLinText.getEditText());//去发送验证码

        }
    }
}

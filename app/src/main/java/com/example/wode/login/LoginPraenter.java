package com.example.wode.login;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.example.wode.AppManager;
import com.example.wode.UserUtilsBean;
import com.example.wode.api.ApiUrl;
import com.example.wode.api.LoginApi;
import com.example.wode.api.result.OnResultListener;
import com.example.wode.api.result.ResultLogin;
import com.example.wode.util.User;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.content.Context.MODE_PRIVATE;

/**
 * 没有验证码接口 所以只支持密码登录及注册
 */
public class LoginPraenter implements LoginContract.LoginPraenter {
    LoginContract.LoginView loginView;
    LoginApi loginApi;
    String errorMsg="";//错误提示信息
    public LoginPraenter( LoginContract.LoginView loginView){
        //初始化
        this.loginView=loginView;
        this.loginApi=new LoginApi();
    }

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==ApiUrl.ERROR_20){
                loginView.showMsg(errorMsg);
                loginView.hideLoad();
                return;
            }

            loginView.goHome();
        }
    };

    /**
     * @param loginMode //两个状态 验证码登录或者密码登陆
     */
    @Override
    public void toLogin(int loginMode,String username,String code) {

        if (CheckLoginCode(username,code,loginMode)){//验证成功
            //TODO model
            //loginView.OnLoading();
            if (ApiUrl.ISHTTP){
                loginView.OnLoading();
                loginApi.toLogin(username, code, new OnResultListener<ResultLogin>() {
                    @Override
                    public void onSuccess(ResultLogin resultLogin) {
                        if (resultLogin.getCode()==200){
                            User user=resultLogin.getData();
                             // 保存账号密码到SharedPreferences
                            SharedPreferences sharedPerference= AppManager.getContext().getSharedPreferences("login",MODE_PRIVATE);
                            SharedPreferences.Editor edit = sharedPerference.edit();
                            edit.putString("mobile",user.getUserMobile());//保存
                            edit.putString("pass",user.getUserPass());//保存密码
                            edit.commit();//提交
                            String qqValue=sharedPerference.getString("qqV","");
                            String wxValue=sharedPerference.getString("wxV","");
                            String sex=sharedPerference.getString("sex","");
                            user.setQQValue(qqValue);
                            user.setWXValue(wxValue);
                            user.setSex(sex);
                            UserUtilsBean.getInstance().setUser(user);//设置全局的user属性

                            handler.sendEmptyMessage(0);
                        }else {
                            errorMsg=resultLogin.getTitle();
                            handler.sendEmptyMessage(ApiUrl.ERROR_20);
                        }
                    }

                    @Override
                    public void onError() {

                    }
                });
            }else {
                loginView.goHome();
            }

        }
    }

    /**
     * 去注册
     */
    @Override
    public void toRegistered(String mobile, String pass, String code) {
        if (CheckLoginCode(mobile,pass,LoginActivity.PassLoginMode) && CheckLoginCode(mobile,code,LoginActivity.CodeLoginMode)){//验证
            //TODO MODEL
            if (ApiUrl.ISHTTP){
                loginView.OnLoading();
                loginApi.toRegistered(mobile, pass, new OnResultListener<ResultLogin>() {
                    @Override
                    public void onSuccess(ResultLogin resultLogin) {
                        if (resultLogin.getCode()==200){
                            UserUtilsBean.getInstance().setUser(resultLogin.getData());
                            handler.sendEmptyMessage(0);
                        }else {
                            errorMsg=resultLogin.getTitle();
                            handler.sendEmptyMessage(ApiUrl.ERROR_20);

                        }
                    }
                    @Override
                    public void onError() {

                    }
                });
            }else {
                loginView.goHome();
            }

        }
    }

    @Override
    public void sendCode(String mobile) {
        if (CheckLoginCode(mobile)){
            //TODO model
            if (ApiUrl.ISHTTP){
            }else {
                loginView.sendCodeLayout(true,"");//test
            }
        }
    }


    private  boolean CheckLoginCode(String mobile,String pass,int mode) {
        String pattern = "^(13[0-9]|15[012356789]|17[013678]|18[0-9]|14[57]|19[89]|166)[0-9]{8}";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(mobile);
            if (!m.matches()) {
                loginView.showMsg("手机号格式错误");
                return false;
            }
        if (pass==null||pass.equals("")){
            loginView.showMsg(mode==LoginActivity.PassLoginMode?"密码不能为空":"请输入验证码");
            return false;
        }
        if (mode==LoginActivity.PassLoginMode){//如果是密码登陆 验证密码

            if (pass.length()<6|| pass.length()>=12){
                loginView.showMsg("密码必须在6-12位之间");
                return false;
            }
        }else{//如果是验证码登录 验证验证码
            if(pass.length()!=6){
                loginView.showMsg("请输入6位数验证码");
            }
        }
        return true;
    }

    private  boolean CheckLoginCode(String mobile) {
        String pattern = "^(13[0-9]|15[012356789]|17[013678]|18[0-9]|14[57]|19[89]|166)[0-9]{8}";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(mobile);
        if (!m.matches()) {
            loginView.showMsg("手机号格式错误");
            return false;
        }
        return true;
    }

}

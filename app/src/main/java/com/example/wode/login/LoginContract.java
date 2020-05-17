package com.example.wode.login;

import com.example.wode.base.MainListener;

public interface LoginContract {
    interface LoginModel extends MainListener.BaseModel{

    }
    interface LoginView extends MainListener.BaseView{

        void goHome();
        void sendCodeLayout(boolean IsSuccess, String title);//IsSuccess为是否发送成功 title是发送失败是的原因字符串
        void hideLoad();
        void showMsg(String msg);//提示信息
    }
    interface LoginPraenter extends MainListener.BasePraenter {
        void toLogin(int loginMode, String username, String code);
        void toRegistered(String mobile, String pass, String code);//去注册接口
        void sendCode(String mobile);
    }

    interface SetPassPraenter extends MainListener.BasePraenter{
        void toSetPass(String mobile,String pass,String newpass);
    }

    interface SetPassView extends MainListener.BasePraenter{
        void showMsg(String msg);//提示信息
        void setSuccess();
    }
}

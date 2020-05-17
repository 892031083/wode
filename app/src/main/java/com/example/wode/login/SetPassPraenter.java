package com.example.wode.login;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SetPassPraenter implements LoginContract.SetPassPraenter {

    private LoginContract.SetPassView setPassView;

    public SetPassPraenter(LoginContract.SetPassView setPassView) {
        this.setPassView = setPassView;
    }



    @Override
    public void toSetPass(String mobile, String pass, String newpass) {
        //TODO

    }

    private boolean checkPass(String mobile,String pass){
        String pattern = "^(13[0-9]|15[012356789]|17[013678]|18[0-9]|14[57]|19[89]|166)[0-9]{8}";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(mobile);
        if (!m.matches()) {
            setPassView.showMsg("手机号格式错误");
            return false;
        }

        if (pass.length()<6|| pass.length()>=12) {
            setPassView.showMsg("密码必须在6-12位之间");
            return false;
        }
        return true;
    }
}

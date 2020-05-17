package com.example.wode.userdata;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.wode.AppManager;
import com.example.wode.UserUtilsBean;
import com.example.wode.api.ApiUrl;
import com.example.wode.api.ImageTool;
import com.example.wode.api.LoginApi;
import com.example.wode.api.result.OnResultListener;
import com.example.wode.api.result.Result;
import com.example.wode.api.result.ResultLogin;
import com.example.wode.util.User;

import static android.content.Context.MODE_PRIVATE;

public class UserDataPraenter implements UserDataContract.UserDataPraenter {
    UserDataContract.UserDataView userDataView;
    String imgurl;
    private int TYPECODE_UPLOAD=400;
    private int SAVEOK=500;
    private int ERROR=5200;
    String errorStr="";
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==TYPECODE_UPLOAD){
                //上传成功
                userDataView.hideLoading();
                userDataView.uploadPhotoBack(imgurl);

            }
            if (msg.what==SAVEOK){
                userDataView.saveUserDataBack(true);
            }
            if (msg.what==ERROR){
                userDataView.showMsgT(errorStr);
            }
        }
    };
    public UserDataPraenter(UserDataContract.UserDataView userDataView) {
        this.userDataView = userDataView;
    }

    @Override
    public void saveUserData(final User user) {
        String avatar=user.getAvatarUrl(),name=user.getNickName();
//        if (avatar.equals(UserUtilsBean.getInstance().getUser().getAvatarUrl())
//                &&name.equals(UserUtilsBean.getInstance().getUser().getNickName())){
//               userDataView.saveUserDataBack(false);
//               return;
//        }
        LoginApi loginApi=new LoginApi();
        loginApi.setUserInfo(avatar, name, new OnResultListener<ResultLogin>() {
            @Override
            public void onSuccess(ResultLogin resultLogin) {
                //成功
                if (resultLogin.getCode()==200){//成功之后 个别信息保存到 sharedperference
                    SharedPreferences sharedPerference= AppManager.getContext().getSharedPreferences("login",MODE_PRIVATE);
                    SharedPreferences.Editor edit = sharedPerference.edit();
                    edit.putString("qqV",user.getQQValue());//保存qq
                    edit.putString("wxV",user.getWXValue());//保存微信
                    edit.putString("sex",user.getSex());//保存密码
                    edit.commit();//提交

                    handler.sendEmptyMessage(SAVEOK);
                }else {
                    errorStr=resultLogin.getTitle();
                    handler.sendEmptyMessage(ERROR);
                }

            }

            @Override
            public void onError() {

            }
        });
    }

    @Override
    public void uploadInfile(Bitmap bitmap) {
        if (ApiUrl.ISHTTP){
            userDataView.OnLoading();
            ImageTool imageTool=new ImageTool();
            imageTool.uploadBitmapBase64Img(bitmap, new OnResultListener<Result>() {
                @Override
                public void onSuccess(Result result) {
                    if (result.getCode()==200){
                        imgurl=result.getTitle();
                        handler.sendEmptyMessage(TYPECODE_UPLOAD);
                    }
                }

                @Override
                public void onError() {

                }
            });
        }
    }



    @Override
    public void getUserData() {
        userDataView.showData();
    }

}

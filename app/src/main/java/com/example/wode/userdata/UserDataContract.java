package com.example.wode.userdata;

import android.graphics.Bitmap;

import com.example.wode.base.MainListener;
import com.example.wode.util.User;

public interface UserDataContract {

    interface UserDataModel extends MainListener.BaseModel {

    }
    interface UserDataPraenter extends MainListener.BasePraenter {
        void saveUserData(User user);
        void uploadInfile(Bitmap bitmap);
        void getUserData();
    }
    interface UserDataView extends MainListener.BaseView {
        void saveUserDataBack(boolean ischeck);
        void uploadPhotoBack(String url);
        void showData();
        void hideLoading();

        void showMsgT(String errorStr);
    }

}

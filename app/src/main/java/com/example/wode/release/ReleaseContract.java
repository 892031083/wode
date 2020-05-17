package com.example.wode.release;

import android.graphics.Bitmap;

import com.example.wode.base.MainListener;
import com.example.wode.util.Product;

public interface ReleaseContract {
    interface RelaseModel extends MainListener.BaseModel {

    }
    interface RelasePraenter extends MainListener.BasePraenter {
//        void upLoadPhoto(String path);//上传图片
        void uploadInfile(Bitmap bitmap);

        void saveProduct(Product product);//添加商品
    }
    interface RelaseView extends MainListener.BaseView {
        void upLoadPhotoBack(String string);
        void SaveProductBack();
        void hideLoading();
        void showMsgT(String string);
    }
}

package com.example.wode.release;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;

import com.example.wode.api.ApiUrl;
import com.example.wode.api.ImageTool;
import com.example.wode.api.ProductApi;
import com.example.wode.api.result.OnResultListener;
import com.example.wode.api.result.Result;
import com.example.wode.api.result.ResultProduct;
import com.example.wode.util.Product;


public class ReleasePraenter implements ReleaseContract.RelasePraenter {
    ReleaseContract.RelaseView relaseView;
    int TYPECODE_UPLOAD=100;
    int TYPECODE_SAVE=200;
    String imgurl="";
    String errorTitle="";
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==TYPECODE_UPLOAD){

                relaseView.upLoadPhotoBack(imgurl);
                relaseView.hideLoading();
            }else if (msg.what==TYPECODE_SAVE){
                relaseView.SaveProductBack();
            }else {
                relaseView.showMsgT(errorTitle);
            }
        }
    };

    public ReleasePraenter(ReleaseContract.RelaseView relaseView) {
        this.relaseView = relaseView;
    }


    @Override
    public void uploadInfile(Bitmap bitmap) {
        if (ApiUrl.ISHTTP){
            relaseView.OnLoading();
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
    public void saveProduct(Product product) {
        //TODO
//        relaseView.SaveProductBack();
        if (!isEm(product.getImageUrl()) && !isEm(product.getProductName()) && !isEm(product.getMobile())){
            if (ApiUrl.ISHTTP){
                ProductApi productApi=new ProductApi();
                productApi.SaveProduct(product, new OnResultListener<ResultProduct>() {
                    @Override
                    public void onSuccess(ResultProduct resultProduct) {
                        if (resultProduct.getCode()==200){
                            //添加成功
                            handler.sendEmptyMessage(TYPECODE_SAVE);
                        }else {
                            errorTitle=resultProduct.getTitle();
                            handler.sendEmptyMessage(2);
                        }
                    }

                    @Override
                    public void onError() {

                    }
                });
            }
        }else {
            relaseView.showMsgT("不能为空");
        }
    }

    private boolean isEm(String string){
        if (string==null||string.equals("")){
            return  true;
        }
        return false;
    }
}

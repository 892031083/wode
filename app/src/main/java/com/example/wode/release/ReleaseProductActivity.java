package com.example.wode.release;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListPopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.bumptech.glide.Glide;
import com.example.wode.R;
import com.example.wode.UserUtilsBean;
import com.example.wode.api.ImageTool;
import com.example.wode.base.DataBaseActivity;
import com.example.wode.util.Power;
import com.example.wode.util.Product;
import com.example.wode.widget.LoadDialog;

import java.io.File;
import java.io.FileOutputStream;

public class ReleaseProductActivity extends DataBaseActivity<ReleaseContract.RelasePraenter> implements ReleaseContract.RelaseView {

    public static final String TAG="发布商品";
    LoadDialog loadDialog;//加载中弹窗
    EditText itmeText,ProductName,Mobile,ProductData;
    ImageView photoView;

    String imgUrl;//上传图片成功后返回的路径
    @Override
    protected boolean IsShowTitle() {
        return true;
    }

    @Override
    protected void initData() {
        praenter=new ReleasePraenter(this);

    }

    @Override
    protected void initView() {
        setTitleText(TAG);
        itmeText=findViewById(R.id.web_edit);
        photoView=findViewById(R.id.productPhoto);
        findViewById(R.id.laBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showListPopulWindow();
            }
        });
        photoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });
        findViewById(R.id.saveBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                praenter.saveProduct(getThisProduct());
            }
        });
    }

    private Product getThisProduct() {
        Product product=new Product();
        product.setType(((EditText)findViewById(R.id.web_edit)).getText().toString());
        product.setProductName(((EditText)findViewById(R.id.productName)).getText().toString());
        product.setMobile(((EditText)findViewById(R.id.mobile)).getText().toString());
        String S =((EditText)findViewById(R.id.num)).getText().toString();
        product.setNum(Integer.valueOf(S));//数量
         S =((EditText)findViewById(R.id.productPrice)).getText().toString();
        product.setPrive(Double.valueOf(S));//数量
        product.setUserId(UserUtilsBean.getInstance().getUser().getUserId());
        product.setUserNickName(UserUtilsBean.getInstance().getUser().getNickName());
        product.setImageUrl(imgUrl);
//        product.set(UserUtilsBean.getInstance().getUser().getUserId());
        return product;
    }

    /**
     * 弹出框 下拉选择类目
     */
    private void showListPopulWindow() {
        final String[] list = {"电子产品","学习用品","体育用品","生活用品"};//要填充的数据
        final ListPopupWindow listPopupWindow;
        listPopupWindow = new ListPopupWindow(this);
        listPopupWindow.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, list));//用android内置布局，或设计自己的样式
        listPopupWindow.setAnchorView(itmeText);//以哪个控件为基准，在该处以mEditText为基准
        listPopupWindow.setModal(true);

        listPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {//设置项点击监听
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                itmeText.setText(list[i]);//把选择的选项内容展示在EditText上
                listPopupWindow.dismiss();//如果已经选择了，隐藏起来
            }
        });
        listPopupWindow.show();//把ListPopWindow展示出来
    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_releaseproduct;
    }


    private void uploadImage() {//上传图片
        final Dialog dialog=new Dialog(this);
        View imgview= LayoutInflater.from(this).inflate(R.layout.dialog_uploadimg,null);
        dialog.setContentView(imgview);
        WindowManager.LayoutParams layoutParams=dialog.getWindow().getAttributes();//布局
        layoutParams.width=WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height=WindowManager.LayoutParams.WRAP_CONTENT;
        //imgview.setLayoutParams(layoutParams);
        dialog.getWindow().setAttributes((WindowManager.LayoutParams) layoutParams);
        dialog.getWindow().setGravity(Gravity.BOTTOM);//底部显示
        dialog.getWindow().setWindowAnimations(R.style.MyDialog);//设置动画
        dialog.getWindow().getDecorView().setPadding(0,40,0,40);
        dialog.show();
        imgview.findViewById(R.id.imgku).setOnClickListener(new View.OnClickListener() {//选择图库
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,2);
                dialog.cancel();
            }
        });
        imgview.findViewById(R.id.camar).setOnClickListener(new View.OnClickListener() {//调用摄像机拍照

            @Override
            public void onClick(View view) {
             //   uploadImage();
                dialog.cancel();

                PackageManager pm=ReleaseProductActivity.this.getPackageManager();
                if(PackageManager.PERMISSION_GRANTED== pm.checkPermission( "android.permission.CAMERA",
                        ReleaseProductActivity.this.getPackageName())){
                    //该应用程序有拍照权限
                    startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE),1);
                    dialog.cancel();
                }else{
                    //没有拍照权限
                    ActivityCompat.requestPermissions(ReleaseProductActivity.this, new String[]{"android.permission.CAMERA"},1);
                }

            }
        });
        imgview.findViewById(R.id.quxiao).setOnClickListener(new View.OnClickListener() {//选择图库
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
    }
    Bitmap image;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {//拍照
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");//返回照片
            Matrix matrix = new Matrix();
            matrix.setScale(0.5f, 0.5f);
            image = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            photoView.setImageBitmap(image);


            //保存到相册
//            new Thread() {
//                @Override
//                public void run() {
//                    saveImagetoTK(image, System.currentTimeMillis() + ".jpeg");
//                }
//            }.start();
        } else if (requestCode == 2 && resultCode == Activity.RESULT_OK) {//选择图库
            Uri uri = data.getData();
            String[] path = {MediaStore.Images.Media.DATA};
            //查询数据
            Cursor cursor = getContentResolver().query(uri, path, null, null, null);
            cursor.moveToFirst();
            int item = cursor.getColumnIndex(path[0]);//获取下表
            final File file=new File(cursor.getString(item));
            if (file.length() / 1024 > 250) {
                Power.getDialog("上传图片失败", "图片过大！请重新上传", ReleaseProductActivity.this);
                return;
            }
            fileName = cursor.getString(item);
            image = BitmapFactory.decodeFile(fileName);
            Log.i("=============",fileName);
            cursor.close();
          //  photoView.setImageBitmap(image);
            Glide.with(this).load(fileName).into(photoView);

        }
        praenter.uploadInfile(image);
        //上传图片
//        praenter.upLoadPhoto();
    }
    String fileName = null;//上传图片的路径

    private void saveImagetoTK(Bitmap bit,String name) {
        /**
         * Environment.getExternalStorageDirectory().getPath() //获取外部存储的根目录   DCIM是图库目录
         */

        File file ;
        try {
            if (Build.BRAND.equals("Xiaomi")) { // 小米手机
                fileName = Environment.getExternalStorageDirectory().getPath() + "/DCIM/Camera/" + name;//
            } else {  // Meizu 、Oppo
                fileName = Environment.getExternalStorageDirectory().getPath() + "/DCIM/" + name;//保存文件的路径
            }
            file = new File(fileName);
            // imgurl=fileName;
            if (file.exists()) {
                //file.createNewFile();
                file.delete();
            }
            FileOutputStream out=new FileOutputStream(file);//文件输入流
            bit.compress(Bitmap.CompressFormat.JPEG,100,out);//亚索图片函数 第一个参数是文件格式 第二个参数是图片压缩  第三个是输出流
            out.flush();
            out.close();
            MediaStore.Images.Media.insertImage(this.getContentResolver(), file.getAbsolutePath(), name, null);//插入相册
            //发送广播 提示图册更新
            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://"+fileName)));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {//权限回调
        if(requestCode==1){
            if (grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                //已授权
                startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE),1);//去拍照界面
            }else{
                //已拒绝
            }
        }
    }

    @Override
    public void OnLoading() {
        loadDialog=new LoadDialog(this,"加载中");
        loadDialog.setCancelable(false);
        loadDialog.show();
    }

    @Override
    public void hideLoading() {
        loadDialog.cancel();
    }


    @Override
    public void upLoadPhotoBack(String string) {
        //上传图片回掉
        showMsg("上传图片成功");
        imgUrl=string;//保存路径
    }

    @Override
    public void showMsgT(String string) {
        showMsg(string);
    }

    @Override
    public void SaveProductBack() {
        //添加商品回掉
        showMsg("发布成功");
        finish();

    }


}

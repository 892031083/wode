package com.example.wode.userdata;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.bumptech.glide.Glide;
import com.example.wode.R;
import com.example.wode.UserUtilsBean;
import com.example.wode.api.ApiUrl;
import com.example.wode.base.DataBaseActivity;
import com.example.wode.home.fragment.MeFragment;
import com.example.wode.release.ReleaseProductActivity;
import com.example.wode.util.Power;
import com.example.wode.util.User;
import com.example.wode.widget.LoadDialog;
import com.example.wode.widget.ToastDialog;

import java.io.File;
import java.io.FileOutputStream;

/**
 * 个人信息界面
 */
public class UserInfoActivity extends DataBaseActivity<UserDataContract.UserDataPraenter> implements UserDataContract.UserDataView {
    public String TAG="个人信息";
    LoadDialog loadDialog;
    ImageView avatar;//头像

    User user;//此类用的User信息
//    String avatarUrl="";//头像地址
//    String nickName="";//用户昵称

    @Override
    protected boolean IsShowTitle() {
        return true;
    }

    @Override
    protected void initData() {
        user=new User(UserUtilsBean.getInstance().getUser());//一定要new一个 不要直接修改全局的USER

        praenter=new UserDataPraenter(this);
        praenter.getUserData();
    }

    @Override
    protected void initView() {
        setTitleText(TAG);
        avatar=findViewById(R.id.avatar);
        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();//上传图片
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_userinfo;
    }

    @Override
    public void saveUserDataBack(boolean is) {
        if (is){//已修改信息
            //上传信息后回掉
            UserUtilsBean.getInstance().setUser(new User(user));//修改全局的user

            showOnloading(false);
            showMsg("信息修改成功");
            MeFragment.isCheck=true;
        }

        finish();
    }

    @Override
    public void uploadPhotoBack(String url) {
        //头像上传回掉
        user.setAvatarUrl(url);
    }

    @Override
    public void showData() {
         //显示数据
        if (ApiUrl.ISHTTP){
            if (!user.getAvatarUrl().equals("defus")){//defus表示显示默认图片
                Glide.with(this).load(user.getAvatarUrl())
                        .into(avatar);
                ((TextView)findViewById(R.id.userName)).setText(user.getNickName());
                ((TextView)findViewById(R.id.qqcode)).setText(user.getQQValue());
                ((TextView)findViewById(R.id.wxCode)).setText(user.getWXValue());
                ((TextView)findViewById(R.id.mobile)).setText(user.getUserMobile());
                ((TextView)findViewById(R.id.sex)).setText(user.getSex());
            }

        }

    }

    @Override
    public void OnLoading() {
       super.showOnloading(true);
    }

    @Override
    public void hideLoading() {
        showOnloading(false);
    }

    @Override
    public void showMsgT(String errorStr) {
        showMsg(errorStr);
    }


    @Override
    public void onBackPressed() {
            final ToastDialog toastDialog=new ToastDialog(this,"即将退出,需要保存个人信息吗?");
            toastDialog.setOnClickDialog(new ToastDialog.OnClickDialog() {
                @Override
                public void onFixClick() {
                    toastDialog.cancel();
                    String nickName=((TextView)findViewById(R.id.userName)).getText().toString();
                    user.setNickName(nickName);
                    user.setQQValue(((TextView)findViewById(R.id.qqcode)).getText().toString());
                    user.setWXValue(((TextView)findViewById(R.id.wxCode)).getText().toString());
                    user.setSex(((TextView)findViewById(R.id.sex)).getText().toString());
                    showOnloading(true);
                    praenter.saveUserData(user);

                }

                @Override
                public void onCancelClick() {//取消
                    toastDialog.cancel();
                    UserInfoActivity.super.onBackPressed();

                }
            });
            toastDialog.show();
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

                PackageManager pm= UserInfoActivity.this.getPackageManager();
                if(PackageManager.PERMISSION_GRANTED== pm.checkPermission( "android.permission.CAMERA",
                        UserInfoActivity.this.getPackageName())){
                    //该应用程序有拍照权限
                    startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE),1);
                    dialog.cancel();
                }else{
                    //没有拍照权限
                    ActivityCompat.requestPermissions(UserInfoActivity.this, new String[]{"android.permission.CAMERA"},1);
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
            avatar.setImageBitmap(image);

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

            if (new File(cursor.getString(item)).length() / 1024 > 250) {
                Power.getDialog("上传图片失败", "图片过大！请重新上传", UserInfoActivity.this);
                return;
            }
            fileName = cursor.getString(item);
            image = BitmapFactory.decodeFile(fileName);
            Log.i("=============",fileName);
            cursor.close();
            avatar.setImageBitmap(image);
           // Glide.with(this).load(fileName).into(avatar);//
        }
        //上传图片
        praenter.uploadInfile(image);
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

}

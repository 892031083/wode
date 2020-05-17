package com.example.wode.home.fragment;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.wode.R;
import com.example.wode.UserUtilsBean;
import com.example.wode.api.ApiUrl;
import com.example.wode.api.ImageTool;
import com.example.wode.base.BaseFragment;
import com.example.wode.home.HomeActivity;
import com.example.wode.home.KeepActivity;
import com.example.wode.home.MproActivity;
import com.example.wode.login.SetPassActivity;
import com.example.wode.release.ReleaseProductActivity;
import com.example.wode.userdata.UserInfoActivity;
import com.example.wode.util.User;

import java.util.List;

public class MeFragment extends BaseFragment {
    public static final int ADAPTER_TYPE=4;
    public static boolean isCheck=false;//做个标识，如果用户信息改变用此函数判断

    private ListView listView;//底部布局
    String[] items={"个人信息","修改密码","发布物品","我的收藏","我的发布","退出登陆"};
    int[] res={R.mipmap.icon_user,R.mipmap.icon_updatepass,R.mipmap.icon_keep,R.mipmap.icon_keep2,
            R.mipmap.icon_mypro,R.mipmap.icon_userout};
    @Override
    protected void initView() {
        HomeActivity.showIsHome=false;
        initUserInfo();
       listView=deasView.findViewById(R.id.listview);
       //设置adapter
       listView.setAdapter(new BaseAdapter() {
           @Override
           public int getCount() {
               return items.length;
           }

           @Override
           public Object getItem(int position) {
               return items[position];
           }

           @Override
           public long getItemId(int position) {
               return position;
           }

           @Override
           public View getView(int position, View convertView, ViewGroup parent) {
               convertView=getLayoutInflater().inflate(R.layout.userlayout_item,null);
               TextView textView=convertView.findViewById(R.id.text);
               ImageView imageView=convertView.findViewById(R.id.img);
              // convertView.setVisibility(View.VISIBLE);
               imageView.setImageResource(res[position]);
               textView.setText(items[position]);
               return convertView;
           }
       });
       //item点击事件
       listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//               showTitle(items[position]);
               switch (position){
                   case 0://个人信息
                       startActivity(new Intent(getActivity(), UserInfoActivity.class));
                       break;
                   case 1://修改密码
                       startActivity(new Intent(getActivity(), SetPassActivity.class));
                       break;
                   case 2://发布物品
                       startActivity(new Intent(getActivity(), ReleaseProductActivity.class));
                       break;
                   case 3://我的收藏
                       startActivity(new Intent(getActivity(), KeepActivity.class));
                       break;
                   case 4://我的发布
                       startActivity(new Intent(getActivity(), MproActivity.class));
                       break;
                   case 5://退出登陆
                       break;
               }
           }
       });
    }

    private void initUserInfo() {
        TextView nickname=deasView.findViewById(R.id.nickName);
        ImageView avatar=deasView.findViewById(R.id.avatar);
        if (ApiUrl.ISHTTP){
            //判断 当用户名发生改变时

//            if (!nickname.
//                    getText().toString().equals(UserUtilsBean.getInstance().getUser().getNickName())){
                Log.i("KKKKKKK",UserUtilsBean.getInstance().getUser().getAvatarUrl());
                nickname.setText(UserUtilsBean.getInstance().getUser().getNickName());
                // avatar.setImageBitmap(ImageTool.getImgBitmap(UserUtilsBean.getInstance().getUser().getAvatarUrl(),getActivity()));
              //  avatar.setImageURI(Uri.parse(UserUtilsBean.getInstance().getUser().getAvatarUrl()));
                String imgurl=UserUtilsBean.getInstance().getUser().getAvatarUrl();
                if (!imgurl.equals("defus")){//defus表示显示默认图片
                    Glide.with(getActivity()).load(imgurl)
                            .into(avatar);
                }

//            }
//
        }else {
//            Glide.with(getActivity()).load(User.getTestUser().getAvatarUrl())
//                    .into(avatar);
        }

    }

    @Override
    public void onStart() {
        if (isCheck){
            initUserInfo();
        }
        super.onStart();

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_me;
    }

    //下面item点击
    public  void ItemClick(final View view){

    }
    public void showTitle(String  msg){
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
//        if (((HomeActivity)getActivity()).getUserFragment()==null)
//        ((HomeActivity)getActivity()).setUserFragment(deasView);

        super.onDestroy();
    }
}


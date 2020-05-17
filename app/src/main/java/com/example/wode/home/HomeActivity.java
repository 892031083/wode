package com.example.wode.home;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.wode.R;
import com.example.wode.UserUtilsBean;
import com.example.wode.base.BaseActivity;
import com.example.wode.base.BaseFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends BaseActivity {
    public View findFragment=null;
    public View homeFragment=null;
    public View orderFragment=null;
    public View userFragment=null;
    public static boolean showIsHome=true;//当前界面是否是homefragment
    @Override
    protected boolean IsShowTitle() {
        return false;
    }
    private BottomNavigationView bottomNavigationView;
    @Override
    protected void initData() {
        Log.i("===========",UserUtilsBean.getInstance().getUser().toString());
    }

    @Override
    protected void initView() {
//        Log.i("LOGINMSG", UserUtilsBean.getInstance().getUser().toString());
        BottomNavigationView navView = findViewById(R.id.nav_view);
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navView, navController);

    }



    @Override
    protected int getLayoutId() {
        return R.layout.activity_home;
    }



    long backtime=0;//上次点击返回键的时间
    @Override
    public void onBackPressed() {
        if (!showIsHome) {//如果不是homefragment
            super.onBackPressed();
            return;
        }
        if (System.currentTimeMillis() - backtime < 2000) {
            super.onBackPressed();
        } else {
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            backtime = System.currentTimeMillis();
        }

    }

    public View getFindFragment() {
        return findFragment;
    }

    public void setFindFragment(View findFragment) {
        this.findFragment = findFragment;
    }

    public View getHomeFragment() {
        return homeFragment;
    }

    public void setHomeFragment(View homeFragment) {
        this.homeFragment = homeFragment;
    }

    public View getOrderFragment() {
        return orderFragment;
    }

    public void setOrderFragment(View orderFragment) {
        this.orderFragment = orderFragment;
    }

    public View getUserFragment() {
        return userFragment;
    }

    public void setUserFragment(View userFragment) {
        this.userFragment = userFragment;
    }
}

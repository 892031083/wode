package com.example.wode.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.wode.R;
import com.example.wode.home.HomeActivity;

public abstract class BaseFragment extends Fragment {
    protected View deasView;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        deasView=inflater.inflate(getLayoutId(),null);
//        switch (getLayoutId()){
//            case R.layout.fragment_home://home界面 NavigationUI 切换fragment时是重新加载的 在这里判断一下 避免重复加载
//                if (((HomeActivity)getActivity()).getHomeFragment()!=null){
//                    deasView=((HomeActivity)getActivity()).getHomeFragment();
//                    return  deasView;
//                }
//                break;
//            case R.layout.fragment_find:
//                if (((HomeActivity)getActivity()).getFindFragment()!=null){
//                    deasView= ((HomeActivity)getActivity()).getFindFragment();
//                    return  deasView;
//                }
//
//                break;
//            case R.layout.fragment_order:
//                if (((HomeActivity)getActivity()).getOrderFragment()!=null){
//                    deasView= ((HomeActivity)getActivity()).getOrderFragment();
//                    return  deasView;
//                }
//
//                break;
//            case R.layout.fragment_me:
//                if (((HomeActivity)getActivity()).getUserFragment()!=null){
//                    deasView= ((HomeActivity)getActivity()).getUserFragment();
//                    return  deasView;
//                }
//                break;
//
//        }
        initView();
        return deasView;
    }

    protected abstract void initView();

    protected abstract int getLayoutId();

    @Override
    public void onDestroy() {
//        switch (getLayoutId()){
//            case R.layout.fragment_home://home界面 NavigationUI 切换fragment时是重新加载的 在这里判断一下 避免重复加载
//                break;
//            case R.layout.fragment_find:
//
//                ((HomeActivity)getActivity()).setFindFragment(deasView);
//                break;
//            case R.layout.fragment_order:
//                ((HomeActivity)getActivity()).setOrderFragment(deasView);
//                break;
//            case R.layout.fragment_me:
//                ((HomeActivity)getActivity()).setUserFragment(deasView);
//                break;
//
//        }

        super.onDestroy();
    }
}

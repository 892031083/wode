package com.example.wode.home.fragment;

import android.content.Intent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wode.R;
import com.example.wode.api.result.Looper;
import com.example.wode.base.BaseFragment;
import com.example.wode.home.BaseHomeAdapter;
import com.example.wode.home.DetailsActivity;
import com.example.wode.home.HomeActivity;
import com.example.wode.home.ProductListActivity;
import com.example.wode.util.BaseUtil;
import com.example.wode.util.Product;
import com.itheima.loopviewpager.LoopViewPager;

import java.util.List;

public class HomeFragment extends BaseFragment implements FragmentContract.FragmentView, View.OnClickListener {

    public static final int ADAPTER_TYPE=1;//适配器类型
    public static List datas;//元数据
    private LoopViewPager loopViewPager;//轮播图
    RecyclerView recyclerView;//推荐view
    FragmentContract.FragmentParenter parenter;
    @Override
    protected void initView() {
        HomeActivity.showIsHome=true;
        loopViewPager = deasView.findViewById(R.id.head);
        loopViewPager.setImgData(Looper.imgListString());
        loopViewPager.setTitleData(Looper.titleListString());
        loopViewPager.start();
        LinearLayout linearLayout=deasView.findViewById(R.id.navigation);
        for (int i=0;i<linearLayout.getChildCount();i++){
            linearLayout.getChildAt(i).setOnClickListener(this);//定义点击监听
        }
        recyclerView=deasView.findViewById(R.id.recycler_view);


        parenter=new FragmentPraenter(this );
        if (datas==null){
            parenter.getRecommends();//请求数据
        }else {
            showData(datas);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    //展示数据
    @Override
    public void showData(List list) {
        if (datas==null) datas=list;
        GridLayoutManager gridLayoutManager=new GridLayoutManager(getActivity(),2);
        recyclerView.setLayoutManager(gridLayoutManager);
        BaseHomeAdapter baseAdapter=new BaseHomeAdapter(list,getActivity(),HomeFragment.ADAPTER_TYPE,R.layout.home_recommend_item);//新建一个适配器
        recyclerView.setAdapter(baseAdapter);
        baseAdapter.setOnItemClick(new BaseHomeAdapter.onItemClick() {//每个item点击事件
            @Override
            public void onClick(BaseUtil p) {
                toDetails(p);
            }
        });
    }

    @Override
    public void onLoad(List list) {

    }

    @Override
    public void onRefresh(List list) {

    }

    private void toDetails(BaseUtil p) {
        Intent intent=new Intent(getActivity(),DetailsActivity.class);
        intent.putExtra("product",(Product)p);//已实现序列化接口 可以传递对象
        startActivity(intent);
    }

    @Override
    public void OnLoading() {
         //无
    }


    @Override
    public void onClick(View v) {

        startActivity(new Intent(getActivity(), ProductListActivity.class));
    }

    @Override
    public void onDestroy() {
//        if (((HomeActivity)getActivity()).getHomeFragment()==null)
//
//            ((HomeActivity)getActivity()).setHomeFragment(deasView);

        super.onDestroy();
    }
}

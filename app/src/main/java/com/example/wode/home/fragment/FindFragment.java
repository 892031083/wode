package com.example.wode.home.fragment;

import android.content.Intent;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wode.R;
import com.example.wode.base.BaseFragment;
import com.example.wode.home.BaseHomeAdapter;
import com.example.wode.home.HomeActivity;
import com.example.wode.home.NewsDetailsActivity;
import com.example.wode.util.BaseUtil;
import com.example.wode.util.News;
import com.example.wode.widget.PullLoadRecyclerView;

import java.util.List;

public class FindFragment extends BaseFragment implements FragmentContract.FragmentView {
    public static final int ADAPTER_TYPE=2;
    public static List datas;//元数据
    public static int page=1;//页数
    public int RefreshPage=1;
    private BaseHomeAdapter baseAdapter;
    private RecyclerView recyclerView;
    private PullLoadRecyclerView pullLoad;
    private FragmentPraenter praenter;
    @Override
    protected void initView() {
        HomeActivity.showIsHome=false;
        recyclerView=deasView.findViewById(R.id.recycler_view);
        pullLoad=deasView.findViewById(R.id.pullLoadView);//刷新加载

        praenter=new FragmentPraenter(this);
        if (datas==null){
            praenter.getFinds();//获取数据
        }else {
            showData(datas);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_find;
    }
    @Override
    public void showData(List list) {//显示界面
        if (datas==null) datas=list;
        baseAdapter=new BaseHomeAdapter(list,getActivity(),FindFragment.ADAPTER_TYPE,R.layout.layout_find_item);//新建一个适配器
        GridLayoutManager gridLayoutManager=new GridLayoutManager(getActivity(),1);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(baseAdapter);
        baseAdapter.setOnItemClick(new BaseHomeAdapter.onItemClick() {
            @Override
            public void onClick(BaseUtil p) {
                //去详情页
                toDetails((News)p);
            }
        });
        pullLoad.setOnDateLoadListener(new PullLoadRecyclerView.OnDateLoadListener() {
            @Override
            public void onLoad() {
                //上啦加载的时候
                if (page!=10){//最多加载10页数据
                    praenter.onFindLoad(page);
                    page+=1;
                }

            }
        });
        pullLoad.setOnDateRefreshListener(new PullLoadRecyclerView.OnDateRefreshListener() {
            @Override
            public void onRefresh() {
                //下拉刷新的时候
                praenter.onFindRefresh(RefreshPage);
                if (RefreshPage !=10)RefreshPage+=1;
            }
        });
    }

    @Override
    public void onLoad(List list) {//下拉加载的时候
        if (datas!=null) datas.addAll(list);
        baseAdapter.setList(datas);
        baseAdapter.notifyDataSetChanged();//重新加载
        pullLoad.setLoad(false);//停止加载
    }

    @Override
    public void onRefresh(List list) {//刷新的时候
        if (datas!=null) datas=list;
        baseAdapter.setList(datas);
        baseAdapter.notifyDataSetChanged();//重新加载
        pullLoad.setRefresh(false);//停止刷新
    }

    private void toDetails(News p) {
        Intent intent=new Intent(getActivity(), NewsDetailsActivity.class);
        intent.putExtra("url",p.getUrl());
        startActivity(intent);
    }

    @Override
    public void OnLoading() {
        //正在加载中的时候
    }

    @Override
    public void onDestroy() {
//        if (((HomeActivity)getActivity()).getFindFragment()==null)
//            ((HomeActivity)getActivity()).setFindFragment(deasView);

        super.onDestroy();
    }
}

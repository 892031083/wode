package com.example.wode.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.wode.R;

/**
 * 自定义一个支持上啦刷新 及 下拉加载的控件
 */
public class PullLoadRecyclerView extends LinearLayout {

    private LayoutInflater inflater;
    private Context context;

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    public boolean IsRefresh=false;//是否刷新
    public boolean IsLoad=false;//是否加载更多
    private View footerView;
    public PullLoadRecyclerView(Context context) {
        super(context);
        initView(context);
    }

    public PullLoadRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public PullLoadRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        this.context=context;
        inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.pull_load_layout,null);
        swipeRefreshLayout=view.findViewById(R.id.swipe_refresh);
//        swipeRefreshLayout.setColorSchemeColors();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (!IsRefresh){
                    IsRefresh=true;
                    refreshData();
                }
            }
        });

        //处理RecyclerView
        recyclerView=view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);//设置固定大小
        recyclerView.setItemAnimator(new DefaultItemAnimator());//使用默认动画
        recyclerView.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {


                return IsRefresh || IsLoad;
            }
        });

        recyclerView.setVerticalFadingEdgeEnabled(false);//隐藏滚动条
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int firstItem=0;
                int lastItem=0;

                RecyclerView.LayoutManager  manager=recyclerView.getLayoutManager();
                int totalCoout=manager.getItemCount();
                Log.e("*****",totalCoout+"");
                if (manager instanceof GridLayoutManager){
                    GridLayoutManager gridLayoutManager=(GridLayoutManager)manager;
                    firstItem=gridLayoutManager.findFirstCompletelyVisibleItemPosition();//第一个完全可见的item
                    lastItem=gridLayoutManager.findLastCompletelyVisibleItemPosition();//最后一个完全可见的item
                    Log.e("*****",firstItem+":"+lastItem);
                    if (firstItem==0 ||firstItem== RecyclerView.NO_POSITION){
                        lastItem=gridLayoutManager.findLastCompletelyVisibleItemPosition();
                    }
                }
                //什么时候触发上啦加载更多
                if (swipeRefreshLayout.isEnabled()){
                    swipeRefreshLayout.setEnabled(true);
                }else{
                    swipeRefreshLayout.setEnabled(false);
                }

                if (!IsLoad &&
                        totalCoout==lastItem+1  //总条数和已经展示的条数相等时
                        && (dx>0||dy>0)){
                    footerView.setVisibility(View.VISIBLE);
                    loadData();
                }
            }
        });


        footerView=view.findViewById(R.id.footer_view);
        footerView.setVisibility(View.GONE);
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.rotate_anim);
        (footerView.findViewById(R.id.anim_img)).startAnimation(animation);//開始动画
        this.addView(view);
    }

    private void loadData() {
        Log.e("Load","=============");
        onDateLoadListener.onLoad();
    }

    private void refreshData() {
        onDateRefreshListener.onRefresh();

    }
    public interface OnDateLoadListener{//上拉加载更多接口
        void onLoad();
    }

    OnDateLoadListener onDateLoadListener;
    OnDateRefreshListener onDateRefreshListener;
    public void setOnDateLoadListener(OnDateLoadListener onDateLoadListener) {
        this.onDateLoadListener = onDateLoadListener;
    }

    public void setOnDateRefreshListener(OnDateRefreshListener onDateRefreshListener) {
        this.onDateRefreshListener = onDateRefreshListener;
    }

    public interface OnDateRefreshListener{//下拉刷新接口
        void onRefresh();
    }

    public void setRefresh(boolean refresh) {
        IsRefresh = refresh;
        swipeRefreshLayout.setRefreshing(false);//停止动画
        footerView.setVisibility(View.GONE);
    }

    public void setLoad(boolean load) {
        IsLoad = load;
        footerView.setVisibility(View.GONE);
    }
}

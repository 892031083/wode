package com.example.wode.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 上啦加载更多的RecyclerView
 */
public class LoadRecyclerView extends RecyclerView {
    private Context context;

    public LoadRecyclerView(@NonNull Context context) {
        super(context);
        initView(context);
    }

    public LoadRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public LoadRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context);
    }

    private void initView(Context context) {
        this.context=context;
    }


    @Override
    public void onDraw(Canvas c) {
        super.onDraw(c);
        this.addOnScrollListener(new OnScrollListener() {
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
                if (manager instanceof GridLayoutManager){
                    GridLayoutManager gridLayoutManager=(GridLayoutManager)manager;
                    firstItem=gridLayoutManager.findFirstCompletelyVisibleItemPosition();//第一个完全可见的item
                    lastItem=gridLayoutManager.findLastCompletelyVisibleItemPosition();//最后一个完全可见的item
                    //Log.e("*****",firstItem+":"+lastItem);
                    if (firstItem==0 ||firstItem== RecyclerView.NO_POSITION){
                        lastItem=gridLayoutManager.findLastCompletelyVisibleItemPosition();
                    }
                }
                //什么时候触发上啦加载更多

                if (
                        totalCoout==lastItem+1  //总条数和已经展示的条数相等时
                        && (dx>0||dy>0)){
                        loadData();
                }
            }
        });
    }

    private void loadData() {//上啦加载更多
        if (onLoadRecycler!=null){
            onLoadRecycler.onLoad();
        }
    }
    private OnLoadRecycler onLoadRecycler;

    public void setOnLoadRecycler(OnLoadRecycler onLoadRecycler) {
        this.onLoadRecycler = onLoadRecycler;
    }

    public interface  OnLoadRecycler{
        void onLoad();
    }

}

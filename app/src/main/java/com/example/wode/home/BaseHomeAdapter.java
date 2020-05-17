package com.example.wode.home;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.wode.R;
import com.example.wode.api.ImageTool;
import com.example.wode.home.fragment.FindFragment;
import com.example.wode.home.fragment.HomeFragment;
import com.example.wode.home.fragment.OrderFragment;
import com.example.wode.util.BaseUtil;
import com.example.wode.util.CartKeep;
import com.example.wode.util.News;
import com.example.wode.util.Order;
import com.example.wode.util.OrderForm;
import com.example.wode.util.Product;

import java.util.List;


public class BaseHomeAdapter extends RecyclerView.Adapter<BaseHomeAdapter.productHolder> {

    private List list;
    private Context context;
    private int adapter_type=0;//adapter类型 首页的 商品列表页的 发现页的
    private onItemClick onItemClick;//条目点击事件
    private int LayoutId;
    public void setOnItemClick(BaseHomeAdapter.onItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    public List<BaseUtil> getList() {
        return list;
    }

    public void setList(List<BaseUtil> list) {
        this.list = list;
    }



    public BaseHomeAdapter(List list, Context context, int adapter_type, int LayoutId) {
        this.list = list;
        this.context = context;
        this.adapter_type = adapter_type;
        this.LayoutId=LayoutId;
    }

    @NonNull
    @Override
    public productHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(LayoutId,null);
        return new productHolder(view);
    }




    @Override
    public void onBindViewHolder(@NonNull productHolder holder, final int position) {
        switch (adapter_type){
            case HomeFragment.ADAPTER_TYPE:
                bindHomeView(holder,position);
                break;
            case FindFragment
                        .ADAPTER_TYPE:
                 bindFindView(holder,position);
                break;
            case OrderFragment
                        .ADAPTER_TYPE:
                 bindOrderView(holder,position);
                break;
            case ProductListActivity.ADAPTER_TYPE:
                  bindProductListView(holder,position);
                break;
        }


        holder.holderView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClick!=null){
                    onItemClick.onClick((BaseUtil) list.get(position));
                }
            }
        });
    }
    //商品列表页面 (productlistactivity)
    private void bindProductListView(productHolder holder, int position) {
        Product product= (Product) list.get(position);

        ((TextView)holder.holderView.findViewById(R.id.productName)).setText(product.getProductName());
        Glide.with(context).load(product.getImageUrl()).into((ImageView) holder.holderView.findViewById(R.id.productItemImg));
        Log.i("WWWW",product.getImageUrl());
        ((TextView)holder.holderView.findViewById(R.id.productPrice)).setText(product.getPrive()+"元");
        ((TextView)holder.holderView.findViewById(R.id.productUserName)).setText("捐赠者:"+product.getUserNickName());

    }

    //设置订单页面的适配器 （orderFragment）
    private void bindOrderView(productHolder holder, int position) {
        final OrderForm order=(OrderForm) list.get(position);
        List<CartKeep> cartKeeps=order.getOrderData(order.getDataJson());
        ((TextView)holder.holderView.findViewById(R.id.productName)).setText("商品名称:"+cartKeeps.get(0).getProductName());
        Glide.with(context).load(cartKeeps.get(0).getProductImg()).into(((ImageView)holder.holderView.findViewById(R.id.photo)));
        ((TextView)holder.holderView.findViewById(R.id.createTime)).setText("创建时间:"+Order.getTime(order.getCreate_time()));
        String string;

        ((TextView)holder.holderView.findViewById(R.id.productType)).setText("商品分类:"+cartKeeps.get(0).getType());
        ((TextView)holder.holderView.findViewById(R.id.productNum)).setText("共"+cartKeeps.size()+"件商品");
        ((TextView)holder.holderView.findViewById(R.id.productPrice)).setText("￥"+order.getPrive());
        holder.holderView.findViewById(R.id.toorderDetails).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//定义点击
                if (onItemClick!=null){
                    onItemClick.onClick(order);
                }
            }
        });
    }

    //设置findfragment页面的适配器
    private void bindFindView(productHolder holder, int position) {
        News news= (News) list.get(position);
        ((TextView)holder.holderView.findViewById(R.id.name)).setText(news.getDescription());
        ((TextView)holder.holderView.findViewById(R.id.title)).setText(news.getTitle());
        Glide.with(context).load(news.getPicUrl()).into((ImageView)holder.holderView.findViewById(R.id.img));
    }

    //设置首页的适配器
    private void bindHomeView(productHolder holder, int position) {
        Product product= (Product) list.get(position);

        ((TextView)holder.holderView.findViewById(R.id.productName)).setText(product.getProductName());
        Glide.with(context).load(product.getImageUrl()).into((ImageView) holder.holderView.findViewById(R.id.productItemImg));
        ((TextView)holder.holderView.findViewById(R.id.productPrice)).setText("￥"+product.getPrive());
        ((TextView)holder.holderView.findViewById(R.id.productUserName)).setText("捐赠者:"+product.getUserNickName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    /**
     * ViewHolder
     */
    class productHolder extends RecyclerView.ViewHolder{
      View holderView;
        public productHolder(@NonNull View itemView) {
            super(itemView);
            holderView=itemView;

        }
    }
    public interface onItemClick {
        void onClick(BaseUtil p);
    }
}

package com.example.wode.home.fragment;

import android.content.Intent;
import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wode.R;
import com.example.wode.base.BaseFragment;
import com.example.wode.home.BaseHomeAdapter;
import com.example.wode.home.HomeActivity;
import com.example.wode.userdata.OrderDetailsActivity;
import com.example.wode.util.BaseUtil;
import com.example.wode.util.Order;
import com.example.wode.util.OrderForm;

import java.util.List;

public class OrderFragment extends BaseFragment implements FragmentContract.FragmentView {
    public static final int ADAPTER_TYPE=3;
    public static List datas;//元数据

    FragmentPraenter praenter;
    private RecyclerView recyclerView;
    @Override
    protected void initView() {
       // Log.i("ORDERFRAGMENT","create");
        HomeActivity.showIsHome=false;
        recyclerView=deasView.findViewById(R.id.recycler_view);
        praenter=new FragmentPraenter(this);
        if (datas==null){
            praenter.getOrders();
        }else{
            showData(datas);
        }

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_order;
    }

    @Override
    public void showData(List list) {
        List<OrderForm> mlist=list;
//        for (OrderForm orderForm:mlist){
//            Log.i("=========",orderForm.getOrderData(orderForm.getDataJson()).get(0).toString());
//        }
        if (datas==null) datas=list;
        BaseHomeAdapter baseHomeAdapter=new BaseHomeAdapter(list,getActivity(),ADAPTER_TYPE,R.layout.layout_order_item);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(baseHomeAdapter);
        baseHomeAdapter.setOnItemClick(new BaseHomeAdapter.onItemClick() {
            @Override
            public void onClick(BaseUtil p) {
                toDetails((OrderForm)p);
            }
        });
    }

    @Override
    public void onLoad(List list) {

    }

    @Override
    public void onRefresh(List list) {

    }

    private void toDetails(OrderForm p) {
    //去详情页
        Intent intent=new Intent(getActivity(), OrderDetailsActivity.class);//
        intent.putExtra("orderForm",p);//已实现序列化 可以传递对象
        startActivity(intent);
    }

    @Override
    public void OnLoading() {

    }

    @Override
    public void onDestroy() {
//        if (((HomeActivity)getActivity()).getOrderFragment()==null)
//            ((HomeActivity)getActivity()).setOrderFragment(deasView);

        super.onDestroy();
    }
}

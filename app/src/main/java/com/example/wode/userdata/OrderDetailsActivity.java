package com.example.wode.userdata;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.wode.R;
import com.example.wode.base.BaseActivity;
import com.example.wode.home.CartActivity;
import com.example.wode.util.CartKeep;
import com.example.wode.util.Order;
import com.example.wode.util.OrderForm;
import com.example.wode.widget.CalculationText;

import java.util.List;

/**
 * 订单详情页
 */
public class OrderDetailsActivity extends BaseActivity {
    /**
     * 订单详情页面
     * @return
     */
    private OrderForm orderForm;
    @Override
    protected boolean IsShowTitle() {
        return true;
    }

    @Override
    protected void initData() {
        setTitleText("订单详情");
    }

    @Override
    protected void initView() {
        orderForm=getIntent().getParcelableExtra("orderForm");
        if (orderForm!=null){
            ((TextView)findViewById(R.id.orderId)).setText("订单编号:"+orderForm.getId());
            ((TextView)findViewById(R.id.ctime)).setText("创建时间:"+(Order.getTime(orderForm.getCreate_time())));
            ((TextView)findViewById(R.id.sum)).setText("订单总价:"+orderForm.getPrive()+"元");
            showData();
        }
    }
    ListView listView;
    private void showData() {
        List<CartKeep> cartKeeps=orderForm.getOrderData(orderForm.getDataJson());
        listView=findViewById(R.id.listview);
        listView.setAdapter(new OrderDetailsAdapter(cartKeeps));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_orderdetails;
    }
    public void toCommit(int ProductId){
        Intent intent=new Intent(this,CommitActivity.class);
        intent.putExtra("productId",ProductId);
        startActivity(intent);
    }
    class OrderDetailsAdapter extends BaseAdapter {
        List<CartKeep> list;

        public List<CartKeep> getList() {
            return list;
        }

        public void setList(List<CartKeep> list) {
            this.list = list;
        }

        public OrderDetailsAdapter(List<CartKeep > list){
            this.list=list;
        }
        @Override
        public int getCount() {
            return list.size();
        }


        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }


        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
           convertView=OrderDetailsActivity.this.getLayoutInflater().inflate(R.layout.order_product_item,null);
           TextView pname=convertView.findViewById(R.id.productName);
            TextView pnum=convertView.findViewById(R.id.productNum);
            TextView price=convertView.findViewById(R.id.productPrice);
            pname.setText(list.get(position).getProductName());
            pnum.setText("x"+list.get(position).getNum());
            price.setText("￥"+(list.get(position).getMoney()*list.get(position).getNum()));
            Glide.with(OrderDetailsActivity.this).load(list.get(position).getProductImg()).into((ImageView) convertView.findViewById(R.id.photo));
            convertView.findViewById(R.id.commit).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    toCommit(list.get(position).getProductId());
                }
            });
           return convertView;
        }
        class ViewHolder{
            ImageView photo;
            TextView title,sum,num;
            CalculationText calculationText;
        }
    }

}

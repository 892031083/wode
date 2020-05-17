package com.example.wode.home;

import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.wode.R;
import com.example.wode.UserUtilsBean;
import com.example.wode.api.ApiUrl;
import com.example.wode.api.DataBaseTool;
import com.example.wode.api.OrderApi;
import com.example.wode.api.result.OnResultListener;
import com.example.wode.api.result.ResultOrder;
import com.example.wode.base.BaseActivity;
import com.example.wode.home.fragment.OrderFragment;
import com.example.wode.userdata.OrderDetailsActivity;
import com.example.wode.util.OrderForm;
import com.example.wode.widget.CalculationText;
import com.example.wode.util.CartKeep;
import com.example.wode.widget.ToastDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * 购物车界面
 */
public class CartActivity extends BaseActivity implements View.OnClickListener {
    public static final String  TAG="购物车";
    Button oredrBtn;//结算
    TextView sumText;//总计
    ListView listView;
    private double Totalprice=0;//总价
    private int TotalNum=0;//商品数量
    private Handler handler=new Handler();
    private List<int[]> updateList;//用户点击数量增加减少按钮 先把要修改的数据存到此集合 然后在推出后后做批量修改sqllite
    @Override
    protected boolean IsShowTitle() {//显示标题
        return true;
    }

    @Override
    protected void initData() {
        handler.post(new Runnable() {
           @Override
           public void run() {
               List<CartKeep> list=DataBaseTool.getInstance().getCartKeepList(1,0);
                Log.i("==============",list.size()+"");
               showData(list);//查询sql

           }
       });
    }

    @Override
    protected void initView() {
        setTitleText(TAG);
        listView=findViewById(R.id.listview);
        sumText=findViewById(R.id.sum);
        updateList=new ArrayList<>();
        findViewById(R.id.order).setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_cart;
    }

    @Override
    public void onClick(View v) {
        final ToastDialog toastDialog=new ToastDialog(this,"您确定要支付吗");
        toastDialog.setCancelable(false);
        toastDialog.setOnClickDialog(new ToastDialog.OnClickDialog() {
            @Override
            public void onFixClick() {
                //是
                toastDialog.dismiss();
                toPay();
            }

            @Override
            public void onCancelClick() {
                //否
                toastDialog.dismiss();
            }
        });

        toastDialog.show();


    }

    private void toPay() {
        //去支付
        List<CartKeep> list=((CartAdapter)listView.getAdapter()).getList();//获取购物车列表
        final OrderForm orderForm=new OrderForm(System.currentTimeMillis(),Totalprice,System.currentTimeMillis(), UserUtilsBean.getInstance().getUser().getUserId()
        ,UserUtilsBean.getInstance().getUser().getNickName(),UserUtilsBean.getInstance().getUser().getUserMobile(),"");
        orderForm.setDataJson(orderForm.getOrderData(list));
        showOnloading(true);
        //直接在activity里调用请求。
        OrderApi orderApi=new OrderApi();
        orderApi.SaveOrder(orderForm, new OnResultListener<ResultOrder>() {
            @Override
            public void onSuccess(final ResultOrder resultOrder) {
                if (resultOrder.getCode()==200){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            OrderFragment.datas=null;
                            toOrder(orderForm);
                        }
                    });
                }else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showMsg(resultOrder.getMsg());
                        }
                    });
                }
            }

            @Override
            public void onError() {

            }
        });
    }

    private void toOrder(OrderForm orderForm) {
        showOnloading(false);
        showMsg("支付成功");
        Intent intent=new Intent(this, OrderDetailsActivity.class);
        intent.putExtra("orderForm",orderForm);
        startActivity(intent);
        finish();
    }


    public void showData(List<CartKeep> list) {//显示数据
        listView.setAdapter(new CartAdapter(list));//显示listview
        //显示总价
        for (CartKeep cartKeep:list){
            Totalprice+=(cartKeep.getNum()*cartKeep.getMoney());//计算总价
        }
        sumText.setText("￥"+Totalprice);
        TotalNum=list.size();//计算商品数量
        ((TextView)findViewById(R.id.titleProductNum)).setText("共"+TotalNum+"件商品");
    }

    protected void removeCartItem( int i, final int id,double money){//参数 需要删除list的下标  购物车ID 以及价格
        final List<CartKeep> list=((CartAdapter)listView.getAdapter()).getList();

        handler.post(new Runnable() {
            @Override
            public void run() {
                DataBaseTool.getInstance().DeleteCartData(id);//删除数据 sqlite
            }
        });//删除数据
        list.remove(i);
        CartAdapter cartAdapter=new CartAdapter(list);
        listView.setAdapter(cartAdapter);
        cartAdapter.notifyDataSetChanged();
        //总价减少
        Totalprice-=money;
        sumText.setText("￥"+Totalprice);
        TotalNum--;//数量减少
        ((TextView)findViewById(R.id.titleProductNum)).setText("共"+TotalNum+"件商品");
    }


    protected void setCartProductNum(int id,int num,double prive){//id为cart的id  num修改的数量
        //这里因为用户可能会频繁点击 所以不做sqlite处理 先把要修改的数据存起来 然后在推出后做批量修改
        updateList.add(new int[]{id,num});
        //修改总价
        Totalprice+=prive;
        sumText.setText("￥"+Totalprice);
    }



    @Override
    protected void onDestroy() {
        DataBaseTool.getInstance().asyupdateCartNum(updateList);
        super.onDestroy();
    }





    class CartAdapter extends BaseAdapter{
        List<CartKeep> list;

        public List<CartKeep> getList() {
            return list;
        }

        public void setList(List<CartKeep> list) {
            this.list = list;
        }

        public CartAdapter(List<CartKeep > list){
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
            ViewHolder viewHolder=new ViewHolder();
            if (convertView==null){
                convertView=CartActivity.this.getLayoutInflater().inflate(R.layout.layout_cart_item,null);
                viewHolder.photo=convertView.findViewById(R.id.photo);
                viewHolder.title=convertView.findViewById(R.id.title);
                viewHolder.sum=convertView.findViewById(R.id.sum);
                viewHolder.calculationText=convertView.findViewById(R.id.calculationText);
                viewHolder.num=convertView.findViewById(R.id.num);
                convertView.setTag(viewHolder);
            }else{
               viewHolder=(ViewHolder) convertView.getTag();
            }
            Glide.with(CartActivity.this).load(list.get(position).getProductImg()).into(viewHolder.photo);
            viewHolder.title.setText(list.get(position).getProductName());
            viewHolder.sum.setText("单价:￥"+list.get(position).getMoney());
            viewHolder.num.setText(""+list.get(position).getNum());
          //  final int UpdataNum=Integer.valueOf(viewHolder.num.getText().toString());

            viewHolder.calculationText.setOnClickNum(new CalculationText.OnClickNum() {
                @Override
                public void onClickUpdateNum(int dataNum,boolean isAdd) {
                    //增加 and 减少数量 TODO i参数 id 数量 及 要修改的moeny值
                    setCartProductNum(list.get(position).getId(),dataNum,isAdd?list.get(position).getMoney():-list.get(position).getMoney());
                }


                @Override
                public void onClickDeleZero() {
                    //删除
                removeCartItem(position,list.get(position).getId(),list.get(position).getMoney());
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

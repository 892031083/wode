package com.example.wode.home;

import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.wode.R;
import com.example.wode.api.ApiUrl;
import com.example.wode.api.CommApi;
import com.example.wode.api.DataBaseTool;
import com.example.wode.api.result.OnResultListener;
import com.example.wode.api.result.ResultComm;
import com.example.wode.base.BaseActivity;
import com.example.wode.util.CartKeep;
import com.example.wode.util.Comment;
import com.example.wode.util.Product;

import java.util.List;

/**
 * 商品详情页
 */
public class DetailsActivity extends BaseActivity implements View.OnClickListener {
    public static  String TAG="商品详情";

    TextView productUserName,productNum,productPrice;
    ImageView productImg;
    ListView listView;//评论列表
    Product product;
    @Override
    protected boolean IsShowTitle() {
        return true;
    }

    @Override
    protected void initData() {
        getComments();
    }

    @Override
    protected void initView() {
        setTitleText(TAG);
        productImg=findViewById(R.id.productImg);
        productNum=findViewById(R.id.productNum);
        productPrice=findViewById(R.id.productPrice);
        productUserName=findViewById(R.id.productUserName);
        listView=findViewById(R.id.listview);
        findViewById(R.id.keepBtn).setOnClickListener(this);
        findViewById(R.id.addCartBtn).setOnClickListener(this);
        findViewById(R.id.goShopBtn).setOnClickListener(this);

         product=getIntent().getParcelableExtra("product");
        if (product!=null){
          //  productUserName.setText(product.getUser());
           // Log.i("ProductDetails",product.toString());
            productNum.setText("剩余数量:"+product.getNum());
            productPrice.setText("￥"+product.getPrive());

            Glide.with(this).load(product.getImageUrl()).into(productImg);
        }
    }

    //获取评论列表 (这个界面简单 直接从activity里拉数据)
    protected void getComments(){
        //TODO model
//       List list= Comment.getCommentList(10);//获取10条测试数据
//        showCommentsData(list);
        if (!ApiUrl.ISHTTP){
            List list= Comment.getCommentList(10);//获取10条测试数据
        showCommentsData(list);
            return;
        }
        CommApi commApi=new CommApi();
        commApi.OnGetComment(product.getProductID(), new OnResultListener<ResultComm>() {
            @Override
            public void onSuccess(final ResultComm resultComm) {
                if (resultComm.getCode()==200){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showCommentsData(resultComm.getList());//显示数据

                        }
                    });
                }else {

                }
            }

            @Override
            public void onError() {

            }
        });
    }

    public void  showCommentsData(List<Comment> list){//显示数据
        listView.setAdapter(new MAdapter(list));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_product_details;
    }

    Handler handler=new Handler();
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.addCartBtn://加入购物车
                addCartData(1);
                break;
            case R.id.goShopBtn://立即购买
                addCartData(3);
                break;
            case R.id.keepBtn://收藏和购物车用的一个表
                addCartData(2);
                break;
        }
    }

    private void addCartData(final int type) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                Boolean b=DataBaseTool.getInstance().addCartKeepData(CartKeep.ProductToCart(product,1,type==3?1:type));//加入数据库


                if (type==1){
                    showMsg("已加入购物车");
                }else if(type==2){
                    toKeep(b);
                }else if (type==3){
                    toCart();
                }
            }

        });
    }

    private void toCart() {
      startActivity(new Intent(this,CartActivity.class));
    }

    private void toKeep(boolean b) {//去收藏
        if (b){
            showMsg("已收藏成功");
            ((TextView)findViewById(R.id.keepBtn)).setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.icon_keeppall),null,null,null);
        }else {
            showMsg("取消收藏成功");
            ((TextView)findViewById(R.id.keepBtn)).setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.mipmap.icon_keep2),null,null,null);

        }

    }


    public class MAdapter extends BaseAdapter{
        List<Comment> mlist;

        public MAdapter(List<Comment> mlist) {
            this.mlist = mlist;
        }

        @Override
        public int getCount() {
            return mlist.size();
        }


        @Override
        public Object getItem(int position) {
            return mlist.get(position);
        }


        @Override
        public long getItemId(int position) {
            return position;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView=DetailsActivity.this.getLayoutInflater().inflate(R.layout.layout_comment_item,null);
            if (ApiUrl.ISHTTP) Glide.with(DetailsActivity.this).load(mlist.get(position).getUserurl()).into((ImageView) convertView.findViewById(R.id.userAvatar));
            TextView username=convertView.findViewById(R.id.userName);
            TextView date=convertView.findViewById(R.id.commentDate);
            TextView commentText=convertView.findViewById(R.id.coomentText);
            username.setText(mlist.get(position).getUserName());
            date.setText(mlist.get(position).getDate());
            commentText.setText(mlist.get(position).getCommentText());
            return convertView;
        }
    }
}

package com.example.wode.home;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.wode.R;
import com.example.wode.UserUtilsBean;
import com.example.wode.api.ProductApi;
import com.example.wode.api.result.OnResultListener;
import com.example.wode.api.result.ResultProduct;
import com.example.wode.base.BaseActivity;
import com.example.wode.util.CartKeep;
import com.example.wode.util.Product;

import java.util.List;

public class MproActivity extends BaseActivity {
    private static String TAG="我的发布";
    public static final int ADAPTERTYPE=600;

    private ListView listView;
    List<Product> list;
    @Override
    protected boolean IsShowTitle() {
        return true;
    }

    @Override
    protected void initData() {

        ProductApi productApi=new ProductApi();
        productApi.OnGetProductList(UserUtilsBean.getInstance().getUser().getUserId(), new OnResultListener<ResultProduct>() {
            @Override
            public void onSuccess(final ResultProduct resultProduct) {
                if (resultProduct.getCode()==200){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            list=resultProduct.getData();
                            showData();
                        }
                    });
                }
            }

            @Override
            public void onError() {

            }
        });
    }

    private void showData() {
        listView.setAdapter(new MproAdapter(list));
    }

    @Override
    protected void initView() {
       setTitleText(TAG);
        listView=findViewById(R.id.listview);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_keep;
    }
    class MproAdapter extends BaseAdapter {
        List<Product> list;

        public List<Product> getList() {
            return list;
        }

        public void setList(List<Product> list) {
            this.list = list;
        }

        public MproAdapter(List<Product > list){
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
            convertView=MproActivity.this.getLayoutInflater().inflate(R.layout.layout_mypro_item,null);
            viewHolder.photo=convertView.findViewById(R.id.photo);
            viewHolder.mobile=convertView.findViewById(R.id.mobile);
            viewHolder.price=convertView.findViewById(R.id.sum);
            viewHolder.title=convertView.findViewById(R.id.title);
            convertView.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) convertView.getTag();
        }
        Glide.with(MproActivity.this).load(list.get(position).getImageUrl()).into(viewHolder.photo);
        viewHolder.price.setText("￥"+list.get(position).getPrive());
        viewHolder.title.setText(list.get(position).getProductName());
        viewHolder.mobile.setText(list.get(position).getMobile());
            return convertView;
        }
        class ViewHolder{
            ImageView photo;
            TextView title,price,mobile;

        }
    }

}

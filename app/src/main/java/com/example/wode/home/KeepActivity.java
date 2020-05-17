package com.example.wode.home;

import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.wode.R;
import com.example.wode.api.DataBaseTool;
import com.example.wode.base.BaseActivity;
import com.example.wode.util.CartKeep;

import java.util.ArrayList;
import java.util.List;

/**
 * 购物车界面
 */
public class KeepActivity extends BaseActivity implements View.OnClickListener {
    public static final String  TAG="我的收藏";

    ListView listView;

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
                List<CartKeep> list=DataBaseTool.getInstance().getCartKeepList(2,0);
                showData(list);//查询sql

            }
        });
    }

    @Override
    protected void initView() {
        setTitleText(TAG);
        listView=findViewById(R.id.listview);
        updateList=new ArrayList<>();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_keep;
    }

    @Override
    public void onClick(View v) {

    }


    public void showData(List<CartKeep> list) {//显示数据
        listView.setAdapter(new KeepAdapter(list==null?new ArrayList<CartKeep>():list));//显示listview

    }



    @Override
    protected void onDestroy() {
        DataBaseTool.getInstance().asyupdateCartNum(updateList);
        super.onDestroy();
    }




    private void cancelKeep(int position, final int id) {
        final List<CartKeep> list=((KeepAdapter)listView.getAdapter()).getList();

        handler.post(new Runnable() {
            @Override
            public void run() {
                DataBaseTool.getInstance().DeleteCartData(id);//删除数据 sqlite
            }
        });//删除数据
        list.remove(position);
        KeepActivity.KeepAdapter keepAdapter=new  KeepAdapter(list);
        listView.setAdapter(keepAdapter);
        keepAdapter.notifyDataSetChanged();
    }

    class KeepAdapter extends BaseAdapter{
        List<CartKeep> list;

        public List<CartKeep> getList() {
            return list;
        }

        public void setList(List<CartKeep> list) {
            this.list = list;
        }

        public KeepAdapter(List<CartKeep > list){
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
                convertView=KeepActivity.this.getLayoutInflater().inflate(R.layout.layout_keep_item,null);
                viewHolder.photo=convertView.findViewById(R.id.photo);
                viewHolder.title=convertView.findViewById(R.id.title);
                viewHolder.name=convertView.findViewById(R.id.name);
                viewHolder.RecallBtn=convertView.findViewById(R.id.cancelBtn);
                convertView.setTag(viewHolder);
            }else{
                viewHolder=(ViewHolder) convertView.getTag();
            }
            Glide.with(KeepActivity.this).load(list.get(position).getProductImg()).into(viewHolder.photo);
            viewHolder.title.setText(list.get(position).getProductName());
            viewHolder.name.setText("捐赠者:");
            //  final int UpdataNum=Integer.valueOf(viewHolder.num.getText().toString());
            viewHolder.RecallBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cancelKeep(position,list.get(position).getId());//取消收藏
                }
            });

            return convertView;
        }
        class ViewHolder{
            ImageView photo;
            TextView title,name;
            LinearLayout RecallBtn;
        }
    }

}

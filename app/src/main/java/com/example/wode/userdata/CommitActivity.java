package com.example.wode.userdata;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.wode.R;
import com.example.wode.UserUtilsBean;
import com.example.wode.api.ApiUrl;
import com.example.wode.api.CommApi;
import com.example.wode.api.result.OnResultListener;
import com.example.wode.api.result.ResultComm;
import com.example.wode.base.BaseActivity;
import com.example.wode.util.Comment;
import com.example.wode.util.Product;


import java.util.ArrayList;
import java.util.List;

/**
 * 直接在activity里请求吧
 *
 */
public class CommitActivity extends BaseActivity implements View.OnClickListener {

    private ListView listView;
    Comment comment;
//    private List<Comment> comments;//元数据
    int ProductId;
    @Override
    protected boolean IsShowTitle() {
        return true;
    }

    @Override
    protected void initData() {
        setTitle("添加评论");
        comment=new Comment();
        comment.setUserId(UserUtilsBean.getInstance().getUser().getUserId());
        comment.setUserName(UserUtilsBean.getInstance().getUser().getNickName());
//        if (UserUtilsBean.getInstance())
        comment.setUserurl(UserUtilsBean.getInstance().getUser().getAvatarUrl());
    }

    @Override
    protected void initView() {
        listView=findViewById(R.id.listview);
        ProductId=getIntent().getIntExtra("productId",0);
        if (!ApiUrl.ISHTTP){
            showData(null);
            return;
        }
        CommApi commApi=new CommApi();
        commApi.OnGetComment(ProductId, new OnResultListener<ResultComm>() {
            @Override
            public void onSuccess(final ResultComm resultComm) {
                if (resultComm.getCode()==200){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showData(resultComm.getList());//显示数据

                        }
                    });
                }else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showData(null);

                        }
                    });
                }
            }

            @Override
            public void onError() {

            }
        });

        findViewById(R.id.postbtn).setOnClickListener(this);
    }
    protected void showData(List<Comment> list){
        MAdapter mAdapter=new MAdapter(list);
        listView.setAdapter(mAdapter);
    }
    @Override
    protected int getLayoutId() {
        return R.layout.layout_commit;
    }


    @Override
    public void onClick(View v) {
        if (!ApiUrl.ISHTTP) return;

        String commData=((EditText)findViewById(R.id.commdata)).getText().toString();
        if (commData==null||commData.equals("")){
            showMsg("评论不能为空");
            return;
        }
        ((EditText) findViewById(R.id.commdata)).setText("");
        showOnloading(true);
        //添加评论
        comment.setCommentText(commData);
        comment.setProid(ProductId);
        CommApi commApi=new CommApi();
        commApi.SaveComment(comment, new OnResultListener<ResultComm>() {
            @Override
            public void onSuccess(ResultComm resultComm) {
                if (resultComm.getCode()==200){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showMsg("添加评论成功");
                            addList();
                        }
                    });
                }
            }

            @Override
            public void onError() {

            }
        });
    }

    private void addList() {
        MAdapter mAdapter= (MAdapter) listView.getAdapter();
        List<Comment> list=mAdapter.getMlist();
        list.add(0,comment);
        mAdapter=new MAdapter(list);
        listView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        showOnloading(false);

    }

    public class MAdapter extends BaseAdapter {
        List<Comment> mlist;

        public List<Comment> getMlist() {
            return mlist;
        }

        public void setMlist(List<Comment> mlist) {
            this.mlist = mlist;
        }

        public MAdapter(List<Comment> mlist) {
            this.mlist = mlist==null?new ArrayList<Comment>():mlist;
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
            convertView=CommitActivity.this.getLayoutInflater().inflate(R.layout.layout_comment_item,null);
            if(ApiUrl.ISHTTP) Glide.with(CommitActivity.this).load(mlist.get(position).getUserurl()).into((ImageView)convertView.findViewById(R.id.userAvatar));
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

package com.example.wode.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.wode.util.BaseUtil;

import java.util.List;

/**
 * 所有界面中 ListView 的适配器
 * @param <T>
 */
public class BaseListAdapter<T extends BaseUtil> extends BaseAdapter {
    private List<T> list;
    private Context context;
    private int AdapterType;//标识 分别是界面的adapter
    int LayoutId;//布局资源文件ID
    public BaseListAdapter(List<T> list, Context context,int AdapterType,int LayoutId) {
        this.list = list;
        this.context = context;
        this.AdapterType=AdapterType;
        this.LayoutId=LayoutId;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        switch (AdapterType){

        }

        return null;
    }


}

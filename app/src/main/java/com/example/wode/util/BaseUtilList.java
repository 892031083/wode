package com.example.wode.util;

import java.util.ArrayList;
import java.util.List;

/**
 * 自己封装一个List
 * @param <T>
 */
public class BaseUtilList<T extends BaseUtil> {
    List<T> list;

    public BaseUtilList() {
       list=new ArrayList<>();
    }
    public void add(T t){
        list.add(t);
    }
    public void addAll(BaseUtilList baseUtilList){
        list.addAll(baseUtilList.getList());
    }
    public void remove(int i){
        list.remove(i);
    }
    public void removeAll(BaseUtilList baseUtilList){
        list.removeAll(baseUtilList.getList());
    }
    public int  size(){
        return list.size();
    }
    public T get(int i){
        return list.get(i);
    }
    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}

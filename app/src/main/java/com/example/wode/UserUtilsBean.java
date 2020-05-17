package com.example.wode;

import com.example.wode.util.User;

/**
 * 用户类
 */
public class UserUtilsBean {
    private static UserUtilsBean userUtilsBean;
    private User user;
    private UserUtilsBean(){
        user=new User(0,"");
    }
    public static UserUtilsBean getInstance(){//单利模式
        if (userUtilsBean==null){
            synchronized (UserUtilsBean.class){
                if (userUtilsBean==null){
                    userUtilsBean=new UserUtilsBean();
                }
            }
        }
        return userUtilsBean;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

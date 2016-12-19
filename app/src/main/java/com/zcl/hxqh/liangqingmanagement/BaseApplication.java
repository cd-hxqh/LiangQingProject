package com.zcl.hxqh.liangqingmanagement;

import android.app.Application;


/**
 * Created by think on 2015/12/11.
 */
public class BaseApplication extends Application {
    private String username = "";
    private static BaseApplication mContext;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public static BaseApplication getInstance(){
        return mContext;
    }


    @Override
    public void onCreate() {
        super.onCreate();

        mContext = this;
    }


}

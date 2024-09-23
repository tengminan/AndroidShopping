package com.example.androidshopping.app;

import android.app.Application;
import android.content.Context;




/**

 * 作用：整个软件
 */
public class MyApplication extends Application {

    public static Context getContext() {
        return mContext;
    }

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;


    }


}

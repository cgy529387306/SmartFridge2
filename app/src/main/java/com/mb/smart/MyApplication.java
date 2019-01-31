package com.mb.smart;

import android.app.Application;
import android.content.Context;

import com.avos.avoscloud.AVAnalytics;
import com.avos.avoscloud.AVOSCloud;


public class MyApplication extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;// 初始化
        AVOSCloud.initialize(this,"D7Pob1Nvpahux5OmN5spEgoK-gzGzoHsz", "MYMeAku8VpjhJSh4yOP47m9C");
        AVOSCloud.setDebugLogEnabled(true);
        AVAnalytics.enableCrashReport(this, true);
    }

    public static Context getAppContext() {
        return  mContext;
    }
}

package com.cw.shareprefences;

import android.app.Application;
import android.content.Context;

/**
 * Create By chao.wang on 2017/10/30 17:39
 * <p>
 * email：wc0811@163.com
 * <p>
 * 类描述：单例模式获取当前项目的Context
 * <p>
 * 更改记录：
 */
public class App extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        initContext();
    }


    public static Context getContext() {
        return context;
    }

    public void initContext() {
        if (context == null) {
            synchronized (App.class) {
                if (context == null) {
                    context = this;
                }
            }
        }
    }
}

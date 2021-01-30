package com.benshanyang.utils;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.benshanyang.toolslibrary.manager.ActivityLifecycle;
import com.benshanyang.toolslibrary.manager.ActivityStackManager;

public class UtilsApp extends Application {

    public static ActivityStackManager manager;

    @Override
    public void onCreate() {
        super.onCreate();
        manager = ActivityStackManager.getInstance();
        //manager.clearStack();//清空activity栈
        //manager.topActivity();//获取Activity栈顶activity
        manager.setActivityLifecycle(new ActivityLifecycle(){
            @Override
            public void onActivityCreated(@NonNull Activity activity, Bundle savedInstanceState) {

            }

            @Override
            public void onActivityStarted(@NonNull Activity activity) {

            }

            @Override
            public void onActivityResumed(@NonNull Activity activity) {

            }

            @Override
            public void onActivityPaused(@NonNull Activity activity) {

            }

            @Override
            public void onActivityStopped(@NonNull Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(@NonNull Activity activity) {

            }
        });
        registerActivityLifecycleCallbacks(manager);
    }
}

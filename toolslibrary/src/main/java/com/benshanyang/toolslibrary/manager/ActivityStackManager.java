package com.benshanyang.toolslibrary.manager;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;

import java.util.Stack;

/**
 * @ClassName: ActivityStackManager
 * @Description: Activity栈
 * @Author: YangKuan
 * @Date: 2020/6/30 10:20
 */
public final class ActivityStackManager implements Application.ActivityLifecycleCallbacks {

    private final Stack<Activity> activityStack = new Stack<Activity>();
    private static ActivityStackManager activityStackManager;
    private ActivityLifecycle activityLifecycle;

    private ActivityStackManager() {

    }

    public final static ActivityStackManager getInstance() {
        if (activityStackManager == null) {
            activityStackManager = new ActivityStackManager();
        }
        return activityStackManager;
    }

    @Override
    public final void onActivityCreated(@NonNull Activity activity, Bundle savedInstanceState) {
        //可以在这里add
        activityStack.add(activity);
        Intent intent = activity.getIntent();
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (intent.getBooleanExtra("onlyAlive", false) || (bundle != null && bundle.getBoolean("onlyAlive"))) {
                aliveLastActivity(activity);
            }
        }
        if (activityLifecycle != null) {
            activityLifecycle.onActivityCreated(activity, savedInstanceState);
        }
    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {
        if (activityLifecycle != null) {
            activityLifecycle.onActivityStarted(activity);
        }
    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {
        if (activityLifecycle != null) {
            activityLifecycle.onActivityResumed(activity);
        }
    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {
        if (activityLifecycle != null) {
            activityLifecycle.onActivityPaused(activity);
        }
    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {
        if (activityLifecycle != null) {
            activityLifecycle.onActivityStopped(activity);
        }
    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {
        if (activityLifecycle != null) {
            activityLifecycle.onActivitySaveInstanceState(activity, outState);
        }
    }

    @Override
    public final void onActivityDestroyed(@NonNull Activity activity) {
        //可以在这里remove
        activityStack.remove(activity);
        if (activityLifecycle != null) {
            activityLifecycle.onActivityDestroyed(activity);
        }
    }

    /**
     * 设置Activity生命周期回调帮助类
     *
     * @param activityLifecycle Activity生命周期回调帮助类
     */
    public final ActivityStackManager setActivityLifecycle(ActivityLifecycle activityLifecycle) {
        this.activityLifecycle = activityLifecycle;
        return activityStackManager;
    }

    /**
     * 获取栈顶Activity
     *
     * @return 返回栈顶activity
     */
    public final Activity topActivity() {
        return activityStack.peek();
    }

    /**
     * 清空栈内所有Activity
     */
    public final ActivityStackManager clearStack() {
        for (Activity activity : activityStack) {
            activity.finish();
        }
        activityStack.clear();
        return activityStackManager;
    }

    /**
     * 除去要保活的Activity，其他Activity全部关闭并从栈中移除
     *
     * @param activity 要保活的Activity
     */
    private void aliveLastActivity(Activity activity) {
        for (Activity item : activityStack) {
            if (item != activity) {
                item.finish();
            }
        }
    }

}

package com.benshanyang.toolslibrary.manager;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;

/**
 * @ClassName: ActivityLifecycle
 * @Description: Activity生命周期回调帮助类
 * @Author: YangKuan
 * @CreateDate: 2021/1/30 10:14
 */
public interface ActivityLifecycle {

    void onActivityCreated(@NonNull Activity activity, Bundle savedInstanceState);

    void onActivityStarted(@NonNull Activity activity);

    void onActivityResumed(@NonNull Activity activity);

    void onActivityPaused(@NonNull Activity activity);

    void onActivityStopped(@NonNull Activity activity);

    void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState);

    void onActivityDestroyed(@NonNull Activity activity);

}

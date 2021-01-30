package com.benshanyang.toolslibrary.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;

import java.lang.reflect.Field;

/**
 * 类描述: 设备管理工具 </br>
 * 时间: 2019/3/20 10:51
 *
 * @author YangKuan
 * @version 1.0.0
 * @since
 */
public class DeviceUtils {

    /**
     * 获取状态栏高度
     *
     * @param context 上下文
     * @return 返回状态栏的高度
     */
    public static int getStatusBarHeight(Context context) {
        if (isXiaomi()) {
            int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
            if (resourceId > 0) {
                return context.getResources().getDimensionPixelSize(resourceId);
            }
            return 0;
        }
        try {
            Class<?> c = Class.forName("com.android.internal.R$dimen");
            Object obj = c.newInstance();
            Field field = c.getField("status_bar_height");
            int x = Integer.parseInt(field.get(obj).toString());
            if (x > 0) {
                return context.getResources().getDimensionPixelSize(x);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 设置全屏
     *
     * @param activity
     */
    public static void setFullScreen(Activity activity) {
        if (activity != null) {
            Window window = activity.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
    }

    /**
     * 取消全屏
     *
     * @param activity
     */
    public static void cancelFullScreen(Activity activity) {
        if (activity != null) {
            Window window = activity.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }

    /**
     * 判断是否全屏
     *
     * @param activity
     * @return
     */
    public static boolean isFullScreen(Activity activity) {
        if (activity != null) {
            WindowManager.LayoutParams params = activity.getWindow().getAttributes();
            return (params.flags & WindowManager.LayoutParams.FLAG_FULLSCREEN) == WindowManager.LayoutParams.FLAG_FULLSCREEN;
        }
        return false;
    }

    /**
     * 判断是否为小米
     */
    private static boolean isXiaomi() {
        //https://dev.mi.com/doc/?p=254
        return Build.MANUFACTURER.toLowerCase().equals("xiaomi");
    }

    /**
     * 获得设备屏幕密度
     *
     * @param activity 上下文
     * @return 设备屏幕密度
     */
    public static float getDisplayMetrics(Activity activity) {
        if (activity != null) {
            DisplayMetrics dm = new DisplayMetrics();
            activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
            return dm.density;
        }
        return 0;
    }


    /**
     * 获得屏幕宽度
     *
     * @param context 上下文
     * @return 屏幕宽度
     */
    public static int getScreenWidth(Context context) {
        if (context != null) {
            WindowManager wm = (WindowManager) context
                    .getSystemService(Context.WINDOW_SERVICE);
            DisplayMetrics outMetrics = new DisplayMetrics();
            wm.getDefaultDisplay().getMetrics(outMetrics);
            return outMetrics.widthPixels;
        }
        return 0;
    }

    /**
     * 获得屏幕高度
     *
     * @param context 上下文
     * @return 屏幕高度
     */
    public static int getScreenHeight(Context context) {
        if (context != null) {
            WindowManager wm = (WindowManager) context
                    .getSystemService(Context.WINDOW_SERVICE);
            DisplayMetrics outMetrics = new DisplayMetrics();
            wm.getDefaultDisplay().getMetrics(outMetrics);
            return outMetrics.heightPixels;
        }
        return 0;
    }

}

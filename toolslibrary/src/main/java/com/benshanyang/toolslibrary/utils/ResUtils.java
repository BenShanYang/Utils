package com.benshanyang.toolslibrary.utils;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;

import androidx.annotation.ArrayRes;
import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.ContextCompat;

/**
 * 类描述: 通过资源id获取相关内容 </br>
 * 时间: 2019/3/29 9:36
 *
 * @author YangKuan
 * @version 1.0.0
 * @since
 */
public class ResUtils {

    /**
     * 获取资源文件中的Drawable
     * @param context 上下文
     * @param resId drawable的资源id
     * @return
     */
    public static Drawable getDrawable(@NonNull Context context, @DrawableRes int resId) {
        return AppCompatResources.getDrawable(context, resId);
    }

    /**
     * 获取资源文件中的字符串
     * @param context 上下文
     * @param resId 字符串的资源id
     * @return
     */
    @NonNull
    public static String getString(Context context, @StringRes int resId) {
        return context.getResources().getString(resId);
    }

    /**
     * 获取资源文件中的字符串数组
     * @param context 上下文
     * @param resId 字符串数组的资源id
     * @return
     */
    @NonNull
    public static String[] getStrings(Context context, @ArrayRes int resId) {
        return context.getResources().getStringArray(resId);
    }

    /**
     * 获取资源文件中的颜色
     * @param context 上下文
     * @param color 颜色的资源id
     * @return
     */
    @ColorInt
    public static int getColor(@NonNull Context context, @ColorRes int color){
        return ContextCompat.getColor(context, color);
    }

    /**
     * 获取资源文件中的颜色样式文件
     * @param context 上下文
     * @param resId 颜色样式文件的资源id
     * @return
     */
    @NonNull
    public static ColorStateList getColorStateList(Context context, @ColorRes int resId) {
        return context.getResources().getColorStateList(resId);
    }

}

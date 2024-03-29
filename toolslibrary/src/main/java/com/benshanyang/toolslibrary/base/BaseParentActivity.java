package com.benshanyang.toolslibrary.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.google.android.material.snackbar.Snackbar;

/**
 * @ClassName: BaseParentActivity
 * @Description: Activity基类
 * @Author: YangKuan
 * @Date: 2020/11/12 10:10
 */
public abstract class BaseParentActivity extends FragmentActivity {

    public Activity activity;
    private Toast toast = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = BaseParentActivity.this;
    }

    /**
     * 设置 app 不随着系统字体的调整而变化
     */
    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        if (res.getConfiguration().fontScale != 1) {//非默认值
            Configuration newConfig = new Configuration();
            newConfig.setToDefaults();//设置默认
            res.updateConfiguration(newConfig, res.getDisplayMetrics());
        }
        return res;
    }

    /**
     * 获取资源文件中的颜色
     *
     * @param id 颜色的资源id
     * @return 返回颜色的色值
     */
    @ColorInt
    public int getResColor(@ColorRes int id) {
        try {
            return getResources().getColor(id);
        } catch (Exception e) {
            return Color.TRANSPARENT;
        }
    }

    /**
     * 获取资源文件图片或者drawable
     *
     * @param id 资源文件的id
     * @return 返回的资源文件
     */
    public Drawable getResDrawable(@DrawableRes int id) {
        try {
            return getResources().getDrawable(id);
        } catch (Exception e) {
            return new ColorDrawable(Color.TRANSPARENT);
        }
    }

    /**
     * 获取布局文件
     *
     * @param resource 布局id
     * @return 返回的布局view
     */
    public View getLayout(@LayoutRes int resource) {
        return LayoutInflater.from(activity).inflate(resource, null);
    }

    /**
     * 获取布局文件
     *
     * @param resource 布局id
     * @param root     布局要加入的父布局ViewGroup
     * @return 返回的布局view
     */
    public View getLayout(@LayoutRes int resource, @Nullable ViewGroup root) {
        return LayoutInflater.from(activity).inflate(resource, root);
    }

    /**
     * 获取布局文件
     *
     * @param resource     布局id
     * @param root         布局要加入的父布局ViewGroup
     * @param attachToRoot
     * @return 返回的布局view
     */
    public View getLayout(@LayoutRes int resource, @Nullable ViewGroup root, boolean attachToRoot) {
        return LayoutInflater.from(activity).inflate(resource, root, attachToRoot);
    }

    /**
     * 弹出提示
     *
     * @param message 提示的信息
     */
    public void showToast(CharSequence message) {
        if (message != null) {
            if (toast == null) {
                toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.BOTTOM, 0, dp2px(100));
            }
            toast.setText(message);
            toast.show();
        }
    }

    /**
     * 弹出提示
     *
     * @param message 提示的信息
     */
    public void showLongToast(CharSequence message) {
        if (message != null) {
            if (toast == null) {
                toast = Toast.makeText(this, message, Toast.LENGTH_LONG);
                toast.setGravity(Gravity.BOTTOM, 0, dp2px(100));
            }
            toast.setText(message);
            toast.show();
        }
    }

    /**
     * 弹出提示
     *
     * @param message 提示的信息
     * @param gravity 位于屏幕中的位置。例: Gravity.BOTTOM
     */
    public void showToast(CharSequence message, int gravity) {
        if (message != null) {
            if (toast == null) {
                toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
                toast.setGravity(gravity, 0, 0);
            }
            toast.setText(message);
            toast.show();
        }
    }

    /**
     * 弹出提示
     *
     * @param message 提示的信息
     * @param gravity 位于屏幕中的位置。例: Gravity.BOTTOM
     */
    public void showLongToast(CharSequence message, int gravity) {
        if (message != null) {
            if (toast == null) {
                toast = Toast.makeText(this, message, Toast.LENGTH_LONG);
                toast.setGravity(gravity, 0, 0);
            }
            toast.setText(message);
            toast.show();
        }
    }

    /**
     * 弹出提示
     *
     * @param message 提示的信息
     * @param gravity 位于屏幕中的位置。例: Gravity.BOTTOM
     * @param xOffset x轴上的偏移量
     * @param yOffset y轴上的偏移量
     */
    public void showToast(CharSequence message, int gravity, int xOffset, int yOffset) {
        if (message != null) {
            if (toast == null) {
                toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
                toast.setGravity(gravity, xOffset, yOffset);
            }
            toast.setText(message);
            toast.show();
        }
    }

    /**
     * 弹出提示
     *
     * @param message 提示的信息
     * @param gravity 位于屏幕中的位置。例: Gravity.BOTTOM
     * @param xOffset x轴上的偏移量
     * @param yOffset y轴上的偏移量
     */
    public void showLongToast(CharSequence message, int gravity, int xOffset, int yOffset) {
        if (message != null) {
            if (toast == null) {
                toast = Toast.makeText(this, message, Toast.LENGTH_LONG);
                toast.setGravity(gravity, xOffset, yOffset);
            }
            toast.setText(message);
            toast.show();
        }
    }

    /**
     * 弹出提示
     *
     * @param text 提示的文字
     */
    public void showSnackbar(CharSequence text) {
        Snackbar.make(findViewById(android.R.id.content), text, Snackbar.LENGTH_SHORT).show();
    }

    /**
     * 弹出提示
     *
     * @param text 提示的文字
     */
    public void showLongSnackbar(CharSequence text) {
        Snackbar.make(findViewById(android.R.id.content), text, Snackbar.LENGTH_LONG).show();
    }

    /**
     * 打开指定Activity
     *
     * @param clazz 指定Activity
     */
    public void toActivity(Class<?> clazz) {
        startActivity(new Intent(this, clazz));
    }

    /**
     * 打开指定Activity
     *
     * @param clazz       指定Activity
     * @param requestCode 请求码
     */
    public void toActivity(Class<?> clazz, int requestCode) {
        Intent intent = new Intent(this, clazz);
        intent.putExtra("requestCode", (long) requestCode);
        startActivityForResult(intent, requestCode);
    }

    /**
     * 打开指定Activity
     *
     * @param clazz       指定Activity
     * @param requestCode 请求码
     */
    public void toActivity(Class<?> clazz, long requestCode) {
        Intent intent = new Intent();
        intent.setClass(this, clazz);
        intent.putExtra("requestCode", requestCode);
        startActivity(intent);
    }

    /**
     * 打开指定Activity
     *
     * @param clazz       指定Activity
     * @param bundle      数据bundle
     * @param requestCode 请求码
     */
    public void toActivity(Class<?> clazz, Bundle bundle, long requestCode) {
        Intent intent = new Intent();
        intent.setClass(this, clazz);
        intent.putExtra("requestCode", requestCode);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    /**
     * 打开指定Activity
     *
     * @param clazz  指定Activity
     * @param bundle 携带的数据源
     */
    public void toActivity(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    /**
     * 打开指定Activity
     *
     * @param clazz       指定Activity
     * @param bundle      携带的数据源
     * @param requestCode 请求码
     */
    public void toActivity(Class<?> clazz, Bundle bundle, int requestCode) {
        Intent intent = new Intent(this, clazz);
        intent.putExtra("requestCode", (long) requestCode);
        intent.putExtras(bundle);
        startActivityForResult(intent, requestCode);
    }

    /**
     * 获取携带的数据
     *
     * @return
     */
    public Bundle getBundle() {
        Intent intent = getIntent();
        Bundle bundle = null;
        if (intent != null) {
            bundle = intent.getExtras();
        }
        return bundle;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     *
     * @param dpValue
     * @return
     */
    public int dp2px(float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue
     * @return
     */
    public int sp2px(float spValue) {
        final float fontScale = getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 获取请求码
     *
     * @return
     */
    public long getRequestCode() {
        Intent intent = getIntent();
        if (intent != null) {
            return intent.getLongExtra("requestCode", -1L);
        }
        return -1L;
    }
}

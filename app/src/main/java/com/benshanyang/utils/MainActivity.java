package com.benshanyang.utils;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.benshanyang.toolslibrary.base.BaseParentActivity;
import com.benshanyang.toolslibrary.fingerprint.FingerprintCallback;
import com.benshanyang.toolslibrary.fingerprint.FingerprintHelper;

public class MainActivity extends BaseParentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        FingerprintHelper.Builder builder = new FingerprintHelper.Builder(activity)
                .callback(new FingerprintCallback() {
                    @Override
                    public void onHmUnavailable() {
                        //硬件不支持
                        showToast("硬件不支持");
                    }

                    @Override
                    public void onNoneEnrolled() {
                        //未添加指纹
                        showToast("未添加指纹");
                    }

                    @Override
                    public void fingerprintOk() {
                        //设备支持指纹并且已经录入指纹并且设备也打开了指纹识别
                        showToast("设备支持指纹并且已经录入指纹并且设备也打开了指纹识别");
                    }

                    @Override
                    public void onSuccee() {
                        //指纹识别成功
                        showToast("指纹识别成功");
                    }

                    @Override
                    public void onFailed(int errorCode, CharSequence errString) {
                        //指纹识别失败
                        if (errorCode == FingerprintCallback.DISABLED) {
                            //处于指纹禁用期
                            showToast("处于指纹禁用期");
                        } else if (errorCode == FingerprintCallback.VALIDATION_FAILED) {
                            //多次验证指纹失败被系统禁用指纹一段时间
                            showToast("多次验证指纹失败被系统禁用指纹一段时间");
                        } else if (errorCode == FingerprintCallback.FINGERPRINT_READER_DISABLED) {
                            //尝试次数过多，指纹传感器已停用
                            showToast("尝试次数过多，指纹传感器已停用");
                        } else if (errorCode == FingerprintCallback.FINGERPRINT_CANCEL) {
                            //指纹操作已取消
                            showToast("指纹操作已取消");
                        } else if (errorCode == FingerprintCallback.FINGERPRINT_FAILED) {
                            //没有调用系统的回调函数
                            showToast("没有调用系统的回调函数");
                        }
                    }

                    @Override
                    public void onCancel() {
                        //取消指纹识别
                        showToast("取消指纹识别");
                    }
                });
        builder.build();
    }

    public void onClick(View view) {
        toActivity(SettingsActivity.class);
    }

    // 退出时间
    private long currentBackPressedTime = 0;
    // 退出间隔
    private static final int BACK_PRESSED_INTERVAL = 2000;

    @Override
    public void onBackPressed() {
        // 判断时间间隔
        if (System.currentTimeMillis() - currentBackPressedTime > BACK_PRESSED_INTERVAL) {
            currentBackPressedTime = System.currentTimeMillis();
            Toast.makeText(this, "再按一次返回键退出程序", Toast.LENGTH_SHORT).show();
        } else {
            // 退出
            UtilsApp.manager.clearStack();//清空activity栈
        }
    }

}
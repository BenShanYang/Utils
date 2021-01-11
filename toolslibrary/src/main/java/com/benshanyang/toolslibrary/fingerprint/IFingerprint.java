package com.benshanyang.toolslibrary.fingerprint;

import android.content.Context;

/**
 * @ClassName: IFingerprint
 * @Description: 指纹接口
 * @Author: YangKuan
 * @CreateDate: 2021/1/11 10:48
 */
interface IFingerprint {
    /**
     * 初始化并调起指纹验证
     *
     * @param context
     * @param callback
     */
    void authenticate(Context context, FingerprintCallback callback);

    /**
     * 判断是否支持指纹
     *
     * @param context
     * @param callback
     * @return
     */
    boolean canAuthenticate(Context context, FingerprintCallback callback);
}

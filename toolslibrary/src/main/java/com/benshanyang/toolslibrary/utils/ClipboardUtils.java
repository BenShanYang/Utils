package com.benshanyang.toolslibrary.utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.text.TextUtils;

/**
 * 类描述: 剪切板 </br>
 * 时间: 2019/3/29 15:43
 *
 * @author YangKuan
 * @version 1.0.0
 * @since
 */
public class ClipboardUtils {

    /**
     * 获取剪切板的内容
     *
     * @param context 上下文
     * @return 返回剪切板的内容
     */
    public static String getClipboardContent(Context context) {
        CharSequence content = null;
        ClipData data = null;
        ClipData.Item item = null;

        ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        if (cm != null) {
            data = cm.getPrimaryClip();
        }
        if (data != null) {
            item = data.getItemAt(0);
        }
        if (item != null) {
            content = item.getText();
        }
        return content != null ? content.toString() : "";
    }

    /**
     * 复制文字
     *
     * @param context 上下文
     * @param text    要复制的内容
     * @return true-复制成功 false-复制失败
     */
    public static boolean copy(Context context, CharSequence text) {
        ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData data = ClipData.newPlainText("clipboard", TextUtils.isEmpty(text) ? "" : text);
        if (cm == null || data == null) {
            return false;
        } else {
            cm.setPrimaryClip(data);
            return true;
        }
    }

    /**
     * 复制文字
     *
     * @param context 上下文
     * @param label   将此剪切板的描述展示给用户
     * @param text    要复制的内容
     * @return true-复制成功 false-复制失败
     */
    public static boolean copy(Context context, CharSequence label, CharSequence text) {
        ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData data = ClipData.newPlainText(label, text);
        if (cm == null || data == null) {
            return false;
        } else {
            cm.setPrimaryClip(data);
            return true;
        }
    }

}

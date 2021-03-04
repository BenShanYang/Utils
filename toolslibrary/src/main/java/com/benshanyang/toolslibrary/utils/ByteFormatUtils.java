package com.benshanyang.toolslibrary.utils;

import java.text.DecimalFormat;

/**
 * @ClassName: ByteFormatUtils
 * @Description: 字节格式化工具
 * @Author: YangKuan
 * @Date: 2021/3/3 10:12
 */
public class ByteFormatUtils {
    //定义GB的计算常量
    private static final double GB = 1024 * 1024 * 1024;
    //定义MB的计算常量
    private static final double MB = 1024 * 1024;
    //定义KB的计算常量
    private static final double KB = 1024;

    public static String format(Long bytes) {
        //格式化小数
        DecimalFormat format = new DecimalFormat("###.0");
        if (bytes / GB >= 1) {
            return format.format(bytes / GB) + "GB";
        } else if (bytes / MB >= 1) {
            return format.format(bytes / MB) + "MB";
        } else if (bytes / KB >= 1) {
            return format.format(bytes / KB) + "KB";
        } else {
            return bytes + "B";
        }
    }
}

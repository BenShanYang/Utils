package com.benshanyang.toolslibrary.utils;

import java.io.Closeable;
import java.io.IOException;

/**
 * @ClassName: CloseUtils
 * @Description: 输入输出流的关闭工具类
 * @Author: YangKuan
 * @Date: 2020/12/15 15:51
 */
final class CloseUtils {
    private CloseUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 关闭IO流
     *
     * @param closeables 所要关闭的Closeable
     */
    public static void closeIO(final Closeable... closeables) {
        if (closeables != null) {
            for (Closeable closeable : closeables) {
                try {
                    if (closeable != null) {
                        closeable.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}

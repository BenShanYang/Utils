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
     * Close the io stream.
     *
     * @param closeables closeables
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

    /**
     * Close the io stream quietly.
     *
     * @param closeables closeables
     */
    public static void closeIOQuietly(final Closeable... closeables) {
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

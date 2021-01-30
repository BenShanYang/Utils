package com.benshanyang.toolslibrary.utils;

import java.util.regex.Pattern;

/**
 * 类描述: 判断是否是邮箱 </br>
 * 时间: 2020/11/26 17:44
 *
 * @author YangKuan
 * @version 1.0.0
 * @since
 */
class EmailUtils {

    /**
     * 验证Email
     *
     * @param email email地址，格式：yangkuan@sina.com，yangkuan@xxx.com.cn，xxx代表邮件服务商
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkEmail(CharSequence email) {
        String regex = "\\w+@\\w+\\.[a-z]+(\\.[a-z]+)?";
        return Pattern.matches(regex, email);
    }

}

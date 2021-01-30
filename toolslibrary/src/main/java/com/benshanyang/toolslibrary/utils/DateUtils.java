package com.benshanyang.toolslibrary.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 类描述: 日期格式化工具类 </br>
 * 时间: 2019/3/28 15:37
 *
 * @author YangKuan
 * @version 1.0.0
 * @since
 */
public class DateUtils {

    /**
     * 通过时间戳获取格式化后的时间串
     *
     * @param timeStamp 时间戳(毫秒)
     * @param template  格式化模板。例如: yyyy-MM-dd HH:mm:ss
     * @return 返回格式化后的日期格式
     */
    public static String formatTimeStamp(Long timeStamp, String template) {
        String templateStr = "yyyy-MM-dd HH:mm:ss";
        if (!TextUtils.isEmpty(template)) {
            templateStr = template;
        }
        SimpleDateFormat format = new SimpleDateFormat(templateStr);
        Date date = new Date(timeStamp);
        return format.format(date);
    }

    /**
     * 将时间的字符串转换成时毫秒时间戳
     *
     * @param strDate  时间的字符串
     * @param template 格式化模板,需要和时间字符串的格式匹配。例如: yyyy-MM-dd HH:mm:ss
     * @return 返回毫秒级的时间戳
     */
    public static long stringDateToTimeStamp(String strDate, String template) {
        DateFormat formatter = new SimpleDateFormat(template != null ? template : "");
        Date date = null;
        try {
            date = (Date) formatter.parse(strDate != null ? strDate : "");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date != null ? date.getTime() : 0;
    }
}

package com.benshanyang.toolslibrary.utils;

import androidx.annotation.NonNull;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
        if (timeStamp != null) {
            String templateStr = "yyyy-MM-dd HH:mm:ss";
            if (!TextUtils.isEmpty(template)) {
                templateStr = template;
            }
            SimpleDateFormat format = new SimpleDateFormat(templateStr);
            Date date = new Date(timeStamp);
            return format.format(date);
        } else {
            return "";
        }
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

    /**
     * 通过Calendar获取格式化后的时间串
     *
     * @param calendar Calendar时间
     * @param template 格式化模板。例如: yyyy-MM-dd HH:mm:ss
     * @return 返回格式化后的日期格式
     */
    public static String formatTimeCalendar(@NonNull Calendar calendar, String template) {
        if (calendar != null) {
            String templateStr = "yyyy-MM-dd HH:mm:ss";
            if (!TextUtils.isEmpty(template)) {
                templateStr = template;
            }
            SimpleDateFormat format = new SimpleDateFormat(templateStr);
            Date date = calendar.getTime();
            return format.format(date);
        } else {
            return "";
        }
    }

    /**
     * 通过Date获取格式化后的时间串
     *
     * @param date     Date时间
     * @param template 格式化模板。例如: yyyy-MM-dd HH:mm:ss
     * @return 返回格式化后的日期格式
     */
    public static String formatTimeDate(@NonNull Date date, String template) {
        if (date != null) {
            String templateStr = "yyyy-MM-dd HH:mm:ss";
            if (!TextUtils.isEmpty(template)) {
                templateStr = template;
            }
            SimpleDateFormat format = new SimpleDateFormat(templateStr);
            return format.format(date);
        } else {
            return "";
        }
    }

    /**
     * 将字符串时间转换为Calendar
     *
     * @param strDate  时间字符串
     * @param template 时间模板。例如: yyyy-MM-dd HH:mm:ss
     * @return 返回转换后的Calendar
     */
    public static Calendar stringDateToCalendar(String strDate, String template) {
        try {
            Calendar calendar = Calendar.getInstance();
            DateFormat formatter = new SimpleDateFormat(template != null ? template : "");
            Date date = formatter.parse(strDate != null ? strDate : "");
            long timeStamp = date.getTime();
            calendar.setTimeInMillis(timeStamp);
            return calendar;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将字符串时间转换为Date
     *
     * @param strDate  时间字符串
     * @param template 时间模板。例如: yyyy-MM-dd HH:mm:ss
     * @return 返回转换后的Date
     */
    public static Date stringDateToDate(String strDate, String template) {
        DateFormat formatter = new SimpleDateFormat(template != null ? template : "");
        Date date = null;
        try {
            date = (Date) formatter.parse(strDate != null ? strDate : "");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}

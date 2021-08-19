package com.benshanyang.toolslibrary.utils;

import androidx.annotation.NonNull;

import com.benshanyang.toolslibrary.annotation.Week;
import com.benshanyang.toolslibrary.annotation.WeekType;

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

    /**
     * 获取星期几
     *
     * @return
     */
    @Week
    public static int getWeek() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 获取星期几
     *
     * @param calendar 日历
     * @return
     */
    @Week
    public static int getWeek(Calendar calendar) {
        int dayOfWeek = 2;
        if (calendar != null) {
            dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        } else {
            dayOfWeek = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        }
        return dayOfWeek;
    }

    /**
     * 获取星期几
     *
     * @param date
     * @return
     */
    @Week
    public static int getWeek(Date date) {
        if (date != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            return calendar.get(Calendar.DAY_OF_WEEK);
        } else {
            return Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        }

    }

    /**
     * 获取星期几
     *
     * @param timeStamp 时间戳
     * @return
     */
    @Week
    public static int getWeek(String timeStamp) {
        try {
            return getWeek(Long.parseLong(timeStamp));
        } catch (Exception e) {
            return Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        }
    }

    /**
     * 获取星期几
     *
     * @param timeStamp 时间戳
     * @return
     */
    @Week
    public static int getWeek(long timeStamp) {
        Calendar calendar = Calendar.getInstance();
        Date date = new Date(timeStamp);
        calendar.setTime(date);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        return dayOfWeek;
    }

    /**
     * 星期几字符串格式化
     *
     * @param week 星期几
     * @param type 要格式化的类型  WeekType.CN-中文全称、WeekType.CN_SIMPLE-中文简称、WeekType.EN-英文全称、WeekType.EN_SIMPLE-英文简称
     * @return
     */
    public static String formatWeek(@Week int week, @WeekType int type) {
        String weekStr = "星期一";
        switch (week) {
            case Week.SUNDAY:
                //周日
                if (type == WeekType.CN) {
                    weekStr = "星期日";
                } else if (type == WeekType.CN_SIMPLE) {
                    weekStr = "周日";
                } else if (type == WeekType.EN) {
                    weekStr = "Sunday";
                } else if (type == WeekType.EN_SIMPLE) {
                    weekStr = "Sun.";
                }
                break;
            case Week.MONDAY:
                //周一
                if (type == WeekType.CN) {
                    weekStr = "星期一";
                } else if (type == WeekType.CN_SIMPLE) {
                    weekStr = "周一";
                } else if (type == WeekType.EN) {
                    weekStr = "Monday";
                } else if (type == WeekType.EN_SIMPLE) {
                    weekStr = "Mon.";
                }
                break;
            case Week.TUESDAY:
                //周二
                if (type == WeekType.CN) {
                    weekStr = "星期二";
                } else if (type == WeekType.CN_SIMPLE) {
                    weekStr = "周二";
                } else if (type == WeekType.EN) {
                    weekStr = "Tuesda";
                } else if (type == WeekType.EN_SIMPLE) {
                    weekStr = "Tues.";
                }
                break;
            case Week.WEDNESDAY:
                //周三
                if (type == WeekType.CN) {
                    weekStr = "星期三";
                } else if (type == WeekType.CN_SIMPLE) {
                    weekStr = "周三";
                } else if (type == WeekType.EN) {
                    weekStr = "Wednesday";
                } else if (type == WeekType.EN_SIMPLE) {
                    weekStr = "Wed.";
                }
                break;
            case Week.THURSDAY:
                //周四
                if (type == WeekType.CN) {
                    weekStr = "星期四";
                } else if (type == WeekType.CN_SIMPLE) {
                    weekStr = "周四";
                } else if (type == WeekType.EN) {
                    weekStr = "Thursday";
                } else if (type == WeekType.EN_SIMPLE) {
                    weekStr = "Thur.";
                }
                break;
            case Week.FRIDAY:
                //周五
                if (type == WeekType.CN) {
                    weekStr = "星期五";
                } else if (type == WeekType.CN_SIMPLE) {
                    weekStr = "周五";
                } else if (type == WeekType.EN) {
                    weekStr = "Friday";
                } else if (type == WeekType.EN_SIMPLE) {
                    weekStr = "Fri.";
                }
                break;
            case Week.SATURDAY:
                //周六
                if (type == WeekType.CN) {
                    weekStr = "星期六";
                } else if (type == WeekType.CN_SIMPLE) {
                    weekStr = "周六";
                } else if (type == WeekType.EN) {
                    weekStr = "Saturday";
                } else if (type == WeekType.EN_SIMPLE) {
                    weekStr = "Sat.";
                }
                break;
        }
        return weekStr;
    }

}

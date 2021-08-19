package com.benshanyang.toolslibrary.annotation;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@IntDef({Week.MONDAY, Week.TUESDAY, Week.WEDNESDAY, Week.THURSDAY, Week.FRIDAY, Week.SATURDAY, Week.SUNDAY})
@Retention(RetentionPolicy.SOURCE)
public @interface Week {

    /**
     * 星期日  Sun.
     */
    int SUNDAY = 1;

    /**
     * 星期一  Mon.
     */
    int MONDAY = 2;

    /**
     * 星期二  Tues.
     */
    int TUESDAY = 3;

    /**
     * 星期三  Wed.
     */
    int WEDNESDAY = 4;

    /**
     * 星期四  Thur./Thurs.
     */
    int THURSDAY = 5;

    /**
     * 星期五  Fri.
     */
    int FRIDAY = 6;

    /**
     * 星期六  Sat.
     */
    int SATURDAY = 7;


}

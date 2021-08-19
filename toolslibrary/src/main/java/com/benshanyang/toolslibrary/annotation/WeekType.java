package com.benshanyang.toolslibrary.annotation;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@IntDef({WeekType.CN, WeekType.CN_SIMPLE, WeekType.EN, WeekType.EN_SIMPLE})
@Retention(RetentionPolicy.SOURCE)
public @interface WeekType {

    /**
     * 中式 星期几
     */
    int CN = 0;
    /**
     * 中式简化 周几
     */
    int CN_SIMPLE = 1;

    /**
     * 英式
     */
    int EN = 2;

    /**
     * 英式简化
     */
    int EN_SIMPLE = 3;
}

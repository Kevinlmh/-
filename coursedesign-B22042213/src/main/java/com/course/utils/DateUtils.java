package com.course.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
    // 判断是否同一天
    public static boolean isSameDay(Date d1, Date d2) {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
        return fmt.format(d1).equals(fmt.format(d2));
    }

    // 判断是否同一年
    public static boolean isSameYear(Date d1, Date d2) {
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(d1);
        c2.setTime(d2);
        return c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR);
    }

    // 判断d2是否在d1之后N个月内
    public static boolean isWithinMonths(Date d1, Date d2, int months) {
        Calendar c1 = Calendar.getInstance();
        c1.setTime(d1);
        c1.add(Calendar.MONTH, months);
        return d2.before(c1.getTime());
    }

    // 判断是否超过一年
    public static boolean isOverOneYear(Date start, Date now) {
        Calendar c = Calendar.getInstance();
        c.setTime(start);
        c.add(Calendar.YEAR, 1);
        return now.after(c.getTime());
    }
}
package com.zf.library;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * author:zhufu
 * email:zhufui@sina.com
 * time:2018/12/12
 * desc:
 * version:1.0
 */
public class DateUtil {
    public static final String yyyy_MM_dd = "yyyy-MM-dd";
    public static final String yyyy_MM_dd_HH_mm_ss = "yyyy-MM-dd HH:mm:ss";
    public static final String HH_mm_ss = "HH:mm:ss";
    public static final String HH_mm = "HH:mm";
    public static final String MM_dd = "MM-dd";

    /**
     * 时间格式化
     *
     * @param format 格式化规则
     * @param mills  时间戳
     * @return
     */
    public static String dateFormat(String format, long mills) {
        Date date = new Date(mills);
        DateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }

}

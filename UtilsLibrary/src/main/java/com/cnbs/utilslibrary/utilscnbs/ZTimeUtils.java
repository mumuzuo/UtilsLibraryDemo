package com.cnbs.utilslibrary.utilscnbs;

import java.text.SimpleDateFormat;

/**
 * 时间相关的工具类
 * Created by Administrator on 2017/3/8.
 */

public class ZTimeUtils {

    /**
     * 获取系统的当前时间以yyyy-MM-dd HH:mm:ss格式返回
     * @return
     */
    public static String getTimeYMDHMS() {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = sf.format(System.currentTimeMillis());
        return time;
    }

    /**
     * 获取系统的当前日期以yyyy-MM-dd格式返回
     * @return
     */
    public static String getTodayYMD() {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        String time = sf.format(System.currentTimeMillis());
        return time;
    }

    /**
     * 将已有long型时间以yyyy-MM-dd HH:mmss格式返回
     * @return
     */
    public static String long2TimeYMDHMS(Long time) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String mTime = sf.format(time);
        return mTime;
    }

    /**
     * 将已有long型时间以yyyy-MM-dd格式返回
     * @return
     */
    public static String long2DateYMD(Long time) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        String mTime = sf.format(time);
        return mTime;
    }

    /**
     * 将已有long型时间以mm:ss格式返回
     * @return
     */
    public static String long2TimeMS(Long time) {
        SimpleDateFormat sf = new SimpleDateFormat("mm:ss");
        String mTime = sf.format(time);
        return mTime;
    }

    /**
     * 把HH:mm:ss格式的字符串转化为long型时间，秒为单位
     * @param timeStr
     * @return
     */
    public static long time2Long(String timeStr) {
        String[] time = timeStr.split(":");
        long hh = Long.valueOf(time[0]);
        long mm = Long.valueOf(time[1]);
        long ss = 0;
        if (time.length>2){
            ss = Long.valueOf(time[2]);
        }else {
            ss = Long.valueOf("00");
        }
        if (hh != 0) {
            ss += Long.valueOf(time[0]) * 3600;
        }
        if (mm != 0) {
            ss += Long.valueOf(time[1]) * 60;
        }
        return ss;
    }

    /**
     * 把yyyy-MM-dd格式的字符串转化为long型时间，天为单位
     * @param dateStr
     * @return
     */
    public static long day2Long(String dateStr) {
        String[] time = dateStr.split("-");
        long yy = Long.valueOf(time[0]);
        long mm = Long.valueOf(time[1]);
        long dd = Long.valueOf(time[2]);
        if (yy != 0) {
            dd += Long.valueOf(time[0]) * 365;
        }
        if (mm != 0) {
            dd += Long.valueOf(time[1]) * 30;
        }
        return dd;
    }


    /**
     * 把yyyy-MM-dd格式的字符串转化为long型时间，秒为单位
     * @param dateTimeStr
     * @return
     */
    public static long dayTime2Long(String dateTimeStr) {
        String[] dateStr = dateTimeStr.split(" ");
        String date = dateStr[0];
        String time = dateStr[1];
        String[] dateS = date.split("-");
        long yy = Long.valueOf(dateS[0]);
        long MM = Long.valueOf(dateS[1]);
        long dd = Long.valueOf(dateS[2]);
        if (yy != 0) {
            dd += Long.valueOf(dateS[0]) * 365;
        }
        if (MM != 0) {
            dd += Long.valueOf(dateS[1]) * 30;
        }
        String[] timeS = time.split(":");
        long hh = Long.valueOf(timeS[0]);
        long mm = Long.valueOf(timeS[1]);
        long ss = 0;
        if (timeS.length>2){
            ss = Long.valueOf(timeS[2]);
        }else {
            ss = Long.valueOf("00");
        }
        if (hh != 0) {
            ss += Long.valueOf(timeS[0]) * 3600;
        }
        if (mm != 0) {
            ss += Long.valueOf(timeS[1]) * 60;
        }
        ss+=dd*24*3600;
        return ss;
    }

}

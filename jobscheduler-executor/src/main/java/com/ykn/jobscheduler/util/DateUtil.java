package com.ykn.jobscheduler.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * @author xiangtaohe
 * @version 1.0
 * @className DateUtil
 * @date 2020/5/12 11:42
 **/
public class DateUtil {
    public static final DateTimeFormatter YMD_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static final DateTimeFormatter STANDARD_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    public static LocalDateTime toLocalDateTime(Date date) {
        Instant instant = date.toInstant();
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());

    }


    /**
     * 日期格式转换成时间毫秒数,时间部分补00:00:00
     *
     * @Author xiangtaohe
     * @Date 2020/4/24 11:33
     * @Version 1.0
     * @Param date
     * @Return long
     **/
    public static long toMills(String date) {
        return LocalDate.parse(date, YMD_FORMATTER).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    /**
     * @Author xiangtaohe
     * @Date 2020/4/27 10:38
     * @Version 1.0
     * @Param date
     * @Return long
     **/
    public static long toMills(LocalDate date) {
        return date.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    /**
     * @Author xiangtaohe
     * @Date 2020/4/27 10:38
     * @Version 1.0
     * @Param date
     * @Return java.time.LocalDate
     **/
    public static LocalDate toLocalDate(String date) {

        return LocalDate.parse(date, YMD_FORMATTER);
    }

    public static LocalDateTime toLocalDateTime(String date) {
        return LocalDateTime.parse(date, STANDARD_DATE_TIME_FORMATTER);
    }

    /**
     * format date
     *
     * @Author xiangtaohe
     * @Date 2020/4/29 16:27
     * @Version 1.0
     * @Param date
     * @Return java.lang.String
     **/
    public static String formatDate(LocalDate date) {
        return date.format(YMD_FORMATTER);
    }

    /**
     * datetime format
     *
     * @Author xiangtaohe
     * @Date 2020/4/28 11:04
     * @Version 1.0
     * @Param dateTime
     * @Return java.lang.String (yyyy-MM-dd HH:mm:ss)
     **/
    public static String format(LocalDateTime dateTime) {
        return dateTime.format(STANDARD_DATE_TIME_FORMATTER);
    }

    /**
     * datetime format
     *
     * @Author xiangtaohe
     * @Date 2020/4/29 11:52
     * @Version 1.0
     * @Param dateTime
     * @Param formatter
     * @Return java.lang.String
     **/
    public static String format(LocalDateTime dateTime, DateTimeFormatter formatter) {
        return dateTime.format(formatter);
    }


    /**
     * datetime format
     *
     * @Author xiangtaohe
     * @Date 2020/4/28 11:06
     * @Version 1.0
     * @Param dateTime
     * @Return java.lang.String
     **/
    public static String format(Long dateTime) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(dateTime), ZoneId.systemDefault());
        return localDateTime.format(STANDARD_DATE_TIME_FORMATTER);
    }

    /**
     * date time format
     *
     * @Author xiangtaohe
     * @Date 2020/4/29 11:53
     * @Version 1.0
     * @Param dateTime
     * @Param formatter
     * @Return java.lang.String
     **/
    public static String format(Long dateTime, DateTimeFormatter formatter) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(dateTime), ZoneId.systemDefault());
        return localDateTime.format(formatter);
    }


    /**
     * 计算指定日期与当前日期与{@code endDate}的间隔天数
     *
     * @param startDate
     * @return 间隔天数  指定日期在当前日期之前，返回的是正数,之后返回的是负数
     */
    public static long until(LocalDate startDate) {
        return startDate.until(LocalDate.now(), ChronoUnit.DAYS);
    }


    /**
     * 计算日期{@code startDate}与{@code endDate}的间隔天数
     *
     * @Author xiangtaohe
     * @Date 2020/4/28 20:40
     * @Version 1.0
     * @Param startDate
     * @Param endDate
     * @Return long   开始日期大于结果日期，返回正值，否则负值
     **/
    public static long until(LocalDate startDate, LocalDate endDate) {
        return startDate.until(endDate, ChronoUnit.DAYS);
    }
}

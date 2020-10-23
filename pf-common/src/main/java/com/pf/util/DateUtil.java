package com.pf.util;

import com.pf.enums.DateFormatTypeEnum;
import com.pf.enums.QuarterNumEnum;
import lombok.extern.slf4j.Slf4j;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;


@Slf4j
public class DateUtil {
    /**
     * 功能描述：获取两个日期相隔天数
     * @param beginDate
     * @param endDate
     * @return long
     * @author zhuqingsen
     * @date 2019年2月23日 上午11:49:35
     * @修改日志：
     */
    public static long getBetweenDays(Date beginDate,Date endDate) {
        LocalDate startDate_ = DateToLocalDate(beginDate);
        LocalDate endDate_ = DateToLocalDate(endDate);
        return endDate_.toEpochDay()-startDate_.toEpochDay();
    }
    

    /**
     * 功能描述：获取本月实际天数
     * @param date
     * @return int
     * @author zhuqingsen
     * @date 2019年2月23日 上午11:48:19
     * @修改日志：
     */
    public static int getDayOfMonth(Date date) {
        return getLastDateOfMonthForLd(date).getDayOfMonth();
    }
    

    /**
     * 功能描述：获取日期对应月份最后一天的日期
     * @param date
     * @return Date
     * @author zhuqingsen
     * @date 2019年2月23日 上午11:47:36
     * @修改日志：
     */
    public static Date getLastDateOfMonth(Date date) {
        return LocalDateToDate(getLastDateOfMonthForLd(date));
    }
    

    /**
     * 功能描述：获取日期对应月份最后一天的日期
     * @param date
     * @return LocalDate
     * @author zhuqingsen
     * @date 2019年2月23日 上午11:40:22
     * @修改日志：
     */
    public static LocalDate getLastDateOfMonthForLd(Date date) {
        LocalDate date_ = DateToLocalDate(date);
        return date_.with(TemporalAdjusters.lastDayOfMonth());
    }
    

    /**
     * 功能描述：获取当前日期为几号
     * @param date
     * @return int
     * @author zhuqingsen
     * @date 2019年2月23日 上午11:39:16
     * @修改日志：
     */
    public static int getDayByDate(Date date) {
        LocalDate date_ = DateToLocalDate(date);
        return date_.getDayOfMonth();
    }
    

    
    /**
     * 功能描述：计算在给定的日期加上或减去 n 天后的日期
     * @param date
     * @param days
     * @return String
     * @author zhuqingsen
     * @date 2019年2月23日 上午11:38:16
     * @修改日志：
     */
    public static Date addByDays(Date date,int days) {
        LocalDate date_ = DateToLocalDate(date);
        return LocalDateToDate(date_.plusDays(days));
    }
    
    

    /**
     * 功能描述：计算在给定的日期加上或减去 n 周后的日期
     * @param date
     * @param weeks
     * @return Date
     * @author zhuqingsen
     * @date 2019年2月23日 上午11:37:47
     * @修改日志：
     */
    public static Date addByWeeks(Date date,int weeks) {
        LocalDate date_ = DateToLocalDate(date);
        return LocalDateToDate(date_.plusWeeks(weeks));
    }
    

    /**
     * 功能描述：计算在给定的日期加上或减去 n 月后的日期
     * @param date
     * @param months
     * @return Date
     * @author zhuqingsen
     * @date 2019年2月23日 上午11:37:10
     * @修改日志：
     */
    public static Date addByMonths(Date date,int months) {
        LocalDate date_ = DateToLocalDate(date);
        return LocalDateToDate(date_.plusMonths(months));
    }
    

    /**
     * 功能描述：计算在给定的日期加上或减去 n 年后的日期
     * @param date
     * @param years
     * @return Date
     * @author zhuqingsen
     * @date 2019年2月23日 上午11:34:30
     * @修改日志：
     */
    public static Date addByYears(Date date,int years) {
        LocalDate date_ = DateToLocalDate(date);
        return LocalDateToDate(date_.plusYears(years));
    }
    

    /**
     * 功能描述：字符串按照默认格式转LocalDate
     * @param date
     * @return LocalDate
     * @author zhuqingsen
     * @date 2019年2月23日 上午11:33:32
     * @修改日志：
     */
    public static LocalDate getLocalDateFormat(String date) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DateFormatTypeEnum.LONG_DATE.getMessage());
        return LocalDate.parse(date, dtf);
    }
    
    
    /**
     * 功能描述：字符串按照指定格式转LocalDate
     * @param date
     * @param format
     * @return LocalDate
     * @author zhuqingsen
     * @date 2019年2月23日 上午11:32:15
     * @修改日志：
     */
    public static LocalDate getLocalDateFormat(String date,String format) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(format);
        return LocalDate.parse(date, dtf);
    }
    
    

    /**
     * 功能描述：Date转LocalDateTime
     * @param date
     * @return LocalDateTime
     * @author zhuqingsen
     * @date 2019年2月23日 上午11:31:03
     * @修改日志：
     */
    public static LocalDateTime DateToLocalDateTime(Date date) {
        Instant instant = date.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
        return localDateTime;
    }

    

    /**
     * 功能描述：Date转LocalDate
     * @param date
     * @return LocalDate
     * @author zhuqingsen
     * @date 2019年2月23日 上午11:30:31
     * @修改日志：
     */
    public static LocalDate DateToLocalDate(Date date) {
        Instant instant = date.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
        LocalDate localDate = localDateTime.toLocalDate();
        return localDate;
    }
    

    /**
     * 功能描述：Date转LocalTime
     * @param date
     * @return LocalTime
     * @author zhuqingsen
     * @date 2019年2月23日 上午11:30:07
     * @修改日志：
     */
    public static LocalTime DateToLocalTime(Date date) {
        Instant instant = date.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
        LocalTime localTime = localDateTime.toLocalTime();
        return localTime;
    }
    
    
    /**
     * 功能描述：LocalDateTime转Date
     * @param localDateTime
     * @return Date
     * @author zhuqingsen
     * @date 2019年2月23日 上午11:29:36
     * @修改日志：
     */
    public static Date LocalDateTimeToDate(LocalDateTime localDateTime) {
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDateTime.atZone(zone).toInstant();
        Date date = Date.from(instant);
        return date;
    }
    
    
    /**
     * 功能描述：LocatDate转Date
     * @param localDate
     * @return Date
     * @author zhuqingsen
     * @date 2019年2月23日 上午11:29:14
     * @修改日志：
     */
    public static Date LocalDateToDate(LocalDate localDate) {
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDate.atStartOfDay().atZone(zone).toInstant();
        Date date = Date.from(instant);
        return date;
    }
    
    public static String DateFormat(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(DateFormatTypeEnum.LONG_DATE.getMessage());
        String str = sdf.format(date);
        return str;
    }
    
    /**
     * 
     * @Description 获取一天最大时间 20201111 23:59:59
     * @param date
     * @return Date
     * @author haozuguo
     * @date 2020年3月5日 下午8:32:30
     * @ModifyLogs
     */
    public static Date LocalDateMaxDate(Date date) {
        LocalDate localDate = DateToLocalDate(date);
        LocalDateTime dateTime = LocalDateTime.of(localDate, LocalTime.MAX);
        Date endDate = LocalDateTimeToDate(dateTime);
        return endDate; 
    }
    
    /**
     * 
     * @Description 获取一天最小时间 20201111 00:00:00
     * @param date
     * @return Date
     * @author haozuguo
     * @date 2020年3月5日 下午8:32:30
     * @ModifyLogs
     */
    public static Date LocalDateMinDate(Date date) {
        LocalDate localDate = DateToLocalDate(date);
        LocalDateTime dateTime = LocalDateTime.of(localDate, LocalTime.MIN);
        Date endDate = LocalDateTimeToDate(dateTime);
        return endDate; 
    }

    /**
     * 功能描述：LocalDate转默认格式字符串
     * @param date
     * @return String
     * @author zhuqingsen
     * @date 2019年2月23日 上午11:26:57
     * @修改日志：
     */
    public static String LocalDateToString(LocalDate date) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DateFormatTypeEnum.LONG_DATE.getMessage());
        return dtf.format(date);
    }
    
    
    /**
     * 功能描述：LocalDate转指定格式字符串
     * @param date
     * @param format
     * @return String
     * @author zhuqingsen
     * @date 2019年2月23日 上午11:28:30
     * @修改日志：
     */
    public static String LocalDateToString(LocalDate date,String format) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(format);
        return dtf.format(date);
    }
    
    /**
         * 功能描述：字符串转LocalDate
     * @param date
     * @param format
     * @return LocalDate
     * @author zhuqingsen
     * @date 2019年7月10日 下午3:53:36
     * @修改日志：
     */
    public static LocalDate StringToLocalDate(String date,String format) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(format);
        LocalDate parse = LocalDate.parse(date, dtf);
        return parse;
    }
    
    /**
         * 功能描述：字符串转Date
     * @param date
     * @param format
     * @return Date
     * @author zhuqingsen
     * @date 2019年7月10日 下午3:55:54
     * @修改日志：
     */
    public static Date StringToDate(String date,String format) {
        Date parse = LocalDateToDate(StringToLocalDate(date,format));
        return parse;
    }

    /**
     * 功能描述：根据日期与指定天数组合成新的日期
     * @param date
     * @param day
     * @return String
     * @author zhuqingsen
     * @date 2019年2月23日 上午11:19:02
     * @修改日志：
     */
    public static Date getDateByDay(Date date,int day) {
        LocalDate date_ = DateToLocalDate(date);
        return LocalDateToDate(date_.withDayOfMonth(day));
    }
    

    /**
     * 功能描述：根据日期与指定月份组合成新的日期
     * @param date
     * @param month
     * @return Date
     * @author zhuqingsen
     * @date 2019年2月23日 上午11:24:56
     * @修改日志：
     */
    public static Date getDateByMonth(Date date,int month) {
        LocalDate date_ = DateToLocalDate(date);
        return LocalDateToDate(date_.withMonth(month));
    }
    
    

    /**
     * 功能描述：获取日期的月份
     * @param date
     * @return int
     * @author zhuqingsen
     * @date 2019年2月23日 上午11:24:11
     * @修改日志：
     */
    public static int getMonthByDate(Date date) {
        return DateToLocalDate(date).getMonthValue();
    }
    
    /**
     * 功能描述：获取日期的年份
     * @param date
     * @return int
     * @author zhuqingsen
     * @date 2019年2月23日 上午11:24:11
     * @修改日志：
     */
    public static int getYearByDate(Date date) {
        return DateToLocalDate(date).getYear();
    }



    /**
     * 功能描述：两个日期比较(年月日比较)
     * @param date1
     * @param date2
     * @return int
     * @author zhuqingsen
     * @date 2019年2月23日 上午11:23:51
     * @修改日志：
     */
    public static int compareDate(Date date1, Date date2) {
        LocalDate date1_ = DateToLocalDate(date1);
        LocalDate date2_ = DateToLocalDate(date2);
        return date1_.compareTo(date2_);
    }
    

    /**
     * 功能描述：获取日期所在季度
     * @param date
     * @return int
     * @author zhuqingsen
     * @date 2019年2月23日 上午11:23:28
     * @修改日志：
     */
     public static int getQuarterNum(Date date) {
          LocalDate date_ = DateToLocalDate(date);
          if(date_.isBefore(date_.with(Month.APRIL).withDayOfMonth(1))) {
              return QuarterNumEnum.QUARTER_FIRST.getCode();
          } else if(date_.isBefore(date_.with(Month.JULY).withDayOfMonth(1))) {
              return QuarterNumEnum.QUARTER_SECOND.getCode();
          } else if(date_.isBefore(date_.with(Month.NOVEMBER).withDayOfMonth(1))) {
              return QuarterNumEnum.QUARTER_THIRD.getCode();
          } else {
             return QuarterNumEnum.QUARTER_FOURTH.getCode();
          }
      }
     

     /**
      * 功能描述：获取指定日期所在年份的总天数
      * @param date
      * @return int
      * @author zhuqingsen
      * @date 2019年2月23日 上午11:21:27
      * @修改日志：
      */
     public static int getDaysOfYear(Date date) {
         return DateToLocalDate(date).lengthOfYear();
     }
     
     /**
      * 功能描述：计算两个日期相差月份
      * @param minDate
      * @param maxDate
      * @return int
      * @author yyc
      * @date 2020年5月28日 上午11:29:10
      * @修改日志：
      */
     public static int getBetweenMonths(Date minDate,Date maxDate) {
         return getMonthByDate(maxDate) - getMonthByDate(minDate);
     }
     
     /**
      * 功能描述：计算两个日期相差年数
      * @param minDate
      * @param maxDate
      * @return int
      * @author zhuqingsen
      * @date 2019年3月2日 上午11:29:10
      * @修改日志：
      */
     public static int getBetweenYears(Date minDate,Date maxDate) {
         return getYearByDate(maxDate) - getYearByDate(minDate);
     }
     
     /**
      * 功能描述：判断两个日期是否为不同年份
      * @param date1
      * @param date2
      * @return boolean
      * @author zhuqingsen
      * @date 2019年3月2日 上午11:29:25
      * @修改日志：
      */
     public static boolean isDiffentYear(Date date1,Date date2) {
         return getYearByDate(date1) - getYearByDate(date2) != 0;
     }
    
    /**
     * 字符转日期
     * 
     * @param dateStr
     * @return
     */
    public static Date getDateByStr(String dateStr) {
        SimpleDateFormat formatter = null;
        if (dateStr == null) {
            return null;
        } else if (dateStr.length() == 10) {
            formatter = new SimpleDateFormat(DateFormatTypeEnum.LONG_DATE.getMessage());
        } else if (dateStr.length() == 16) {
            formatter = new SimpleDateFormat(DateFormatTypeEnum.SHORT_DATETIME.getMessage());
        } else if (dateStr.length() == 19) {
            formatter = new SimpleDateFormat(DateFormatTypeEnum.LONG_DATETIME.getMessage());
        } else if (dateStr.length() > 19) {
            dateStr = dateStr.substring(0, 19);
            formatter = new SimpleDateFormat(DateFormatTypeEnum.LONG_DATETIME.getMessage());
        } else {
            return null;
        }
        try {
            return formatter.parse(dateStr);
        } catch (ParseException e) {
            log.error(e.getMessage());
            return null;
        }
    }

	/**
	 * 按照时间格式获取当前时间
	 * yyyyMMdd yyyy-MM-dd yyyy-MM-dd HH:mm:ss yyyy-MM-dd HH:mm:ss
	 * 
	 * @return
	 */
	public static String getCurrentDate(String format) {
		SimpleDateFormat formatTimestamp = new SimpleDateFormat(format);
		return formatTimestamp.format(new Date());
	}

	/**
	 * 按照指定时间指定的时间格式获取时间
	 * yyyyMMdd yyyy-MM-dd yyyy-MM-dd HH:mm:ss yyyy-MM-dd HH:mm:ss
	 * 
	 * @return
	 */
	public static String getReqTime(Date date, String format) {
		SimpleDateFormat formatTimestamp = new SimpleDateFormat(format);
		return formatTimestamp.format(date);
	}
	
	/**
	 * @Description 获取指定临近的日期
	 * @param date
	 * @param next
	 * @return Date
	 * @author haozuguo
	 * @date 2019年10月10日 下午6:39:08
	 * @ModifyLogs
	 */
	public static Date getNextDate(Date date, int next) {
	    
	    Instant instant = date.toInstant();
	    ZoneId zone = ZoneId.systemDefault();
	    LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
	    LocalDate localDate = localDateTime.toLocalDate();
	    localDate = localDate.minusDays(next);
	    instant = localDate.atStartOfDay().atZone(zone).toInstant();
	    return Date.from(instant);
	}
	
//	public static void main(String[] args) {
//	    LocalDate localDate = LocalDate.now();
//	    System.out.println(localDate);
//	    localDate = localDate.minusDays(-1);
//	    System.out.println(localDate);
//	    localDate.get
//    }
}

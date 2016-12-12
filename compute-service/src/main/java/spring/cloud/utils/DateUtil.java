package spring.cloud.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

public class DateUtil {

   public static SimpleDateFormat DATE_FORMAT_TO_SECOND=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
   public static SimpleDateFormat DATE_FORMAT_TO_DATE=new SimpleDateFormat("yyyy-MM-dd");
   public static SimpleDateFormat DATE_FORMAT_TO_HOUR_MIN_ONLY=new SimpleDateFormat("HH:mm");
   
   public static final SimpleDateFormat FORMATINSTANCE_DEFAULTDATE = new SimpleDateFormat("yyyyMMdd");
   
   /**
	 * 将日期对象格式化成yyyy-MM-dd的形式
	 * @param dt 日期对象
	 * @return String
	 * @since  
	 */
	public static String formatDate(Date dt) {
		if (dt == null)
			return "";
		return FORMATINSTANCE_DEFAULTDATE.format(dt);
	}
   
   /**
    * Function:在当前的日期点上加入指定的天数
    * @param days 天数，正数为向后；负数为向前
    * @return 返回改变后的时间
    * Create author:2011-12-20
    * Create on:2011-12-20
    * Edit author:
    * Edit on:
    * Why:
    */
   public static Date addDate(int days) {
   	Locale loc = Locale.getDefault();
       GregorianCalendar cal = new GregorianCalendar(loc);
       int day = cal.get(Calendar.DAY_OF_MONTH);
       int month = cal.get(Calendar.MONTH);
       int year = cal.get(Calendar.YEAR);
       cal.set(year, month, day + days);
       return cal.getTime();
   }
   
	/**
	 * 计算2个日期相差的天数
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws ParseException
	 */
	public static Long differDays(String startDate, String endDate) throws ParseException{
		SimpleDateFormat df =new SimpleDateFormat("yyyy/MM/dd");
		Date date1 = df.parse(startDate);
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date1);
		Long time1 = cal1.getTimeInMillis();
		
		Date date2 = df.parse(endDate);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(date2);
		Long time2 = cal2.getTimeInMillis();
		
		return (time2-time1)/(1000*3600*24);
	}
	
	/**
	 * 计算2个日期相差的天数
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws ParseException
	 */
	public static Long differDays(Date startDate, Date endDate) throws ParseException{
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(startDate);
		Long time1 = cal1.getTimeInMillis();
		
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(endDate);
		Long time2 = cal2.getTimeInMillis();
		
		return (time2-time1)/(1000*3600*24);
	}
	
	/**
	 * 提供起始日期，通过相差天数计算出目标日期
	 * @param startDate
	 * @param day
	 * @return
	 * @throws ParseException
	 */
	public static String getDateByDifferDays(String startDate, int day) throws ParseException{
		SimpleDateFormat df =new SimpleDateFormat("yyyy-MM-dd");
		Date date = df.parse(startDate);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, day);
		
		return df.format(cal.getTime());
	}
	/**
	 * 提供起始日期，通过相差天数计算出目标日期
	 * @param startDate
	 * @param day
	 * @return
	 * @throws ParseException
	 */
	public static Date getDateByDifferDays(Date startDate, int day){
		Calendar cal = Calendar.getInstance();
		cal.setTime(startDate);
		cal.add(Calendar.DAY_OF_MONTH, day);
		return cal.getTime();
	}
	/**
	 * 提供起始日期，通过相差天数计算出目标日期
	 * @param startDate
	 * @param hour
	 * @return
	 * @throws ParseException
	 */
	public static Date getDateByDifferHours(Date startDate, int hour){
		Calendar cal = Calendar.getInstance();
		cal.setTime(startDate);
		cal.add(Calendar.HOUR_OF_DAY, hour);
		return cal.getTime();
	}


    public static Date getDateByDifferMinutes(Date date,int differMinutes){
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE,differMinutes);
        return  calendar.getTime();
    }

    public static Long getDifferMinutes(Date date,Date compareDate){
        return (compareDate.getTime()-date.getTime())/(60*1000);
    }
    public static Long getDifferSeconds(Date date,Date compareDate){
        return (compareDate.getTime()-date.getTime())/(1000);
    }
    public static Long getDifferHours(Date date,Date compareDate){
        return (compareDate.getTime()-date.getTime())/(60*60*1000);
    }
    public static Long getDifferMinutes(Timestamp date,Timestamp compareDate){
        return (compareDate.getTime()-date.getTime())/(60*1000);
    }


    /**
     *将date的时分秒置空
     * @return
     */
    public static Date getFirstTimeOfDate(Date date){
        Calendar cal=Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY,0);
        cal.set(Calendar.MINUTE,0);
        cal.set(Calendar.SECOND,0);
        return cal.getTime();
    }
    /**
     *将date的时分秒置空
     * @return
     */
    public static Date getFirstTimeOfNextDate(Date date){
        Date nextDay=getDateByDifferDays(date,1);
        Calendar cal=Calendar.getInstance();
        cal.setTime(nextDay);
        cal.set(Calendar.HOUR_OF_DAY,0);
        cal.set(Calendar.MINUTE,0);
        cal.set(Calendar.SECOND,0);
        return cal.getTime();
    }

    public static String getWeekDayFormat(int wday){
        switch (wday){
            case 1:return "星期一";
            case 2:return "星期二";
            case 3:return "星期三";
            case 4:return "星期四";
            case 5:return "星期五";
            case 6:return "星期六";
            case 0:return "星期日";
        }
        return "";
    }
    
    /**
     * 获取指定时区的时间
     * 中国一般使用东八区则为8，美国华盛顿西五区则为-5等等
     * @param timeZoneOffset表示时区
     * @return
     */
    public static String getDateByTimeZone(float timeZoneOffset){
        if (timeZoneOffset > 13 || timeZoneOffset < -12) {
            timeZoneOffset = 0;
        }
        
        int newTime=(int)(timeZoneOffset * 60 * 60 * 1000);
        TimeZone timeZone;
        String[] ids = TimeZone.getAvailableIDs(newTime);
        if (ids.length == 0) {
            timeZone = TimeZone.getDefault();
        } else {
            timeZone = new SimpleTimeZone(newTime, ids[0]);
        }
    
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(timeZone);
        return sdf.format(new Date());
    }

}

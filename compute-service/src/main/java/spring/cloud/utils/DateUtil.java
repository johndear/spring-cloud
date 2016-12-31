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
   
	public static String getDateByDifferDays(String startDate, int day) throws ParseException{
		SimpleDateFormat df =new SimpleDateFormat("yyyy-MM-dd");
		Date date = df.parse(startDate);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, day);
		
		return df.format(cal.getTime());
	}
	

}

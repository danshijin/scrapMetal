package com.smm.scrapMetal.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * @author zengshihua
 */
public class DateUtil {

    // 格式化当前系统日期
    public static String getDateWidthFormat(String format) {
        if (format == null || format.equals("")) {
            format = "yyyy-MM-dd HH:mm:ss";
        }

        SimpleDateFormat dateFm = new SimpleDateFormat(format);
        String dateTime = dateFm.format(new java.util.Date());
        return dateTime;
    }

    // 格式化日期
    public static String doFormatDate(Date date, String format) {
        if (format == null || format.equals("")) {
            format = "yyyy-MM-dd HH:mm:ss";
        }

        if (date == null) {
            date = new Date();
        }

        SimpleDateFormat dateFm = new SimpleDateFormat(format);
        String dateTime = dateFm.format(date);
        return dateTime;
    }

    // 转化时间字符串为日期
    public static Date doSFormatDate(String dateStr, String format) {
        if (dateStr.equals("")) {
            return null;
        }
        if (format == null || format.equals("")) {
            format = "yyyy-MM-dd HH:mm:ss";
        }

        SimpleDateFormat dateFm = new SimpleDateFormat(format);
        Date date = null;
        try {
            date = dateFm.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String doFormatDate(Date oldDate, Date newDate) {
        Calendar oldCal = Calendar.getInstance();
        Calendar newCal = Calendar.getInstance();
        oldCal.setTime(oldDate);
        newCal.setTime(newDate);
        if (oldCal.equals(newCal)) {
            return "";
        } else if (oldCal.before(newCal)) {
            return "";
        } else if (oldCal.after(newCal)) {
            return "";
        } else {
            return "";
        }

    }

    public static int getMinute() {
        Calendar calendar = Calendar.getInstance();
        int minute = calendar.get(Calendar.MINUTE);
        return minute;
    }

    //	public static String dateFormat2En(Date date){
    //		SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy",Locale.UK);
    //		return sdf.format(date);
    //	};

    public static String dateFormat2En(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy", Locale.UK);
        return sdf.format(new Date(Long.parseLong(time + "000")));
    };

    /**
     * 天数加减
     * 
     * @param days加减天数，如：+1就是日期的后一天，-1就是日期的前一天
     * @param format 转换的格式
     * @param date 日期
     * @return
     */
    public static String dateAddAndSubtract(String days, String format, String date) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            Date now = sdf.parse(date);
            Calendar cal = Calendar.getInstance();
            cal.setTime(now);
            cal.add(Calendar.DAY_OF_MONTH, Integer.parseInt(days));//取当前日期的后一天
            return sdf.format(cal.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    };

    /**
     * 月份加减
     * 
     * @param days加减天数，如：+1就是日期的后一天，-1就是日期的前一天
     * @param format 格式
     * @param date 日期
     * @return
     */
    public static String monthAddAndSubtract(String months, String format, String date) {

        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            Date now = sdf.parse(date);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(now);
            calendar.add(Calendar.MONTH, Integer.parseInt(months));
            return sdf.format(calendar.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;

    }
    
    /**     一天的开始时间 00:00:00
     * @discription 
     * @author Nancy/张楠       
     * @created 2015年9月14日 上午11:09:11     
     * @return     
     */
public static Date startOfTodDay(String date) {
	Date startDate = DateUtil.doSFormatDate(date ,"yyyy-MM-dd");
   
	Calendar calendar = Calendar.getInstance();
    calendar.setTime(startDate);
    calendar.set(Calendar.HOUR_OF_DAY, 0);
    calendar.set(Calendar.MINUTE, 0);
    calendar.set(Calendar.SECOND, 0);
    calendar.set(Calendar.MILLISECOND, 0);
    
    return calendar.getTime();
}


   
/**     一天的结束时间 23:59:59
 * @discription 
 * @author Nancy/张楠       
 * @created 2015年9月14日 上午11:09:26     
 * @return     
 */
public static Date endOfTodDay(String date) {
	Date endDate = DateUtil.doSFormatDate(date ,"yyyy-MM-dd");
	
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(endDate);
    calendar.set(Calendar.HOUR_OF_DAY, 23);
    calendar.set(Calendar.MINUTE, 59);
    calendar.set(Calendar.SECOND, 59);
    calendar.set(Calendar.MILLISECOND, 999);
    
    return calendar.getTime();
 }
    
    /**
	 * 返回当前日期
	 * */
	public String currentDate(){
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str = sdf.format(date);
		return str;
	}

    public static void main(String[] args) {
        //System.out.println(dateAddAndSubtract("7", "yyyy-MM-dd", "2015-11-11"));
        System.out.println(dateAddAndSubtract("-6", "yyyy-MM-dd 23:00:00", "2015-11-11 23:00:00"));
        System.out.println(monthAddAndSubtract("-3", "yyyy-MM-dd 23:00:00", "2015-11-11 23:00:00"));
    }
}

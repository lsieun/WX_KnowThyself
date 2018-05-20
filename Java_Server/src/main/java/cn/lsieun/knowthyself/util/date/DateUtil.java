package cn.lsieun.knowthyself.util.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

public class DateUtil {
    public static Date getDate(String dateStr){
        Date currentDate = null;
        try {
            currentDate = DateUtils.parseDate(dateStr,"yyyyMMdd", "yyyy-MM-dd", "yyyy-MM-dd HH:mm", "yyyy-MM-dd HH:mm:ss");
        } catch (ParseException e) {
            currentDate = new Date();
            e.printStackTrace();
        }
        return currentDate;
    }

    public static Date getMondayDate(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.setFirstDayOfWeek(Calendar.MONDAY);// 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
        if(dayWeek==1){
            dayWeek = 8;
        }

        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - dayWeek);// 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
        Date mondayDate = cal.getTime();

        return mondayDate;
    }

    public static Date getSundayDate(Date date){

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.setFirstDayOfWeek(Calendar.MONDAY);// 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
        if(dayWeek==1){
            dayWeek = 8;
        }

        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - dayWeek);// 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值


        cal.add(Calendar.DATE, 4 +cal.getFirstDayOfWeek());
        Date sundayDate = cal.getTime();

        return sundayDate;
    }

    public static String formatCurrentDay(String dateStr){
        Date currentDate = getDate(dateStr);
        String currentDay = DateFormatUtils.format(currentDate,"yyyy-MM-dd");
        return currentDay;
    }

    public static Map<String, String> formatCurrentWeek(String dateStr){
        Date currentDate = getDate(dateStr);
        Map<String, String> weekDate = getWeekDate(currentDate);

        return weekDate;
    }

    public static String formatCurrentMonth(String dateStr){
        Date currentDate = getDate(dateStr);
        String currentMonth = DateFormatUtils.format(currentDate,"yyyy-MM");
        return currentMonth;
    }

    /**
     * 当前时间所在一周的周一和周日时间
     * @param date 当前时间
     * @return
     */
    public static Map<String,String> getWeekDate(Date date) {
        Map<String,String> map = new HashMap<String,String>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.setFirstDayOfWeek(Calendar.MONDAY);// 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
        if(dayWeek==1){
            dayWeek = 8;
        }

        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - dayWeek);// 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
        Date mondayDate = cal.getTime();
        String weekBegin = sdf.format(mondayDate);
        System.out.println("所在周星期一的日期：" + weekBegin);


        cal.add(Calendar.DATE, 4 +cal.getFirstDayOfWeek());
        Date sundayDate = cal.getTime();
        String weekEnd = sdf.format(sundayDate);
        System.out.println("所在周星期日的日期：" + weekEnd);

        map.put("mondayDate", weekBegin);
        map.put("sundayDate", weekEnd);
        return map;
    }
}

package cn.lsieun.knowthyself.test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import cn.lsieun.knowthyself.util.json.JSONUtil;

public class DateExample {

    public static void main(String[] args) {
//        Map<String, String> weekDate = getWeekDate();
//        System.out.println(JSONUtil.getJsonString(weekDate));
    }
//    public static Map<String,String> getWeekDate() {
//        Map<String,String> map = new HashMap<String,String>();
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//
//        Calendar cal = Calendar.getInstance();
//        cal.setFirstDayOfWeek(Calendar.MONDAY);// 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
//        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
//        if(dayWeek==1){
//            dayWeek = 8;
//        }
//
//        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - dayWeek);// 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
//        Date mondayDate = cal.getTime();
//        String weekBegin = sdf.format(mondayDate);
//        System.out.println("所在周星期一的日期：" + weekBegin);
//
//
//        cal.add(Calendar.DATE, 4 +cal.getFirstDayOfWeek());
//        Date sundayDate = cal.getTime();
//        String weekEnd = sdf.format(sundayDate);
//        System.out.println("所在周星期日的日期：" + weekEnd);
//
//        map.put("mondayDate", weekBegin);
//        map.put("sundayDate", weekEnd);
//        return map;
//    }
}

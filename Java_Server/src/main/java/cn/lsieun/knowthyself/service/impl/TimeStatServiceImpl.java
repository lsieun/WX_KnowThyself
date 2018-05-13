package cn.lsieun.knowthyself.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.lsieun.knowthyself.dao.TimelineDao;
import cn.lsieun.knowthyself.dao.TimelineTypeDao;
import cn.lsieun.knowthyself.dto.TimeStatDTO;
import cn.lsieun.knowthyself.entity.Timeline;
import cn.lsieun.knowthyself.entity.TimelineType;
import cn.lsieun.knowthyself.service.TimeStatService;
import cn.lsieun.knowthyself.util.page.PageUtil;

@Service("timeStatService")
public class TimeStatServiceImpl implements TimeStatService {
    @Autowired
    private TimelineDao timelineDao;

    @Autowired
    private TimelineTypeDao timelineTypeDao;

    @Override
    public List<TimeStatDTO> getTimeDayStat(String userid, String yyyyMMdd) {



        Map<String,String> queryMap = new HashMap<String,String>();
        queryMap.put("userid",userid);
        queryMap.put("currentDate", PageUtil.formatyyyyMMdd(yyyyMMdd));
        queryMap.put("firstResult", "0");
        queryMap.put("maxResult","500");
        queryMap.put("sortType","1");
        queryMap.put("isValid","1");
        List<Timeline> timelineList = timelineDao.getList(queryMap);
        if(timelineList == null || timelineList.size() < 1) return null;

        Map<String,Integer> timelineMap = getMapFromTimelineList(timelineList);
        if(timelineMap.size() < 1) return null;

        queryMap.clear();
        queryMap.put("userid",userid);
        queryMap.put("firstResult","0");
        queryMap.put("maxResult","100");
        queryMap.put("sortType","1");
        queryMap.put("isValid","1");
        List<TimelineType> timelineTypeList = timelineTypeDao.getList(queryMap);

        List<TimeStatDTO> list = getEchartData(timelineMap, timelineTypeList);

        return list;
    }

    @Override
    public List<TimeStatDTO> getTimeWeekStat(String userid, String yyyyMMdd) {
        // region 处理日期
        Date currentDate = null;
        try {
            currentDate = DateUtils.parseDate(yyyyMMdd,"yyyyMMdd", "yyyy-MM-dd", "yyyy-MM-dd HH:mm", "yyyy-MM-dd HH:mm:ss");
        } catch (ParseException e) {
            currentDate = new Date();
            e.printStackTrace();
        }
        Map<String, String> weekDate = getWeekDate(currentDate);
        String mondayDate = weekDate.get("mondayDate");
        String sundayDate = weekDate.get("sundayDate");
        // endregion

        Map<String,String> queryMap = new HashMap<String,String>();
        queryMap.put("userid",userid);
        queryMap.put("mondayDate", mondayDate);
        queryMap.put("sundayDate", sundayDate);
        queryMap.put("firstResult", "0");
        queryMap.put("maxResult","1000");
        queryMap.put("sortType","1");
        queryMap.put("isValid","1");
        List<Timeline> timelineList = timelineDao.getList(queryMap);
        if(timelineList == null || timelineList.size() < 1) return null;

        Map<String,Integer> timelineMap = getMapFromTimelineList(timelineList);
        if(timelineMap.size() < 1) return null;

        queryMap.clear();
        queryMap.put("userid",userid);
        queryMap.put("firstResult","0");
        queryMap.put("maxResult","100");
        queryMap.put("sortType","1");
        queryMap.put("isValid","1");
        List<TimelineType> timelineTypeList = timelineTypeDao.getList(queryMap);

        List<TimeStatDTO> list = getEchartData(timelineMap, timelineTypeList);

        return list;
    }

    @Override
    public List<TimeStatDTO> getTimeMonthStat(String userid, String yyyyMMdd) {
        // region 处理日期
        Date currentDate = null;
        try {
            currentDate = DateUtils.parseDate(yyyyMMdd,"yyyyMMdd", "yyyy-MM-dd", "yyyy-MM-dd HH:mm", "yyyy-MM-dd HH:mm:ss");
        } catch (ParseException e) {
            currentDate = new Date();
            e.printStackTrace();
        }
        String currentMonth = DateFormatUtils.format(currentDate,"yyyy-MM");
        // endregion

        Map<String,String> queryMap = new HashMap<String,String>();
        queryMap.put("userid",userid);
        queryMap.put("currentMonth", currentMonth);
        queryMap.put("firstResult", "0");
        queryMap.put("maxResult","1000");
        queryMap.put("sortType","1");
        queryMap.put("isValid","1");
        List<Timeline> timelineList = timelineDao.getList(queryMap);
        if(timelineList == null || timelineList.size() < 1) return null;

        Map<String,Integer> timelineMap = getMapFromTimelineList(timelineList);
        if(timelineMap.size() < 1) return null;

        queryMap.clear();
        queryMap.put("userid",userid);
        queryMap.put("firstResult","0");
        queryMap.put("maxResult","100");
        queryMap.put("sortType","1");
        queryMap.put("isValid","1");
        List<TimelineType> timelineTypeList = timelineTypeDao.getList(queryMap);

        List<TimeStatDTO> list = getEchartData(timelineMap, timelineTypeList);

        return list;
    }

    private List<TimeStatDTO> getEchartData(Map<String,Integer> timelineMap, List<TimelineType> timelineTypeList){
        List<TimeStatDTO> list = new ArrayList<TimeStatDTO>();

        if(timelineTypeList == null || timelineTypeList.size() < 1){
            Set<Map.Entry<String, Integer>> entries = timelineMap.entrySet();
            Iterator<Map.Entry<String, Integer>> it = entries.iterator();
            Integer total = 0;
            while(it.hasNext()){
                Map.Entry<String, Integer> entry = it.next();
                String key = entry.getKey();
                Integer value = entry.getValue();
                total += value;
            }

            TimeStatDTO dto = new TimeStatDTO();
            dto.setName("未分类(" + total + ")");
            dto.setValue(String.valueOf(total));
            list.add(dto);
        }
        else{
            for(int i=0;i<timelineTypeList.size();i++){
                TimelineType timelineType = timelineTypeList.get(i);
                String uid = timelineType.getUid();
                String name = timelineType.getName();

                if(!timelineMap.containsKey(uid)) continue;
                Integer minutes = timelineMap.get(uid);

                TimeStatDTO dto = new TimeStatDTO();
                dto.setName(name);
                dto.setValue(String.valueOf(minutes));
                list.add(dto);
            }
        }
        return list;
    }

    private Map<String,Integer> getMapFromTimelineList(List<Timeline> list){
        Map<String,Integer> map = new HashMap<String,Integer>();
        if(list == null || list.size() < 1) return map;
        for(int i=0;i<list.size();i++){
            Timeline timeline = list.get(i);
            String uid = timeline.getTimelineType();
            Date startTime = timeline.getStartTime();
            Date endTime = timeline.getEndTime();

            Long between = (endTime.getTime() - startTime.getTime()) / 1000;//除以1000是为了转换成秒
            Long minutes = between / 60;

            if(map.containsKey(uid)){
                Integer oldValue = map.get(uid);
                Integer newValue = oldValue + minutes.intValue();
                map.put(uid,newValue);
            }
            else{
                map.put(uid,minutes.intValue());
            }
        }
        return map;
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

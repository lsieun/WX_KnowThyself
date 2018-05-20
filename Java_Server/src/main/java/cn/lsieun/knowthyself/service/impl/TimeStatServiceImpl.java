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
    public List<TimeStatDTO> getTimeDayStat(String userid, String currentDay) {
        Map<String,String> queryMap = new HashMap<String,String>();
        queryMap.put("userid",userid);
        queryMap.put("currentDate", currentDay);
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
    public List<TimeStatDTO> getTimeWeekStat(String userid, String mondayDate, String sundayDate) {
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
    public List<TimeStatDTO> getTimeMonthStat(String userid, String currentMonth) {

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


}

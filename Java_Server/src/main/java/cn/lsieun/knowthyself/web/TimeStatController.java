package cn.lsieun.knowthyself.web;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.lsieun.knowthyself.dto.ResultDTO;
import cn.lsieun.knowthyself.dto.TimeStatDTO;
import cn.lsieun.knowthyself.entity.Timeline;
import cn.lsieun.knowthyself.service.TimeStatService;
import cn.lsieun.knowthyself.util.date.DateUtil;

@RestController
@RequestMapping("/timestat")
public class TimeStatController {
    @Autowired
    private TimeStatService service;

    @RequestMapping(value = "/day", method = RequestMethod.POST)
    public ResultDTO day(@RequestBody Timeline timeline){
        String userid = timeline.getUserid();
        String currentDay = timeline.getCurrentDay();
        Date currentDate = DateUtil.getDate(currentDay);
        currentDay = DateFormatUtils.format(currentDate,"yyyy-MM-dd");
        List<TimeStatDTO> list = service.getTimeDayStat(userid, currentDay);

        ResultDTO result = new ResultDTO();
        result.setDataList(list);

        String title = DateFormatUtils.format(currentDate,"yyyy年MM月dd日");
        Map<String,String> modelMap = new HashMap<String,String>();
        modelMap.put("title",title);
        result.setData(modelMap);

        return result;
    }

    @RequestMapping(value = "/week", method = RequestMethod.POST)
    public ResultDTO week(@RequestBody Timeline timeline){
        String userid = timeline.getUserid();
        String currentDay = timeline.getCurrentDay();
        Date currentDate = DateUtil.getDate(currentDay);
        Date mondayDate = DateUtil.getMondayDate(currentDate);
        Date sundayDate = DateUtil.getSundayDate(currentDate);
        String monday = DateFormatUtils.format(mondayDate,"yyyy-MM-dd");
        String sunday = DateFormatUtils.format(sundayDate,"yyyy-MM-dd");
        List<TimeStatDTO> list = service.getTimeWeekStat(userid, monday, sunday);

        ResultDTO result = new ResultDTO();
        result.setDataList(list);

        String title = DateFormatUtils.format(mondayDate,"yyyy年MM月dd日")
                 + "-"
                + DateFormatUtils.format(sundayDate,"yyyy年MM月dd日");
        Map<String,String> modelMap = new HashMap<String,String>();
        modelMap.put("title",title);
        result.setData(modelMap);

        return result;
    }

    @RequestMapping(value = "/month", method = RequestMethod.POST)
    public ResultDTO month(@RequestBody Timeline timeline){
        String userid = timeline.getUserid();
        String currentDay = timeline.getCurrentDay();
        Date currentDate = DateUtil.getDate(currentDay);
        String currentMonth = DateFormatUtils.format(currentDate,"yyyy-MM");
        List<TimeStatDTO> list = service.getTimeMonthStat(userid, currentMonth);

        ResultDTO result = new ResultDTO();
        result.setDataList(list);

        String title = DateFormatUtils.format(currentDate,"yyyy年MM月");
        Map<String,String> modelMap = new HashMap<String,String>();
        modelMap.put("title",title);
        result.setData(modelMap);

        return result;
    }
}

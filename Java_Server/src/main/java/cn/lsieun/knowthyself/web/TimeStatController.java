package cn.lsieun.knowthyself.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.lsieun.knowthyself.dto.ResultDTO;
import cn.lsieun.knowthyself.dto.TimeStatDTO;
import cn.lsieun.knowthyself.entity.Timeline;
import cn.lsieun.knowthyself.service.TimeStatService;

@RestController
@RequestMapping("/timestat")
public class TimeStatController {
    @Autowired
    private TimeStatService service;

    @RequestMapping(value = "/day", method = RequestMethod.POST)
    public ResultDTO day(@RequestBody Timeline timeline){
        String userid = timeline.getUserid();
        String currentDay = timeline.getCurrentDay();
        List<TimeStatDTO> list = service.getTimeDayStat(userid, currentDay);

        ResultDTO result = new ResultDTO();
        result.setDataList(list);
        return result;
    }

    @RequestMapping(value = "/week", method = RequestMethod.POST)
    public ResultDTO week(@RequestBody Timeline timeline){
        String userid = timeline.getUserid();
        String currentDay = timeline.getCurrentDay();
        List<TimeStatDTO> list = service.getTimeWeekStat(userid, currentDay);

        ResultDTO result = new ResultDTO();
        result.setDataList(list);
        return result;
    }

    @RequestMapping(value = "/month", method = RequestMethod.POST)
    public ResultDTO month(@RequestBody Timeline timeline){
        String userid = timeline.getUserid();
        String currentDay = timeline.getCurrentDay();
        List<TimeStatDTO> list = service.getTimeMonthStat(userid, currentDay);

        ResultDTO result = new ResultDTO();
        result.setDataList(list);
        return result;
    }
}

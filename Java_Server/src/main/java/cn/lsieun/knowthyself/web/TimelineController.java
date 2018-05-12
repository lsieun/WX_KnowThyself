package cn.lsieun.knowthyself.web;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.lsieun.knowthyself.dto.ResultDTO;
import cn.lsieun.knowthyself.dto.TimelineDTO;
import cn.lsieun.knowthyself.entity.Timeline;
import cn.lsieun.knowthyself.service.TimelineService;

@RestController
@RequestMapping(value="/timeline")
public class TimelineController {
    @Autowired
    private TimelineService service;

    @RequestMapping(value="/add", method= RequestMethod.POST)
    public ResultDTO add(@RequestBody Timeline timeline){
        TimelineDTO dto = service.addTimeline(timeline);
        ResultDTO result = new ResultDTO();
        if(dto == null || StringUtils.isBlank(dto.getUid())){
            result.setSuccess(false);
        }
        result.setData(dto);
        return result;
    }

    @RequestMapping(value="/modify", method= RequestMethod.POST)
    public ResultDTO modify(@RequestBody Timeline timeline){
        boolean flag = service.modifyTimeline(timeline);

        ResultDTO result = new ResultDTO();
        result.setSuccess(flag);
        return result;
    }

    @RequestMapping(value="/info", method= RequestMethod.POST)
    public ResultDTO info(@RequestBody Timeline timeline){
        String uid = timeline.getUid();

        TimelineDTO dto = service.getTimeline(uid);

        ResultDTO result = new ResultDTO();
        if(dto == null || StringUtils.isBlank(dto.getUid())){
            result.setSuccess(false);
        }
        result.setData(dto);
        return result;
    }

    @RequestMapping(value="/daylist", method= RequestMethod.POST)
    public ResultDTO daylist(@RequestBody Timeline timeline){
        String userid = timeline.getUserid();
        String currentDay = timeline.getCurrentDay();
        String firstResult = timeline.getFirstResult();
        String maxResult = timeline.getMaxResult();
        String order = timeline.getOrder();

        List<TimelineDTO> timelineList = service.getTimelineListByDay(userid, currentDay, firstResult, maxResult, order);

        ResultDTO result = new ResultDTO();
        result.setDataList(timelineList);
        return result;
    }
}

package cn.lsieun.knowthyself.web;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.lsieun.knowthyself.dto.ResultDTO;
import cn.lsieun.knowthyself.dto.TimelineTypeDTO;
import cn.lsieun.knowthyself.entity.TimelineType;
import cn.lsieun.knowthyself.service.TimelineTypeService;

@RestController
@RequestMapping(value="/timelinetype")
public class TimelineTypeController {
    @Autowired
    private TimelineTypeService service;

    @RequestMapping(value="/add", method= RequestMethod.POST)
    public ResultDTO add(@RequestBody TimelineType timelineType){
        TimelineTypeDTO dto = service.addTimelineType(timelineType);
        ResultDTO result = new ResultDTO();
        if(dto == null || StringUtils.isBlank(dto.getUid())){
            result.setSuccess(false);
        }
        result.setData(dto);
        return result;
    }

    @RequestMapping(value="/modify", method= RequestMethod.POST)
    public ResultDTO modify(@RequestBody TimelineType timelineType){
        boolean flag = service.modifyTimelineType(timelineType);

        ResultDTO result = new ResultDTO();
        result.setSuccess(flag);
        return result;
    }

    @RequestMapping(value="/list", method= RequestMethod.POST)
    public ResultDTO list(@RequestBody TimelineType timelineType){
        String userid = timelineType.getUserid();
        String firstResult = timelineType.getFirstResult();
        String maxResult = timelineType.getMaxResult();
        String order = timelineType.getOrder();

        List<TimelineTypeDTO> timelineTypeList = service.getTimelineTypeList(userid, firstResult, maxResult, order);
        ResultDTO result = new ResultDTO();
        result.setDataList(timelineTypeList);
        return result;
    }

    @RequestMapping(value="/info", method= RequestMethod.POST)
    public ResultDTO info(@RequestBody TimelineType timelineType){
        String uid = timelineType.getUid();

        TimelineTypeDTO dto = service.getTimelineType(uid);

        ResultDTO result = new ResultDTO();
        if(dto == null || StringUtils.isBlank(dto.getUid())){
            result.setSuccess(false);
        }
        result.setData(dto);
        return result;
    }
}

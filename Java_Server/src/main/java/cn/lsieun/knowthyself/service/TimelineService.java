package cn.lsieun.knowthyself.service;

import java.util.List;
import java.util.Map;

import cn.lsieun.knowthyself.dto.TimelineDTO;
import cn.lsieun.knowthyself.dto.ValidationDTO;
import cn.lsieun.knowthyself.entity.Timeline;



public interface TimelineService {

    TimelineDTO addTimeline(Timeline timeline);

    boolean modifyTimeline(Timeline timeline);

    boolean delete(String uid);

    TimelineDTO getTimeline(String uid);

    List<TimelineDTO> getTimelineListByDay(String userid, String yyyy_MM_dd, String firstResult, String maxResult, String order);

    List<TimelineDTO> getList(Map<String,String> queryMap);

    int getCount(Map<String,String> queryMap);

    TimelineDTO entity2DTO(Timeline entity);

    List<TimelineDTO> entityList2DTOList(List<Timeline> entityList);

    ValidationDTO validate(Timeline timeline, boolean isCreate);

}

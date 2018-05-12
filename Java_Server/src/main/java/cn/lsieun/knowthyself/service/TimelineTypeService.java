package cn.lsieun.knowthyself.service;

import java.util.List;
import java.util.Map;

import cn.lsieun.knowthyself.dto.TimelineTypeDTO;
import cn.lsieun.knowthyself.dto.ValidationDTO;
import cn.lsieun.knowthyself.entity.TimelineType;



public interface TimelineTypeService {

    TimelineTypeDTO addTimelineType(TimelineType timelineType);

    boolean modifyTimelineType(TimelineType timelineType);

    boolean delete(String uid);

    TimelineTypeDTO getTimelineType(String uid);

    List<TimelineTypeDTO> getList(Map<String,String> queryMap);

    List<TimelineTypeDTO> getTimelineTypeList(String userid, String firstResult, String maxResult, String order);

    int getCount(Map<String,String> queryMap);

    TimelineTypeDTO entity2DTO(TimelineType entity);

    List<TimelineTypeDTO> entityList2DTOList(List<TimelineType> entityList);

    ValidationDTO validate(TimelineType timelineType, boolean isCreate);

}

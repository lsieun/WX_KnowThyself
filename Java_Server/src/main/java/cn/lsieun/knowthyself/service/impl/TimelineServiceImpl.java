package cn.lsieun.knowthyself.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.lsieun.knowthyself.dao.TimelineDao;
import cn.lsieun.knowthyself.dto.TimelineDTO;
import cn.lsieun.knowthyself.dto.ValidationDTO;
import cn.lsieun.knowthyself.entity.Timeline;
import cn.lsieun.knowthyself.exception.TimelineException;
import cn.lsieun.knowthyself.service.TimelineService;
import cn.lsieun.knowthyself.util.page.PageUtil;
import cn.lsieun.knowthyself.util.snowflake.SnowflakeIdWorker;


@Service("timelineService")
public class TimelineServiceImpl implements TimelineService{

    @Autowired
    private SnowflakeIdWorker snowflakeIdWorker;

    @Autowired
    private TimelineDao dao;

    @Override
    public TimelineDTO getTimeline(String uid) {
        Timeline timeline = dao.getById(uid);
        TimelineDTO dto = entity2DTO(timeline);
        return dto;
    }

    @Override
    public List<TimelineDTO> getTimelineListByDay(String userid, String yyyyMMdd, String firstResult, String maxResult, String order) {
        Map<String,String> queryMap = new HashMap<String,String>();
        queryMap.put("userid",userid);
        queryMap.put("currentDate",PageUtil.formatyyyyMMdd(yyyyMMdd));
        queryMap.put("firstResult", PageUtil.formatFirstResult(firstResult));
        queryMap.put("maxResult",PageUtil.formatMaxResult(maxResult));
        if("desc".equalsIgnoreCase(order)){
            queryMap.put("sortType","1");
        }
        else if("asc".equalsIgnoreCase(order)){
            queryMap.put("sortType","2");
        }
        else{
            queryMap.put("sortType","1");
        }
        queryMap.put("isValid","1");
        List<Timeline> list = dao.getList(queryMap);
        List<TimelineDTO> dtoList = entityList2DTOList(list);
        return dtoList;
    }

    @Override
    public TimelineDTO addTimeline(Timeline timeline) {
        /*
        处理思路：
        （1）进行数据有效性检验
        （2）将数据添加到数据库中
        （3）将结果进行返回
        * */
        // region （1）进行数据有效性检验
        ValidationDTO validationDTO = validate(timeline, true);
        if(validationDTO.isSuccess() == false) throw new TimelineException(validationDTO.getErrMsg());
        // endregion

        // region （2）将数据添加到数据库中
        long sid = snowflakeIdWorker.nextId();
        String uid = String.valueOf(sid);
        timeline.setUid(uid);

        int effectedNum = dao.insert(timeline);
        if(effectedNum != 1) throw new TimelineException("操作失败，数据库出错啦！");
        // endregion

        // region （3）将结果进行返回
        TimelineDTO dto = entity2DTO(timeline);
        return dto;
        // endregion
    }

    @Override
    public boolean modifyTimeline(Timeline timeline) {
        /*
        处理思路：
        （1）进行数据有效性检验
        （2）将数据更新到数据库中
        （3）将结果进行返回
        * */

        // region （1）进行数据有效性检验
        ValidationDTO validationDTO = validate(timeline, false);
        if(validationDTO.isSuccess() == false) throw new TimelineException(validationDTO.getErrMsg());
        // endregion

        // region （2）将数据更新到数据库中
        timeline.setLastEditTime(new Date());
        int effectedNum = dao.update(timeline);
        if(effectedNum != 1) throw new TimelineException("操作失败，数据库出错啦！");
        // endregion

        // region （3）将结果进行返回
        return true;
        // endregion
    }

    @Override
    public boolean delete(String uid) {
        Timeline timeline = new Timeline();
        timeline.setUid(uid);
        timeline.setIsValid(0);
        int effectedNum = dao.update(timeline);
        if(effectedNum != 1) return false;
        return true;
    }

    @Override
    public List<TimelineDTO> getList(Map<String,String> queryMap) {
        List<Timeline> taskList = dao.getList(queryMap);
        List<TimelineDTO> dtoList = entityList2DTOList(taskList);
        return dtoList;
    }

    @Override
    public int getCount(Map<String,String> queryMap) {
        return dao.getCount(queryMap);
    }


    @Override
    public TimelineDTO entity2DTO(Timeline entity) {
        TimelineDTO dto = new TimelineDTO();
        try {
            PropertyUtils.copyProperties(dto,entity);
            dto.setCurrentDay(entity.getStartTime());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return dto;
    }

    @Override
    public List<TimelineDTO> entityList2DTOList(List<Timeline> entityList) {
        if(entityList == null) return null;
        List<TimelineDTO> dtoList = new ArrayList<TimelineDTO>();
        for(Timeline entity : entityList){
            TimelineDTO dto = entity2DTO(entity);
            dtoList.add(dto);
        }
        return dtoList;
    }

    @Override
    public ValidationDTO validate(Timeline timeline, boolean isCreate) {
        String operation = (isCreate)?("添加"):("更新");
        String errMsg = "";
        if(timeline == null)
        {
            errMsg = operation + "操作失败，timeline不能为空！";
            return new ValidationDTO(false,errMsg);
        }
        if(isCreate && StringUtils.isNotBlank(timeline.getUid())){
            errMsg = "操作失败，主键ID不为空！";
            return new ValidationDTO(false,errMsg);
        }
        if(!isCreate && StringUtils.isBlank(timeline.getUid())){
            errMsg = "操作失败，主键ID不能为空！";
            return new ValidationDTO(false,errMsg);
        }
        if(StringUtils.isBlank(timeline.getUserid())) {
            errMsg = operation + "操作失败，用户ID不能为空！";
            return new ValidationDTO(false,errMsg);
        }
        if(StringUtils.isBlank(timeline.getName())) {
            errMsg = operation + "操作失败，时间线名称不能为空！";
            return new ValidationDTO(false,errMsg);
        }
        if(timeline.getTimelineType() == null){
            errMsg = operation + "操作失败，时间线类型不能为空！";
            return new ValidationDTO(false,errMsg);
        }
        if(timeline.getStartTime() == null){
            errMsg = operation + "操作失败，开始时间不能为空！";
            return new ValidationDTO(false,errMsg);
        }
        if(timeline.getEndTime() == null){
            errMsg = operation + "操作失败，结束时间不能为空！";
            return new ValidationDTO(false,errMsg);
        }
        if(timeline.getStartTime().after(timeline.getEndTime())){
            errMsg = operation + "操作失败，开始时间不能晚于结束时间！";
            return new ValidationDTO(false,errMsg);
        }

        return new ValidationDTO(true,errMsg);
    }

}

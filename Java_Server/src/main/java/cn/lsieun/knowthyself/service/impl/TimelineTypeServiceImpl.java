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

import cn.lsieun.knowthyself.dao.TimelineTypeDao;
import cn.lsieun.knowthyself.dto.TimelineTypeDTO;
import cn.lsieun.knowthyself.dto.ValidationDTO;
import cn.lsieun.knowthyself.entity.TimelineType;
import cn.lsieun.knowthyself.exception.TimelineTypeException;
import cn.lsieun.knowthyself.service.TimelineTypeService;
import cn.lsieun.knowthyself.util.page.PageUtil;
import cn.lsieun.knowthyself.util.snowflake.SnowflakeIdWorker;


@Service("timelineTypeService")
public class TimelineTypeServiceImpl implements TimelineTypeService{

    @Autowired
    private SnowflakeIdWorker snowflakeIdWorker;

    @Autowired
    private TimelineTypeDao dao;

    @Override
    public TimelineTypeDTO getTimelineType(String uid) {
        TimelineType timelineType = dao.getById(uid);
        TimelineTypeDTO dto = entity2DTO(timelineType);
        return dto;
    }

    @Override
    public TimelineTypeDTO addTimelineType(TimelineType timelineType) {
        /*
        处理思路：
        （1）进行数据有效性检验
        （2）将数据添加到数据库中
        （3）将结果进行返回
        * */
        // region （1）进行数据有效性检验
        ValidationDTO validationDTO = validate(timelineType, true);
        if(validationDTO.isSuccess() == false) throw new TimelineTypeException(validationDTO.getErrMsg());
        // endregion

        // region （2）将数据添加到数据库中
        long sid = snowflakeIdWorker.nextId();
        String uid = String.valueOf(sid);
        timelineType.setUid(uid);

        int effectedNum = dao.insert(timelineType);
        if(effectedNum != 1) throw new TimelineTypeException("操作失败，数据库出错啦！");
        // endregion

        // region （3）将结果进行返回
        TimelineTypeDTO dto = entity2DTO(timelineType);
        return dto;
        // endregion
    }

    @Override
    public boolean modifyTimelineType(TimelineType timelineType) {
        /*
        处理思路：
        （1）进行数据有效性检验
        （2）将数据更新到数据库中
        （3）将结果进行返回
        * */

        // region （1）进行数据有效性检验
        ValidationDTO validationDTO = validate(timelineType, false);
        if(validationDTO.isSuccess() == false) throw new TimelineTypeException(validationDTO.getErrMsg());
        // endregion

        // region （2）将数据更新到数据库中
        timelineType.setLastEditTime(new Date());
        int effectedNum = dao.update(timelineType);
        if(effectedNum != 1) throw new TimelineTypeException("操作失败，数据库出错啦！");
        // endregion

        // region （3）将结果进行返回
        return true;
        // endregion
    }

    @Override
    public boolean delete(String uid) {
        TimelineType timelineType = new TimelineType();
        timelineType.setUid(uid);
        timelineType.setIsValid(0);
        int effectedNum = dao.update(timelineType);
        if(effectedNum != 1) return false;
        return true;
    }

    @Override
    public List<TimelineTypeDTO> getList(Map<String,String> queryMap) {
        List<TimelineType> timelineTypeList = dao.getList(queryMap);
        List<TimelineTypeDTO> dtoList = entityList2DTOList(timelineTypeList);
        return dtoList;
    }

    @Override
    public List<TimelineTypeDTO> getTimelineTypeList(String userid, String firstResult, String maxResult, String order) {
        Map<String,String> queryMap = new HashMap<String,String>();
        queryMap.put("userid",userid);
        queryMap.put("firstResult", PageUtil.formatFirstResult(firstResult));
        queryMap.put("maxResult",PageUtil.formatMaxResult(maxResult));
        if("asc".equalsIgnoreCase(order)){
            queryMap.put("sortType","1");
        }
        else if("desc".equalsIgnoreCase(order)){
            queryMap.put("sortType","2");
        }
        else{
            queryMap.put("sortType","1");
        }
        queryMap.put("isValid","1");
        List<TimelineType> list = dao.getList(queryMap);
        List<TimelineTypeDTO> dtoList = this.entityList2DTOList(list);
        return dtoList;
    }

    @Override
    public int getCount(Map<String,String> queryMap) {
        return dao.getCount(queryMap);
    }

    @Override
    public TimelineTypeDTO entity2DTO(TimelineType entity) {
        TimelineTypeDTO dto = new TimelineTypeDTO();
        try {
            PropertyUtils.copyProperties(dto,entity);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return dto;
    }

    @Override
    public List<TimelineTypeDTO> entityList2DTOList(List<TimelineType> entityList) {
        if(entityList == null) return null;
        List<TimelineTypeDTO> dtoList = new ArrayList<TimelineTypeDTO>();
        for(TimelineType entity : entityList){
            TimelineTypeDTO dto = entity2DTO(entity);
            dtoList.add(dto);
        }
        return dtoList;
    }

    @Override
    public ValidationDTO validate(TimelineType timelineType, boolean isCreate) {
        String operation = (isCreate)?("添加"):("更新");
        String errMsg = "";
        if(timelineType == null)
        {
            errMsg = operation + "操作失败，任务不能为空！";
            return new ValidationDTO(false,errMsg);
        }
        if(isCreate && StringUtils.isNotBlank(timelineType.getUid())){
            errMsg = "操作失败，主键ID不为空！";
            return new ValidationDTO(false,errMsg);
        }
        if(!isCreate && StringUtils.isBlank(timelineType.getUid())){
            errMsg = "操作失败，主键ID不能为空！";
            return new ValidationDTO(false,errMsg);
        }
        if(StringUtils.isBlank(timelineType.getUserid())) {
            errMsg = operation + "操作失败，用户ID不能为空！";
            return new ValidationDTO(false,errMsg);
        }
        if(StringUtils.isBlank(timelineType.getName())) {
            errMsg = operation + "操作失败，名称不能为空！";
            return new ValidationDTO(false,errMsg);
        }

        return new ValidationDTO(true,errMsg);
    }

}

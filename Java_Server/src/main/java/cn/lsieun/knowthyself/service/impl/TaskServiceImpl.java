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

import cn.lsieun.knowthyself.dao.TaskDao;
import cn.lsieun.knowthyself.dto.TaskDTO;
import cn.lsieun.knowthyself.dto.ValidationDTO;
import cn.lsieun.knowthyself.entity.Task;
import cn.lsieun.knowthyself.exception.TaskException;
import cn.lsieun.knowthyself.service.TaskService;
import cn.lsieun.knowthyself.util.page.PageUtil;
import cn.lsieun.knowthyself.util.snowflake.SnowflakeIdWorker;

@Service(value = "taskService")
public class TaskServiceImpl implements TaskService {

    @Autowired
    private SnowflakeIdWorker snowflakeIdWorker;

    @Autowired
    private TaskDao dao;

    @Override
    public TaskDTO getTask(String uid) {
        Task task = dao.getById(uid);
        TaskDTO dto = entity2DTO(task);
        return dto;
    }

    @Override
    public TaskDTO addTask(Task task) {
        /*
        处理思路：
        （1）进行数据有效性检验
        （2）将数据添加到数据库中
        （3）将结果进行返回
        * */
        // region （1）进行数据有效性检验
        ValidationDTO validationDTO = validate(task, true);
        if(validationDTO.isSuccess() == false) throw new TaskException(validationDTO.getErrMsg());
        // endregion

        // region （2）将数据添加到数据库中
        long sid = snowflakeIdWorker.nextId();
        String uid = String.valueOf(sid);
        task.setUid(uid);

        int effectedNum = dao.insert(task);
        if(effectedNum != 1) throw new TaskException("操作失败，数据库出错啦！");
        // endregion

        // region （3）将结果进行返回
        TaskDTO dto = entity2DTO(task);
        return dto;
        // endregion
    }

    @Override
    public boolean modifyTask(Task task) {
        /*
        处理思路：
        （1）进行数据有效性检验
        （2）将数据更新到数据库中
        （3）将结果进行返回
        * */

        // region （1）进行数据有效性检验
        ValidationDTO validationDTO = validate(task, false);
        if(validationDTO.isSuccess() == false) throw new TaskException(validationDTO.getErrMsg());
        // endregion

        // region （2）将数据更新到数据库中
        task.setLastEditTime(new Date());
        int effectedNum = dao.update(task);
        if(effectedNum != 1) throw new TaskException("操作失败，数据库出错啦！");
        // endregion

        // region （3）将结果进行返回
        return true;
        // endregion
    }

    @Override
    public boolean deleteTask(String uid) {
        Task task = new Task();
        task.setUid(uid);
        task.setIsValid(0);
        int effectedNum = dao.update(task);
        if(effectedNum != 1) return false;
        return true;
    }

    //TODO: 有没有自己写asc的语句呢？
    @Override
    public List<TaskDTO> getTaskList(String userid, String firstResult, String maxResult, String order) {
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
        List<Task> taskList = dao.getList(queryMap);
        List<TaskDTO> dtoList = entityList2DTOList(taskList);
        return dtoList;
    }

    @Override
    public List<TaskDTO> getTaskListByDay(String userid, String yyyyMMdd, String firstResult, String maxResult, String order) {
        Map<String,String> queryMap = new HashMap<String,String>();
        queryMap.put("userid",userid);
        queryMap.put("currentDate",PageUtil.formatyyyyMMdd(yyyyMMdd));
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
        List<Task> taskList = dao.getList(queryMap);
        List<TaskDTO> dtoList = entityList2DTOList(taskList);
        return dtoList;
    }

    @Override
    public boolean finishTask(String uid) {
        Task task = new Task();
        task.setUid(uid);
        task.setStatus(1);
        int effectedNum = dao.update(task);
        if(effectedNum != 1) return false;
        return true;
    }

    @Override
    public boolean unfinishTask(String uid) {
        Task task = new Task();
        task.setUid(uid);
        task.setStatus(0);
        int effectedNum = dao.update(task);
        if(effectedNum != 1) return false;
        return true;
    }

    @Override
    public TaskDTO entity2DTO(Task entity) {
        TaskDTO dto = new TaskDTO();
        try {
            PropertyUtils.copyProperties(dto,entity);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return dto;
    }

    @Override
    public List<TaskDTO> entityList2DTOList(List<Task> entityList) {
        if(entityList == null) return null;
        List<TaskDTO> dtoList = new ArrayList<TaskDTO>();
        for(Task entity : entityList){
            TaskDTO dto = entity2DTO(entity);
            dtoList.add(dto);
        }
        return dtoList;
    }

    //TODO: 用户里也改成这样吧。
    @Override
    public ValidationDTO validate(Task task, boolean isCreate) {
        String operation = (isCreate)?("添加"):("更新");
        String errMsg = "";
        if(task == null)
        {
            errMsg = operation + "任务失败，任务不能为空！";
            return new ValidationDTO(false,errMsg);
        }
        if(isCreate && StringUtils.isNotBlank(task.getUid())){
            errMsg = "创建任务失败，主键ID不为空！";
            return new ValidationDTO(false,errMsg);
        }
        if(!isCreate && StringUtils.isBlank(task.getUid())){
            errMsg = "更新任务失败，主键ID不能为空！";
            return new ValidationDTO(false,errMsg);
        }
        if(StringUtils.isBlank(task.getUserid())) {
            errMsg = operation + "任务失败，用户ID不能为空！";
            return new ValidationDTO(false,errMsg);
        }
        if(StringUtils.isBlank(task.getName())) {
            errMsg = operation + "任务失败，任务名称不能为空！";
            return new ValidationDTO(false,errMsg);
        }
        if(task.getStartTime() == null){
            errMsg = operation + "任务失败，开始时间不能为空！";
            return new ValidationDTO(false,errMsg);
        }
        if(task.getEndTime() == null){
            errMsg = operation + "任务失败，结束时间不能为空！";
            return new ValidationDTO(false,errMsg);
        }
        if(task.getStartTime().after(task.getEndTime())){
            errMsg = operation + "任务失败，开始时间不能晚于结束时间！";
            return new ValidationDTO(false,errMsg);
        }

        return new ValidationDTO(true,errMsg);
    }


}

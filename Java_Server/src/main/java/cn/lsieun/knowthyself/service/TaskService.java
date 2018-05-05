package cn.lsieun.knowthyself.service;

import cn.lsieun.knowthyself.dto.TaskDTO;
import cn.lsieun.knowthyself.dto.ValidationDTO;
import cn.lsieun.knowthyself.entity.Task;

import java.util.List;

//FIXME: 修改后注释
public interface TaskService {
    // 查看任务
    TaskDTO getTask(String uid);
    //创建任务
    TaskDTO addTask(Task task);
    //修改任务
    boolean modifyTask(Task task);
    //删除任务
    boolean deleteTask(String uid);
    //获取任务列表
    List<TaskDTO> getTaskList(String userid, String firstResult, String maxResult, String order);
    //获取某一天的任务列表
    List<TaskDTO> getTaskListByDay(String userid, String yyyy_MM_dd, String firstResult, String maxResult, String order);
    //TODO：我用MQ来实现数据库与Redis的同步
    // 完成任务
    boolean finishTask(String uid);
    // 未完成任务
    boolean unfinishTask(String uid);

    TaskDTO entity2DTO(Task entity);

    List<TaskDTO> entityList2DTOList(List<Task> entityList);

    ValidationDTO validate(Task task, boolean isCreate);
}

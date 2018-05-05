package cn.lsieun.knowthyself.web;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.lsieun.knowthyself.dto.ResultDTO;
import cn.lsieun.knowthyself.dto.TaskDTO;
import cn.lsieun.knowthyself.entity.Task;
import cn.lsieun.knowthyself.service.TaskService;

@RestController
@RequestMapping(value="/task")
public class TaskController{

    @Autowired
    private TaskService service;

    @RequestMapping(value="/add", method= RequestMethod.POST)
    public ResultDTO add(@RequestBody Task task) {
        TaskDTO dto =  service.addTask(task);
        ResultDTO result = new ResultDTO();
        if(dto == null || StringUtils.isBlank(dto.getUid())){
            result.setSuccess(false);
        }
        result.setData(dto);
        return result;
    }

    @RequestMapping(value="/modify", method= RequestMethod.POST)
    public ResultDTO modify(@RequestBody Task task) {
        boolean flag = service.modifyTask(task);

        ResultDTO result = new ResultDTO();
        result.setSuccess(flag);
        return result;
    }

    @RequestMapping(value="/daylist", method= RequestMethod.POST)
    public ResultDTO daylist(@RequestBody Task task) {
        String userid = task.getUserid();
        String currentDay = task.getCurrentDay();
        String firstResult = task.getFirstResult();
        String maxResult = task.getMaxResult();
        String order = task.getOrder();

        List<TaskDTO> taskList = service.getTaskListByDay(userid, currentDay, firstResult, maxResult, order);

        ResultDTO result = new ResultDTO();
        result.setDataList(taskList);
        return result;
    }

    @RequestMapping(value="/all", method= RequestMethod.POST)
    public ResultDTO all(@RequestBody Task task) {
        String userid = task.getUserid();
        String firstResult = task.getFirstResult();
        String maxResult = task.getMaxResult();
        String order = task.getOrder();

        List<TaskDTO> taskList = service.getTaskList(userid, firstResult, maxResult, order);

        ResultDTO result = new ResultDTO();
        result.setDataList(taskList);
        return result;
    }

    @RequestMapping(value="/done", method= RequestMethod.POST)
    public ResultDTO done(@RequestBody Task task) {
        String uid = task.getUid();

        boolean flag = service.finishTask(uid);

        ResultDTO result = new ResultDTO();
        result.setSuccess(flag);
        return result;
    }

    @RequestMapping(value="/undo", method= RequestMethod.POST)
    public ResultDTO undo(@RequestBody Task task) {
        String uid = task.getUid();

        boolean flag = service.unfinishTask(uid);

        ResultDTO result = new ResultDTO();
        result.setSuccess(flag);
        return result;
    }

    @RequestMapping(value="/del", method= RequestMethod.POST)
    public ResultDTO del(@RequestBody Task task) {
        String uid = task.getUid();

        boolean flag = service.deleteTask(uid);

        ResultDTO result = new ResultDTO();
        result.setSuccess(flag);
        return result;
    }

    @RequestMapping(value="/info", method= RequestMethod.POST)
    public ResultDTO info(@RequestBody Task task) {
        String uid = task.getUid();

        TaskDTO dto = service.getTask(uid);

        ResultDTO result = new ResultDTO();
        if(dto == null || StringUtils.isBlank(dto.getUid())){
            result.setSuccess(false);
        }
        result.setData(dto);
        return result;
    }
}

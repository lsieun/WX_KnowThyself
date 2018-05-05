package cn.lsieun.knowthyself.service;

import cn.lsieun.knowthyself.dto.TaskDTO;
import cn.lsieun.knowthyself.entity.Task;
import cn.lsieun.knowthyself.util.json.JSONUtil;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING) // 按方法名大小升序执行
public class TaskServiceTest {

    @Autowired
    private TaskService service;

    @Test
    public void createTask() throws Exception {
        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH,1);
        Date nextTime = calendar.getTime();

        Task entity = new Task();
        entity.setUserid("441517664024657920");
        entity.setName("这里我的第一个任务");
        entity.setPriority(0);
        entity.setStartTime(now);
        entity.setEndTime(nextTime);
        entity.setStatus(0);

        TaskDTO task = service.addTask(entity);
        System.out.println(JSONUtil.getJsonString(task));
    }

    @Test
    public void modifyTask() throws Exception {
    }

    @Test
    public void deleteTask() throws Exception {
    }

    @Test
    public void getTaskList() throws Exception {
    }

    @Test
    public void getTaskListByDay() throws Exception {
        String userid = "userid";
        String yyyyMMdd = "2018-05-03 10:47:24";
        String firstResult = "0";
        String maxResult = "20";
        String order = "asc";
        List<TaskDTO> list = service.getTaskListByDay(userid, yyyyMMdd, firstResult, maxResult, order);
        System.out.println(JSONUtil.getJsonString(list));
    }

    @Test
    public void finishTask() throws Exception {
    }

    @Test
    public void unfinishTask() throws Exception {
    }

    @Test
    public void entity2DTO() throws Exception {
    }

    @Test
    public void entityList2DTOList() throws Exception {
    }

    @Test
    public void validate() throws Exception {
    }

}
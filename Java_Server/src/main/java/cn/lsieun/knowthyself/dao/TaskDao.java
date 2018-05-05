package cn.lsieun.knowthyself.dao;
import cn.lsieun.knowthyself.entity.Task;
import org.springframework.stereotype.Repository;

@Repository(value = "taskDao")
public interface TaskDao extends CommonDao<Task>{

}

package cn.lsieun.knowthyself.dao;

import cn.lsieun.knowthyself.entity.User;
import org.springframework.stereotype.Repository;

@Repository(value = "userDao")
public interface UserDao extends CommonDao<User>{

}

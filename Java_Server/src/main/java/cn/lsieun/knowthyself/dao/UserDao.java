package cn.lsieun.knowthyself.dao;

import cn.lsieun.knowthyself.entity.User;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

//@Repository(value = "userDao") //FIXME: 如果我这么写，会不会有错误
public interface UserDao extends CommonDao<User>{

}

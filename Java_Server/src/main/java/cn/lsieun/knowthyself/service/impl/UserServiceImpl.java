package cn.lsieun.knowthyself.service.impl;

import cn.lsieun.knowthyself.dao.UserDao;
import cn.lsieun.knowthyself.dto.UserDTO;
import cn.lsieun.knowthyself.entity.User;
import cn.lsieun.knowthyself.exception.UserException;
import cn.lsieun.knowthyself.service.UserService;
import cn.lsieun.knowthyself.util.snowflake.SnowflakeIdWorker;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.Map;

@Service(value = "userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private SnowflakeIdWorker snowflakeIdWorker;

    @Autowired
    private UserDao dao;

    @Override
    public UserDTO signin(User user) {
        if(user == null) throw new UserException("注册失败，用户不能为空");
        String uid = user.getUid();
        if(StringUtils.isNotBlank(uid)) throw new UserException("注册失败，用户uid应为空值");
        long sid = snowflakeIdWorker.nextId();
        uid = String.valueOf(sid);
        user.setUid(uid);
        int effectedNum = dao.insert(user);
        if(effectedNum != 1) throw new UserException("注册失败，数据库操作发生错误");

        UserDTO dto = new UserDTO();
        dto.setUid(uid);
        return dto;
    }

    @Override
    public boolean saveUserInfo(User user) {
        if(user == null) throw new UserException("更新用户信息失败，用户不能为空");
        String uid = user.getUid();
        if(StringUtils.isBlank(uid)) throw new UserException("更新用户信息失败，用户uid不能为空值");
        user.setLastEditTime(new Date());
        int effectedNum = dao.update(user);
        if(effectedNum != 1) throw new UserException("更新用户信息失败，数据库操作发生错误");
        return true;
    }

    @Override
    public UserDTO getUserInfo(String uid) {
        if(StringUtils.isBlank(uid)) throw new UserException("获取用户信息失败，用户uid不能为空值");
        User user = dao.getById(uid);
        if(user == null) throw new UserException("获取用户信息失败，不存在该用户。");

        UserDTO dto = new UserDTO();
        dto.setUid(uid);
        try {
            PropertyUtils.copyProperties(dto,user);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new UserException("获取用户信息失败，用户信息转换过程出错。");
        }
        //dto.setData(user);
        return dto;
    }
}

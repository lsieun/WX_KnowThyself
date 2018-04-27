package cn.lsieun.knowthyself.service;

import cn.lsieun.knowthyself.dto.UserDTO;
import cn.lsieun.knowthyself.entity.User;

import java.util.Map;

public interface UserService {
    UserDTO signin(User user);
    boolean saveUserInfo(User user);
    UserDTO getUserInfo(String uid);
}

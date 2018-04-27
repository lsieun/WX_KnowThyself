package cn.lsieun.knowthyself.web;

import cn.lsieun.knowthyself.dto.UserDTO;
import cn.lsieun.knowthyself.entity.User;
import cn.lsieun.knowthyself.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService service;

    @RequestMapping(value = "/signin", method = RequestMethod.POST)
    public UserDTO signin(User user){
        UserDTO dto = service.signin(user);
        return dto;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public UserDTO save(User user){
        boolean flag = service.saveUserInfo(user);
        UserDTO dto = new UserDTO();
        if(flag == false) dto.setSuccess(false);
        return dto;
    }

    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public UserDTO info(String uid){
        UserDTO dto = service.getUserInfo(uid);
        return dto;
    }
}

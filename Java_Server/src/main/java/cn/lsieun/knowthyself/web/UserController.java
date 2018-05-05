package cn.lsieun.knowthyself.web;

import cn.lsieun.knowthyself.dto.ResultDTO;
import cn.lsieun.knowthyself.dto.UserDTO;
import cn.lsieun.knowthyself.dto.WXUserDTO;
import cn.lsieun.knowthyself.dto.WeiXinDTO;
import cn.lsieun.knowthyself.entity.User;
import cn.lsieun.knowthyself.exception.UserException;
import cn.lsieun.knowthyself.service.UserService;
import cn.lsieun.knowthyself.util.json.JSONUtil;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService service;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public UserDTO loginOrSignup(@RequestBody User user){
        if(user == null) throw new UserException("用户登录失败，用户不能为空");

        String js_code = user.getCode();
        if(StringUtils.isBlank(js_code)) throw new UserException("用户登录失败，用户微信code为空");

        WeiXinDTO wxDTO = service.getWxUserInfo(js_code);
        if(wxDTO == null || StringUtils.isBlank(wxDTO.getOpenid())) throw new UserException("用户登录失败，用户微信code无效");

        String wx_openid = wxDTO.getOpenid();
        String session_key = wxDTO.getSession_key();
        String iv = user.getIv();
        String encryptedData = user.getEncryptedData();

        String strUserInfo = service.getDecryptString(encryptedData,session_key,iv);
        System.out.println("userInfo = " + strUserInfo);
        WXUserDTO wxUserDTO = JSONUtil.getObjectFromJson(strUserInfo, WXUserDTO.class);


        User dbUser = service.getUserInfoByWxOpenId(wx_openid);
        int userStatus = service.getUserStatus(dbUser);

        if(userStatus == 0) {
            copyUserInfo(wxUserDTO,user);
            return service.signup(user);
        }

        if(userStatus == 1) {
            return service.login(dbUser);
        }

        throw new UserException("用户登录失败，用户状态异常");
    }

    private void copyUserInfo(WXUserDTO wxUserDTO, User user){
        String openId = wxUserDTO.getOpenId();
        String nickName = wxUserDTO.getNickName();
        String gender = wxUserDTO.getGender();
        String avatarUrl = wxUserDTO.getAvatarUrl();
        String language = wxUserDTO.getLanguage();
        String city = wxUserDTO.getCity();
        String province = wxUserDTO.getProvince();
        String country = wxUserDTO.getCountry();

        user.setWxopenid(openId);
        user.setUname(nickName);
        user.setUgender(NumberUtils.toInt(gender,0));
        user.setUavatar(avatarUrl);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResultDTO save(User user){
        if(user == null) throw new UserException("更新用户信息失败，用户不能为空");

        String uid = user.getUid();
        if(StringUtils.isBlank(uid)) throw new UserException("更新用户信息失败，用户uid不能为空值");

        boolean flag = service.saveUserInfo(user);
        ResultDTO dto = new ResultDTO();
        if(flag == false) dto.setSuccess(false);
        return dto;
    }

    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public UserDTO info(String uid){
        UserDTO dto = service.getUserInfo(uid);
        return dto;
    }
}

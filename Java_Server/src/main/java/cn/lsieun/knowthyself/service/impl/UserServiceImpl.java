package cn.lsieun.knowthyself.service.impl;

import cn.lsieun.knowthyself.dao.UserDao;
import cn.lsieun.knowthyself.dto.UserDTO;
import cn.lsieun.knowthyself.dto.WXUserDTO;
import cn.lsieun.knowthyself.dto.WeiXinDTO;
import cn.lsieun.knowthyself.entity.User;
import cn.lsieun.knowthyself.exception.UserException;
import cn.lsieun.knowthyself.service.UserService;
import cn.lsieun.knowthyself.util.constant.StringContants;
import cn.lsieun.knowthyself.util.json.JSONUtil;
import cn.lsieun.knowthyself.util.snowflake.SnowflakeIdWorker;
import cn.lsieun.knowthyself.util.weixin.AES;
import cn.lsieun.knowthyself.util.weixin.WeiXinNetworkUtil;
import cn.lsieun.knowthyself.util.weixin.WxPKCS7Encoder;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(value = "userService")
public class UserServiceImpl implements UserService {

    @Value("${wx.appid}")
    private String appid;
    @Value("${wx.appSecret}")
    private String secret;

    @Autowired
    private SnowflakeIdWorker snowflakeIdWorker;

    @Autowired
    private UserDao dao;

    // region 查询：获取用户信息、判断用户状态
    @Override
    public User getUserInfoByWxOpenId(String wx_openid) {
        Map<String,String> queryMap = new HashMap<String,String>();
        queryMap.put("wxopenid",wx_openid);
        queryMap.put("firstResult", StringContants.NUM_ZERO);
        queryMap.put("maxResult",StringContants.NUM_ONE);
        List<User> list = dao.getList(queryMap);
        if(list == null || list.size() < 1) return null;
        return list.get(0);
    }

    @Override
    public int getUserStatus(User user) {
        if(user == null) return 0;

        if(user.getIsValid() == 1) return 1;
        return 2;
    }

    @Override
    public UserDTO getUserInfo(String uid) {
        if(StringUtils.isBlank(uid)) throw new UserException("获取用户信息失败，用户uid不能为空值");
        User user = dao.getById(uid);
        if(user == null) throw new UserException("获取用户信息失败，不存在该用户。");

        UserDTO dto = entity2DTO(user);
        return dto;
    }
    // endregion

    // region 查询和写入：用户登录或注册
    @Override
    public UserDTO login(User user) {
        return entity2DTO(user);
    }

    @Override
    public UserDTO signup(User user) {
        long sid = snowflakeIdWorker.nextId();
        String uid = String.valueOf(sid);
        user.setUid(uid);
        user.setUname("新用户");
        user.setUgender(0);
        user.setUavatar("");

        int effectedNum = dao.insert(user);
        if(effectedNum != 1) throw new UserException("注册失败，数据库操作发生错误");

        return entity2DTO(user);
    }
    // endregion

    // region 写入：保存用户信息
    @Override
    public boolean saveUserInfo(User user) {
        user.setLastEditTime(new Date());
        int effectedNum = dao.update(user);
        if(effectedNum != 1) throw new UserException("更新用户信息失败，数据库操作发生错误");
        return true;
    }
    // endregion

    // region 微信功能
    @Override
    public WeiXinDTO getWxUserInfo(String js_code) {
        String content = WeiXinNetworkUtil.getWeiXinUserInfo(appid, secret, js_code);
        WeiXinDTO dto = WeiXinNetworkUtil.str2DTO(content);
        return dto;
    }

    @Override
    public String getDecryptString(String encryptedData, String session_key, String iv) {
        String result = "";
        try {
            AES aes = new AES();
            byte[] resultByte = aes.decrypt(Base64.decodeBase64(encryptedData), Base64.decodeBase64(session_key), Base64.decodeBase64(iv));
            if(null != resultByte && resultByte.length > 0){
                result = new String(WxPKCS7Encoder.decode(resultByte));


            }
        } catch (Exception e) {
            result = "";
            e.printStackTrace();
        }
        return result;
    }
    // endregion

    // region 工具
    @Override
    public UserDTO entity2DTO(User user) {
        UserDTO dto = new UserDTO();
        try {
            PropertyUtils.copyProperties(dto,user);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new UserException("entity2DTO，用户信息转换过程出错。");
        }
        return dto;
    }
    // endregion
}

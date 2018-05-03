package cn.lsieun.knowthyself.service;

import cn.lsieun.knowthyself.dto.UserDTO;
import cn.lsieun.knowthyself.dto.WeiXinDTO;
import cn.lsieun.knowthyself.entity.User;

import java.util.Map;

public interface UserService {
    // region 查询：获取用户信息、判断用户状态
    /**
     * 作用：通过微信openid获取用户信息
     * @param wx_openid 微信ID
     * @return 用户信息
     */
    User getUserInfoByWxOpenId(String wx_openid);

    /**
     * 获取用户的状态信息
     * @param user 用户信息
     * @return 用户当前状态 （0-未注册，1-正常注册，2-异常状态【例如正常注册后被无效掉】）
     */
    int getUserStatus(User user);

    /**
     * 查询用户的信息
     * @param uid 用户ID
     * @return 用户信息
     */
    UserDTO getUserInfo(String uid);
    // endregion

    // region 查询和写入：用户登录或注册
    /**
     * 用户登录
     * @param user 用户信息
     * @return 主要返回用户ID
     */
    UserDTO login(User user);

    /**
     * 用户注册
     * @param user 用户信息
     * @return 主要返回用户ID
     */
    UserDTO signup(User user);
    // endregion

    // region 写入：保存用户信息
    /**
     * 更新（修改）用户的信息
     * 触发时机：用户注册后，修改自己的信息时，调用此方法。
     * @param user 用户信息
     * @return 是否成功
     */
    boolean saveUserInfo(User user);
    // endregion

    // region 微信功能
    /**
     * 通过微信用户的js_code，获取微信用户的openid
     * @param js_code 小程序端传过来的值（在小程序端调用wx.login得到）
     * @return 返回微信用户的信息，包括openid、session_key。
     */
    WeiXinDTO getWxUserInfo(String js_code);

    String getDecryptString(String encryptedData,String session_key, String iv);
    // endregion

    // region 工具
    /**
     * 将Entity转换成为DTO
     * @param user 用户信息（用于数据库交互的实体类）
     * @return 用户信息（用于与外界进行数据传输）
     */
    UserDTO entity2DTO(User user);
    // endregion
}

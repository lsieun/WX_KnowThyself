package cn.lsieun.knowthyself.util.redis;

public class RedisKeys {
    private static final String WX_USER_SESSION_KEY = "wx:userid:sessionkey:hash";

    public static String getWxUserSessionKey(){
        return WX_USER_SESSION_KEY;
    }
}

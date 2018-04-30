package cn.lsieun.knowthyself.util.weixin;

import cn.lsieun.knowthyself.dto.WeiXinDTO;
import cn.lsieun.knowthyself.util.json.JSONUtil;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING) // 按方法名大小升序执行
public class WeiXinNetworkUtilTest {

    @Value("${wx.appid}")
    private String appid;
    @Value("${wx.appSecret}")
    private String secret;

    @Test
    public void testGetContent() throws IOException {
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid=APPID&secret=SECRET&js_code=JSCODE&grant_type=authorization_code";
        String content = WeiXinNetworkUtil.getContent(url);
        System.out.println("content = " + content);
    }

    @Test
    public void testGetWeiXinUserInfo() throws IOException {
        String code = "021RxeYl1uTiEl0MIp0m1AkZXl1RxeYx";
        String content = WeiXinNetworkUtil.getWeiXinUserInfo(appid, secret, code);
        //content = {"session_key":"MdMW6xQghVHLqgU67od2Ig==","openid":"oI1q05PsCLBqr3CnZcjKuA6d1hpk"}
        //{"errcode":40163,"errmsg":"code been used, hints: [ req_id: yn930a0760th41 ]"}
        System.out.println("content = " + content);
        WeiXinDTO dto = JSONUtil.getObjectFromJson(content, WeiXinDTO.class);
        System.out.println(dto.getErrmsg());
    }
}
package cn.lsieun.knowthyself.service;

import cn.lsieun.knowthyself.dto.UserDTO;
import cn.lsieun.knowthyself.entity.User;
import cn.lsieun.knowthyself.util.json.JSONUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;


@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING) // 按方法名大小升序执行
public class UserServiceTest {

    private static ObjectWriter objectWriter;

    @Autowired
    private UserService service;

    @Before
    public void setUp(){
        ObjectMapper mapper = new ObjectMapper();
        objectWriter = mapper.writerWithDefaultPrettyPrinter();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testASignin() throws Exception {
        User user = new User();
        user.setWxopenid("wxopenid" + System.currentTimeMillis());
        user.setUname("uname" + System.currentTimeMillis());

        user.setUgender((int)(Math.random() * 3));
        user.setUavatar("uavatar");
        UserDTO dto = service.signup(user);

        String json = objectWriter.writeValueAsString(dto);
        System.out.println(json);
    }

    @Test
    public void testBSaveUserInfo(){
        User user = new User();
        user.setUid("439430904427839488");
        user.setUname("天下无猜");

        boolean flag = service.saveUserInfo(user);
        System.out.println("flag = " + flag);
    }

    @Test
    public void testCGetUserInfo() throws JsonProcessingException {
        String uid = "439430904427839488";
        UserDTO dto = service.getUserInfo(uid);
        String json = objectWriter.writeValueAsString(dto);
        System.out.println(json);
    }

    @Test
    public void testGetUserInfoByWxOpenId(){
        String wx_openid = "wxopenid1524810091840";
        User user = service.getUserInfoByWxOpenId(wx_openid);
        String json = JSONUtil.getJsonString(user);
        System.out.println("user = " + json);
    }

}
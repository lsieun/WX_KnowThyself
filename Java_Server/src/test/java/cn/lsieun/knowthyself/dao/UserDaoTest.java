package cn.lsieun.knowthyself.dao;

import cn.lsieun.knowthyself.entity.User;
import cn.lsieun.knowthyself.util.snowflake.SnowflakeIdWorker;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING) // 按方法名大小升序执行
public class UserDaoTest {

    @Value("${snowflake.dataCenterId}")
    private static long dataCenterId;
    @Value("${snowflake.dataCenterId}")
    private static long workerId;

    private static long snowflakeId;
    private static SnowflakeIdWorker snowflakeIdWorker;
    private static ObjectWriter objectWriter;

    private static Map<String,String> queryMap;
    private static String testString = "用户名测试";

    @Autowired
    private UserDao dao;

    @BeforeClass
    public static void beforeClass(){
        snowflakeIdWorker = new SnowflakeIdWorker(workerId, dataCenterId);
        snowflakeId = snowflakeIdWorker.nextId();
        ObjectMapper mapper = new ObjectMapper();
        objectWriter = mapper.writerWithDefaultPrettyPrinter();
        queryMap = new HashMap<String,String>();
    }

    @AfterClass
    public static void afterClass(){
        System.out.println("snowflakeId = " + snowflakeId);
    }

    @Before
    public void setUp(){
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testAInsert(){
        String uid = String.valueOf(snowflakeId);
        User entity = new User();
        entity.setUid(uid);
        entity.setWxopenid("wxopenid" + snowflakeId);
        entity.setUname("uname");
        entity.setUgender(0);
        entity.setUavatar("uavatar");
        entity.setIsValid(1);
        int effectedNum = dao.insert(entity);
        assertEquals(1,effectedNum);
    }

    @Test
    public void testBUpdate(){
        String uid = String.valueOf(snowflakeId);
        User entity = new User();
        entity.setUid(uid);
        entity.setUname(testString);
        entity.setLastEditTime(new Date());
        int effectedNum = dao.update(entity);
        assertEquals(1,effectedNum);
    }

    @Test
    public void testCGetById() throws JsonProcessingException {
        String uid = String.valueOf(snowflakeId);
        User entity = dao.getById(uid);

        assertEquals(testString, entity.getUname());

        String json = objectWriter.writeValueAsString(entity);
        System.out.println(json);
    }

    @Test
    public void testDGetList() throws JsonProcessingException {
        String uid = String.valueOf(snowflakeId);
        queryMap.put("uid",uid);
        queryMap.put("isValid","1");
        queryMap.put("firstResult","0");
        queryMap.put("maxResult","10");
        List<User> list = dao.getList(queryMap);

        assertEquals(1,list.size());
        String json = objectWriter.writeValueAsString(list);
        System.out.println(json);
    }

    @Test
    public void testEGetCount(){
        String uid = String.valueOf(snowflakeId);
        queryMap.put("uid",uid);
        queryMap.put("isValid","1");
        queryMap.put("firstResult","0");
        queryMap.put("maxResult","10");
        int count = dao.getCount(queryMap);
        assertEquals(1, count);
    }

    @Test
    @Ignore
    public void testFDelete(){
        String uid = String.valueOf(snowflakeId);
        int effectedNum = dao.delete(uid);
        assertEquals(1,effectedNum);
    }
}
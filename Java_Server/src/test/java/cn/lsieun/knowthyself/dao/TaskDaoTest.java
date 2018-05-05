package cn.lsieun.knowthyself.dao;

import cn.lsieun.knowthyself.entity.Task;
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

import java.util.*;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;


@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING) // 按方法名大小升序执行
public class TaskDaoTest{

    @Value("${snowflake.dataCenterId}")
    private static long dataCenterId;
    @Value("${snowflake.dataCenterId}")
    private static long workerId;

    private static long snowflakeId;
    private static SnowflakeIdWorker snowflakeIdWorker;
    private static ObjectWriter objectWriter;

    private static Map<String,String> queryMap;
    private static String testString = "XXXX测试";

    @Autowired
    private TaskDao dao;

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
    public void testAInsert() {
        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH,1);

        String uid = String.valueOf(snowflakeId);
        Task entity = new Task();
        entity.setUid(uid);
        entity.setUserid("userid");
        entity.setName("name");
        entity.setPriority(0);
        entity.setStartTime(now);
        entity.setEndTime(calendar.getTime());
        entity.setStatus(0);

        int effectedNum = dao.insert(entity);
        assertEquals(1,effectedNum);
    }

    @Test
    public void testBUpdate() {
        //String uid = String.valueOf(snowflakeId);
        String uid = "441578125327859712";
        Task entity = new Task();
        entity.setUid(uid);
        //entity.setUserid("userid");
        entity.setName("好名字");
//        entity.setPriority("priority");
//        entity.setStartTime("startTime");
//        entity.setEndTime("endTime");
//        entity.setStatus("status");
//        entity.setCreateTime("createTime");
//        entity.setLastEditTime("lastEditTime");
//        entity.setIsValid("isValid");

        int effectedNum = dao.update(entity);
        assertEquals(1,effectedNum);
    }

    @Test
    public void testCGetById() throws JsonProcessingException {
        //String uid = String.valueOf(snowflakeId);
        String uid = "441578125327859712";
        Task entity = dao.getById(uid);

//        assertEquals(testString, entity.getUname());

        String json = objectWriter.writeValueAsString(entity);
        System.out.println(json);
    }

    @Test
    public void testGetDList() throws JsonProcessingException {
        //String uid = String.valueOf(snowflakeId);
        String uid = "441578125327859712";
        queryMap.put("uid",uid);
        queryMap.put("isValid","1");
        queryMap.put("firstResult","0");
        queryMap.put("maxResult","10");

        List<Task> list = dao.getList(queryMap);

        assertEquals(1,list.size());
        String json = objectWriter.writeValueAsString(list);
        System.out.println(json);
    }

    @Test
    public void testEGetCount(){
        //String uid = String.valueOf(snowflakeId);
        String uid = "441578125327859712";
        queryMap.put("uid",uid);
        queryMap.put("isValid","1");
        queryMap.put("firstResult","0");
        queryMap.put("maxResult","10");
        int count = dao.getCount(queryMap);
        assertEquals(1, count);
    }

    @Test
    public void testFDelete(){
        //String uid = String.valueOf(snowflakeId);
        String uid = "441591894724575232";
        int effectedNum = dao.delete(uid);
        assertEquals(1,effectedNum);
    }
}

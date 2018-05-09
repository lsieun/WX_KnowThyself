package cn.lsieun.knowthyself.dao;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.lsieun.knowthyself.entity.TimelineType;
import cn.lsieun.knowthyself.util.json.JSONUtil;
import cn.lsieun.knowthyself.util.snowflake.SnowflakeIdWorker;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING) // 按方法名大小升序执行
public class TimelineTypeDaoTest {
    private static long snowflakeId;
    @Autowired
    private SnowflakeIdWorker snowflakeIdWorker;

    @Autowired
    private TimelineTypeDao dao;

    private static Map<String,String> queryMap;

    @BeforeClass
    public static void beforeClass(){
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
        snowflakeId = snowflakeIdWorker.nextId();
        String uid = String.valueOf(snowflakeId);

        TimelineType entity = new TimelineType();
        entity.setUid(uid);
        entity.setUserid("userid");
        entity.setName("name");

        int effectedNum = dao.insert(entity);
        assertEquals(1,effectedNum);
    }

    @Test
    public void testBUpdate() {
        String uid = "443851600729997312";
        TimelineType entity = new TimelineType();
        entity.setUid(uid);
        entity.setName("alalala");

        int effectedNum = dao.update(entity);
        assertEquals(1,effectedNum);
    }


    @Test
    public void testCGetById()  {
        String uid = "443851600729997312";
        TimelineType entity = dao.getById(uid);


        System.out.println(JSONUtil.getJsonString(entity));
    }

    @Test
    public void testGetDList()  {
        String uid = "443851600729997312";
        queryMap.put("uid",uid);
        queryMap.put("isValid","1");
        queryMap.put("firstResult","0");
        queryMap.put("maxResult","10");

        List<TimelineType> list = dao.getList(queryMap);

        assertEquals(1,list.size());
        System.out.println(JSONUtil.getJsonString(list));
    }

    @Test
    public void testEGetCount(){
        String uid = "443851600729997312";
        queryMap.put("uid",uid);
        queryMap.put("isValid","1");
        queryMap.put("firstResult","0");
        queryMap.put("maxResult","10");
        int count = dao.getCount(queryMap);
        assertEquals(1, count);
    }

    @Test
    public void testFDelete(){
        String uid = "443851600729997312";
        int effectedNum = dao.delete(uid);
        assertEquals(1,effectedNum);
    }
}
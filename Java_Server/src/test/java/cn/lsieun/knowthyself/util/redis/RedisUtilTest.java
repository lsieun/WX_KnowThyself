package cn.lsieun.knowthyself.util.redis;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING) // 按方法名大小升序执行
public class RedisUtilTest {
    @Autowired
    private RedisUtil redisUtil;

    @Test
    public void testSet(){
        boolean bool = redisUtil.set("foo", "Hello World");
        System.out.println("bool = " + bool);
    }

    @Test
    public void testGet(){
        Object foo = redisUtil.get("foo");
        System.out.println(foo);
    }
}
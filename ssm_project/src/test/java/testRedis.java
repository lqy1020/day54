import com.lqy.config.SpringMybatis;
import com.lqy.entity.SysOffice;
import com.lqy.service.SysOfficeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.ClusterOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.clients.jedis.Jedis;

import java.util.List;

/**
 * @auth lqy
 * @date 2020/7/28
 * @Description
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringMybatis.class)
public class testRedis {

    @Autowired
    RedisConnectionFactory connectionFactory;

    @Autowired
    RedisTemplate<Object,Object> redisTemplate;

    @Autowired
    SysOfficeService service;

    @Test
    public void getConnectionFactory(){
        RedisConnection connection = connectionFactory.getConnection();
//        System.out.println(connection.getNativeConnection());
        Jedis jedis = (Jedis) connection.getNativeConnection();
        System.out.println(jedis.get("name"));
    }

    @Test
    public void getRedisTemplate(){
//        ClusterOperations<Object, Object> ops = redisTemplate.opsForCluster();
        ValueOperations<Object, Object> ops = redisTemplate.opsForValue();
        ops.set("redisTemplate1","hello redisTemplate1");
        System.out.println(ops.get("redisTemplate1"));
    }

    @Test
    public void testSysOffice(){
        SysOffice sysOffice = new SysOffice();
        sysOffice.setDelFlag("0");
        List<SysOffice> offices = service.select(sysOffice);
        for (SysOffice office : offices) {
            System.out.println(office);
        }
        System.out.println("====================================");
        offices=service.select(sysOffice);
        System.out.println(offices);

        System.out.println("===============================");
        sysOffice.setAreaId(2L);
        offices=service.select(sysOffice);
        System.out.println(offices);
    }

    @Test
    public void testSelectAll(){
        System.out.println(service.selectAll());
        System.out.println(service.selectAll());
    }


    @Test
    public void testPut(){
        SysOffice sysOffice = service.selectByPrimaryKey(2);
        sysOffice=service.selectByPrimaryKey(2);
        sysOffice.setPhone("121212121212");
        SysOffice sysOffice2 = service.updateByPrimaryKeySelectiveCache(sysOffice);

//        SysOffice sysOffice = service.selectByPrimaryKey(2);
//        sysOffice = service.selectByPrimaryKey(2);
//        sysOffice.setPhone("13800138001");
//        SysOffice sysOffice1 = service.updateByPrimaryKeySelectiveCache(sysOffice);
    }

    @Test
    public void testEvict(){

        SysOffice sysOffice=service.selectByPrimaryKey(2);
        sysOffice=service.selectByPrimaryKey(2);
        sysOffice.setPhone("121212121212");
        service.updateByPrimaryKeySelective(sysOffice);

    }
}

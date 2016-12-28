import com.kris.rest.component.JedisClient;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.util.HashSet;
import java.util.Set;

/**
 * 测试集群版使用Jedis
 *
 * @author kris
 * @create 2016-12-28 16:51
 */
public class JedisClusterTest {
    @Test
    public void testJedisCluster(){
        //创建一个JedisCluster对象
        Set<HostAndPort> nodes = new HashSet<>();
        nodes.add(new HostAndPort("192.168.16.118", 7001));
        nodes.add(new HostAndPort("192.168.16.118", 7002));
        nodes.add(new HostAndPort("192.168.16.118", 7003));
        nodes.add(new HostAndPort("192.168.16.118", 7004));
        nodes.add(new HostAndPort("192.168.16.118", 7005));
        nodes.add(new HostAndPort("192.168.16.118", 7006));
        //在nodes中指定每个节点的地址
        //jedisCluster在系统中是单例的
        JedisCluster jedisCluster = new JedisCluster(nodes);
        jedisCluster.set("name", "kris");
        jedisCluster.set("value","100");

        String name = jedisCluster.get("name");
        String value = jedisCluster.get("value");
        System.out.println(name);
        System.out.println(value);

        //系统关闭时关闭jedisCluster
        jedisCluster.close();
    }

    @Test
    public void testJedisClientSpring(){
        //创建一个spring容器
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");
        //从容器从获得JedisClient
        JedisClient jedisClient = applicationContext.getBean(JedisClient.class);
        //jedisClient操作redis
        jedisClient.set("cliet1", "1000");
        String string = jedisClient.get("cliet1");
        System.out.println(string);

    }
}

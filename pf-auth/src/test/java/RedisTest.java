import com.pf.PfAuthApplication;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @ClassName : RedisTest
 * @Description :
 * @Author : wangjie
 * @Date: 2021/8/21-15:18
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PfAuthApplication.class)
public class RedisTest {
    @Autowired
    private RedisTemplate redisTemplate;

//    public static void main(String[] args) {
//        Jackson2JsonRedisSerializer<Object> serializer = new Jackson2JsonRedisSerializer<Object>(Object.class);
//        ObjectMapper mapper = new ObjectMapper();
//        // 反序列化时候遇到不匹配的属性并不抛出异常
//        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//        // 序列化时候遇到空对象不抛出异常
//        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
//        // 强制JSON 空字符串("")转换为null对象值
//        mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
//        // 反序列化的时候如果是无效子类型,不抛出异常
//        mapper.configure(DeserializationFeature.FAIL_ON_INVALID_SUBTYPE, false);
//        // 不使用默认的dateTime进行序列化,
//        mapper.configure(SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS, false);
//        // 使用JSR310提供的序列化类,里面包含了大量的JDK8时间序列化类
//        mapper.registerModule(new JavaTimeModule());
//        // 启用反序列化所需的类型信息,在属性中添加@class
//        serializer.setObjectMapper(mapper);
//
//        UsernamePasswordAuthenticationToken obj = new UsernamePasswordAuthenticationToken(new SecurityUser(), null);
////        PfSession session = new PfSession();
//        
//        String str = new String(serializer.serialize(obj));
//        System.out.println(str);
//        System.out.println(serializer.deserialize(str.getBytes()));
//    }
//    @Test
//    public void test() {
//        MyFindByIndexNameSessionRepository sessionRepository = new MyFindByIndexNameSessionRepository(redisTemplate);
//        PfSession session = new PfSession();
////        sessionRepository.addSessionInfo(session.getId(), session);
////        redisTemplate.boundHashOps("sessionIds").put("eb2658cb-2beb-46e1-9e49-592abb5540e9", new SecurityUser());
////        PfSession a = (PfSession) redisTemplate.opsForHash().get("sessionIds", "c0463fe7-240f-453c-9274-80ffae301655");
////        BoundHashOperations<String, String, PfSession> operations = redisTemplate.boundHashOps("sessionIds");
////        PfSession a = operations.get(session.getId());
////        Object a1 = redisTemplate.boundHashOps("sessionIds").keys();
////        Object a2 = redisTemplate.boundHashOps("sessionIds").entries();
////        Object a3 = redisTemplate.boundHashOps("sessionIds").values();
////        System.out.println(JacksonsUtils.writeValueAsString(a));
////        System.out.println(JacksonsUtils.writeValueAsString(a1));
////        System.out.println(JacksonsUtils.writeValueAsString(a2));
////        System.out.println(JacksonsUtils.writeValueAsString(a3));
////        PfSession session1 = sessionRepository.getSessionInfo(session.getId());
////        System.out.println(JacksonsUtils.writeValueAsString(session1));
//    }
}

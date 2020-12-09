import com.pf.PfSystemApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {PfSystemApplication.class})
public class JustTest {
    @Autowired
    private RedisTemplate redisTemplate;
    @Test
    public void test() throws Exception {
        redisTemplate.opsForValue().set("test", "test111");
    }
}

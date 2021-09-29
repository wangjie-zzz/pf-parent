import com.pf.PfTestApplication;
import com.pf.test.model.entity.TestA;
import com.pf.test.service.ITestAService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

/**
 * @ClassName : Test
 * @Description :
 * @Author : wangjie
 * @Date: 2021/8/13-18:39
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PfTestApplication.class)
public class TestATest {

    @Autowired
    private ITestAService testAService;
    
    @Test
    public void insert() {
        testAService.save(new TestA(UUID.randomUUID().toString(), "1", "", "", ""));
        if("aaa".equals("aaa")) {
            throw new RuntimeException();
        }
        testAService.save(new TestA(UUID.randomUUID().toString(), "2", "", "", ""));
    }
}

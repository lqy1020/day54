
import com.lqy.config.SpringMybatis;
import com.lqy.mapper.TransferMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringMybatis.class)
public class TestTransfer {


    @Autowired
    TransferMapper mapper;

   /* @Autowired
    DetailService service;*/


   @Test
    public void testSelectById(){
       List<Map<String, Object>> list = mapper.selectById(11);
       for (Map<String, Object> map : list) {
           for (String s : map.keySet()) {
               System.out.println(s+"-"+map.get(s));
           }
           System.out.println("----------------------------------");
       }
   }



}


import com.github.pagehelper.PageInfo;
import com.lqy.config.SpringMybatis;
import com.lqy.entity.Demand;
import com.lqy.mapper.DetailMapper;
import com.lqy.service.DemandService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringMybatis.class)
public class TestDetail {


    @Autowired
    DetailMapper mapper;

   @Autowired
   DemandService service;


   @Test
   public void test1(){
       PageInfo<Demand> demandPageInfo = service.selectPage(1, 5);
       System.out.println(demandPageInfo);
   }

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

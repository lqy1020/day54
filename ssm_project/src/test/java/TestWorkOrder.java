import com.github.pagehelper.PageInfo;
import com.lqy.config.SpringMybatis;
import com.lqy.entity.Examine;
import com.lqy.entity.WorkOrder;
import com.lqy.mapper.ExamineMapper;
import com.lqy.mapper.WorkOrderMapper;
import com.lqy.service.ExamineService;
import com.lqy.service.WorkOrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @auth lqy
 * @date 2020/7/16
 * @Description
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringMybatis.class)
public class TestWorkOrder {

   @Autowired
   WorkOrderMapper mapper;

   @Autowired
    WorkOrderService service;

    @Test
    public void test1(){
        HashMap<String, Object> map = new HashMap<>();
        map.put("status","2");
        map.put("officeId",13);
        map.put("startDate","2016-09-01");
        List<WorkOrder> workOrders = mapper.selectPage(map);
        for (WorkOrder workOrder : workOrders) {
            System.out.println(workOrder);
        }
    }
    @Test
    public void test2(){
        HashMap<String, Object> map = new HashMap<>();
        map.put("status","2");
        map.put("officeId",13);
        map.put("startDate","2016-09-01");
        PageInfo<WorkOrder> pageInfo = service.selectPage(1, 5, map);
        System.out.println(pageInfo);
    }

    @Test
    public void test3(){
        Map<String, Object> map = mapper.selectById(11);
        for (String s : map.keySet()) {
            System.out.println(s+"-"+map.get(s));
        }
    }

    @Test
    public void test4(){
        Map<String, Object> map = service.selectById(11);
        for (String s : map.keySet()) {
            System.out.println(s+"-"+map.get(s));
        }
    }


}

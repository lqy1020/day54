import com.github.pagehelper.PageInfo;
import com.lqy.config.SpringMybatis;
import com.lqy.entity.Examine;
import com.lqy.entity.Qualification;
import com.lqy.entity.QualificationCondition;
import com.lqy.mapper.ExamineMapper;
import com.lqy.mapper.QualificationMapper;
import com.lqy.service.ExamineService;
import com.lqy.service.QualificationService;
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
public class TestExamine {

   @Autowired
   ExamineMapper mapper;

   @Autowired
   ExamineService service;

    @Test
    public void test1(){
        Map<String,Object>params=new HashMap<>();
        params.put("type","1");
        params.put("office",56);
        params.put("name","人员");
        PageInfo<Examine> pageInfo = service.selectPage(1, 5, params);
        System.out.println(pageInfo);
    }

}

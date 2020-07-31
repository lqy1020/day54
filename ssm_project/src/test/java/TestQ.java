import com.github.pagehelper.PageInfo;
import com.lqy.config.SpringMybatis;
import com.lqy.entity.AppVersion;
import com.lqy.entity.Demand;
import com.lqy.entity.Qualification;
import com.lqy.entity.QualificationCondition;
import com.lqy.mapper.AppVersionMapper;
import com.lqy.mapper.QualificationMapper;
import com.lqy.service.AppVersionService;
import com.lqy.service.DemandService;
import com.lqy.service.QualificationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @auth lqy
 * @date 2020/7/16
 * @Description
 */
//SpringJUnit4ClassRunner.class
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringMybatis.class)
public class TestQ {

   @Autowired
    QualificationMapper mapper;

   @Autowired
    QualificationService service;

   @Test
    public void test1(){
       QualificationCondition qualificationCondition = new QualificationCondition();
       qualificationCondition.setType("3");
       qualificationCondition.setCheck("2");
       PageInfo<Qualification> pageInfo = service.selectPage(1, 5, qualificationCondition);
           System.out.println(pageInfo);

   }
}

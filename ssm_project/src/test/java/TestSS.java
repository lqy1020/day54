import com.github.pagehelper.PageInfo;
import com.lqy.config.SpringMybatis;
import com.lqy.entity.AppVersion;
import com.lqy.entity.Demand;
import com.lqy.mapper.AppVersionMapper;
import com.lqy.service.AppVersionService;
import com.lqy.service.DemandService;
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
public class TestSS {

    @Autowired
    AppVersionMapper mapper;

    @Autowired
    AppVersionService service;

    @Autowired
    DemandService demandService;

    @Test
    public void test5(){
        List<Demand> demands = demandService.selectAll();
        for (Demand demand:demands){
            System.out.println(demand);
        }
    }
    @Test
    public void test2(){
        AppVersion appVersion = new AppVersion();
        appVersion.setPlatform(0);
        List<AppVersion> list = mapper.select(appVersion);
        for (AppVersion appVersion1:list){
            System.out.println(appVersion);
        }
    }

    @Test
    public void test3(){
        PageInfo<AppVersion> pageInfo = service.selectPage(1, 5);
        System.out.println(pageInfo);
    }
}

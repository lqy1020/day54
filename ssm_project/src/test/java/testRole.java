
import com.github.pagehelper.PageInfo;
import com.lqy.config.SpringMybatis;
import com.lqy.entity.SysRole  ;

import com.lqy.mapper.SysRoleMapper;
import com.lqy.service.SysRoleService;
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
 * @date 2020/7/29
 * @Description
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringMybatis.class)
public class testRole {

    @Autowired
    SysRoleMapper mapper;

    @Autowired
    SysRoleService service;

    @Test
    public void test1(){
        Map<String,Object> map=new HashMap<>();
        map.put("dataScope",1);
        map.put("oid",56);
        map.put("name","员");
//        List<SysRole  > sysAreas = mapper.selectPage(map);
//        System.out.println(sysAreas);
        PageInfo<SysRole> pageInfo = service.selectPage(1, 5, map);
        System.out.println(pageInfo);
    }


}

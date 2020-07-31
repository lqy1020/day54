import com.alibaba.excel.EasyExcel;
import com.lqy.config.SpringMybatis;
import com.lqy.entity.SysArea;
import com.lqy.listener.SysAreaListener;
import com.lqy.mapper.SysAreaMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @auth lqy
 * @date 2020/7/29
 * @Description
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringMybatis.class)
public class testArea {

    @Autowired
    SysAreaMapper mapper;

    @Test
    public void test1(){
        List<SysArea> sysAreas = mapper.selectByName("区");
        System.out.println(sysAreas);
    }

    @Test
    public void test2(){
        SysArea sysArea = new SysArea();
        sysArea.setOldParentIds("0,1,");
        sysArea.setParentIds("0,1,2,");
        sysArea.setId(3L);
//        mapper.updateParentIds(2l);
        mapper.updateParentIds(sysArea);
    }

    @Test
    public void test3(){
        List<SysArea> sysAreas = mapper.selectAll();
        EasyExcel.write("D:\\work\\ssm_project\\day69-区域管理\\3.其他\\area.xls",SysArea.class).sheet().doWrite(sysAreas);
    }

    @Test
    public void test4(){
        EasyExcel.read("D:\\work\\ssm_project\\day69-区域管理\\3.其他\\area.xls",SysArea.class,new SysAreaListener(mapper)).sheet().doRead();
    }


}

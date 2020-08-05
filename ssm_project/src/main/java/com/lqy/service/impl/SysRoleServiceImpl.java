package com.lqy.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lqy.entity.SysOffice;
import com.lqy.entity.SysResource;
import com.lqy.entity.SysRole  ;
import com.lqy.mapper.DetailMapper;
import com.lqy.mapper.SysRoleMapper;
import com.lqy.mapper.TransferMapper;
import com.lqy.service.SysRoleService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @auth lqy
 * @date 2020/7/16
 * @Description
 */
@Service
@Transactional
public class SysRoleServiceImpl extends BaseSerciveImpl<SysRole  > implements SysRoleService {

    @Autowired
    SysRoleMapper sysRoleMapper;



    @Override
    public  PageInfo<SysRole> selectPage(int pageNum, int pageSize, Map<String, Object> params){

        PageHelper.startPage(pageNum,pageSize);
        List<SysRole  > list = sysRoleMapper.selectPage(params);
        /*
        * 方法1 前端页面添加改变触发事件 @change="_selectPage(1,pageInfo.pageSize)"重新查询
        * 方法2后台处理前端页面条件查询出现的bug，解决条件点击下一页出现的bug
        * */
        Page page=(Page) list;
        if (page.getPageNum()>page.getPages()){
            PageHelper.startPage(1,pageSize);
            list=sysRoleMapper.selectPage(params);
        }

        PageInfo<SysRole  > pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @Override
    public int deleteBatch(@Param("rid") long rid, @Param("ids") long[] ids){
        return sysRoleMapper.deleteBatch(rid,ids);
    }

    @Override
    public int insertBatch(@Param("rid") long rid, @Param("cids") List<Long> cids){
        return sysRoleMapper.insertBatch(rid,cids);
    }

    @Override
    public int updateByPrimaryKeySelective(SysRole sysRole){
        int result=0;
        sysRoleMapper.updateByPrimaryKeySelective(sysRole);
        result++;
        List<SysResource> resources=sysRole.getResources();
        List<SysResource> oldResources=sysRole.getOldResources();
         List<SysOffice> offices=sysRole.getOffices();//角色已授权公司
         List<SysOffice> oldOffices=sysRole.getOldOffices();
        if (resources!=null&&oldResources!=null){
            if (resources.size()!=oldResources.size()||(resources.size()==oldResources.size()&&!resources.containsAll(oldResources))){
                sysRoleMapper.deleteByRid(sysRole.getId());
                sysRoleMapper.insertRoleResources(sysRole.getId(),resources);
            }
        }
        result++;
        if (offices!=null&&oldOffices!=null){
            if (offices.size()!=oldOffices.size()||(oldOffices.size()==offices.size()&&!offices.containsAll(oldOffices))){
                sysRoleMapper.deleteOfficeByRid(sysRole.getId());
                if (offices.size()>0) {
                    sysRoleMapper.insertRoleOffices(sysRole.getId(), offices);
                }
            }
        }
        else if (offices!=null&&oldOffices==null){
            sysRoleMapper.deleteOfficeByRid(sysRole.getId());
            sysRoleMapper.insertRoleOffices(sysRole.getId(),offices);
        }
        else if (oldOffices!=null&&offices==null){
            sysRoleMapper.deleteOfficeByRid(sysRole.getId());
//            sysRoleMapper.insertRoleOffices(sysRole.getId(),offices);
        }
        result++;
        return result;
    }

}

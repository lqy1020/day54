package com.lqy.service;

import com.github.pagehelper.PageInfo;
import com.lqy.entity.SysRole;
import com.lqy.entity.WorkOrder;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface SysRoleService extends BaseService<SysRole> {

    PageInfo<SysRole> selectPage(int pageNum, int pageSize, Map<String, Object> params);


    int deleteBatch(@Param("rid") long rid, @Param("ids") long[] ids);

    int insertBatch(@Param("rid") long rid, @Param("cids") List<Long> cids);

    int updateByPrimaryKeySelective(SysRole sysRole);
}

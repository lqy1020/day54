package com.lqy.service;


import com.lqy.entity.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysUserService extends  BaseService<SysUser> {


    List<SysUser> selectByRid(long rid);

    List<SysUser> selectNoRole(@Param("oid") long oid, @Param("rid") long rid);
}

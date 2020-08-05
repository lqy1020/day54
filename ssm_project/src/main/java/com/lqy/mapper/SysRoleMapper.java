package com.lqy.mapper;

import com.lqy.entity.SysOffice;
import com.lqy.entity.SysResource;
import com.lqy.entity.SysRole;
import org.apache.ibatis.annotations.*;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface SysRoleMapper extends Mapper<SysRole> {

    @SelectProvider(type = SysRoleSqlProvider.class,method ="selectPage")
     List<SysRole> selectPage(Map<String,Object> params);

    @DeleteProvider(type = SysRoleSqlProvider.class,method="deleteBatch")
    int deleteBatch(@Param("rid") long rid,@Param("ids") long[] ids);

    @InsertProvider(type = SysRoleSqlProvider.class,method = "insertBatch")
    int insertBatch(@Param("rid") long rid, @Param("cids") List<Long> cids);


    /**
     * 删除原已选资源
     */
    @Delete("delete from sys_role_resource where role_id=#{rid}")
    int deleteByRid(long rid);

    @InsertProvider(type=SysRoleSqlProvider.class,method = "insertRoleResources")
    int insertRoleResources(@Param("rid") long rid,@Param("resources") List<SysResource> resources);


    @Delete("delete from sys_role_office where role_id=#{rid}")
    int deleteOfficeByRid(Long rid);

    @InsertProvider(type=SysRoleSqlProvider.class,method = "insertRoleOffices")
    int insertRoleOffices(@Param("rid") long rid,@Param("offices") List<SysOffice> offices);


}
package com.lqy.mapper;

import com.lqy.entity.SysArea;
import org.apache.ibatis.annotations.*;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface SysAreaMapper extends Mapper<SysArea> {

    @Select("select sub.*,parent.name parent_name from sys_area sub,sys_area parent  " +
            "where " +
            "sub.parent_id=parent.id " +
            "and sub.del_flag='0' " +
            "and " +
            "sub.parent_ids like concat('%,',#{id},',%')")
    List<SysArea> selectByPid(Long id);

    @SelectProvider(type = SysAreaSqlProvider.class,method = "selectByName")
    List<SysArea>selectByName(String name);

    @Update("UPDATE sys_area " +
            "SET parent_ids = REPLACE ( parent_ids, #{oldParentIds}, #{parentIds} )," +
            "update_date = NOW( )  " +
            "WHERE " +
            "parent_ids LIKE concat( '%,', #{id}, ',%' )")
    int updateParentIds(SysArea parentArea);

    @Update("UPDATE sys_area  " +
            "SET del_flag = #{delFlag} " +
            "update_date=NOW() " +
            "WHERE " +
            " parent_ids LIKE CONCAT('%,',#{id},',%') " +
            "or id=#{id}")
    int deleteParentIds(SysArea sysArea);



    @InsertProvider(type = SysAreaSqlProvider.class,method = "insertBatch")
    int insertBatch(@Param("areas")List<SysArea>areas);

}
package com.lqy.mapper;

import com.lqy.entity.SysResource;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface SysResourceMapper extends Mapper<SysResource> {

    @Select("SELECT  " +
            "    sr.*   " +
            "FROM  " +
            "    sys_resource sr,  " +
            "    sys_role_resource srr   " +
            "WHERE  " +
            "    srr.role_id = #{rid}   " +
            "    AND srr.resource_id = sr.id   " +
            "    AND sr.del_flag = 0")
    List<SysResource> selectByRid(Long rid);


    @Select("SELECT DISTINCT  " +
            "   sre.*    " +
            "FROM   " +
            "   sys_user_role sur,   " +
            "   sys_role sro,   " +
            "   sys_role_resource srr,   " +
            "   sys_resource sre    " +
            "WHERE   " +
            "   sur.user_id = #{uid}    " +
            "   AND sre.del_flag = 0    " +
            "   AND sro.del_flag = 0    " +
            "   AND sre.type = 0    " +
            "   AND sur.role_id = sro.id    " +
            "   AND sro.id = srr.role_id    " +
            "   AND srr.resource_id = sre.id")
    List<SysResource> selectByUid(Long uid);

    @Select("SELECT    " +
            "   *     " +
            "FROM    " +
            "   sys_resource     " +
            "WHERE    " +
            "   type = 0     " +
            "   AND del_flag = 0     " +
            "   AND url <> ''")
    List<SysResource> selectResource();

}
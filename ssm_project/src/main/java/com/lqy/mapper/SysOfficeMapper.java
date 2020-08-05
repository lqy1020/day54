package com.lqy.mapper;

import com.lqy.entity.SysOffice;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface SysOfficeMapper extends Mapper<SysOffice> {
    
    @Select("SELECT    " +
            "    so.*     " +
            "FROM    " +
            "    sys_role_office sro,    " +
            "    sys_office so     " +
            "WHERE    " +
            "    sro.role_id = #{rid}     " +
            "    AND sro.office_id = so.id     " +
            "    AND so.del_flag = 0")
    List<SysOffice>selectOfficeByRid(Long rid);
    
}
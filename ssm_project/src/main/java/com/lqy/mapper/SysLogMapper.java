package com.lqy.mapper;

import com.lqy.entity.SysLog;
import org.apache.ibatis.annotations.SelectProvider;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface SysLogMapper extends Mapper<SysLog> {

    @SelectProvider(value = SysLogSqlProvider.class,method = "selectPage")
    List<SysLog> selectPage(Map<String,Object> params);




}
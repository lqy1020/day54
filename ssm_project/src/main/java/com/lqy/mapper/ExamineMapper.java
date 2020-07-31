package com.lqy.mapper;

import com.lqy.entity.Examine;
import org.apache.ibatis.annotations.SelectProvider;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface ExamineMapper extends Mapper<Examine> {

    @SelectProvider(type = ExamineSqlProvider.class,method = "selectPage")
    List<Examine> selectPage(Map<String,Object> params);

}
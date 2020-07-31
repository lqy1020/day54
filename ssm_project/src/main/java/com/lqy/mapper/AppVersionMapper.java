package com.lqy.mapper;

import com.lqy.entity.AppVersion;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.JdbcType;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface AppVersionMapper extends Mapper<AppVersion> {

}
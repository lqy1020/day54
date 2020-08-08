package com.lqy.service;

import com.github.pagehelper.PageInfo;
import com.lqy.entity.SysLog;

import java.util.Map;

public interface SysLogService extends BaseService<SysLog> {

    PageInfo<SysLog> selectPage(int pageNum, int pageSize, Map<String, Object> params);

}

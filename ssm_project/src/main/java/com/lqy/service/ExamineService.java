package com.lqy.service;

import com.github.pagehelper.PageInfo;
import com.lqy.entity.Examine;
import java.util.Map;


public interface ExamineService extends BaseService<Examine> {

    PageInfo<Examine> selectPage(int pageNum, int pageSize, Map<String,Object> params);

}

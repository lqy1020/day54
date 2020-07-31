package com.lqy.service;

import com.github.pagehelper.PageInfo;
import com.lqy.entity.Qualification;
import com.lqy.entity.QualificationCondition;


public interface QualificationService extends BaseService<Qualification> {

    PageInfo<Qualification> selectPage(int pageNum, int pageSize, QualificationCondition condition);

    String getPath(long uid);
}

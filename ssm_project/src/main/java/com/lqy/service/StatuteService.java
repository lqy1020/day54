package com.lqy.service;

import com.github.pagehelper.PageInfo;
import com.lqy.entity.Statute;

public interface StatuteService extends BaseService<Statute> {


    PageInfo<Statute> selectPage(int pageNum, int pageSize, Statute statute);
}

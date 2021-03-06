package com.lqy.service;


import com.lqy.entity.SysOffice;
import org.springframework.cache.annotation.CachePut;

import java.util.List;


public interface SysOfficeService extends BaseService<SysOffice> {

    @CachePut(cacheNames = "officeCache",key = "'com.lqy.service.impl.SysOfficeServiceImpl.select:object:'+#office.id")
    SysOffice updateByPrimaryKeySelectiveCache(SysOffice office);

    List<SysOffice> selectOfficeByRid(Long rid);
}

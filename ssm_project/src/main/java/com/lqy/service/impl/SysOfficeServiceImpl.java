package com.lqy.service.impl;


import com.lqy.entity.SysOffice;

import com.lqy.entity.SysResource;
import com.lqy.mapper.SysOfficeMapper;
import com.lqy.service.SysOfficeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @auth lqy
 * @date 2020/7/16
 * @Description
 */
@Service
@Transactional
@CacheConfig(cacheNames = "officeCache")
public class SysOfficeServiceImpl extends BaseSerciveImpl<SysOffice> implements SysOfficeService {

    @Autowired
    SysOfficeMapper sysOfficeMapper;
//    @Autowired
//    RedisTemplate<Object,Object> redisTemplate;
//
//    @Override
//    public List<SysOffice> select(SysOffice sysOffice) {
//        String key="com.lqy.service.impl.SysOfficeServiceImpl.select:SysOffice:"+sysOffice;
//        Object obj = redisTemplate.opsForValue().get(key);
//        List<SysOffice> offices;
//        if (obj!=null){
//            offices= (List<SysOffice>) obj;
//        }else {
//           offices= mapper.select(sysOffice);
//           redisTemplate.opsForValue().set(key,offices);
//        }
//        return offices;
//    }




//    @Cacheable(cacheNames = "officeCache",key = "'com.lqy.service.impl.SysOfficeServiceImpl.select:sysOffice:'+#sysOffice")
    @Cacheable(key = "'com.lqy.service.impl.SysOfficeServiceImpl.select:sysOffice:'+#sysOffice")
    @Override
    public List<SysOffice> select(SysOffice sysOffice) {
        return super.select(sysOffice);
    }


//    @Cacheable(cacheNames = "officeCache",key = "'com.lqy.service.impl.SysOfficeServiceImpl.select:object:'+#o")
    @Cacheable(key = "'com.lqy.service.impl.SysOfficeServiceImpl.select:object:'+#o")
    @Override
    public SysOffice selectByPrimaryKey(Object o) {
        return super.selectByPrimaryKey(o);
    }

//    @CachePut(cacheNames = "officeCache",key = "'com.lqy.service.impl.SysOfficeServiceImpl.select:object:'+#office.id")
    @CachePut(key = "'com.lqy.service.impl.SysOfficeServiceImpl.select:object:'+#office.id")
    @Override
    public SysOffice updateByPrimaryKeySelectiveCache(SysOffice office){
        updateByPrimaryKeySelective(office);
        return office;
    }

//    @CacheEvict(cacheNames = "officeCache",allEntries = true)
    @CacheEvict(allEntries = true)
    @Override
    public int updateByPrimaryKeySelective(SysOffice sysOffice) {
        return super.updateByPrimaryKeySelective(sysOffice);
    }


    @Override
    public List<SysOffice> selectOfficeByRid(Long rid){
        return sysOfficeMapper.selectOfficeByRid(rid);
    }



}

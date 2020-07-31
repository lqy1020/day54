package com.lqy.service.impl;


import com.lqy.entity.SysUser;
import com.lqy.mapper.SysUserMapper;
import com.lqy.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SysUserServiceImpl extends BaseSerciveImpl<SysUser> implements SysUserService {
    //注入的对象其实跟当前的BaseServiceImpl的mapper是同一个对象
    @Autowired
    SysUserMapper sysUserMapper;


}

package com.lqy.service;

import com.lqy.entity.SysResource;
import com.lqy.entity.SysRole;

import java.util.List;

public interface SysResourceService extends BaseService<SysResource> {

    List<SysResource> selectByRid(Long rid);

    List<SysResource> selectByUid(Long uid);

    List<SysResource> selectResource();
}

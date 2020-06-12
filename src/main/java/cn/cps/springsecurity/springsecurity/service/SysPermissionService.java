package cn.cps.springsecurity.springsecurity.service;

import cn.cps.springsecurity.springsecurity.entity.SysPermission;
import cn.cps.springsecurity.springsecurity.mapper.SysPermissionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: Cai Peishen
 * @Date: 2020/6/12 17:05
 * @Description:
 */
@Service
public class SysPermissionService {

    @Autowired
    private SysPermissionMapper permissionMapper;

    /**
     * 获取指定角色所有权限
     */
    public List<SysPermission> listByRoleId(Integer roleId) {
        return permissionMapper.listByRoleId(roleId);
    }

}

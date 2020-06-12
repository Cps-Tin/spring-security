package cn.cps.springsecurity.springsecurity.service;

import cn.cps.springsecurity.springsecurity.entity.SysUserRole;
import cn.cps.springsecurity.springsecurity.mapper.SysUserRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: Cai Peishen
 * @Date: 2020/6/12 11:06
 * @Description:
 */
@Service
public class SysUserRoleService {

    @Autowired
    private SysUserRoleMapper userRoleMapper;

    public List<SysUserRole> listByUserId(Integer userId) {
        return userRoleMapper.listByUserId(userId);
    }

}

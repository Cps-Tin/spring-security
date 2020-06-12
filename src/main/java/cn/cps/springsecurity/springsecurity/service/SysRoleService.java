package cn.cps.springsecurity.springsecurity.service;

import cn.cps.springsecurity.springsecurity.entity.SysRole;
import cn.cps.springsecurity.springsecurity.mapper.SysRoleMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: Cai Peishen
 * @Date: 2020/6/12 11:06
 * @Description:
 */
@Service
public class SysRoleService {

    @Autowired
    private SysRoleMapper roleMapper;

    public SysRole selectById(Integer id){
        return roleMapper.selectById(id);
    }

}

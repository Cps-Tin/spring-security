package cn.cps.springsecurity.springsecurity.service;

import cn.cps.springsecurity.springsecurity.entity.SysUser;
import cn.cps.springsecurity.springsecurity.mapper.SysUserMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: Cai Peishen
 * @Date: 2020/6/12 11:05
 * @Description:
 */
@Service
public class SysUserService {

    @Autowired
    private SysUserMapper userMapper;

    public SysUser selectById(Integer id) {
        return userMapper.selectById(id);
    }

    public SysUser selectByName(String name) {
        return userMapper.selectByName(name);
    }

}

package cn.cps.springsecurity.springsecurity.mapper;

import cn.cps.springsecurity.springsecurity.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @Author: Cai Peishen
 * @Date: 2020/6/12 11:03
 * @Description:
 */
@Mapper
public interface SysUserMapper {
    @Select("SELECT * FROM sys_user WHERE id = #{id}")
    SysUser selectById(Integer id);

    @Select("SELECT * FROM sys_user WHERE name = #{name}")
    SysUser selectByName(String name);
}

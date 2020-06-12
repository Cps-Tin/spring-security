package cn.cps.springsecurity.springsecurity.mapper;

import cn.cps.springsecurity.springsecurity.entity.SysUserRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author: Cai Peishen
 * @Date: 2020/6/12 11:04
 * @Description:
 */
@Mapper
public interface SysUserRoleMapper {

    @Select("SELECT * FROM sys_user_role WHERE user_id = #{userId}")
    List<SysUserRole> listByUserId(Integer userId);

}

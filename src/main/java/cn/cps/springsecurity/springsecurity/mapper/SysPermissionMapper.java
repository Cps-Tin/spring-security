package cn.cps.springsecurity.springsecurity.mapper;

import cn.cps.springsecurity.springsecurity.entity.SysPermission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author: Cai Peishen
 * @Date: 2020/6/12 17:04
 * @Description:
 */
@Mapper
public interface SysPermissionMapper {

    @Select("SELECT * FROM sys_permission WHERE role_id=#{roleId}")
    List<SysPermission> listByRoleId(Integer roleId);

}

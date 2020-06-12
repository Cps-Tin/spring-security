package cn.cps.springsecurity.springsecurity.mapper;

import cn.cps.springsecurity.springsecurity.entity.SysRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @Author: Cai Peishen
 * @Date: 2020/6/12 11:04
 * @Description:
 */
@Mapper
public interface SysRoleMapper {

    @Select("SELECT * FROM sys_role WHERE id = #{id}")
    SysRole selectById(Integer id);

}

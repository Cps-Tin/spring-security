package cn.cps.springsecurity.springsecurity.security.sms;

import cn.cps.springsecurity.springsecurity.entity.SysRole;
import cn.cps.springsecurity.springsecurity.entity.SysUser;
import cn.cps.springsecurity.springsecurity.entity.SysUserRole;
import cn.cps.springsecurity.springsecurity.service.SysRoleService;
import cn.cps.springsecurity.springsecurity.service.SysUserRoleService;
import cn.cps.springsecurity.springsecurity.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @Author: Cai Peishen
 * @Date: 2020/6/12 11:08
 * @Description: 短信登录认证处理
 */
@Service
public class SmsCodeUserDetailsService implements UserDetailsService {

    @Autowired
    private SysUserService userService;

    @Autowired
    private SysRoleService roleService;

    @Autowired
    private SysUserRoleService userRoleService;

    @Override
    public UserDetails loadUserByUsername(String mobile) throws UsernameNotFoundException {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        // 从数据库中取出用户信息
        SysUser user = userService.selectByMobile(mobile);

        // 判断用户是否存在
        if(user == null) {
            throw new UsernameNotFoundException("该手机号不存在");
        }

        // 添加权限
        List<SysUserRole> userRoles = userRoleService.listByUserId(user.getId());
        for (SysUserRole userRole : userRoles) {
            SysRole role = roleService.selectById(userRole.getRoleId());
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }

        // 返回UserDetails实现类
        return new User(user.getMobile(), user.getPassword(), authorities);
    }


}


package cn.cps.springsecurity.springsecurity.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * @Author: Cai Peishen
 * @Date: 2020/6/12 17:03
 * @Description: 权限实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysPermission implements Serializable {
    static final long serialVersionUID = 1L;

    private Integer id;

    private String url;

    private Integer roleId;

    private String permission;

    private List permissions;

    // 省略除permissions外的getter/setter

    public List getPermissions() {
        return Arrays.asList(this.permission.trim().split(","));
    }

    public void setPermissions(List permissions) {
        this.permissions = permissions;
    }
}
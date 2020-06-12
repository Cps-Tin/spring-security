package cn.cps.springsecurity.springsecurity.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: Cai Peishen
 * @Date: 2020/6/12 11:03
 * @Description: 用户角色实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysUserRole implements Serializable {

    static final long serialVersionUID = 1L;

    private Integer userId;

    private Integer roleId;

}

package cn.cps.springsecurity.springsecurity.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: Cai Peishen
 * @Date: 2020/6/12 11:01
 * @Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysUser implements Serializable {

    static final long serialVersionUID = 1L;

    private Integer id;

    private String name;

    private String password;

}

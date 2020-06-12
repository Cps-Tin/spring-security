package cn.cps.springsecurity.springsecurity.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: Cai Peishen
 * @Date: 2020/6/12 11:02
 * @Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysRole implements Serializable {

    static final long serialVersionUID = 1L;

    private Integer id;

    private String name;

}

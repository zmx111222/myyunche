/**
 * @author: ZhongMingxiao
 * @create: 2018-08-05 21:28
 * @description: 角色
 **/
package com.myyunche.zmx.domain.entity;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class Role
{
    private Integer rid;
    private String rname;
    private Set<User> users=new HashSet<User>();
    private Set<Module> modules=new HashSet<Module>();
}

/**
 * @author: ZhongMingxiao
 * @create: 2018-08-05 21:27
 * @description: 用户
 **/
package com.myyunche.zmx.domain.entity;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class User
{
    private Integer uid;
    private String username;
    private String password;
    private Set<Role> roles=new HashSet<Role>();
}

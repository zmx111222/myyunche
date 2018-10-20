/**
 * @author: ZhongMingxiao
 * @create: 2018-08-05 21:28
 * @description: 模块
 **/
package com.myyunche.zmx.domain.entity;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class Module
{
    private Integer mid;
    private String mname;
    private Set<Role> roles=new HashSet<Role>();
}

/**
 * @author: ZhongMingxiao
 * @create: 2018-08-05 21:43
 * @description: 用户service
 **/
package com.myyunche.zmx.service;

import com.myyunche.zmx.domain.entity.User;

public interface UserService
{
    public User findUserByUserName(String username);
}

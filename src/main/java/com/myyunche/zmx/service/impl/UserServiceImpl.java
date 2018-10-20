/**
 * @author: ZhongMingxiao
 * @create: 2018-08-05 21:44
 * @description: 用户serviceImpl
 **/
package com.myyunche.zmx.service.impl;

import com.myyunche.zmx.domain.entity.User;
import com.myyunche.zmx.mapper.UserMapper;
import com.myyunche.zmx.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService
{
    @Autowired
    private UserMapper userMapper;

    @Override
    public User findUserByUserName(String username) {
        return userMapper.findByUserName(username);
    }
}

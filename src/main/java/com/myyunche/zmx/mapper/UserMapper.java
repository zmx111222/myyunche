package com.myyunche.zmx.mapper;

import com.myyunche.zmx.domain.entity.Role;
import com.myyunche.zmx.domain.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;


@Mapper
public interface UserMapper
{
   /* @Select("select * from user where username='zmx'")*/
     User findByUserName(String username);

    List<User> selectUser();

    @Select("SELECT role.* FROM user,user_role,role WHERE `user`.uid = user_role.uid AND user_role.rid = role.rid AND `user`.username='zmx'")
   List<Role> findRoleByUserName();
}

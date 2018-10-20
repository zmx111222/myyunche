package com.myyunche.zmx.domain.mapper;

import com.yunche.loan.domain.entity.UserDO;

public interface UserDOMapper {
    int deleteByPrimaryKey(Integer uid);

    int insert(UserDO record);

    int insertSelective(UserDO record);

    UserDO selectByPrimaryKey(Integer uid);

    int updateByPrimaryKeySelective(UserDO record);

    int updateByPrimaryKey(UserDO record);
}
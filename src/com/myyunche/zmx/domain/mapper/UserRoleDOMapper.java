package com.myyunche.zmx.domain.mapper;

import com.yunche.loan.domain.entity.UserRoleDO;

public interface UserRoleDOMapper {
    int insert(UserRoleDO record);

    int insertSelective(UserRoleDO record);
}
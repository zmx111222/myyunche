package com.myyunche.zmx.domain.mapper;

import com.yunche.loan.domain.entity.ModuleDO;

public interface ModuleDOMapper {
    int deleteByPrimaryKey(Integer mid);

    int insert(ModuleDO record);

    int insertSelective(ModuleDO record);

    ModuleDO selectByPrimaryKey(Integer mid);

    int updateByPrimaryKeySelective(ModuleDO record);

    int updateByPrimaryKey(ModuleDO record);
}
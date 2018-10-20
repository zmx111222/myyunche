package com.myyunche.zmx.domain.mapper;

import com.yunche.loan.domain.entity.RolrDO;

public interface RolrDOMapper {
    int deleteByPrimaryKey(Integer rid);

    int insert(RolrDO record);

    int insertSelective(RolrDO record);

    RolrDO selectByPrimaryKey(Integer rid);

    int updateByPrimaryKeySelective(RolrDO record);

    int updateByPrimaryKey(RolrDO record);
}
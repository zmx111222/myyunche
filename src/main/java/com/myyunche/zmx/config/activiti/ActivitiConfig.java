package com.myyunche.zmx.config.activiti;

import com.alibaba.druid.pool.DruidDataSource;
import com.myyunche.zmx.mapper.UserMapper;
import org.activiti.engine.impl.persistence.StrongUuidGenerator;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@Configuration
public class ActivitiConfig {
    @Autowired
    PlatformTransactionManager transactionManager;

    @Autowired
    DruidDataSource druidDataSource;

    @Bean
    public SpringProcessEngineConfiguration getProcessEngineConfiguration()
    {
        SpringProcessEngineConfiguration config =
                new SpringProcessEngineConfiguration();
        config.setDataSource(druidDataSource);
        config.setTransactionManager(transactionManager);
        config.setDatabaseType("mysql");
        config.setDatabaseSchemaUpdate("true");
        //id生成器
   /*     config.setIdGenerator(new MyUUIDgenerator());*/

      //是否禁用群组、用户、群组用户关系表
   /*     config.setDbIdentityUsed(false);*/

     /*   config.setDatabaseSchemaUpdate("drop-create");*/


     //设置对象变量
        HashMap beansMap = new HashMap<String,Object>();
        config.setBeans(beansMap);


        return config;
    }
}

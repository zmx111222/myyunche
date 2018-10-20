package com.myyunche.zmx.config.activiti;

import org.activiti.engine.impl.cfg.IdGenerator;

import java.util.UUID;

/**
 * @author: ZhongMingxiao
 * @create: 2018-09-08 16:32
 * @description: UUID生成器
 **/
public class MyUUIDgenerator  implements IdGenerator {
    @Override
    public String getNextId() {
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        System.out.println(uuid);
        return uuid;
    }
}

package com.myyunche.zmx.service.impl;

import com.myyunche.zmx.service.MyProcessService;
import org.activiti.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MyProcessServiceImpl implements MyProcessService
{
    @Autowired
    private RuntimeService runtimeService;

    public void myProcessStart()
    {
        runtimeService.startProcessInstanceByKey("leave-dynamic-from");
        System.out.println("流程实例启动成功");
    }
}

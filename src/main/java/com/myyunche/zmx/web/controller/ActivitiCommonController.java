package com.myyunche.zmx.web.controller;

import com.myyunche.zmx.service.ActivitiCommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: ZhongMingxiao
 * @create: 2018-09-03 08:49
 * @description: activiti框架封装通用服务
 **/

@CrossOrigin
@RestController
public class ActivitiCommonController
{
    @Autowired
    private ActivitiCommonService activitiCommonService;
    @GetMapping("/nodeJump")
    public String nodeJump()
    {
        activitiCommonService.nodeJump();
        return "";
    }
    //节点跳转

    //会签
    //会签权重
    //自定义角色-部门表

    //自定义主键生成
    //redis实现缓存
    //自定义属性处理
    //监听器
}

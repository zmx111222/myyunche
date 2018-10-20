package com.myyunche.zmx.service.impl;

import com.myyunche.zmx.mapper.ActivitiDeploymentMapper;
import com.myyunche.zmx.service.ActivitiVersionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Set;


@Service
public class ActivitiVersionServiceImpl implements ActivitiVersionService {

    private static final Logger logger = LoggerFactory.getLogger(ActivitiVersionServiceImpl.class);
    /**
     * 换行符
     */
    public static final String NEW_LINE = System.getProperty("line.separator");
    @Autowired
    private ActivitiDeploymentMapper activitiDeploymentMapper;

    /**
     * 流程替换
     */
    @Override
    @Transactional(rollbackFor=Exception.class)
    public void replaceActivitiVersion()
    {
        // 获取上个版本的部署ID
        Long lastVersionDeploymentId = activitiDeploymentMapper.getLastVersionDeploymentId();

        if (null != lastVersionDeploymentId) {

            // 获取(当前部署的)最新版本的部署ID
            Long newVersionDeploymentId = activitiDeploymentMapper.getNewVersionDeploymentId();

            // 仅保留新版本           资源文件(bpmn/png)
            int deleteAllBpmnAndPngCount = activitiDeploymentMapper.deleteAllBpmnAndPngExcludeNewVersion(newVersionDeploymentId);

            // 删除最新的            流程定义
            int deleteNewVersionProcessDefinitionCount = activitiDeploymentMapper.deleteNewVersionProcessDefinition(newVersionDeploymentId);

            // 部署ID替换
            int replaceDeploymentIdCount = activitiDeploymentMapper.replaceDeploymentId(lastVersionDeploymentId, newVersionDeploymentId);

            logger.info("替换旧流程成功        >>>>>"
                            + NEW_LINE
                            + "lastVersionDeploymentId : {}, newVersionDeploymentId : {}, deleteAllBpmnAndPngCount : {}, deleteNewVersionProcessDefinitionCount : {}, replaceDeploymentIdCount : {}.",
                    lastVersionDeploymentId, newVersionDeploymentId, deleteAllBpmnAndPngCount, deleteNewVersionProcessDefinitionCount, replaceDeploymentIdCount);
        } else {

            logger.info("无旧版本流程       >>>>>"
                    + NEW_LINE
                    + "lastVersionDeploymentId : {}", lastVersionDeploymentId);
        }
    }

    @Override
    public Set<String> getLoginUserOwnDataFlowNodes() {

        return null;
    }

}

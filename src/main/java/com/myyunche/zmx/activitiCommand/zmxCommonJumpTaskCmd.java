package com.myyunche.zmx.activitiCommand;

import org.activiti.bpmn.model.Activity;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityManager;

import java.util.Collection;
import java.util.Map;

/**
 * @author: ZhongMingxiao
 * @create: 2018-09-03 11:50
 * @description: 节点跳转命令
 **/
public class zmxCommonJumpTaskCmd implements Command<Void>
{
    //执行实例id
    protected String executionId;

    //流程实例id
    protected String parentId;

    //目标节点
    protected Activity desActivity;

    //变量
    protected Map<String,Object> parentvar;

    //当前节点
    protected Activity CurrentActivity;


    @Override
    public Void execute(CommandContext commandContext)
    {

        ExecutionEntityManager executionEntityManager = Context.getCommandContext().getExecutionEntityManager();

        ExecutionEntity executionEntity = executionEntityManager.findByRootProcessInstanceId(parentId);

        executionEntity.setVariables(parentvar);




        return null;
    }
}

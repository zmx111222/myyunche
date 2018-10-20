package com.myyunche.zmx.activitiListener;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

/**
 * @author: ZhongMingxiao
 * @create: 2018-09-06 22:29
 * @description: 任务执行监听器
 **/
public class TaskListenerImpl implements TaskListener {
    @Override
    public void notify(DelegateTask delegateTask)
    {
        delegateTask.addCandidateUser("郭靖");
        delegateTask.addCandidateUser("黄蓉");
    }
}

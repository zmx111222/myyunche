import com.myyunche.zmx.App;
import com.myyunche.zmx.mapper.UserMapper;
import com.myyunche.zmx.service.MyProcessService;
import org.activiti.engine.*;
import org.activiti.engine.form.FormProperty;
import org.activiti.engine.form.StartFormData;
import org.activiti.engine.identity.Group;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author: ZhongMingxiao
 * @create: 2018-08-08 14:59
 * @description: 测试模块
 **/

@RunWith(SpringJUnit4ClassRunner.class)    // SpringJUnit支持，由此引入Spring-Test框架支持！
@SpringBootTest(classes = App.class)    // 指定我们SpringBoot工程的Application启动类
@WebAppConfiguration    //由于是Web项目，Junit需要模拟ServletContext，因此我们需要给我们的测试类加上@WebAppConfiguration。
/*@Transactional*/
/*@Rollback*/
public class MySpringbootTest
{
    @Autowired
    private MyProcessService myProcessService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private FormService formService;

    @Autowired
    private IdentityService identityService;



    //启动流程实例
    @Test
    public  void  processSatrt()
    {
       myProcessService.myProcessStart();
        System.out.println("操作成功");
    }

    //设置可以启动的候选人组
    @Test
    public void setStartUser()
    {
        String[] users =new String[]{"zmx"};
        String[] groups = new String[]{};
        String processDefinitionId="leave-dynamic-from:1:4";
        setStartables(processDefinitionId, users, groups);
    }

    //启动流程附带动态表单
    @Test
    public  void  processFormKeySatrt()
    {
        String processId="leave-dynamic-from:1:4";
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .processDefinitionId(processId)
                .singleResult();
        boolean hasStartFormKey = processDefinition.hasStartFormKey();
        Map<String, String> formValues = new HashMap<String, String>();
        //表单填值
        formValues.put("startDate",LocalDate.now().toString());
        formValues.put("endDate",LocalDate.now().plusDays(3).toString());
        formValues.put("reason","放假了！！！！");

        if (hasStartFormKey) { // formkey表单

        } else { // 动态表单
            // 先读取表单字段在根据表单字段的ID读取请求参数值
            StartFormData formData = formService.getStartFormData(processId);
            // 从请求中获取表单字段的值
        }

        // 权限拦截
        boolean startable = false;
        List<IdentityLink> identityLinks = repositoryService.getIdentityLinksForProcessDefinition(processDefinition.getId());
        if (identityLinks == null || identityLinks.isEmpty()) {
            startable = true;
        } else {
            for (IdentityLink identityLink : identityLinks) {
                //判断登录用户是否有关联权限人
                if (StringUtils.isNotBlank(identityLink.getUserId()) && identityLink.getUserId().equals("钟明晓")) {
                    startable = true;
                    break;
                }

                if (StringUtils.isNotBlank(identityLink.getGroupId())) {
                   //判断登录用户是否有关联组权限
                }
            }
        }

        if (!startable) {
           System.out.println("无权限启动！！！！！！！！");
        }

        //设置授权的启动用户
        identityService.setAuthenticatedUserId("钟明晓");


        // 提交表单字段并启动一个新的流程实例
        ProcessInstance processInstance = formService.submitStartFormData(processId, formValues);
    }


    //查询任务
    @Test
    public  void  excuteTask()
    {
        List<Task> tasks = taskService.createTaskQuery()
                .taskCandidateOrAssigned("钟明晓")
                .list();
        if(tasks!=null && tasks.size()>0){
            for(Task task:tasks){
                System.out.println("任务ID:"+task.getId());
                System.out.println("任务名称:"+task.getName());
                System.out.println("任务的创建时间:"+task.getCreateTime());
                System.out.println("任务的办理人:"+task.getAssignee());
                System.out.println("流程实例ID："+task.getProcessInstanceId());
                System.out.println("执行对象ID:"+task.getExecutionId());
                System.out.println("流程定义ID:"+task.getProcessDefinitionId());
                System.out.println("########################################################");
            }
        }
    }

    /**拾取任务，将组任务分给个人任务，指定任务的办理人字段*/
    @Test
    public void claim(){
        //将组任务分配给个人任务
        //任务ID
        String taskId = "7204";
        //分配的个人任务（可以是组任务中的成员，也可以是非组任务的成员）
        String userId = "郭靖";
       taskService.claim(taskId, userId);
    }


    /**将个人任务回退到组任务，前提，之前一定是个组任务*/
    @Test
    public void setAssigee(){
        //任务ID
        String taskId = "6204";
        taskService.setAssignee(taskId, null);
    }

    /**启动流程实例+设置流程变量+获取流程变量+向后执行一步*/
    @Test
    public void startProcessInstance(){
        //流程定义的key
        String processDefinitionKey = "receiveTask";
        ProcessInstance pi =//与正在执行的流程实例和执行对象相关的Service
        runtimeService.startProcessInstanceByKey(processDefinitionKey);//使用流程定义的key启动流程实例，key对应helloworld.bpmn文件中id的属性值，使用key值启动，默认是按照最新版本的流程定义启动
        System.out.println("流程实例ID:"+pi.getId());//流程实例ID    101
        System.out.println("流程定义ID:"+pi.getProcessDefinitionId());//流程定义ID   helloworld:1:4

        /**查询执行对象ID*/
        Execution execution1 = runtimeService
                .createExecutionQuery()//创建执行对象查询
                .processInstanceId(pi.getId())//使用流程实例ID查询
                .activityId("receivetask1")//当前活动的id，对应receiveTask.bpmn文件中的活动节点id的属性值
                .singleResult();

        /**使用流程变量设置当日销售额，用来传递业务参数*/
        runtimeService
                .setVariable(execution1.getId(), "汇总当日销售额", 21000);

        /**向后执行一步，如果流程处于等待状态，使得流程继续执行*/
        runtimeService
                .signalEventReceived(execution1.getId());


        /**查询执行对象ID*/
        Execution execution2 = runtimeService
                .createExecutionQuery()//创建执行对象查询
                .processInstanceId(pi.getId())//使用流程实例ID查询
                .activityId("receivetask2")//当前活动的id，对应receiveTask.bpmn文件中的活动节点id的属性值
                .singleResult();

        /**从流程变量中获取汇总当日销售额的值*/
        Integer value = (Integer)runtimeService
                .getVariable(execution2.getId(), "汇总当日销售额");
        System.out.println("给老板发送短信：金额是："+value);
        /**向后执行一步，如果流程处于等待状态，使得流程继续执行*/
        runtimeService
                .signalEventReceived(execution2.getId());

    }

    @Test
    public void completeMyPersonalTask(){
        //任务ID
        String taskId = "12509";
        taskService
                .setVariable(taskId,"money",800);
        taskService
                .complete(taskId);//与正在执行的任务管理相关的Service

        System.out.println("完成任务：任务ID："+taskId);
    }



    public void setStartables(String processDefinitionId, String[] userArray, String[] groupArray) {

        // 1、清理现有的设置
        List<IdentityLink> links = repositoryService.getIdentityLinksForProcessDefinition(processDefinitionId);
        for (IdentityLink link : links) {
            if (StringUtils.isNotBlank(link.getUserId())) {
                repositoryService.deleteCandidateStarterUser(processDefinitionId, link.getUserId());
            }
            if (StringUtils.isNotBlank(link.getGroupId())) {
                repositoryService.deleteCandidateStarterGroup(processDefinitionId, link.getGroupId());
            }
        }

        // 2.1、循环添加候选人
        if (!ArrayUtils.isEmpty(userArray)) {
            for (String user : userArray) {
                repositoryService.addCandidateStarterUser(processDefinitionId, user);
            }
        }

        // 2.2、循环添加候选组
        if (!ArrayUtils.isEmpty(groupArray)) {
            for (String group : groupArray) {
                repositoryService.addCandidateStarterGroup(processDefinitionId, group);
            }
        }
    }

}

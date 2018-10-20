/**
 * @author: ZhongMingxiao
 * @create: 2018-08-05 16:10
 * @description: 程序启动类
 **/
package com.myyunche.zmx;


import com.myyunche.zmx.config.anno.DistributedLock;
import com.myyunche.zmx.config.cache.ActivitiCache;
import com.myyunche.zmx.service.ActivitiVersionService;
import org.activiti.engine.RepositoryService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.HashMap;
import java.util.Map;

//开启计划任务和异步任务、事务、Feign客户端注解

@SpringBootApplication
@EnableFeignClients
@EnableTransactionManagement
@EnableScheduling
@EnableAsync
@EnableAutoConfiguration
@ServletComponentScan
@MapperScan(basePackages = "com.myyunche.zmx.mapper")
public class App
{
    //mvn package spring-boot:repackage
    public static void main(String[] args)
    {
        SpringApplication.run(App.class, args);
    }

    @Bean
    @Primary
    public TaskExecutor primaryTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        return executor;
    }

    @Bean
    public CommandLineRunner init(RepositoryService repositoryService,
                                  ActivitiCache activitiCache,
                                  ActivitiVersionService activitiVersionService) {

        return new CommandLineRunner() {

            @Override
            @DistributedLock(timeOut = 60 * 5 * 1000)
            public void run(String... args) throws Exception {
               /* Map<String,Object> variables = new HashMap<String, Object>();
                variables.put("from", "583976359@qq.com");
                variables.put("to","17757144205@163.com");*/

                // 部署
             /*   repositoryService.createDeployment()
                        .name("请假流程")
                        .addClasspathResource("processes/leave-dynamic-from/leave-dynamic-from.bpmn")
                        .deploy();*/
                // 刷新activiti缓存数据
              /*  activitiCache.refresh();*/

                // 流程替换
          /*      activitiVersionService.replaceActivitiVersion();*/
            }
        };

    }
}

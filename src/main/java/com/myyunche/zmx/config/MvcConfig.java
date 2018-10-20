package com.myyunche.zmx.config;

import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.ViewResolver;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.TemplateResolver;

/**
 * @author: ZhongMingxiao
 * @create: 2018-08-18 11:57
 * @description: mvc视图解析配置
 **/
public class MvcConfig
{
    /**
     * 设置视图解析器
     * @param templateEngine
     * @return
     */
    @Bean
    public ViewResolver viewResolver(SpringTemplateEngine templateEngine){
        ThymeleafViewResolver resolver = new ThymeleafViewResolver();
        resolver.setTemplateEngine(templateEngine);
        return resolver;
    }

    /**
     * 设置模板引擎
     * @param templateResolver
     * @return
     */
    @Bean
    public SpringTemplateEngine templateEngine(TemplateResolver templateResolver){
        SpringTemplateEngine engine = new SpringTemplateEngine();
        engine.setTemplateResolver(templateResolver);
        return engine;
    }

    /**
     * 模板解析引擎
     * @return
     */
    @Bean
    public TemplateResolver templateResolver(){
        TemplateResolver resolver = new SpringResourceTemplateResolver();
        //设置地址前缀
        resolver.setPrefix("/WEB-INF/template/");
        //设置后缀
        resolver.setSuffix(".thl");
        //设置不缓存
        resolver.setCacheable(false);
        resolver.setTemplateMode("HTML5");
        return resolver;

    }
}

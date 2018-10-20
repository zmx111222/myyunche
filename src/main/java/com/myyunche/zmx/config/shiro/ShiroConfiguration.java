/**
 * @author: ZhongMingxiao
 * @create: 2018-08-05 21:53
 * @description: shiro配置类
 **/
package com.myyunche.zmx.config.shiro;


import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.apache.shiro.mgt.SecurityManager;

import static org.apache.shiro.web.mgt.CookieRememberMeManager.DEFAULT_REMEMBER_ME_COOKIE_NAME;
import static org.apache.shiro.web.servlet.Cookie.ONE_YEAR;
import static org.apache.shiro.web.servlet.Cookie.ROOT_PATH;

/**
 * shiro的配置类
 * @author Administrator
 *
 */
@Configuration
public class ShiroConfiguration
{
    /**
     * session过期时间：30天（单位：秒）
     */
    public static final Integer SESSION_EXPIRE = 3600 * 24 * 30;

    /**
     * 连接到Redis的超时时间：2s（单位：毫秒）
     */
    private static final Integer CONNECT_TO_REDIS_TIMEOUT = 2000;

    @Bean(name="shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(@Qualifier("securityManager") SecurityManager manager)
    {
        ShiroFilterFactoryBean bean=new ShiroFilterFactoryBean();
        bean.setSecurityManager(manager);
        //配置登录的url和登录成功的url
       /* bean.setLoginUrl("/login");
        bean.setSuccessUrl("/home");
        //配置访问权限
        LinkedHashMap<String, String> filterChainDefinitionMap=new LinkedHashMap<>();
        filterChainDefinitionMap.put("/*", "anon"); //表示可以匿名访问
        filterChainDefinitionMap.put("/loginUser", "anon");
        filterChainDefinitionMap.put("/logout*","anon");
        filterChainDefinitionMap.put("/druid/*","anon");
        filterChainDefinitionMap.put("/druid.*","anon");
        filterChainDefinitionMap.put("/index.*","authc");
        filterChainDefinitionMap.put("/*", "authc");//表示需要认证才可以访问
        filterChainDefinitionMap.put("/**", "authc");//表示需要认证才可以访问
        filterChainDefinitionMap.put("/*.*", "authc");
        bean.setFilterChainDefinitionMap(filterChainDefinitionMap);*/
        return bean;
    }
    //配置核心安全事务管理器
    @Bean(name="securityManager")
    public SecurityManager securityManager(@Qualifier("authRealm") AuthRealm authRealm)
    {
        System.err.println("--------------shiro已经加载----------------");
        DefaultWebSecurityManager securityManager=new DefaultWebSecurityManager();
        // 自定义Realm
        securityManager.setRealm(authRealm);

        // 自定义session管理 使用redis
        securityManager.setSessionManager(sessionManager());

        // 自定义缓存实现 使用redis
        securityManager.setCacheManager(cacheManager());

        // RememberMe
        securityManager.setRememberMeManager(rememberMeManager());
        return securityManager;
    }
    //配置自定义的权限登录器
    @Bean(name="authRealm")
    public AuthRealm authRealm(@Qualifier("credentialsMatcher") CredentialsMatcher matcher) {
        AuthRealm authRealm=new AuthRealm();
        authRealm.setCredentialsMatcher(matcher);
        return authRealm;
    }
    //配置自定义的密码比较器
    @Bean(name="credentialsMatcher")
    public CredentialsMatcher credentialsMatcher() {
        return new CredentialsMatcher();
    }
    /*@Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor(){
        return new LifecycleBeanPostProcessor();
    }*/
   /* @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator(){
        DefaultAdvisorAutoProxyCreator creator=new DefaultAdvisorAutoProxyCreator();
        creator.setProxyTargetClass(true);
        return creator;
    }*/
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(@Qualifier("securityManager") SecurityManager manager) {
        AuthorizationAttributeSourceAdvisor advisor=new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(manager);
        return advisor;
    }


    /**
     * 自定义sessionManager
     *
     * @return
     */
    @Bean
    public SessionManager sessionManager() {
        ZmxSessionManager zmxSessionManager = new ZmxSessionManager();

        // sessionDAO
        zmxSessionManager.setSessionDAO(redisSessionDAO());

        // 删除过期的session
        zmxSessionManager.setDeleteInvalidSessions(true);

        // 是否定时检查session
        zmxSessionManager.setSessionValidationSchedulerEnabled(true);

        // 设置全局session超时时间
        zmxSessionManager.setGlobalSessionTimeout(SESSION_EXPIRE * 1000);

        // cookie
        SimpleCookie sessionIdCookie = new SimpleCookie("sid");
        sessionIdCookie.setHttpOnly(true);
        sessionIdCookie.setMaxAge(SESSION_EXPIRE);
        sessionIdCookie.setPath(ROOT_PATH);
        zmxSessionManager.setSessionIdCookie(sessionIdCookie);

        return zmxSessionManager;
    }


    /**
     * RedisSessionDAO shiro sessionDao层的实现 通过redis
     * <p>
     * 使用的是shiro-redis开源插件
     */
    @Bean
    public RedisSessionDAO redisSessionDAO() {
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setRedisManager(redisManager());
        return redisSessionDAO;
    }

    /**
     * 配置shiro redisManager
     * <p>
     * 使用的是shiro-redis开源插件
     *
     * @return
     */
    @ConfigurationProperties(prefix = "spring.redis")
    public RedisManager redisManager() {
        RedisManager redisManager = new RedisManager();
        redisManager.setExpire(SESSION_EXPIRE);
        redisManager.setTimeout(CONNECT_TO_REDIS_TIMEOUT);
        return redisManager;
    }

    /**
     * RememberMe
     *
     * @return
     */
    public CookieRememberMeManager rememberMeManager()
    {

        Cookie cookie = new SimpleCookie(DEFAULT_REMEMBER_ME_COOKIE_NAME);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(ONE_YEAR);

        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(cookie);

        return cookieRememberMeManager;
    }


    /**
     * cacheManager 缓存 redis实现
     * <p>
     * 使用的是shiro-redis开源插件
     *
     * @return
     */
    @Bean
    public RedisCacheManager cacheManager() {
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager());
        return redisCacheManager;
    }

}
/**
 * @author: ZhongMingxiao
 * @create: 2018-08-10 11:24
 * @description: redis的session管理器
 **/
package com.myyunche.zmx.config.shiro;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.Serializable;

public class ZmxSessionManager extends DefaultWebSessionManager
{
    public ZmxSessionManager()
    {
        System.out.println("sessionManager创建了====================");
    }

    private static final String AUTHORIZATION = "authToken";

    private static final String REFERENCED_SESSION_ID_SOURCE = "Stateless request";
    /**
     * 重写getSessionId方法
     *
     * @param request
     * @param response
     * @return
     */
    @Override
    protected Serializable getSessionId(ServletRequest request, ServletResponse response)
    {
        String id = WebUtils.toHttp(request).getHeader(AUTHORIZATION);
        System.out.println("id：" + id);
        if (StringUtils.isEmpty(id))
        {
            //如果没有携带id参数则按照父类的方式在cookie进行获取
            System.out.println("super：" + super.getSessionId(request, response));
            return super.getSessionId(request, response);
        } else
            {
            //如果请求头中有 authToken 则其值为sessionId
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE, REFERENCED_SESSION_ID_SOURCE);
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID, id);
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID, Boolean.TRUE);
            return id;
           }
    }
}

/**
 * @author: ZhongMingxiao
 * @create: 2018-08-05 22:00
 * @description: 登录类
 **/
package com.myyunche.zmx.web.controller;

import com.myyunche.zmx.domain.entity.User;
import com.myyunche.zmx.domain.vo.Son;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@CrossOrigin
@RestController
public class LoginController
{
    @PostMapping("/login")
    public String login()
    {
        System.out.println("登录操作执行了");
        return "login_name";
    }
    @PostMapping("/loginUser")
    public Son loginUser(@RequestBody User user, HttpSession session)
    {
        UsernamePasswordToken usernamePasswordToken=new UsernamePasswordToken(user.getUsername(),user.getPassword());
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(usernamePasswordToken);   //完成登录
            User user1=(User) subject.getPrincipal();
            session.setAttribute("user", user1);
            //测试返回
            Son s1=new Son();
            s1.setFatherName("父类");
            s1.setSonName("子类");

            return s1;
        } catch(Exception e)
        {
            Son s1=new Son();
            s1.setFatherName("父类");
            s1.setSonName("子类");


            return s1;//返回登录页面
        }

    }
    @RequestMapping("/logOut")
    public String logOut(HttpSession session)
    {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
//        session.removeAttribute("user");
        return "login_name";
    }
}
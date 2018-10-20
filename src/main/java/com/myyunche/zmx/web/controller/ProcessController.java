package com.myyunche.zmx.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * @author: ZhongMingxiao
 * @create: 2018-08-19 10:55
 * @description: 流程相关
 **/
@CrossOrigin
@Controller
public class ProcessController
{
    @RequestMapping("/process")
    public String processStore( HttpSession session)
    {
        session.setAttribute("name","钟明晓");
       return  "process";
    }


}

package com.itheima.shiro.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Author:Young
 * Date:2020/7/25-21:46
 */
@Controller
public class MyController {

    @RequestMapping(value = {"/", "/index"})
    public String toIndex(Model model) {
        model.addAttribute("msg", "hello shiro");
        return "index";
    }

    @RequestMapping("/user/add")
    public String add() {

        return "user/add";
    }

    @RequestMapping("/user/update")
    @RequiresPermissions("item:type:save")
    public String update() {

        return "user/update";
    }

    @RequestMapping("/toLogin")
    public String toLogin() {

        return "login";
    }

    @RequestMapping("/login")
    public String login(String username, String password,Model model) {
        //获取当前用户
        Subject subject = SecurityUtils.getSubject();

        try {
            //封装用户的登录数据
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            //执行登录方法，如果没有异常，就可以在Realm方法中获取
            subject.login(token);

        } catch (UnknownAccountException e) {
            model.addAttribute("msg","用户名错误");
            return "login";
        } catch (IncorrectCredentialsException e) {
            model.addAttribute("msg","密码错误");
            return "login";
        } catch (LockedAccountException e) {
            model.addAttribute("msg",e.getMessage());
            return "login";
        } catch (AuthenticationException e) {
            model.addAttribute("msg",e.getMessage());
            return "login";
        }
        return "index";
    }

    @RequestMapping("/noauth")
    @ResponseBody
    public String unauthorized(){
        return "未经授权无法访问此页面";
    }
}

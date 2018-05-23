package com.youling.shirolearn.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("login")
public class LoginController {

    @RequestMapping
    public String login(){
        return "login";
    }

    @RequestMapping(value = "/queryByUsername/{username}/{password}")
    public String queryByUsername(@PathVariable("username") String username, @PathVariable("password") String password){

        //获取主体对象
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(new UsernamePasswordToken(username, password));
            return "redirect:/index";
        } catch (UnknownAccountException e) {
            System.out.println("账户错误");
        } catch (IncorrectCredentialsException e) {
            System.out.println("密码错误");
        }
        return "redirect:/login";
    }

    @RequestMapping("logout")
    public String logout(){
        //获取主体对象
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "redirect:/index";
    }
}

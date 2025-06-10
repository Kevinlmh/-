package com.course.controller;

import com.course.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 登录平台 Controller
 * 调用 Service 层处理用户登录积分逻辑
 */
@Component
public class LoginControllerController {

    @Autowired
    private LoginService loginService;

    public void login(int userId) {
        loginService.login(userId);
        System.out.println("======login方法执行并处理积分======");
    }
}

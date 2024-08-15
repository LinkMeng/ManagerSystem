package com.linkmeng.managersystem.controller;

import com.linkmeng.managersystem.business.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户控制器
 *
 * @since 2024-08-15
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 校验用户是否具有指定资源的权限
     *
     * @param resource 资源ID
     */
    @GetMapping("/{resource}")
    public void authCheck(@PathVariable String resource) {
        // TODO: 校验
        userService.checkAccount(0, resource); // TODO: userId
    }
}

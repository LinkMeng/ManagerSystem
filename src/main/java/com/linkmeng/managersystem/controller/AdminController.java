package com.linkmeng.managersystem.controller;

import com.linkmeng.managersystem.business.AdminService;
import com.linkmeng.managersystem.common.exception.CommonException;
import com.linkmeng.managersystem.common.exception.InputIllegalException;
import com.linkmeng.managersystem.model.CommonMessage;
import com.linkmeng.managersystem.model.User;
import com.linkmeng.managersystem.model.UserResource;
import com.linkmeng.managersystem.role.RequiredUserRole;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 管理员控制器
 *
 * @since 2024-08-15
 */
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    /**
     * 为指定用户设置资源权限
     *
     * @param userResource 配置信息
     */
    @RequiredUserRole({User.Role.ADMIN})
    @PostMapping("/addUser")
    @ResponseBody
    public CommonMessage addUser(@RequestBody UserResource userResource) throws CommonException {
        if (userResource == null) {
            throw new InputIllegalException("userResource");
        } else if (userResource.getUserId() == null || userResource.getUserId() < 0) {
            throw new InputIllegalException("userResource.userId");
        }
        return adminService.setResource(userResource);
    }
}

package com.linkmeng.managersystem.controller;

import com.linkmeng.managersystem.business.UserService;
import com.linkmeng.managersystem.common.exception.CommonException;
import com.linkmeng.managersystem.common.util.ResourceUtil;
import com.linkmeng.managersystem.common.constant.I18nConstant;
import com.linkmeng.managersystem.model.BooleanMessage;
import com.linkmeng.managersystem.model.User;
import com.linkmeng.managersystem.role.RequiredUserRole;
import com.linkmeng.managersystem.role.RoleUserInfo;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户控制器
 *
 * @since 2024-08-15
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 校验用户是否具有指定资源的权限
     *
     * @param resource 资源ID
     * @param userInfo 用戶信息
     */
    @RequiredUserRole({User.Role.ADMIN, User.Role.USER})
    @GetMapping("/{resource}")
    @ResponseBody
    public BooleanMessage authCheck(@PathVariable String resource, @RoleUserInfo User userInfo) throws CommonException {
        if (StringUtils.isEmpty(resource)) {
            throw new IllegalArgumentException("resource");
        }
        return userService.checkAccount(userInfo.getUserId(), resource);
    }
}

package com.linkmeng.managersystem.business.impl;

import com.linkmeng.managersystem.business.AdminService;
import com.linkmeng.managersystem.common.constant.I18nConstant;
import com.linkmeng.managersystem.common.exception.CommonException;
import com.linkmeng.managersystem.dao.UserResourceDao;
import com.linkmeng.managersystem.model.CommonMessage;
import com.linkmeng.managersystem.model.UserResource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 管理员服务
 *
 * @since 2024-08-16
 */
@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private UserResourceDao userResourceDao;

    @Override
    public CommonMessage setResource(UserResource userResource) throws CommonException {
        Integer userId = userResource.getUserId();
        int resourceNum = userResourceDao.insertUserResource(userId, userResource.getEndpoint());
        return CommonMessage.of(true, I18nConstant.MESSAGE_ADMIN_ADD_USER_SUCCESS, userId, resourceNum);
    }
}

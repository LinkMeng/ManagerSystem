package com.linkmeng.managersystem.business.impl;

import com.linkmeng.managersystem.business.UserService;

import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Override
    public boolean checkAccount(int userId, String resource) {
        return false;
    }
}

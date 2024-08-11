package com.zephyrtoria.schedule.service.impl;

import com.zephyrtoria.schedule.dao.SysUserDao;
import com.zephyrtoria.schedule.dao.impl.SysUserDaoImpl;
import com.zephyrtoria.schedule.pojo.SysUser;
import com.zephyrtoria.schedule.service.SysUserService;
import com.zephyrtoria.schedule.util.MD5Util;

import java.util.List;

public class SysUserServiceImpl implements SysUserService {
    private final SysUserDao userDao = new SysUserDaoImpl();

    @Override
    public int register(SysUser sysUser) {
        sysUser.setUserPwd(MD5Util.encrypt(sysUser.getUserPwd()));
        System.out.println("Service run successfully!");
        System.out.println("Going to connect DAO...");
        return userDao.addUser(sysUser);
    }

    @Override
    public SysUser login(SysUser sysUser) {
        return userDao.findByName(sysUser.getUsername());
    }

    @Override
    public List<SysUser> checkUsers() {
        System.out.println("Service run successfully!");
        System.out.println("Going to connect DAO...");
        return userDao.findAll();
    }

    @Override
    public SysUser findByName(String username) {
        return userDao.findByName(username);
    }
}

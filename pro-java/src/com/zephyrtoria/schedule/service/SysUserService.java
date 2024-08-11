package com.zephyrtoria.schedule.service;

import com.zephyrtoria.schedule.pojo.SysUser;

import java.util.List;

public interface SysUserService {

    int register(SysUser sysUser);

    SysUser login(SysUser sysUser);

    List<SysUser> checkUsers();

    SysUser findByName(String username);
}

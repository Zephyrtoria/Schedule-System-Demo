package com.zephyrtoria.schedule.dao;

import com.zephyrtoria.schedule.pojo.SysUser;

import java.util.List;

public interface SysUserDao {
    /**
     * 添加一名用户信息至数据库sys_user中
     * @param user 要添加的用户信息以SysUser对象入参
     * @return 返回操作影响数据库的行数。返回值大于0则添加成功；等于0则添加失败
     */
    int addUser(SysUser user);

    /**
     * 查询sys_user中所有的用户信息
     * @return 查询所有用户信息以集合形式返回
     */
    List<SysUser> findAll();

    /**
     * 在sys_user中根据username的值进行查询
     * @param username 需要查询的username
     * @return 返回查询到的SysUser信息
     */
    SysUser findByName(String username);
}

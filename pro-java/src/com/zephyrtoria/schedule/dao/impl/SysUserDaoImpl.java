package com.zephyrtoria.schedule.dao.impl;

import com.zephyrtoria.schedule.dao.BaseDao;
import com.zephyrtoria.schedule.dao.SysUserDao;
import com.zephyrtoria.schedule.pojo.SysUser;

import java.sql.SQLException;
import java.util.List;

public class SysUserDaoImpl extends BaseDao implements SysUserDao {
    @Override
    public int addUser(SysUser user) {
        String sql = "insert into sys_user values(DEFAULT, ?, ?)";
        try {
            return executeUpdate(sql, user.getUsername(), user.getUserPwd());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<SysUser> findAll() {
        String sql = "select uid, username, user_pwd userPwd from sys_user";
        try {
            return executeQuery(SysUser.class, sql);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public SysUser findByName(String username) {
        String sql = "select uid, username, user_pwd userPwd from sys_user where username = ?";
        try {
            return executeQueryBean(SysUser.class, sql, username);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

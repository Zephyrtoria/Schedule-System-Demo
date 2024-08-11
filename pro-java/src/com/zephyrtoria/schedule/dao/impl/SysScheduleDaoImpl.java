package com.zephyrtoria.schedule.dao.impl;

import com.zephyrtoria.schedule.dao.BaseDao;
import com.zephyrtoria.schedule.dao.SysScheduleDao;
import com.zephyrtoria.schedule.pojo.SysSchedule;
import com.zephyrtoria.schedule.util.JDBCUtil;

import java.sql.SQLException;
import java.util.List;

public class SysScheduleDaoImpl extends BaseDao implements SysScheduleDao {
    @Override
    public List<SysSchedule> findItemByUid(Integer uid) {
        String sql = "select sid, uid, title, completed from sys_schedule where uid = ?";
        try {
            return executeQuery(SysSchedule.class, sql, uid);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Integer addOneSchedule(Integer uid) {
        String sql = "insert into sys_schedule values(DEFAULT, ?, '请输入日程', 0)";
        try {
            return executeUpdate(sql, uid);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Integer updateSchedule(SysSchedule schedule) {
        String sql = "update sys_schedule set title = ?, completed = ? where sid = ?";
        try {
            return executeUpdate(sql, schedule.getTitle(), schedule.getCompleted(), schedule.getSid());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Integer removeSchedule(Integer sid) {
        String sql = "delete from sys_schedule where sid = ?";
        try {
            return executeUpdate(sql, sid);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

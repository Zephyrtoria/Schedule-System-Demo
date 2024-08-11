package com.zephyrtoria.schedule.dao;

import com.zephyrtoria.schedule.pojo.SysSchedule;

import java.util.List;

public interface SysScheduleDao {

    List<SysSchedule> findItemByUid(Integer uid);

    Integer addOneSchedule(Integer uid);

    Integer updateSchedule(SysSchedule schedule);

    Integer removeSchedule(Integer sid);
}

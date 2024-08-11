package com.zephyrtoria.schedule.service.impl;

import com.zephyrtoria.schedule.dao.SysScheduleDao;
import com.zephyrtoria.schedule.dao.impl.SysScheduleDaoImpl;
import com.zephyrtoria.schedule.pojo.SysSchedule;
import com.zephyrtoria.schedule.service.SysScheduleService;

import java.util.List;

public class SysScheduleServiceImpl implements SysScheduleService {
    private final SysScheduleDao scheduleDao = new SysScheduleDaoImpl();

    @Override
    public List<SysSchedule> findItemListByUid(Integer uid) {
        return scheduleDao.findItemByUid(uid);
    }

    @Override
    public Integer addOneSchedule(Integer uid) {
        return scheduleDao.addOneSchedule(uid);
    }

    @Override
    public Integer updateSchedule(SysSchedule schedule) {
        return scheduleDao.updateSchedule(schedule);
    }

    @Override
    public Integer removeSchedule(Integer sid) {
        return scheduleDao.removeSchedule(sid);
    }
}

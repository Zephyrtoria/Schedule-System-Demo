package com.zephyrtoria.schedule.service;

import com.zephyrtoria.schedule.pojo.SysSchedule;

import java.util.List;

public interface SysScheduleService {
    /**
     * 根据传入的uid值在sys_schedule数据库中查询对应uid的日程记录
     * @param uid 需要查询的uid
     * @return 返回查询到的日程记录，以List形式返回
     */
    List<SysSchedule> findItemListByUid(Integer uid);


    /**
     * 为当前用户增加一个空的日程记录
     * @param uid 根据传入的uid确定是哪位用户
     */
    Integer addOneSchedule(Integer uid);

    Integer updateSchedule(SysSchedule schedule);

    Integer removeSchedule(Integer sid);
}

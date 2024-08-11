package com.zephyrtoria.schedule.controller;

import com.zephyrtoria.schedule.common.Result;
import com.zephyrtoria.schedule.pojo.SysSchedule;
import com.zephyrtoria.schedule.service.SysScheduleService;
import com.zephyrtoria.schedule.service.impl.SysScheduleServiceImpl;
import com.zephyrtoria.schedule.util.WebUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/schedule/*")
public class SysScheduleController extends BaseController {
    private final SysScheduleService sysScheduleService = new SysScheduleServiceImpl();

    /**
     * 查询所有日程接口
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void findAllSchedule(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 接收请求中的uid参数
        Integer uid = Integer.parseInt(req.getParameter("uid"));

        // 查询该用户的所有日程
        List<SysSchedule> itemList = sysScheduleService.findItemListByUid(uid);

        // 将用户的所有日程放入一个Result对象
        // 此时给result.data赋值为一个List对象，但是最好还是使用Map
        // Result<List<SysSchedule>> result = Result.ok(itemList);
        Map<String, Object> data = new HashMap();
        data.put("itemList", itemList);
        Result result = Result.ok(data);

        // 将Result对象转换为JSON串响应给客户端
        WebUtil.writeJson(resp, result);
    }

    /**
     * 为当前用户添加一个空的日程记录
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void addSchedule(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 接收请求中的uid值
        Integer uid = Integer.parseInt(req.getParameter("uid"));

        // 请求添加一个空的日程记录
        sysScheduleService.addOneSchedule(uid);

        // 响应结果
        WebUtil.writeJson(resp, Result.ok(uid));
    }

    /**
     * 数据修改后对数据库内的信息进行更新
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void updateSchedule(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // post方式传递，过来的是一个JSON串，转换为一个SysSchedule
        SysSchedule schedule = WebUtil.readJson(req, SysSchedule.class);

        // 调用服务层方法，将信息更新进入数据
        sysScheduleService.updateSchedule(schedule);

        // 响应结果
        WebUtil.writeJson(resp, Result.ok(null));
    }


    /**
     * 根据传入sid删除对应日程信息
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void removeSchedule(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 通过GET方式传递，直接获取对应sid
        Integer sid = Integer.parseInt(req.getParameter("sid"));

        // 调用Service层方法，删除对应日程信息
        sysScheduleService.removeSchedule(sid);

        // 响应结果
        WebUtil.writeJson(resp, Result.ok(null));
    }
}

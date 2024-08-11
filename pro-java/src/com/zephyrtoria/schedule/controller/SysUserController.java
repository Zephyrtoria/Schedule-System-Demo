package com.zephyrtoria.schedule.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zephyrtoria.schedule.common.Result;
import com.zephyrtoria.schedule.common.ResultCodeEnum;
import com.zephyrtoria.schedule.pojo.SysUser;
import com.zephyrtoria.schedule.service.SysUserService;
import com.zephyrtoria.schedule.service.impl.SysUserServiceImpl;
import com.zephyrtoria.schedule.util.MD5Util;
import com.zephyrtoria.schedule.util.WebUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/user/*")
public class SysUserController extends BaseController {
    private final SysUserService userService = new SysUserServiceImpl();

    /**
     * 用户登录的业务接口，根据登录结果将跳转至不同页面
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 1. 接收用户名和密码
        SysUser sysUser = WebUtil.readJson(req, SysUser.class);
        System.out.println("username is: " + sysUser.getUsername());
        System.out.println("userPwd is: " + sysUser.getUserPwd());

        // 2. 调用服务层方法，根据用户名查询用户信息
        SysUser loginUser = userService.login(sysUser);
        Result<Object> result = null;
        if (null == loginUser) {
            // 该用户名不存在
            result = Result.build(null, ResultCodeEnum.USERNAME_ERROR);
        } else if (!loginUser.getUserPwd().equals(MD5Util.encrypt(sysUser.getUserPwd()))) {
            // 用户存在但密码输入错误
            result = Result.build(null, ResultCodeEnum.PASSWORD_ERROR);
        } else {
            // 账号密码都正确
            // 将用户uid和username都响应给客户端，需要避免将密码返回客户端
            loginUser.setUserPwd("");
            // 使用Map来传递参数，因为可能会需要返回多个数据
            Map<Object, Object> data = new HashMap();
            data.put("loginUser", loginUser);
            result = Result.ok(data);  // result.data.get("loginUser") = loginUser

        }
        // 3. 将登录结果响应给客户端
        WebUtil.writeJson(resp, result);
    }

    /**
     * 实现用户注册功能的业务接口，根据注册结果会跳转至不同页面
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void register(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 1. 接收客户端提交的JSON参数，并转换为User对象，获取信息
        SysUser sysUser = WebUtil.readJson(req, SysUser.class);
        // String username = req.getParameter("username");
        // String userPwd = req.getParameter("userPwd");
        System.out.println("username is: " + sysUser.getUsername());
        System.out.println("userPwd is: " + sysUser.getUserPwd());
        // 2. 调用服务层方法，完成注册功能
        int rows = userService.register(sysUser);
        // 3. 返回注册结果
        Result result = Result.ok(null);
        if (rows < 1) {
            // 避免发生验证通过，但是两人同时注册同意用户名的情况
            result = Result.build(null, ResultCodeEnum.USERNAME_USED);
        }
        WebUtil.writeJson(resp, result);

/*        if (rows > 0) {
            resp.sendRedirect("/registerSuccess.html");  后端服务器不再需要页面
        } else {
            resp.sendRedirect("/registerFail.html");
        }*/
    }

    /**
     * 查询所有的用户，并输出在后端控制台
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void checkUsers(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("servlet register run successfully!");
        userService.checkUsers().forEach(System.out::println);
    }

    /**
     * 注册时，接收要注册的用户名，检验用户名是否被占用的业务接口
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void checkUsernameUsed(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 接收用户名
        String username = req.getParameter("username");
        // 调用Service中的方法查询该用户名是否有对应的用户
        SysUser sysUser = userService.findByName(username);

        // 默认响应成功
        Result<Object> result = Result.ok(null);
        // 用户名被占用
        if (null != sysUser) {
            result = Result.build(null, ResultCodeEnum.USERNAME_USED);
        }

        // 将result对象转换为JSON串响应给客户端
        WebUtil.writeJson(resp, result);
    }
}

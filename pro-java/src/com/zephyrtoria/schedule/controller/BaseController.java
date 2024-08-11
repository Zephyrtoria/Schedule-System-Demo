package com.zephyrtoria.schedule.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.lang.reflect.Method;

public class BaseController extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestURI = req.getRequestURI();
        String[] splitStrings = requestURI.split("/");
        String methodName = splitStrings[splitStrings.length - 1];
        Class aClass = this.getClass();
        try {
            Method method = aClass.getDeclaredMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
            method.setAccessible(true);
            method.invoke(this,req, resp);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

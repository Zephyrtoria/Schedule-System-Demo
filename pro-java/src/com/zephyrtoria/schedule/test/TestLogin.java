package com.zephyrtoria.schedule.test;

import com.zephyrtoria.schedule.service.SysUserService;
import com.zephyrtoria.schedule.service.impl.SysUserServiceImpl;
import org.junit.Test;

public class TestLogin {
    @Test
    public void testServiceLogin() {
        SysUserService service = new SysUserServiceImpl();
        System.out.println(service.findByName("Exusiai"));
    }
}

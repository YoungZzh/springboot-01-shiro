package com.itheima.shiro;

import com.itheima.shiro.service.SysUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Springboot01ShiroApplicationTests {

    @Autowired
    SysUserService sysUserService;

    @Test
    void contextLoads() {
        System.out.println(sysUserService.findOne("杰克"));
    }

}

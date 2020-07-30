package com.itheima.shiro;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages= "com.itheima.shiro.mapper")
public class Springboot01ShiroApplication {

    public static void main(String[] args) {
        SpringApplication.run(Springboot01ShiroApplication.class, args);
    }

}

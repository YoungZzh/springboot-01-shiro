package com.itheima.shiro.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.shiro.pojo.SysUserEntity;

/**
 * Author:Young
 * Date:2020/7/28-21:00
 */
public interface SysUserService extends IService<SysUserEntity> {

    SysUserEntity findOne(String name);
}

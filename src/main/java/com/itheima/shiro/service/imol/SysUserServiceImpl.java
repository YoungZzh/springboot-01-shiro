package com.itheima.shiro.service.imol;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.shiro.mapper.SysUserDao;
import com.itheima.shiro.pojo.SysUserEntity;
import com.itheima.shiro.service.SysUserService;
import org.springframework.stereotype.Service;

/**
 * Author:Young
 * Date:2020/7/28-21:16
 */
@Service("sysUserService")
public class SysUserServiceImpl extends ServiceImpl<SysUserDao, SysUserEntity> implements SysUserService {
    @Override
    public SysUserEntity findOne(String name) {
        SysUserEntity entity = baseMapper.findOne(name);
        return entity;
    }
}

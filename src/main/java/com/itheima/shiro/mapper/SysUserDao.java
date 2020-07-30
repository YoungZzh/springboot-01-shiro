package com.itheima.shiro.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itheima.shiro.pojo.SysUserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * Author:Young
 * Date:2020/7/28-16:21
 */
@Mapper
public interface SysUserDao extends BaseMapper<SysUserEntity> {

    //查询用户的所有权限
    List<String> queryAllPerms(Long userId);

    //查询用户的所有权限
    List<Long> queryAllMenuId(Long userId);

    //根据用户id获取部门数据Id列表 ~ 数据权限
    Set<Long> queryDeptIdsByUserId(Long userId);

    List<SysUserEntity> findAll();

    SysUserEntity findOne(String name);

    SysUserEntity selectByUserName(@Param("userName") String userName);

}

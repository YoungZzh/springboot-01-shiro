package com.itheima.shiro.config;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.itheima.shiro.mapper.SysUserDao;
import com.itheima.shiro.pojo.SysUserEntity;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * Author:Young
 * Date:2020/7/25-22:03
 */

public class UserRealm extends AuthorizingRealm {

    @Autowired
    SysUserDao sysUserDao;

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("执行了授权方法————doGetAuthorizationInfo");

        SysUserEntity user = (SysUserEntity) principalCollection.getPrimaryPrincipal();
        Long userId = user.getUserId();
        List<String> perms = Lists.newLinkedList();

        perms = sysUserDao.queryAllPerms(userId);

        //对于每一个授权编码进行的解析拆分
        Set<String> stringPermissions = Sets.newHashSet();
        if (perms != null && !perms.isEmpty()){
            for (String p : perms){
                if (StringUtils.isNotBlank(p)){
                    stringPermissions.addAll(Arrays.asList(StringUtils.split(p.trim(),",")));
                }
            }
        }

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setStringPermissions(stringPermissions);
        return info;
    }

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("执行了认证方法————doGetAuthenticationInfo");

        /*//用户名，密码~ 数据库中取
        String name = "root";
        String password = "123456";*/

        UsernamePasswordToken token = (UsernamePasswordToken)authenticationToken;
        final String userName = token.getUsername();
        final String password = String.valueOf(token.getPassword());

        SysUserEntity entity = sysUserDao.selectByUserName(userName);
        //账户不存在
        if (entity==null){
            throw new UnknownAccountException("账户不存在!");
        }

        //密码认证，shiro做~
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(entity, entity.getPassword(), ByteSource.Util.bytes(entity.getSalt()), getName());
        return info;
    }

    /**
     * 密码验证器~匹配逻辑 ~ 第二种验证逻辑
     * @param credentialsMatcher
     */
    @Override
    public void setCredentialsMatcher(CredentialsMatcher credentialsMatcher) {
        HashedCredentialsMatcher shaCredentialsMatcher = new HashedCredentialsMatcher();
        shaCredentialsMatcher.setHashAlgorithmName(ShiroUtil.hashAlgorithmName);
        shaCredentialsMatcher.setHashIterations(ShiroUtil.hashIterations);
        super.setCredentialsMatcher(shaCredentialsMatcher);
    }
}

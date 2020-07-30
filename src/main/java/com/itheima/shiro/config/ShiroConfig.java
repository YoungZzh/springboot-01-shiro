package com.itheima.shiro.config;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Author:Young
 * Date:2020/7/25-21:56
 */
@Configuration
public class ShiroConfig {

    //1.创建realm 用户对象, 需要自定义类
    @Bean
    public UserRealm getRealm(){
        return new UserRealm();
    }

    //DefaultWebSecurityManager 安全器管理-管理所有的subject
    @Bean(name = "securityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("getRealm") UserRealm userRealm){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(userRealm);
        return securityManager;
    }

    //ShiroFilterFactoryBean 过滤工厂对象
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
        //设置安全管理器
        shiroFilter.setSecurityManager(securityManager);

        //添加shiro的内置过滤器
        /**
         * anon：无需认证就可以访问
         * authc：必须认证了才能访问
         * user：必须拥有 记住我  功能才能访问
         * perms：拥有对某个资源的权限才能访问
         * role：拥有某个角色才能访问
         */
        Map<String,String> filterMap = new LinkedHashMap<>();

        //filter拦截请求是从上到下的，一旦一个满足，直接放行，所以这里先判断授权
        //授权，正常情况下未授权会跳到授权页面
        //filterMap.put("/user/add","anon");

        //拦截
        filterMap.put("/user/**","authc");

        shiroFilter.setFilterChainDefinitionMap(filterMap);

        //设置登录请求
        shiroFilter.setLoginUrl("/toLogin");

        //设置未授权页面
        shiroFilter.setUnauthorizedUrl("/noauth");
        return shiroFilter;
    }

    //关于Shiro的Bean生命周期的管理
    @Bean("lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor(){
        return new LifecycleBeanPostProcessor();
    }

    /**
     *  开启shiro aop注解支持.
     *  使用代理方式;所以需要开启代码支持;
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager){
        AuthorizationAttributeSourceAdvisor advisor=new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }

    /**
     *  解决注解不生效的问题
     * @return
     */
    @Bean
    public static DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator(){
        return new DefaultAdvisorAutoProxyCreator();
    }
}

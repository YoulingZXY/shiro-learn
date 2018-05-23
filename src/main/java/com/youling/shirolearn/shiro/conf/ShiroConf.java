package com.youling.shirolearn.shiro.conf;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.youling.shirolearn.shiro.realm.MyRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ShiroConf {

    //thymeleaf引擎使用shiro需要此类
    @Bean
    public ShiroDialect shiroDialect() {
        return new ShiroDialect();
    }

    //过滤器
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(SecurityManager securityManager){
        //获取过滤器工厂
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //注入安全管理器
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        //设置过滤连
        Map<String,String> filterChainDefinitionMap = new HashMap<>();
        //放行链接，执行顺序优先于下面的规则
        filterChainDefinitionMap.put("/login/queryByUsername/*/*", "anon");
        filterChainDefinitionMap.put("/index", "anon");
        //过滤所有链接
        filterChainDefinitionMap.put("/**","authc" );
        //注入过滤连
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        //强制登录界面
        shiroFilterFactoryBean.setLoginUrl("/login");
        //shiroFilterFactoryBean.setSuccessUrl("");
        return shiroFilterFactoryBean;
    }

    //安全管理器
    @Bean
    public SecurityManager getSecurityManager(Realm realm){
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        defaultWebSecurityManager.setRealm(realm);
        return defaultWebSecurityManager;
    }

    //自定义认证管理器
    @Bean
    public Realm getRealm(HashedCredentialsMatcher hashedCredentialsMatcher) {
        MyRealm myRealm = new MyRealm();
        //注入凭证匹配器
        myRealm.setCredentialsMatcher(hashedCredentialsMatcher);
        return myRealm;
    }

    //凭证匹配器
    @Bean
    public HashedCredentialsMatcher getHashedCredentialsMatcher(){
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        //加密方式
        hashedCredentialsMatcher.setHashAlgorithmName("MD5");
        //散列次数
        hashedCredentialsMatcher.setHashIterations(1024);
        return hashedCredentialsMatcher;
    }
}

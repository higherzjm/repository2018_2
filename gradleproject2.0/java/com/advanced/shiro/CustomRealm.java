package com.advanced.shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.ArrayList;
import java.util.List;

public class CustomRealm extends AuthorizingRealm {
    public CustomRealm() {
        System.out.println("初始化CustomRealm");
    }

    /**
     * 认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        // 从 token 中获取用户身份信息
        String username = (String) token.getPrincipal();
        // 通过 username 从数据库中查询
    
        // 如果查询不到则返回 null
        if(!username.equals("lisi")){//这里模拟查询不到
            return null;
        }
        
        //获取从数据库查询出来的用户密码 
        String dbPassword = "lisi";//这里使用静态数据模拟
        
        //返回认证信息由父类 AuthenticatingRealm 进行认证
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(username, dbPassword, getName());

        return simpleAuthenticationInfo;
    }
    
    /**
     * 授权
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        // 获取身份信息
        String username = (String) principals.getPrimaryPrincipal();
        // 根据身份信息从数据库中查询权限数据
        // 这里使用静态数据模拟
        List<String> permissions = new ArrayList<String>();
        //permissions.add("user:*");
        //permissions.add("department:*");
        permissions.add("user:query");//自定义只有查询权限
        
        // 将权限信息封闭为AuthorizationInfo
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        // 模拟数据，添加 manager、guest 角色
        simpleAuthorizationInfo.addRole("manager");//自定义角色
        simpleAuthorizationInfo.addRole("guest");
        
        for(String permission:permissions){
            simpleAuthorizationInfo.addStringPermission(permission);
        }
        
        return simpleAuthorizationInfo;
    }

}
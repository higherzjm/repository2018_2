package com.advanced.shiro;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;

/**
 * Created by zjm on 2019/1/11.
 */
public class AnnotationTest {
    @RequiresRoles( "manager" )      // 角色校验
    public String save() {
        return "";
    }
    @RequiresPermissions("user:manage")  //权限检验
    public String delete() {
        return "";
    }
}

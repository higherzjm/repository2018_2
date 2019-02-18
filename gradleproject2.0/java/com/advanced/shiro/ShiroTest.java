package com.advanced.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Test;

public class ShiroTest {
    /**
     * 所有都符合
     */
    @Test
    public void test1()  {
        mainMethod("zhangsan","zhangsan","manager","user:create","shiro.ini");
    }

    /**
     * 角色不对
     */
    @Test
    public void test2()  {
        mainMethod("lisi","lisi","manager","user:query","shiro.ini");
    }
    /**
     * 自定义realm
     * 假定只有lisi才能登入
     * 只有查询权限
     * 拥有manager、guest两个角色
     */
    @Test
    public void test3()  {
        mainMethod("lisi","lisi","guest","user:delete", "shiro-customrealm.ini");
    }
    /**
     * 主函数
     * @param userName 账户
     * @param password 密码
     * @param roleName  角色名称
     * @param permittedName 权限名称
     */
    public void mainMethod(String userName,String password,String roleName,String permittedName,String realmName)  {

        // 读取 shiro.ini 文件内容
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:"+realmName);
        IniSecurityManagerFactory factory2 = new IniSecurityManagerFactory("classpath:"+realmName);
        SecurityManager securityManager = factory2.getInstance();//这边会初始化CustomRealm
        SecurityUtils.setSecurityManager(securityManager);

        Subject currentUser = SecurityUtils.getSubject();

        Session session = currentUser.getSession();
        session.setAttribute("someKey", "aValue");
        String value = (String) session.getAttribute("someKey");
        if (value.equals("aValue")) {
            System.out.println("someKey 的值：" + value);
        }

        // 登陆
        UsernamePasswordToken token = new UsernamePasswordToken(userName, password);
        token.setRememberMe(true);
        try {
            currentUser.login(token);//跳转到realm的doGetAuthenticationInfo方法
        } catch (UnknownAccountException uae) {
            System.out.println("用户名不存在:" + token.getPrincipal());
        } catch (IncorrectCredentialsException ice) {
            System.out.println("账户密码 " + token.getPrincipal()  + " 不正确!");
        } catch (LockedAccountException lae) {
            System.out.println("用户名 " + token.getPrincipal() + " 被锁定 !");
        }

        // 认证成功后
        if (currentUser.isAuthenticated()) {

            System.out.println("用户 " + currentUser.getPrincipal() + " 登陆成功！");
            System.out.println("是否记住密码:"+token.isRememberMe());

            //测试角色
            System.out.println("是否拥有 "+roleName+" 角色：" + currentUser.hasRole(roleName));//跳转到realm的doGetAuthorizationInfo方法

            //测试权限
            System.out.println("是否拥有 "+permittedName+" 权限" + currentUser.isPermitted(permittedName));

            Session currentUserSession=currentUser.getSession();
            String currentUserSessionName= (String) currentUserSession.getAttribute("someKey");
            System.out.println("获取当前用户的session名称:"+currentUserSessionName);

            //退出
            currentUser.logout();
        }

    }

    /**
     * 果不想使用代码进行用户进行授权校验等操作，那么可以使用注解代替。
     */
    @RequiresRoles( "manager" )      // 角色校验
    public void save() {
    }
    @RequiresPermissions("user:manage")  // 权限检验
    public void delete() {
    }
}
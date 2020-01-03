package example.core.shiroSecurity.relam;

import java.util.HashSet;
import java.util.Set;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
//import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Component;

import example.core.dao.UserDao;
import example.core.entity.User;
import example.core.shiroSecurity.config.SpringContextHolder;
import example.utils.LogUtils;

@Component
public class MyShiroRealm  extends AuthorizingRealm{
	
// = SpringContextHolder.getBean(UserDao.class);
	
    /**
    *	 授权用户权限
    *	authc:
	*	AuthencationException:
	*	AuthenticationException 异常是Shiro在登录认证过程中，认证失败需要抛出的异常。
	*	AuthenticationException包含以下子类：
	*	    CredentitalsException 凭证异常
	*	        IncorrectCredentialsException 不正确的凭证
	*	        ExpiredCredentialsException 凭证过期
	*	    AccountException 账号异常
	*	        ConcurrentAccessException 并发访问异常（多个用户同时登录时抛出）
	*	        UnknownAccountException 未知的账号
	*	        ExcessiveAttemptsException 认证次数超过限制
	*	        DisabledAccountException 禁用的账号
	*	            LockedAccountException 账号被锁定
	*	    UnsupportedTokenException 使用了不支持的Token
    */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		 User user = (User)SecurityUtils.getSubject().getPrincipal();
		 SimpleAuthorizationInfo info =  new SimpleAuthorizationInfo();
		 
		//获取用户角色
        Set<String> roleSet = new HashSet<String>();
        roleSet.add("100002");
        info.setRoles(roleSet);
        
        //获取用户权限
        Set<String> permissionSet = new HashSet<String>();
        permissionSet.add("权限添加");
        permissionSet.add("权限删除");
        info.setStringPermissions(permissionSet);
        LogUtils.getBussinessLogger().info("我这是日志输出"+user.getName());
		return info;
	}
	 /**
     * 	验证用户身份
     */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
		UserDao userDao = SpringContextHolder.getBean(UserDao.class);
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
        String username = token.getUsername();


        User user = new User();
        user.setName(username);
        User sysuser  = userDao.getUser(user);
        
//        boolean passwordType = SecurityUtil.validatePassword(password,sysuser.getPassword());
        //三个参数分别为 数据库中user表**前台传过来的密码**前天传递来的用户名称
        return new SimpleAuthenticationInfo(sysuser, sysuser.getPassword(), getName());
	}
	/**
	 * 	重写shiro的登陆校验方法，分情况看是校验密码还是校验其他字段
	 */
	@Override
    protected void assertCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) throws AuthenticationException {
		UsernamePasswordToken tokens = (UsernamePasswordToken) token;
		super.assertCredentialsMatch(tokens, info);
	}
	    		
}

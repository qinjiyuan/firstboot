package example.core.shiroSecurity.AuthMatcher;

import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;

import example.core.shiroSecurity.relam.UsernamePasswordToken;
import example.utils.SecurityUtil;

public class UserCredentialMatcher extends SimpleCredentialsMatcher{
	/**
	 * 	重写密码的验证方法
	 */
	@Override
	public boolean doCredentialsMatch(AuthenticationToken authcToken, AuthenticationInfo info) {
		UsernamePasswordToken token = (UsernamePasswordToken)authcToken;
		String passwordcommit =String.valueOf((char[]) token.getCredentials());
		String passworddatabase = String.valueOf(info.getCredentials());
		boolean status = SecurityUtil.validatePassword(passwordcommit, passworddatabase);
		if(!status) {
			 throw new AccountException("帐号或密码不正确！");
		}else {
			return status;
		}
		
	}
}

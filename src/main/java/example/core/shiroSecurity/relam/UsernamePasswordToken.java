package example.core.shiroSecurity.relam;

public class UsernamePasswordToken  extends org.apache.shiro.authc.UsernamePasswordToken {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//登陆类型
	private String loginType;
	
	//该登陆类型的验证码
	private String code;
	
	public UsernamePasswordToken() {
		super();
	}
	/**
	 * 
	 * @param username
	 * @param password
	 * @param rememberMe
	 * @param host
	 * @param loginType
	 * @param code
	 */
	public UsernamePasswordToken(String username, char[] password,
			boolean rememberMe, String host, String loginType, String code) {
		super(username, password, rememberMe, host);
		this.loginType = loginType;
		this.code = code;
	}

	public String getLoginType() {
		return loginType;
	}

	public void setLoginType(String loginType) {
		this.loginType = loginType;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	
}

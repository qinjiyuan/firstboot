package example.core.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import example.core.entity.User;
import example.core.service.UserService;
import example.core.shiroSecurity.relam.UsernamePasswordToken;
import example.utils.LogUtils;


@Controller
public class LoginController {
	@Autowired
	private UserService userService;
	/**
	 * 登陆请求处理
	 * @param username
	 * @param password
	 * @param vcode
	 * @param rememberMe
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/login")
//	@RequestMapping(value="/login",method=RequestMethod.POST)
    public String login(String username, String password,String vcode,Boolean rememberMe){
		try {
			 System.out.println(username);
		        UsernamePasswordToken token = new UsernamePasswordToken(username, password.toCharArray(),rememberMe,"","","");
		        SecurityUtils.getSubject().login(token);
		        LogUtils.getBussinessLogger().info("loginSuccess"+username);
		}catch(AccountException e) {
			return "loginFail";
		}
       
        
        
        return "loginSuccess";
    }

    @RequestMapping(value="/index",method=RequestMethod.GET)
    public String home(){
    	long begin  = System.currentTimeMillis();
        Subject subject = SecurityUtils.getSubject();
        User principal = (User)subject.getPrincipal();
        userService.saveUser(50000);
        long end = System.currentTimeMillis();
        long times = end - begin;
        System.out.println("总耗时为"+times);
        return "index";
    }
    
    @RequestMapping(value="/logintest",method=RequestMethod.GET)
    public String logintest(){
    	Subject subject = SecurityUtils.getSubject();
    	User principals =  (User)subject.getPrincipal();
    	userService.multiThread();//多线程测试
    	if(principals == null) {
    		
    	}else {
    		
    	}
        return "logintest";
    }
    
    @RequestMapping(value="/loginOut",method=RequestMethod.GET)
    public String loginOut(){
    	Subject subject = SecurityUtils.getSubject();
    	subject.logout();
        return "logintest";
    }
}

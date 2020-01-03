package example.core.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import example.core.dao.UserDao;
import example.core.entity.User;
import example.core.service.UserService;

@RestController
@RequestMapping("/core")
public class UserController {
	 private static final Logger LOG = LoggerFactory.getLogger(UserController.class);
	@Autowired
	private UserService userService;
	
	@RequestMapping("/getUser")
	public Object getUser() {
		User user = new User();
		user.setName("JackMa");
		User users = userService.getUser(user);
		
		User userBack = userService.getUserById(users.getId());
		LOG.info("日志在这里");
		return userBack;
	}
}

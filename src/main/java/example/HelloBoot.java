package example;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloBoot {
	@RequestMapping("/hello")
	public String printHello() {
		System.out.println("Hello");
		return "Hello";
	}
}

package example.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringContextUtil implements ApplicationContextAware{
	
	private static ApplicationContext applicationContext;
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		if(applicationContext == null) {
			SpringContextUtil.applicationContext = applicationContext;
		}
	}
	
	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}
	
	public static Object getBeanByName(String name) {
		return getApplicationContext().getBean(name);
	}
	
	public static <T> T getBeanByClass(Class<T> clazz) {
		return getApplicationContext().getBean(clazz);
	}
	
	public static<T> T getBeanByNameAndClass(String name,Class<T>clazz) {
		return getApplicationContext().getBean(name,clazz);
	}
}

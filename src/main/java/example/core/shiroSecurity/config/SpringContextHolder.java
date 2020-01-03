package example.core.shiroSecurity.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

/**
 * 该方法用于在非SERVICE层，非CONTROLLER 中获取DAO进行查询
 * @author BestSteven
 *
 */
@Service
@Lazy(false)
public class SpringContextHolder  implements ApplicationContextAware, DisposableBean  {
	private static ApplicationContext applicationContext;
	
	@Override
	public void destroy() throws Exception {
		SpringContextHolder.clearHolder();
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		try {
			
		}catch(Exception e) {
			new RuntimeException(e);
		}
		SpringContextHolder.applicationContext = applicationContext;
	}

	public static ApplicationContext getApplicationContext() {
		assertContextInjected();
		return applicationContext;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String name) {
		assertContextInjected();
		return (T) applicationContext.getBean(name);
	}
	
	public static <T>T getBean(Class<T>clazz){
		assertContextInjected();
		return applicationContext.getBean(clazz);
	}
	
	public static void clearHolder() {
		applicationContext =null;
	}
	
	 /**
     * 检查ApplicationContext不为空.
     */
    private static void assertContextInjected() {
//        Validate.validState(applicationContext != null, "applicaitonContext属性未注入, 请在applicationContext.xml中定义SpringContextHolder.");
    	if(applicationContext == null) {
    	}
    }
}

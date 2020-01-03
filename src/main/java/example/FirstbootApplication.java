package example;

//import org.apache.catalina.Context;
//import org.apache.catalina.connector.Connector;
//import org.apache.tomcat.util.descriptor.web.SecurityCollection;
//import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
//import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
//import org.springframework.context.annotation.Bean;

@EnableEurekaClient
@SpringBootApplication
@EnableCaching //开启缓存服务
public class FirstbootApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(FirstbootApplication.class, args);
	}
	
	@Override
	//为了打包springboot项目，打成jar包
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(this.getClass());
    }
	
//	 @Bean
//	  public TomcatServletWebServerFactory servletContainer() {
//	      TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory() {
//	         @Override
//	         protected void postProcessContext(Context context) {
//	            SecurityConstraint constraint = new SecurityConstraint();
//	            constraint.setUserConstraint("CONFIDENTIAL");
//	            SecurityCollection collection = new SecurityCollection();
//	            collection.addPattern("/*");
//	            constraint.addCollection(collection);
//	            context.addConstraint(constraint);
//	         }
//	      };
//	      tomcat.addAdditionalTomcatConnectors(httpConnector());
//	      return tomcat;
//	   }

//	   @Bean
//	   public Connector httpConnector() {
//	      Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
//	      connector.setScheme("http");
//	      //Connector监听的http的端口号
//	      connector.setPort(80);
//	      connector.setSecure(false);
//	      //监听到http的端口号后转向到的https的端口号
//	      connector.setRedirectPort(443);
//	      return connector;
//	   }

}


package example.datasource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SystemParameter {
	@Value("${systemparameter.security.salt}")  
	private static  String salt = "sdjfkdsfjdslfjewiefkldfdslflds";

	public String getSalt() {
		return salt;
	}
	
	
}

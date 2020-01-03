package example.datasource;

import java.time.Duration;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;


@Configuration
@EnableCaching
public class RedisCacheConfig extends CachingConfigurerSupport{
	//下面的版本为springboot1.x版本
//	 	@Bean
//	    public CacheManager cacheManager(@SuppressWarnings("rawtypes") RedisTemplate redisTemplate) {
//	        RedisCacheManager rm = new RedisCacheManager(redisTemplate,"");
//	        rm.setDefaultExpiration(30l);// 设置缓存时间
//	        return rm;
//	    }
	 	/**
	 	 * 	此版本适用于Redis2.xRealease
	 	 * @param redisConnectionFactory
	 	 * @return
	 	 */
	 	@Bean
	    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
	        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofHours(1)); // 设置缓存有效期一小时
	        return RedisCacheManager.builder(RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory)).cacheDefaults(redisCacheConfiguration).build();
	    }

	    // @Bean
	    // public KeyGenerator myKeyGenerator(){
	    // return new KeyGenerator() {
	    // @Override
	    // public Object generate(Object target, Method method, Object... params) {
	    // StringBuilder sb = new StringBuilder();
	    // sb.append(target.getClass().getName());
	    // sb.append(method.getName());
	    // for (Object obj : params) {
	    // sb.append(obj.toString());
	    // }
	    // return sb.toString();
	    // }
	    // };
	    //
	    // }

	    @Bean
	    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory factory) {
	        StringRedisTemplate template = new StringRedisTemplate(factory);
	        @SuppressWarnings({ "rawtypes", "unchecked" })
	        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
	        ObjectMapper om = new ObjectMapper();
	        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
	        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
	        jackson2JsonRedisSerializer.setObjectMapper(om);
	        template.setValueSerializer(jackson2JsonRedisSerializer);
	        template.afterPropertiesSet();
	        return template;
	    }
}

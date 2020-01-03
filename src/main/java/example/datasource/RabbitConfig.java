package example.datasource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;
import org.springframework.messaging.handler.annotation.support.MessageHandlerMethodFactory;


//@Configuration
public class RabbitConfig  implements RabbitListenerConfigurer {
	private static final Logger log= LoggerFactory.getLogger(RabbitConfig.class);

	
	//用于获取配置文件中的属性
	@Autowired
    private Environment env;
 
    @Autowired
    private CachingConnectionFactory connectionFactory;
    
    // 管理RabbitMQ监听器listener的容器工厂
    @Autowired
    private SimpleRabbitListenerContainerFactoryConfigurer factoryConfigurer;
	
    @Bean(name="singleListenerContainer")
    public SimpleRabbitListenerContainerFactory listenerContainer() {
    	 SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
         factory.setConnectionFactory(connectionFactory);
         factory.setMessageConverter(new Jackson2JsonMessageConverter());
         factory.setConcurrentConsumers(1);
         factory.setMaxConcurrentConsumers(1);
         factory.setPrefetchCount(1);
         factory.setTxSize(1);
         factory.setAcknowledgeMode(AcknowledgeMode.AUTO);
         return factory;
    }
    
    @Bean(name="multiListenerContainer")
    public SimpleRabbitListenerContainerFactory multiListenerContainer() {
    	SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
    	factoryConfigurer.configure(factory, connectionFactory);
    	factory.setMessageConverter(new Jackson2JsonMessageConverter());
    	factory.setAcknowledgeMode(AcknowledgeMode.NONE);
    	
    	factory.setConcurrentConsumers(env.getProperty("spring.rabbitmq.listener.concurrency",int.class));
    	factory.setMaxConcurrentConsumers(env.getProperty("spring.rabbitmq.listener.max-concurrency",int.class));
    	factory.setPrefetchCount(env.getProperty("spring.rabbitmq.listener.prefetch",int.class));
    	
    	
    	return factory;
    }
    @Bean
    public RabbitTemplate rabbitTemplate() {
		 connectionFactory.setPublisherConfirms(true);
	     connectionFactory.setPublisherReturns(true);
	     RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
	     rabbitTemplate.setMandatory(true);
	     rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
			@Override
			public void confirm(CorrelationData correlationData, boolean ack, String cause) {
				log.info("消息发送成功:correlationData({}),ack({}),cause({})",correlationData,ack,cause);

			}
		 });
	     rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback() {
			@Override
			public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
				 log.info("消息丢失:exchange({}),route({}),replyCode({}),replyText({}),message:{}",exchange,routingKey,replyCode,replyText,message);
			}
		});
	     return rabbitTemplate;
    }
    

    
    
    @Bean(name="logUserQueue")
    public Queue logUserQueue() {
    	return new Queue(env.getProperty("log.user.queue.name"),true);
    }
    
    @Bean
    public DirectExchange logUserExchange() {
    	return new DirectExchange(env.getProperty("log.user.exchange.name"),true,true);
    }
    
    @Bean
    public Binding logUserBinding() {
    	return BindingBuilder.bind(logUserQueue()).to(logUserExchange()).with(env.getProperty("log.user.routing.key.name"));
    }
    
    
    
    
    /**
	 * 	创建一个消息队列
	 * 	此处的队列需要在RABBITMQ中存在
	 * 	用于测试的队列
	 * @return
	 */
	@Bean
	public Queue helloQueue() {
		return new Queue("hello");
	}

	@Override
	public void configureRabbitListeners(RabbitListenerEndpointRegistrar registrar) {
		 registrar.setMessageHandlerMethodFactory(messageHandlerMethodFactory());
		
	}
	@Bean
    MessageHandlerMethodFactory messageHandlerMethodFactory() {
        DefaultMessageHandlerMethodFactory messageHandlerMethodFactory = new DefaultMessageHandlerMethodFactory();
        messageHandlerMethodFactory.setMessageConverter(consumerJackson2MessageConverter());
        return messageHandlerMethodFactory;
    }

    @Bean
    public MappingJackson2MessageConverter consumerJackson2MessageConverter() {
        return new MappingJackson2MessageConverter();
    }
    
    /**
     * JSON的反序列化
     * @param connectionFactory
     * @return
     */
    @Bean
    public RabbitListenerContainerFactory<?> rabbitListenerContainerFactory(ConnectionFactory connectionFactory){
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(new Jackson2JsonMessageConverter()); 
        return factory;
    }
}

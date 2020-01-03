package example.rabbitmq;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.utils.SerializationUtils;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;



/**
 * @RabbitListener 可以标注在类上面，需配合 @RabbitHandler 注解一起使用
 * @RabbitListener 标注在类上面表示当有收到消息的时候，就交给 @RabbitHandler 的方法处理，具体使用哪个方法处理，根据 MessageConverter 转换后的参数类型
 * @author BestSteven
 *
 */
@Component
public class Receiver {
	@RabbitHandler
	@RabbitListener(queues = "hello")
	public void process(String hello) {
		System.err.println("Receiver:" + hello);
	}
	
	/**
	 * 	使用 @Payload 和 @Headers 注解可以消息(Message)中的 body 与 headers 信息
	 * @param message
	 */
	@RabbitListener(queues = "${log.user.queue.name}",containerFactory = "singleListenerContainer")
	 public void consumeUserLogQueue(Message message){
		
		    try {
//		      User user =   (User) SerializationUtils.deserialize(message.getBody());
//		      System.out.println(user.getAge());
		    }catch (Exception e){
		        e.printStackTrace();
		    }
		}
	
	
	//适合处理字符串信息
//    public void consumeUserLogQueue(Message message){
//		
//        try {
//          System.out.println( new String(message.getBody(),"UTF-8"));
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }
	
	
	
	//比较适合于同一个应用中的反序列化
//	 public void consumeUserLogQueue(Message message){
//			
//	        try {
//	          User user =   (User)getObjectFromBytes(message.getBody());
//	          System.out.println(user.getAge());
//	        }catch (Exception e){
//	            e.printStackTrace();
//	        }
//	    }
//	
//	 public  Object getObjectFromBytes(byte[] objBytes) throws Exception {
//	        if (objBytes == null || objBytes.length == 0) {
//	            return null;
//	        }
//	        ByteArrayInputStream bi = new ByteArrayInputStream(objBytes);
//	        ObjectInputStream oi = new ObjectInputStream(bi);
//	        return oi.readObject();
//	    }
}

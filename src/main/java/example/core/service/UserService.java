package example.core.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import example.core.dao.UserDao;
import example.core.entity.User;

@Service
//@Transactional(readOnly = false)
public class UserService {
	@Autowired
	private UserDao userDao;
	
	
	/**
	 * @Cacheable 可以标注在方法或者类上，
	 * @param user
	 * @return
	 */
	@Cacheable(value="user",key="targetClass + methodName +#p0")
	public User getUser(User user) {
		return userDao.getUser(user);
	}
	
	@Cacheable(value="user",key="'task' +#id")
	public User getUserById(String id) {
		return userDao.getUserById(id);
	}
	
	@CachePut(value = "user", key = "#p0")
	public void insert(User user) {
		userDao.insert(user);
	}
	
	/**
	 * 	清楚名字为name的缓存
	 * @param name
	 */
    @CacheEvict(value = "user", key = "#p0")
    public void removeUser(String name) {
        System.out.println("删除数据" + name + "，同时清除对应的缓存");
    }
    
    public void saveUser(int maxloop) {
    	User user = new User();
    	for(int i = 0;i<maxloop;i++) {
    		user.setName("qjy"+i);
    		user.setAge("");
    		user.setHobby("hobby"+i);
    		user.setPassword("1767b968fa2b707181bd03eb2aba04e71f7dc7c3c11ab42a46924af8");
    		user.setSex("男");
    		user.setDelFlag("0");
    		user.preInsert();
    		user.setRemarks("");
    		userDao.insert(user);
    	}
    }
    
    public static String uuid() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
    
    
    public void multiThread() {
    	long begin  = System.currentTimeMillis();
    	Thread t1 = new Thread(new myWriteThread());
        t1.start();
        Thread t2 = new Thread(new myWriteThread());
        t2.start();
        Thread t3 = new Thread(new myWriteThread());
        t3.start();
        Thread t4 = new Thread(new myWriteThread());
        t4.start();
        Thread t5 = new Thread(new myWriteThread());
        t5.start();
        long end = System.currentTimeMillis();
        
        long times = end - begin;
        System.out.println("总耗时为"+times);
    }
	
    
    class myWriteThread implements Runnable{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			saveUser(100000);
		}
    	
    }
	
	
}

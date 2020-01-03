package example.core.dao;

import org.apache.ibatis.annotations.Mapper;

import example.core.entity.User;
@Mapper
public interface UserDao {
	public User getUser(User user);
	
	
	public User getUserById(String id);
	
	public void update(User user);
	
	public void insert(User user);
}

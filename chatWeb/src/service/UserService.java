package service;
 
import domain.User;
 
public interface UserService {
	//查验登录
	public String checkLogin(User user);
	//注册用户
	public String register(User user);
}

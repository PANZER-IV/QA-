package dao;
 
import java.util.List;
 
import domain.User;
 
public interface UserDao {
	//找到所有元素,用来验证登录信息
	public  List<User> findAll();
	//插入元素,用来注册
	public void insertElement(User people);
	
}

package dao;
 
import java.util.List;
 
import domain.User;
 
public interface UserDao {
	//�ҵ�����Ԫ��,������֤��¼��Ϣ
	public  List<User> findAll();
	//����Ԫ��,����ע��
	public void insertElement(User people);
	
}

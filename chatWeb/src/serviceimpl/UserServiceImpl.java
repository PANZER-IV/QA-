package serviceimpl;
 
import java.util.List;
 
import dao.UserDao;
import daoimpl.UserDaoImpl;
import domain.User;
import service.UserService;
 
public class UserServiceImpl implements UserService {
 
	UserDao dao=new UserDaoImpl();
	
	/*
	 * ��Ҫ���߼�ʵ��
	 */
	@Override
	public String checkLogin(User user) {
		List<User> list = dao.findAll();
		for(int i=0;i<list.size();i++) {//��������
			User user2=list.get(i);
			if(user2.getUsername().equals(user.getUsername()) && user2.getPassword().equals(user.getPassword())) {
				return "��¼�ɹ�";
			}
		}
		return "��¼ʧ��,�����������";
	}
 
	@Override
	public String register(User user) {
		List<User> list = dao.findAll();
		for(int i=0;i<list.size();i++) {
			User user2=list.get(i);
			if(user2.getUsername().equals(user.getUsername())) {
				return "ע��ʧ�ܣ����û����Ѵ��ڣ�";
			}
		}
		dao.insertElement(user);
		return "ע��ɹ�";
	}
 
}

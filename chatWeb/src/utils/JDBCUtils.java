package utils;
 
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
 
 
 
/**
 * ��ΪҪ����õ����µĲ��裬����дһ��������������jdbc
 * @author Justin
 *�����ﲻҪ�������import com.mysql.jdbc.PreparedStatement;�Ǵ��
 */
public class JDBCUtils {
	/**
	 * ���jdbc����
	 */
	static Connection connection=null;
	public static Connection getConnetion() throws Exception {
		//����jdbc����
		Class.forName("com.mysql.jdbc.Driver");
		//�����������ݿ��·��
		//String url = "jdbc:mysql://192.168.1.100:3306/chatbotdb?user=faces&password=12345&useSSL=true";
		//ͨ��url��������ݿ������
	    String url= "jdbc:mysql://192.168.43.185:3306/chatbotdb?characterEncoding=utf8&useSSL=true&serverTimezone=GMT";  
		String username = "faces";  
		String password = "123456";  
		connection = DriverManager.getConnection(url,username,password);
		return connection;
	}
	
	public static void close(Connection connection,PreparedStatement ps,ResultSet rs) {
		//һ��Ҫȷ���ر����ӣ����¹رղ����ǲ��չٷ��ĵ��ģ���Ȩ����
		if(rs!=null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(ps!=null) {
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(connection!=null) {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}

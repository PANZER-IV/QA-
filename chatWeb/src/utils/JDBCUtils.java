package utils;
 
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
 
 
 
/**
 * 因为要多次用到以下的步骤，所以写一个工具类来操作jdbc
 * @author Justin
 *在这里不要导错包，import com.mysql.jdbc.PreparedStatement;是错的
 */
public class JDBCUtils {
	/**
	 * 获得jdbc连接
	 */
	static Connection connection=null;
	public static Connection getConnetion() throws Exception {
		//加载jdbc驱动
		Class.forName("com.mysql.jdbc.Driver");
		//创建连接数据库的路径
		//String url = "jdbc:mysql://192.168.1.100:3306/chatbotdb?user=faces&password=12345&useSSL=true";
		//通过url获得与数据库的连接
	    String url= "jdbc:mysql://192.168.43.185:3306/chatbotdb?characterEncoding=utf8&useSSL=true&serverTimezone=GMT";  
		String username = "faces";  
		String password = "123456";  
		connection = DriverManager.getConnection(url,username,password);
		return connection;
	}
	
	public static void close(Connection connection,PreparedStatement ps,ResultSet rs) {
		//一定要确保关闭连接，以下关闭步骤是参照官方文档的，有权威性
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

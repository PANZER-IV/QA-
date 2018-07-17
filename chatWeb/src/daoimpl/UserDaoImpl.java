package daoimpl;
 
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
 
import dao.UserDao;
import domain.User;
import utils.JDBCUtils;
 
public class UserDaoImpl implements UserDao {
 
	Connection connection=null;
	PreparedStatement ps=null;
	ResultSet rs=null;
	
	/**
	 * dao层，从数据库里面取出数据
	 */
	@Override
	public List<User> findAll() {
		List<User> list=null;
		try {
			//通过工具类获得连接
			connection = JDBCUtils.getConnetion();
			//通过连接对象获取操作数据库的对象
			String sql="SELECT * FROM user;";//查询sql语句
			ps=connection.prepareStatement(sql);
			//返回查询结果集
			rs=ps.executeQuery();
			//遍历rs，并封装数据
			list=new ArrayList<User>();
			while(rs.next()) {
				User user=new User();
				user.setUsername(rs.getString(2));//索引从1开始，id参数不用取
				user.setPassword(rs.getString(3));
				list.add(user);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			JDBCUtils.close(connection, ps, rs);//关闭连接
			
		}
		return list;
	}
 
	@Override
	public void insertElement(User people) {
		try {
			connection=JDBCUtils.getConnetion();
			String sql="INSERT INTO user(username,password) VALUES(?,?);";//插入语句
			ps=connection.prepareStatement(sql);
			ps.setString(1,people.getUsername());//使用prepareStatement可以防止sql注入
			ps.setString(2,people.getPassword());
			//执行更新语句
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			JDBCUtils.close(connection, ps, rs);
		}
	}
 
}

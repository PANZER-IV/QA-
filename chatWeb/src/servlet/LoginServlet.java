package servlet;
 
import java.io.IOException;
import java.io.PrintWriter;
 
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
import domain.User;
import service.UserService;
import serviceimpl.UserServiceImpl;
 
 
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");//解决请求乱码(post)
		response.setCharacterEncoding("UTF-8");//解决响应乱码,下面要以字符流输出（若字节流输出则要再次编码）
		String username=request.getParameter("username");
		String password=request.getParameter("password");
		String sign=request.getParameter("sign");
		PrintWriter out=response.getWriter();
		
		//把传来的数据封装进javabean中
//		User user=new User();
//		user.setUsername(username);
//		user.setPassword(password);
//		UserService service=new UserServiceImpl();
		if("1".equals(sign)) {//登录操作(设置了一个标记)
//			String loginInfo=service.checkLogin(user);
//			out.print(loginInfo);
			if(username.equals("user1")&&password.equals("123456"));
			out.print("登录成功");
		}
		else if("2".equals(sign)) {//注册操作
//			String registerInfo=service.register(user);
//			out.print(registerInfo);
		}
		
		System.out.println(username);//在控制台输出
		System.out.println(password);
		System.out.println(sign);
		
		//request.getSession().setAttribute("username", username);
	
	}
 
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
 
}

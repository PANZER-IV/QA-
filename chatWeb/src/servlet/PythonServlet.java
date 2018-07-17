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
import utils.PythonUtils;
 
 
@WebServlet("/PythonServlet")
public class PythonServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");//解决请求乱码(post)
		response.setCharacterEncoding("UTF-8");//解决响应乱码,下面要以字符流输出（若字节流输出则要再次编码）
		String sign=request.getParameter("sign");
		String username=request.getParameter("username");
		PrintWriter out=response.getWriter();
		
		int result,result1;
		String prepare_true_qfile = "d:/java/chatWeb/"+username+"/question.txt";
    	String prepare_true_afile = "d:/java/chatWeb/"+username+"/answer.txt";
    	String p_t_savepath="d:/java/chatWeb/"+username;
    	String predict1_savepath="d:/java/chatWeb/"+username;
  
    	String cmd1="python D:\\pycham\\prepare_process\\Src\\prepare_true.py"
    	 +" "+prepare_true_qfile+" "+
    			 prepare_true_afile+" "+p_t_savepath;
        String cmd2="python D:\\pycham\\prepare_process\\Src\\predict1.py"+" "+predict1_savepath;
        
		PythonUtils pyu=new PythonUtils();
		
		if("5".equals(sign)) {
			pyu.prepare(cmd1);
			result1=pyu.prepare(cmd2);
			System.out.println(result1);
			if(result1==1) {
				String loginInfo="数据预处理成功！";
				out.print(loginInfo);
			}else {
				String loginInfo="数据预处理失败！";
				out.print(loginInfo);
			}
		}
		System.out.println(sign);
		
	
	}
 
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
 
}

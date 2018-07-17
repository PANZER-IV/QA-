package servlet;
 
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.User;
import service.UserService;
import serviceimpl.UserServiceImpl;
import utils.FileUtils;
import utils.PythonUtils;
 
 
@WebServlet("/getAnswerServlet")
public class getAnswerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");//解决请求乱码(post)
		response.setCharacterEncoding("UTF-8");//解决响应乱码,下面要以字符流输出（若字节流输出则要再次编码）
		String sign=request.getParameter("sign");
		String username=request.getParameter("username");
		String question=request.getParameter("question");
		PrintWriter out=response.getWriter();
		
		String predict2_savepath="d:/java/TestPython/test";
   	    String predict_q="应用场景容器集群管理";
		String cmd3="python D:\\pycham\\prepare_process\\Src\\predict2.py"
		    	 +" "+predict2_savepath+" "+question;
	
		PythonUtils qyu=new PythonUtils();
		if("6".equals(sign)) {
			out.print("1");
			out.print("2");
			//String result=qyu.predict(cmd3);
			//String loginInfo=result;
			//out.print(result);
			//out.print("answer:\n"+filesInfo1);
		}
		System.out.println(sign);
		
	
	}
 
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
 
}

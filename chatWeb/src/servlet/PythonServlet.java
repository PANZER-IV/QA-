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
		request.setCharacterEncoding("UTF-8");//�����������(post)
		response.setCharacterEncoding("UTF-8");//�����Ӧ����,����Ҫ���ַ�����������ֽ��������Ҫ�ٴα��룩
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
				String loginInfo="����Ԥ����ɹ���";
				out.print(loginInfo);
			}else {
				String loginInfo="����Ԥ����ʧ�ܣ�";
				out.print(loginInfo);
			}
		}
		System.out.println(sign);
		
	
	}
 
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
 
}

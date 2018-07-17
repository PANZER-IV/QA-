package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import utils.PythonUtils;

@WebServlet("/FindQAServlet")
public class FindQAServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public FindQAServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");//�����������(post)
		response.setCharacterEncoding("UTF-8");//�����Ӧ����,����Ҫ���ַ�����������ֽ��������Ҫ�ٴα��룩
		String logo=request.getParameter("logo");
		String get_qMessage=request.getParameter("qMessage");
		String get_aMessage=request.getParameter("aMessage");
		String username=request.getParameter("username");
		PrintWriter out=response.getWriter();
		
		if(get_aMessage.isEmpty()) {
			get_aMessage="answer";
		}
		
		int logo1=Integer.parseInt(logo);
	    String question_address="d:/java/chatWeb/"+username+"/question.txt";
	    String answer_address="d:/java/chatWeb/"+username+"/answer.txt";
	    String html_address="d:/java/chatWeb/"+username+"/html_li.txt";
	    //String html_address="�����֪����ʲô��ַ";
	    
	    System.out.println(logo1+"*\n"+get_qMessage+"*\n"+get_aMessage+"*\n"+username+"*");
	    
	    String cmd="python D:\\pycham\\prepare_process\\Src\\find.py"
	       	 +" "+logo+" "+
	       	question_address+" "+answer_address+" "+html_address+" "
	       	+get_qMessage+" "+get_aMessage;
	    
	    PythonUtils pyu=new PythonUtils();
	    
	    String res=pyu.predict(cmd);
	    System.out.println(res);
	    
	    out.print(res);
	    
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

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
 
 
@WebServlet("/ShowInfoServlet")
public class ShowInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");//�����������(post)
		response.setCharacterEncoding("UTF-8");//�����Ӧ����,����Ҫ���ַ�����������ֽ��������Ҫ�ٴα��룩
		String sign=request.getParameter("sign");
		String username=request.getParameter("username");
		PrintWriter out=response.getWriter();
		
		HashMap<String,Integer> questionfilemap=new HashMap<String,Integer>();
		HashMap<String,Integer> answerfilemap=new HashMap<String,Integer>();
		
		FileUtils f=new FileUtils();
		questionfilemap=f.getFileName("D:/java/chatWeb/"+username);
		//answerfilemap=f.getFileName("D:/java/chatWeb/"+username);
		
		ArrayList qlist=new ArrayList();
		//ArrayList alist=new ArrayList();
		
		
		for(String key:questionfilemap.keySet()) {
			//System.out.println(key+":"+questionfilemap.get(key));
			qlist.add(key);
		}
		
//		for(String key:answerfilemap.keySet()) {
//			//System.out.println(key+":"+answerfilemap.get(key));
//			alist.add(key);
//		}
		
		if("4".equals(sign)) {
			String filesInfo="*",filesInfo1="*";
			for(int j=0;j<qlist.size();j++) {
				filesInfo=qlist.get(j)+"\n"+filesInfo;
			}
//			for(int j=0;j<alist.size();j++) {
//				filesInfo1=alist.get(j)+"\n"+filesInfo1;
//			}
			//String loginInfo="�Ҿ����Կ��ܲ��ܴ���ȥ";
			System.out.println(filesInfo);
			out.print(filesInfo+"\n");
			//out.print("answer:\n"+filesInfo1);
		}
		System.out.println(sign);
		
	
	}
 
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
 
}

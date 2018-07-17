package servlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import utils.PythonUtils;
@WebServlet(urlPatterns="/UsePythonServlet", asyncSupported=true)
public class UsePythonServlet extends HttpServlet {
	private static final long serialVersionUID = -8016328059808092454L;
	
	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#service(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		  	resp.setContentType("text/html;charset=UTF-8");
	        PrintWriter out = resp.getWriter();
	        //out.println("����Servlet��ʱ�䣺" + new Date() + ".");
	        //out.flush();
   
	        //�����߳���ִ��ҵ����ã������为�������Ӧ�����߳��˳�
	        final AsyncContext ctx = req.startAsync();
	        ctx.setTimeout(200000);
	        new Work(ctx,req,resp).start();
	        //out.println("����Servlet��ʱ�䣺" + new Date() + ".");
	        //out.flush();
	}
}
 
class Work extends Thread{
	private AsyncContext context;
	HttpServletRequest request;
	HttpServletResponse response;
	
	public Work(AsyncContext context,HttpServletRequest request,HttpServletResponse response){
		this.context = context;
		this.request=request;
		this.response=response;
	}
	@Override
	public void run() {
		try {
			request.setCharacterEncoding("UTF-8");//�����������(post)
			response.setCharacterEncoding("UTF-8");//�����Ӧ����,����Ҫ���ַ�����������ֽ��������Ҫ�ٴα��룩
			String sign=request.getParameter("sign");
			String username=request.getParameter("username");
			String question=request.getParameter("question");
			String predict2_savepath="d:/java/chatWeb/"+username;
	   	    //String predict_q="Ӧ�ó���������Ⱥ����";
			String cmd3="python D:\\pycham\\prepare_process\\Src\\predict2.py"
			    	 +" "+predict2_savepath+" "+question;
							
			//Thread.sleep(2000);//���߳�����2s��ģ�ⳬʱ����
			PrintWriter wirter = context.getResponse().getWriter();	
			PythonUtils qyu=new PythonUtils();
			if("6".equals(sign)) {
				String result=qyu.predict(cmd3);
				//String loginInfo=result;
				wirter.write(result);
				//out.print("answer:\n"+filesInfo1);
			}
			System.out.println(sign);
			
		
			//wirter.write("�ӳ����");
			wirter.flush();
			context.complete();
		} catch (IOException e) {
			
		}
	}
}

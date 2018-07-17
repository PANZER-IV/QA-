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
	        //out.println("进入Servlet的时间：" + new Date() + ".");
	        //out.flush();
   
	        //在子线程中执行业务调用，并由其负责输出响应，主线程退出
	        final AsyncContext ctx = req.startAsync();
	        ctx.setTimeout(200000);
	        new Work(ctx,req,resp).start();
	        //out.println("结束Servlet的时间：" + new Date() + ".");
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
			request.setCharacterEncoding("UTF-8");//解决请求乱码(post)
			response.setCharacterEncoding("UTF-8");//解决响应乱码,下面要以字符流输出（若字节流输出则要再次编码）
			String sign=request.getParameter("sign");
			String username=request.getParameter("username");
			String question=request.getParameter("question");
			String predict2_savepath="d:/java/chatWeb/"+username;
	   	    //String predict_q="应用场景容器集群管理";
			String cmd3="python D:\\pycham\\prepare_process\\Src\\predict2.py"
			    	 +" "+predict2_savepath+" "+question;
							
			//Thread.sleep(2000);//让线程休眠2s钟模拟超时操作
			PrintWriter wirter = context.getResponse().getWriter();	
			PythonUtils qyu=new PythonUtils();
			if("6".equals(sign)) {
				String result=qyu.predict(cmd3);
				//String loginInfo=result;
				wirter.write(result);
				//out.print("answer:\n"+filesInfo1);
			}
			System.out.println(sign);
			
		
			//wirter.write("延迟输出");
			wirter.flush();
			context.complete();
		} catch (IOException e) {
			
		}
	}
}

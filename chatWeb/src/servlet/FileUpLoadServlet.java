package servlet;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.apache.tomcat.jni.User;

@WebServlet("/FileUpLoadServlet")
public class FileUpLoadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static String username,uname="false";
	private static int count=0;
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//username=request.getSession().getAttribute("username").toString();
		count++;
	    username=request.getParameter("username");
		System.out.println(username);
		if(count==1|username!=null) {
			uname=username;
		}
		System.out.println("count:"+count+"uname:"+uname);
		
	}

	@SuppressWarnings("deprecation")
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
		
		String realname = null;
		String temp = request.getSession().getServletContext().getRealPath("/") + "temp"; // ��ʱĿ¼
		System.out.println("temp=" + temp);
		
		// String loadpath = request.getSession().getServletContext()
		// .getRealPath("/")
		//
		// + "Image"; // �ϴ��ļ����Ŀ¼
		// System.out.println("loadpath="+ loadpath);
		DiskFileUpload fu = new DiskFileUpload(); // ��Ҫ����commons-fileupload-1.2.2.jar
		// http://commons.apache.org/fileupload/
		fu.setSizeMax(2 * 1024 * 1024); // ���������û��ϴ��ļ���С,��λ:�ֽ�
		fu.setSizeThreshold(4096); // �������ֻ�������ڴ��д洢������,��λ:�ֽ�
		fu.setRepositoryPath(temp); // ����һ���ļ���С����getSizeThreshold()��ֵʱ���ݴ����Ӳ�̵�Ŀ¼
		// ��ʼ��ȡ�ϴ���Ϣ
		int index = 0;
		List fileItems = null;
		try {
			fileItems = fu.parseRequest(request);
			System.out.println("fileItems=" + fileItems);

			// int t=fileItems.indexOf(",");
		} catch (Exception e) {
			e.printStackTrace();
		}
		Iterator iter = fileItems.iterator(); // ���δ���ÿ���ϴ����ļ�
		while (iter.hasNext()) {
			FileItem item = (FileItem) iter.next();// �������������ļ�������б���Ϣ
			if (!item.isFormField()) {
				String name = item.getName();// ��ȡ�ϴ��ļ���,����·��
				name = name.substring(name.lastIndexOf("\\") + 1);// ��ȫ·������ȡ�ļ���
				realname = name;
				System.out.println(name);
				long size = item.getSize();
				if ((name == null || name.equals("")) && size == 0)
					continue;
				// int point = name.indexOf(".");
				// name = index+(new Date()).getTime()
				// + name.substring(point+1, name.length());
				// index++;
				int x = realname.indexOf(".");
				
				String path = "D:\\java\\chatWeb\\" + uname;
				System.out.print("lujng             " + path);
				// File fNew = new File(loadpath, realname);
				File flode = new File(path);
				flode.mkdir();
				File fNew = new File(path, realname);
				try {
					item.write(fNew);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else// ȡ�������ļ�������б���Ϣ
			{
				String fieldvalue = item.getString();
				// �����������ӦдΪ��(תΪUTF-8����)
				fieldvalue = new String(item.getString().getBytes(), "UTF-8");
			}
		}
	}
}

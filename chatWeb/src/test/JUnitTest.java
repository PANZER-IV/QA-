package test;
import static org.junit.Assert.fail;
import static org.easymock.EasyMock.*;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.Action;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import servlet.LoginServlet;

public class JUnitTest {

	private LoginServlet servlet;
	private HttpServletRequest mockRequest;
	private HttpServletResponse mockResponse;
	
	@Before
	public void setUp(){
		servlet = new LoginServlet();
		
		mockRequest = createMock(HttpServletRequest.class);			//����
		mockResponse = createMock(HttpServletResponse.class);
	}
	
	@After
	public void tearDown(){
		verify(mockRequest);		//��֤
		verify(mockResponse);
	}
	
	
	@Test
	public void testDoPostHttpServletRequestHttpServletResponse() throws ServletException, IOException {
		
		mockRequest.getParameter("id");			//�������
		expectLastCall().andReturn("1");
		
		mockRequest.getParameter("name");		//�������
		expectLastCall().andReturn("chevy");
		
		mockRequest.getParameter("gender");		//�������
		expectLastCall().andReturn("��");
		
		replay(mockRequest);					//�ط�
		replay(mockResponse);
		
		servlet.doPost(mockRequest, mockResponse);	//����
				
	}

}
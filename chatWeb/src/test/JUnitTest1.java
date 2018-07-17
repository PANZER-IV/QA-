package test;
import static org.junit.Assert.assertEquals;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

public class JUnitTest1 {
	private JUnitTest1 servlet;

	private HttpServletRequest mockRequest;

	private HttpServletResponse mockRespones;

	private ServletContext mockServletContext;

	private RequestDispatcher mockDispathcher;

	@Before
	public void setUp() throws Exception {
		mockRequest = EasyMock.createMock(HttpServletRequest.class);
		mockRespones = EasyMock.createMock(HttpServletResponse.class);
		mockServletContext = EasyMock.createMock(ServletContext.class);
		mockDispathcher = EasyMock.createMock(RequestDispatcher.class);

		servlet = new JUnitTest1() {

			private static final long serialVersionUID = 1L;

			public ServletContext getServletContext() {
				return mockServletContext;
			}
		};

	}


	@Test
	public void testDoGetHttpServletRequestHttpServletResponse() throws ServletException, IOException {
		EasyMock.expect(mockRequest.getParameter("username")).andReturn("test").times(1);
		EasyMock.expect(mockRequest.getParameter("password")).andReturn("123456").times(1);
		EasyMock.expect(mockServletContext.getRequestDispatcher("dispather")).andReturn(mockDispathcher).times(1);
		mockDispathcher.forward(mockRequest, mockRespones);
		EasyMock.expectLastCall();
		EasyMock.replay(mockRequest, mockServletContext, mockDispathcher);
		servlet.doGet(mockRequest, mockRespones);
		EasyMock.verify(mockDispathcher);
	}

	@Test
	public void testDoGetFailed() throws ServletException, IOException {
		EasyMock.expect(mockRequest.getParameter("username")).andReturn("test").times(1);
		EasyMock.expect(mockRequest.getParameter("password")).andReturn("123458").times(1);
		EasyMock.replay(mockRequest);
		try {
			servlet.doGet(mockRequest, mockRespones);
		} catch (Exception e) {
			assertEquals("Login error", e.getMessage());
		}
		EasyMock.verify(mockRequest);

	}

}


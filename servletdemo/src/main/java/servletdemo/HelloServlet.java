package servletdemo;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Time;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/HelloServlet")
public class HelloServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html");
		
		PrintWriter printWriter = resp.getWriter();
		
		printWriter.println("<html><body>");
		printWriter.println("<h2>Hello Servlet</h2>");
		printWriter.println("<hr>");
		printWriter.println("Time on the server: " + new Date());
		
		printWriter.println("</body></html>");
	}
}

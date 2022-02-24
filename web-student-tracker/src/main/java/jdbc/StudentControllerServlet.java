package jdbc;

import java.io.IOException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

@WebServlet("/StudentControllerServlet")
public class StudentControllerServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private StudentDbUtil studentDbUtil;
	
	@Resource(name = "jdbc/web_student_tracker")
	private DataSource dataSource;
	
	@Override
	public void init() throws ServletException {
		try {
			studentDbUtil = new StudentDbUtil(dataSource);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			String theCommand = req.getParameter("command");
			
			if (theCommand == null)
				theCommand = "LIST";
			
			switch (theCommand) {
				case "LIST" -> listStudents(req, resp);
				case "ADD" -> addStudent(req, resp);
				case "LOAD" -> loadStudent(req, resp);
				case "UPDATE" -> updateStudent(req, resp);
				case "DELETE" -> deleteStudent(req, resp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void deleteStudent(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		int id = Integer.parseInt(req.getParameter("studentId"));
		
		studentDbUtil.deleteStudent(id);
		
		listStudents(req, resp);
	}

	private void updateStudent(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		int id = Integer.parseInt(req.getParameter("studentId"));
		String firstName = req.getParameter("firstName");
		String lastName = req.getParameter("lastName");
		String email = req.getParameter("email");

		studentDbUtil.updateStudent(
				new Student(id, firstName, lastName, email));
		
		listStudents(req, resp);
	}

	private void loadStudent(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int studentId = Integer.parseInt(req.getParameter("studentId"));
		
		Student student = studentDbUtil.getStudent(studentId);
		
		req.setAttribute("THE_STUDENT", student);
		
		RequestDispatcher requestDispatcher = req.getRequestDispatcher("/update-student-form.jsp");
		requestDispatcher.forward(req, resp);
	}

	public void listStudents(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<Student> students = studentDbUtil.getStudents();
		
		request.setAttribute("STUDENT_LIST", students);
		
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("/list-students.jsp");
		requestDispatcher.forward(request, response);
	}
	
	public void addStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
		
		studentDbUtil.addStudent(
				new Student(firstName, lastName, email));
		
		listStudents(request, response);
	}
}

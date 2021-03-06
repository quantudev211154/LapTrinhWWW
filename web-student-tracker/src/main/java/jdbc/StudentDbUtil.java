package jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.sql.DataSource;

public class StudentDbUtil {
	@Resource(name = "jdbc/web_student_tracker")
	private DataSource dataSource;

	public StudentDbUtil(DataSource dataSource) {
		super();
		this.dataSource = dataSource;
	}

	public List<Student> getStudents() throws SQLException{
		List<Student> students = new ArrayList<Student>();
		
		Connection connection = null;
		ResultSet resultSet = null;
		
		try {
			connection = dataSource.getConnection();
			String sql = "select * from Student";
			
			resultSet = connection.prepareStatement(sql).executeQuery();
			
			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String firstName = resultSet.getString("firstName");
				String lastName = resultSet.getString("lastName");
				String email = resultSet.getString("email");
				
				students.add(new Student(id, firstName, lastName, email));
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			connection.close();
		}
		
		return students;
	}

	public boolean addStudent(Student student) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try {
			connection = dataSource.getConnection();
			
			String sql = "insert into Student(firstName, lastName, email) "
							+ "values(?, ?, ?)";

			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, student.getFirstName());
			preparedStatement.setString(2, student.getLastName());
			preparedStatement.setString(3, student.getEmail());
			
			
			return preparedStatement.executeUpdate() > 0;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			connection.close();
		}
		
		return false;
	}
	
	public Student getStudent(int studentId) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			connection = dataSource.getConnection();
			
			String sql = "select * from Student where id = ?";
			
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, studentId);
			
			resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String firstName = resultSet.getString("firstName");
				String lastName = resultSet.getString("lastName");
				String email = resultSet.getString("email");
				
				return new Student(id, firstName, lastName, email);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean updateStudent(Student student) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try {
			connection = dataSource.getConnection();
			
			String sql = "update Student "
							+ "set firstName = ?, lastName = ?, email = ? "
							+ "where id = ?";

			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, student.getFirstName());
			preparedStatement.setString(2, student.getLastName());
			preparedStatement.setString(3, student.getEmail());
			preparedStatement.setInt(4, student.getId());
			
			return preparedStatement.executeUpdate() > 0;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			connection.close();
		}
		
		return false;
	}
	
	public void deleteStudent(int id) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try {
			connection = dataSource.getConnection();
			
			String sql = "delete student "
					+ "where id = ?";

			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, id);
			
			preparedStatement.executeUpdate();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			connection.close();
		}
	}
}

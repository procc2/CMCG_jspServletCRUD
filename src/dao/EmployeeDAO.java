package dao;

import model.Employee;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class EmployeeDAO {

	private String jdbcURL;
	private String jdbcUsername;
	private String jdbcPassword;
	private Connection jdbcConnection;

	public EmployeeDAO(String jdbcURL, String jdbcUsername, String jdbcPassword) {
		this.jdbcURL = jdbcURL;
		this.jdbcUsername = jdbcUsername;
		this.jdbcPassword = jdbcPassword;
	}

	public void connect() throws SQLException {
		if (jdbcConnection == null || jdbcConnection.isClosed()) {
			try {
				Class.forName("com.mysql.jdbc.Driver");
				jdbcConnection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}

		}
	}

	protected void disconnect() throws SQLException {
		if (jdbcConnection != null && !jdbcConnection.isClosed()) {
			jdbcConnection.close();
		}
	}

	public boolean save(Employee e) throws SQLException {
		String sql = "insert into employee(name,salary,address) values(?,?,?)";
		connect();
		PreparedStatement statement = jdbcConnection.prepareStatement(sql);
		statement.setString(1, e.getName());
		statement.setDouble(2, e.getSalary() );
		statement.setString(3, e.getAddress());
		
		boolean rowInserted = statement.executeUpdate()>0;
		statement.close();
		disconnect();
		return rowInserted;
	}

	public boolean update(Employee e) {
		String sql = "update employee set name='" + e.getName() + "',salary='" + e.getSalary() + "',address='"
				+ e.getAddress() + "' where id=" + e.getId() ;
		try {
			connect();
			PreparedStatement ps = jdbcConnection.prepareStatement(sql);
			boolean rowUpdated = ps.executeUpdate() > 0;
			ps.close();
			disconnect();
			return rowUpdated;
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return false;
		}
	}

	public boolean delete(int id) {
		String sql = "delete from employee where id=" + id + "";
		try {
			connect();
			PreparedStatement ps = jdbcConnection.prepareStatement(sql);
			boolean rowDeleted = ps.executeUpdate() > 0;
	        ps.close();
	        disconnect();
	        return rowDeleted;   
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		  
		
	}

	public List<Employee> getEmployees() {
		String searchText = null;
		String sql = "select * from employee where 1=1 ";
		List<Employee> list = new ArrayList<>();
		if (searchText != null && !searchText.equals(""))
			sql += "and name like '%" + searchText + "%'";
		Statement statement;
		try {
			connect();
			statement = jdbcConnection.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			while(rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				Double salary = rs.getDouble("salary");
				String address =rs.getString("address");
				
				Employee e = new Employee(id, name, salary, address);
				list.add(e);
				
			}
			rs.close();
	        statement.close();
	         
	        disconnect();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public Employee getEmployeeById(int id) {
		Employee e = null;
		String sql = "Select * from employee where id=?";
		try {
			connect();
			PreparedStatement statement = jdbcConnection.prepareStatement(sql);
			statement.setInt(1, id);
			ResultSet rs = statement.executeQuery();
			
			if(rs.next()) {
				String name = rs.getString("name");
				Double salary = rs.getDouble("salary");
				String address = rs.getString("address");
				
				e = new Employee(id, name, salary, address);
			}
			rs.close();
			statement.close();
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		return e;
	}
	
}

package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import model.User;

public class UserDAO {
	private String jdbcURL;
	private String jdbcUsername;
	private String jdbcPassword;
	private Connection jdbcConnection;

	public UserDAO(String jdbcURL, String jdbcUsername, String jdbcPassword) {
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
	public User login(String username, String password) {
		String sql = "select * from user where username= '"+username+"' and password ='"+password + "'";
		try {
			connect();
			Statement statement = jdbcConnection.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			boolean isRegisted = rs.next();
			if(isRegisted ) {
				return new User(username, password);
			}else {
				return null;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		
	}
	
}

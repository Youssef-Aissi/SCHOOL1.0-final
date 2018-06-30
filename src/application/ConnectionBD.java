package application;

import java.sql.*;

public class ConnectionBD {

	private static final String url = "jdbc:mysql://localhost/projet_school";
	private static final String login = "root";
	private static final String psw = "";
	static Statement st = null;
	static ResultSet rs = null;
	
	public static Connection getConnection() {
		
		Connection cn = null;
		
		try {
			//Chargement Driver
			Class.forName("com.mysql.jdbc.Driver");
			//
			cn = DriverManager.getConnection(url, login, psw);
			
			st = cn.createStatement();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}finally {
		}
		
		return cn;
		
	}
}

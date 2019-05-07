package com.revature.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConnectionUtility {
	
	static {
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static Connection getConnection() throws SQLException {
		
		String url = System.getenv("AWS_URL");
		String username = System.getenv("AWS_Username");
		String password = System.getenv("AWS_Password");
		Connection connection = DriverManager.getConnection(url, username, password);		
		return connection;
	}
}

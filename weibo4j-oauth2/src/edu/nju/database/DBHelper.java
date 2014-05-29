package edu.nju.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBHelper {
	
	Connection connection;
	
	public DBHelper() throws ClassNotFoundException, SQLException{
		Class.forName("com.mysql.jdbc.Driver");

		connection = DriverManager.getConnection(
				"jdbc:mysql://localhost/weibost", "root", "Paul_1993");
	}

	
}

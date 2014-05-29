package edu.nju.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBHelper {

	public static void main(String[] args) throws ClassNotFoundException, SQLException{
		Class.forName("com.mysql.jdbc.Driver");

		Connection connection = DriverManager.getConnection(
				"jdbc:mysql://localhost/weibost", "root", "Paul_1993");
		System.out.println("connect");

		Statement statement = connection.createStatement();

		ResultSet resultSet = statement
				.executeQuery("select * from weibo");

		while (resultSet.next()) {
				System.out.println(resultSet.getInt(1));
		}

		connection.close();

	}
}

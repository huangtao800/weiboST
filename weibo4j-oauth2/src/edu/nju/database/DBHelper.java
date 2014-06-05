package edu.nju.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.apache.log4j.chainsaw.Main;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import edu.nju.WeiboStatus;

public class DBHelper {
	
	private static final String SAVE_WEIBO="INSERT INTO weibo(id, text, source, timestamp) VALUES (?,?,?,?)";
	public static DBHelper instance;
	Connection connection;
	
	private DBHelper(){
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(
					"jdbc:mysql://localhost/weibost?useUnicode=true&characterEncoding=utf8", "root", "Paul_1993");
			Statement statement = connection.createStatement();
			statement.executeUpdate("SET NAMES 'utf8mb4'");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public static DBHelper getInstance(){
		if(instance==null){
			instance=new DBHelper();
		}
		return instance;
	}

	public void saveWeiboStatus(ArrayList<WeiboStatus> weiboStatus){
		try {
			PreparedStatement statement = connection.prepareStatement(SAVE_WEIBO);
			for(int i=0;i<weiboStatus.size();i++){
				statement.setString(1, weiboStatus.get(i).getId());
				statement.setString(2, weiboStatus.get(i).getText());
				statement.setString(3, weiboStatus.get(i).getSource());
				statement.setString(4, weiboStatus.get(i).getTimestamp());
				System.out.println(weiboStatus.get(i).getText());
				try {
					statement.executeUpdate();
				} catch (MySQLIntegrityConstraintViolationException e) {
					// TODO: handle exception
					continue;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void showWeibo(){
		PreparedStatement statement;
		try {
			statement = connection.prepareStatement("Select * from weibo;");
			ResultSet resultSet = statement.executeQuery();
			while(resultSet.next()){
				System.out.println(resultSet.getString("text"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
//	public static void main(String args[]){
//		DBHelper dbHelper = new DBHelper();
//		dbHelper.showWeibo();
//	}
}

package it.justsport.api.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import it.justsport.api.ConnectionManager;
import it.justsport.api.bean.UserBean;

public class UserDAO {
	
	private static UserBean getUserFromResult(ResultSet result) throws SQLException
	{
		UserBean user = null;
		
		while(result.next())
			user = new UserBean(result.getInt("id"), result.getString("email"), result.getString("password"), result.getString("type"), false);
		
		result.close();
		
		return user;
	}
	
	public static UserBean getUserByID(int id) throws SQLException, ClassNotFoundException
	{
		ConnectionManager manager = ConnectionManager.get();
		
		PreparedStatement query = manager.prepareQuery("SELECT * FROM users WHERE id = ?");
		query.setInt(1, id);
		
		return getUserFromResult(query.executeQuery());
	}
	
	public static UserBean getUserByEmail(String email) throws SQLException, ClassNotFoundException
	{
		ConnectionManager manager = ConnectionManager.get();
		
		PreparedStatement query = manager.prepareQuery("SELECT * FROM users WHERE email = ?");
		query.setString(1, email);
		
		return getUserFromResult(query.executeQuery());
	}
	
	public static int insertUser(UserBean user) throws SQLException, ClassNotFoundException
	{
		ConnectionManager manager = ConnectionManager.get();
		
		PreparedStatement registerQuery = manager.prepareQuery("INSERT INTO users (email, password, type) VALUES (?, ?, ?)");
		registerQuery.setString(1, user.email);
		registerQuery.setString(2, user.getPassword());
		registerQuery.setString(3, user.type);
		
		return registerQuery.executeUpdate();
	}
	
}

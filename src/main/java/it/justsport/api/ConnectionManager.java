package it.justsport.api;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.cj.jdbc.MysqlDataSource;

public class ConnectionManager {
	
	private static ConnectionManager instance;
	private Connection databaseConnection;
	
	public static ConnectionManager get() throws ClassNotFoundException, SQLException
	{
		if(instance == null)
			instance = new ConnectionManager("jdbc:mysql://localhost:3306/justsport", "root", ""); //TODO: get this data from elsewhere
		
		return instance;
	}
	
	public ConnectionManager(String url, String username, String password) throws ClassNotFoundException, SQLException
	{
		MysqlDataSource ds = new MysqlDataSource();
		ds.setURL(url);
		this.databaseConnection =  ds.getConnection(username, password);
	}
	
	public ResultSet executeQuery(String sql) throws SQLException
	{
		Statement st = databaseConnection.createStatement();
		ResultSet queryResult = st.executeQuery(sql);
		
		return queryResult;
	}
	
	public PreparedStatement prepareQuery(String sql) throws SQLException
	{
		PreparedStatement statement = databaseConnection.prepareStatement(sql);
		statement.closeOnCompletion();
		return statement;
	}

}

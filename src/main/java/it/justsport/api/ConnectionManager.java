package it.justsport.api;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

public class ConnectionManager {
	
	private String databaseURL = "";
	private String databaseUsername = "";
	private String databasePassword = "";
	
	private Connection databaseConnection;
	
	private static HashMap<String, ConnectionManager> connections = new HashMap<String, ConnectionManager>();
	
	public static ConnectionManager getConnection(String databaseURL, String databaseUsername, String databasePassword) throws ClassNotFoundException, SQLException
	{
		if(!connections.containsKey(databaseURL))
			connections.put(databaseURL, new ConnectionManager(databaseURL, databaseUsername, databasePassword));
		
		return connections.get(databaseURL);
	}
	
	private ConnectionManager(String databaseURL, String databaseUsername, String databasePassword) throws ClassNotFoundException, SQLException
	{
		this.databaseURL = databaseURL;
		this.databaseUsername = databaseUsername;
		this.databasePassword = databasePassword;
		
		Class.forName("com.mysql.cj.jdbc.Driver");
		
		this.databaseConnection = DriverManager.getConnection(databaseURL, databaseUsername, databasePassword);
		connections.put(databaseURL, this);
	}
	
	public ResultSet executeQuery(String sql) throws SQLException
	{
		Statement st = databaseConnection.createStatement();
		ResultSet queryResult = st.executeQuery(sql);
		
		return queryResult;
	}
	
	public PreparedStatement prepareQuery(String sql) throws SQLException
	{
		return databaseConnection.prepareStatement(sql);
	}
	
	public void closeConnection() throws SQLException
	{
		databaseConnection.close();
		connections.remove(databaseURL);
	}

}

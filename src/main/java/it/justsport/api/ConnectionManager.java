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
		String url = System.getenv("DATABASE_HOST");
		String user = System.getenv("DATABASE_USER");
		String password = System.getenv("DATABASE_PASSWORD");
		
		if(instance == null)
			instance = new ConnectionManager(url, user, password);
		
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
		PreparedStatement statement = databaseConnection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		statement.closeOnCompletion();
		return statement;
	}
	
	public InsertResult executeInsert(PreparedStatement statement) throws SQLException
	{
		InsertResult result = new InsertResult();
		result.rowsAffected = statement.executeUpdate();
		
		ResultSet key = statement.getGeneratedKeys();
		if(key.next())
			result.newId = key.getLong(1);
		
		return result;
	}

}

package it.justsport.api.table;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

public class User {
	
	public long id = -1;
	public String email;
	public String type;
	
	private transient String password;
	
	public User() {}
	
	public User(long id, String email, String password, String type, boolean doHash)
	{
		this.id = id;
		this.email = email;
		setPassword(password, doHash);
		this.type = type;
	}
	
	public User(String email, String password, String type, boolean doHash)
	{
		this.email = email;
		setPassword(password, doHash);
		this.type = type;
	}
	
	public String getPassword()
	{
		return password;
	}
	
	public void setPassword(String password, boolean doHash)
	{
		String pass = password;
		
		if(doHash)
		{
			Argon2 hasher = Argon2Factory.create();
			pass = hasher.hash(10, 65536, 1, password.getBytes());
		}
		
		this.password = pass;
	}
	
}

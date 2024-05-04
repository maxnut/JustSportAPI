package it.justsport.api.table;

import java.util.ArrayList;

public class Team {
	public long id = -1;
	public String name;
	public ArrayList<User> members;
	
	public Team(long id, String name) {
		super();
		this.id = id;
		this.name = name;
		members = new ArrayList<User>();
	}
	
	public Team(String name) {
		super();
		this.name = name;
		members = new ArrayList<User>();
	}
}

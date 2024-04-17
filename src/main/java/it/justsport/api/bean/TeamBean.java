package it.justsport.api.bean;

import java.util.ArrayList;

public class TeamBean {
	public long id = -1;
	public String name;
	public ArrayList<UserBean> members;
	public TournamentBean tournament = null;
	
	public TeamBean(long id, String name) {
		super();
		this.id = id;
		this.name = name;
		members = new ArrayList<UserBean>();
	}
	
	public TeamBean(String name) {
		super();
		this.name = name;
		members = new ArrayList<UserBean>();
	}
}

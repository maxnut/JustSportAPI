package it.justsport.api.bean;

import java.util.ArrayList;

public class TeamBean {
	public int id = -1;
	public String name;
	public ArrayList<UserBean> members;
	
	public TeamBean(int id, String name) {
		super();
		this.id = id;
		this.name = name;
		members = new ArrayList<UserBean>();
	}
}

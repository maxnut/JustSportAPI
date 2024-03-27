package it.justsport.api.bean;

import java.sql.Date;

public class TournamentBean {
	
	public int id = -1;
	public String name;
	public Date startDate;
	public Date endDate;
	public Date subscriptionDate;
	public String type;
	public int maxSubscriptions;
	public int minSubscriptions;
	public int matchCount;
	
	public TournamentBean(int id, String name, Date startDate, Date endDate, Date subscriptionDate, String type,
			int maxSubscriptions, int minSubscriptions, int matchCount) {
		super();
		this.id = id;
		this.name = name;
		this.startDate = startDate;
		this.endDate = endDate;
		this.subscriptionDate = subscriptionDate;
		this.type = type;
		this.maxSubscriptions = maxSubscriptions;
		this.minSubscriptions = minSubscriptions;
		this.matchCount = matchCount;
	}
	
}

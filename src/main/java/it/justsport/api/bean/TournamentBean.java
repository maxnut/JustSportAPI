package it.justsport.api.bean;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class TournamentBean {
	
	public long id = -1;
	public long ownerID = -1;
	public String name;
	public String sport;
	public Date startDate;
	public Date endDate;
	public Date subscriptionDate;
	public String type;
	public int maxSubscriptions;
	public int minSubscriptions;
	public int matchCount;
	
	public List<TeamBean> teams = new ArrayList<TeamBean>();
	
	public TournamentBean(long id, long ownerID, String name, String sport, Date startDate, Date endDate, Date subscriptionDate, String type,
			int maxSubscriptions, int minSubscriptions, int matchCount) {
		super();
		this.id = id;
		this.ownerID = ownerID; 
		this.sport = sport;
		this.name = name;
		this.startDate = startDate;
		this.endDate = endDate;
		this.subscriptionDate = subscriptionDate;
		this.type = type;
		this.maxSubscriptions = maxSubscriptions;
		this.minSubscriptions = minSubscriptions;
		this.matchCount = matchCount;
	}
	
	public TournamentBean(long ownerID, String name, String sport, Date startDate, Date endDate, Date subscriptionDate, String type,
			int maxSubscriptions, int minSubscriptions, int matchCount) {
		super();
		this.ownerID = ownerID; 
		this.name = name;
		this.sport = sport;
		this.startDate = startDate;
		this.endDate = endDate;
		this.subscriptionDate = subscriptionDate;
		this.type = type;
		this.maxSubscriptions = maxSubscriptions;
		this.minSubscriptions = minSubscriptions;
		this.matchCount = matchCount;
	}
	
}

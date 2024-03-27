package it.justsport.api.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import it.justsport.api.ConnectionManager;
import it.justsport.api.bean.TournamentBean;

public class TournamentDAO {

	public static ArrayList<TournamentBean> getTournamentsFromResult(ResultSet result) throws SQLException {
		ArrayList<TournamentBean> list = new ArrayList<TournamentBean>();

		while (result.next()) {
			TournamentBean tournament = new TournamentBean(result.getInt("id"), result.getString("name"),
					result.getDate("start_date"), result.getDate("end_date"), result.getDate("subscription_date"),
					result.getString("type"), result.getInt("max_subscriptions"), result.getInt("min_subscriptions"),
					result.getInt("match_count"));

			list.add(tournament);
		}

		return list;
	}

	public static ArrayList<TournamentBean> getTournaments() throws ClassNotFoundException, SQLException
	{
		ConnectionManager manager = ConnectionManager.get();
		PreparedStatement query = manager.prepareQuery("SELECT * FROM tournaments");
		
		return getTournamentsFromResult(query.executeQuery());
	}
	
	public static ArrayList<TournamentBean> getTournamentsByType(String type) throws ClassNotFoundException, SQLException
	{
		ConnectionManager manager = ConnectionManager.get();
		PreparedStatement query = manager.prepareQuery("SELECT * FROM tournaments WHERE type = ?");
		query.setString(1, type);
		
		return getTournamentsFromResult(query.executeQuery());
	}
	
}

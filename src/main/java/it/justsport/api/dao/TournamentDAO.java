package it.justsport.api.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import it.justsport.api.ConnectionManager;
import it.justsport.api.InsertResult;
import it.justsport.api.bean.TeamBean;
import it.justsport.api.bean.TournamentBean;

public class TournamentDAO {

	public static ArrayList<TournamentBean> getTournamentsFromResult(ResultSet result) throws SQLException {
		ArrayList<TournamentBean> list = new ArrayList<TournamentBean>();

		while (result.next()) {
			TournamentBean tournament = new TournamentBean(result.getLong("id"), result.getLong("owner_id"), result.getString("name"), result.getString("sport"),
					result.getDate("start_date"), result.getDate("end_date"), result.getDate("subscription_date"),
					result.getString("type"), result.getInt("max_subscriptions"), result.getInt("min_subscriptions"),
					result.getInt("match_count"));
			
			

			list.add(tournament);
		}

		return list;
	}
	
	public static TournamentBean getTournamentFromResult(ResultSet result) throws SQLException {
		TournamentBean tournament = null;

		while (result.next()) {
			tournament = new TournamentBean(result.getLong("id"), result.getLong("owner_id"), result.getString("name"), result.getString("sport"),
					result.getDate("start_date"), result.getDate("end_date"), result.getDate("subscription_date"),
					result.getString("type"), result.getInt("max_subscriptions"), result.getInt("min_subscriptions"),
					result.getInt("match_count"));
		}

		return tournament;
	}
	
	public static TournamentBean getTournamentByID(long id) throws ClassNotFoundException, SQLException
	{
		ConnectionManager manager = ConnectionManager.get();
		PreparedStatement query = manager.prepareQuery("SELECT * FROM tournaments WHERE id = ?");
		query.setLong(1, id);
		
		return getTournamentFromResult(query.executeQuery());
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
	
	public static int insertTournament(TournamentBean tournament) throws SQLException, ClassNotFoundException
	{
		ConnectionManager manager = ConnectionManager.get();
		
		PreparedStatement insertQuery = manager.prepareQuery("INSERT INTO tournaments (owner_id, name, sport, start_date, end_date, subscription_date, type, max_subscriptions, min_subscriptions, match_count) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
		insertQuery.setLong(1, tournament.ownerID);
		insertQuery.setString(2, tournament.name);
		insertQuery.setString(3, tournament.sport);
		insertQuery.setDate(4, tournament.startDate);
		insertQuery.setDate(5, tournament.endDate);
		insertQuery.setDate(6, tournament.subscriptionDate);
		insertQuery.setString(7, tournament.type);
		insertQuery.setInt(8, tournament.maxSubscriptions);
		insertQuery.setInt(9, tournament.minSubscriptions);
		insertQuery.setInt(10, tournament.matchCount);
	
		InsertResult result = manager.executeInsert(insertQuery);
		
		tournament.id = result.newId;
		
		return result.rowsAffected;
	}
	
	public static int addTeamToTournament(TournamentBean tournament, TeamBean team) throws SQLException, ClassNotFoundException
	{
		ConnectionManager manager = ConnectionManager.get();
		
		PreparedStatement insertQuery = manager.prepareQuery("INSERT INTO team_subscriptions (team_id, tournament_id) VALUES (?, ?)");
		
		insertQuery.setLong(1, team.id);
		insertQuery.setLong(2, tournament.id);
		
		tournament.teams.add(team);
		team.tournament = tournament;
		
		InsertResult result = manager.executeInsert(insertQuery);
		
		return result.rowsAffected;
	}
	
}

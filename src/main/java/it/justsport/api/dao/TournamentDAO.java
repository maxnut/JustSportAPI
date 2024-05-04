package it.justsport.api.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import it.justsport.api.ConnectionManager;
import it.justsport.api.InsertResult;
import it.justsport.api.table.Team;
import it.justsport.api.table.Tournament;
import it.justsport.api.table.User;

public class TournamentDAO {

	public static ArrayList<Tournament> getTournamentsFromResult(ResultSet result) throws SQLException, ClassNotFoundException {
		ArrayList<Tournament> list = new ArrayList<Tournament>();
		
		Tournament tournament = null;

		do {
			tournament = getTournamentFromResult(result);

			list.add(tournament);
		} while(tournament != null);

		return list;
	}
	
	public static Tournament getTournamentFromResult(ResultSet result) throws SQLException, ClassNotFoundException {
		Tournament tournament = null;

		if (result.next()) {
			ConnectionManager manager = ConnectionManager.get();
			
			tournament = new Tournament(result.getLong("id"), result.getLong("owner_id"), result.getString("name"), result.getString("sport"),
					result.getDate("start_date"), result.getDate("end_date"), result.getDate("subscription_date"),
					result.getString("type"), result.getInt("max_subscriptions"), result.getInt("min_subscriptions"),
					result.getInt("match_count"));
			
			PreparedStatement teamsQuery = manager.prepareQuery("SELECT team_id FROM team_subscriptions WHERE tournament_id = ?");
			teamsQuery.setLong(1, tournament.id);
			
			ResultSet teamsResult = teamsQuery.executeQuery();
			
			while(teamsResult.next())
			{
				long teamID = teamsResult.getLong("team_id");
				Team team = TeamDAO.getTeamByID(teamID);
				tournament.teams.add(team);
			}
		}

		return tournament;
	}
	
	public static Tournament getTournamentByID(long id) throws ClassNotFoundException, SQLException
	{
		ConnectionManager manager = ConnectionManager.get();
		PreparedStatement query = manager.prepareQuery("SELECT * FROM tournaments WHERE id = ?");
		query.setLong(1, id);
		
		return getTournamentFromResult(query.executeQuery());
	}

	public static ArrayList<Tournament> getTournaments(String type, String startDate) throws ClassNotFoundException, SQLException
	{
		ConnectionManager manager = ConnectionManager.get();
		
		PreparedStatement query = null;
		
		if(type == null && startDate == null)
			query = manager.prepareQuery("SELECT * FROM tournaments");
		else if(startDate == null)
		{
			query = manager.prepareQuery("SELECT * FROM tournaments WHERE type = ?");
			query.setString(1, type);
		}
		else if(type == null)
		{
			query = manager.prepareQuery("SELECT * FROM tournaments WHERE start_date = ?");
			query.setString(1, startDate);
		}
		else
		{
			query = manager.prepareQuery("SELECT * FROM tournaments WHERE type = ? AND start_date = ?");
			query.setString(1, type);
			query.setString(2, startDate);
		}
		
		return getTournamentsFromResult(query.executeQuery());
	}
	
	public static int insertTournament(Tournament tournament) throws SQLException, ClassNotFoundException
	{
		ConnectionManager manager = ConnectionManager.get();
		
		PreparedStatement insertQuery = manager.prepareQuery("INSERT INTO tournaments (owner_id, name, sport, start_date, end_date, subscription_date, type, max_subscriptions, min_subscriptions, match_count) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
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
	
	public static int addTeamToTournament(Tournament tournament, Team team) throws SQLException, ClassNotFoundException
	{
		ConnectionManager manager = ConnectionManager.get();
		
		PreparedStatement insertQuery = manager.prepareQuery("INSERT INTO team_subscriptions (team_id, tournament_id) VALUES (?, ?)");
		
		insertQuery.setLong(1, team.id);
		insertQuery.setLong(2, tournament.id);
		
		tournament.teams.add(team);
		
		InsertResult result = manager.executeInsert(insertQuery);
		
		return result.rowsAffected;
	}
	
}

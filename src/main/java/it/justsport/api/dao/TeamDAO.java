package it.justsport.api.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import it.justsport.api.ConnectionManager;
import it.justsport.api.InsertResult;
import it.justsport.api.table.Team;
import it.justsport.api.table.User;

public class TeamDAO {
	public static ArrayList<Team> getTeamsFromResult(ResultSet result) throws SQLException, ClassNotFoundException {
		ArrayList<Team> list = new ArrayList<Team>();
		
		Team team = null;

		do {
			team = getTeamFromResult(result);

			if(team != null)
				list.add(team);
		} while(team != null);

		return list;
	}
	
	public static Team getTeamFromResult(ResultSet result) throws SQLException, ClassNotFoundException {
		Team team = null;

		if (result.next()) {
			ConnectionManager manager = ConnectionManager.get();
			
			team = new Team(result.getInt("id"), result.getString("name"));
			
			PreparedStatement membersQuery = manager.prepareQuery("SELECT user_id FROM subscriptions WHERE team_id = ?");
			membersQuery.setLong(1, team.id);
			
			ResultSet memberResult = membersQuery.executeQuery();
			
			while(memberResult.next())
			{
				long userID = memberResult.getLong("user_id");
				User user = UserDAO.getUserByID(userID);
				team.members.add(user);
			}
		}

		return team;
	}
	
	public static Team getTeamByID(long id) throws SQLException, ClassNotFoundException
	{
		ConnectionManager manager = ConnectionManager.get();
		
		PreparedStatement query = manager.prepareQuery("SELECT * FROM team WHERE id = ?");
		query.setLong(1, id);
		
		return getTeamFromResult(query.executeQuery());
	}

	public static ArrayList<Team> getTeams() throws ClassNotFoundException, SQLException {
		ConnectionManager manager = ConnectionManager.get();
		PreparedStatement query = manager.prepareQuery("SELECT * FROM team");

		ArrayList<Team> teams = getTeamsFromResult(query.executeQuery());

		for (Team team : teams) {
			PreparedStatement membersQuery = manager.prepareQuery("SELECT user_id FROM subscriptions WHERE team_id = ?");
			membersQuery.setLong(1, team.id);
			
			ResultSet result = membersQuery.executeQuery();
			
			while(result.next())
			{
				long userID = result.getLong("user_id");
				User user = UserDAO.getUserByID(userID);
				team.members.add(user);
			}
		}

		return teams;
	}

	public static int insertTeam(Team team) throws SQLException, ClassNotFoundException {
		ConnectionManager manager = ConnectionManager.get();

		PreparedStatement insertQuery = manager.prepareQuery("INSERT INTO team (name) VALUES (?)");
		insertQuery.setString(1, team.name);

		InsertResult result = manager.executeInsert(insertQuery);

		team.id = result.newId;

		return result.rowsAffected;
	}

	public static int addUserToTeam(Team team, User user) throws SQLException, ClassNotFoundException {
		ConnectionManager manager = ConnectionManager.get();

		PreparedStatement insertQuery = manager
				.prepareQuery("INSERT INTO subscriptions (team_id, user_id) VALUES (?, ?)");
		insertQuery.setLong(1, team.id);
		insertQuery.setLong(2, user.id);

		InsertResult result = manager.executeInsert(insertQuery);

		team.members.add(user);

		return result.rowsAffected;
	}
}

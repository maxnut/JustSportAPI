package it.justsport.api.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import it.justsport.api.ConnectionManager;
import it.justsport.api.InsertResult;
import it.justsport.api.bean.TeamBean;
import it.justsport.api.bean.TournamentBean;
import it.justsport.api.bean.UserBean;

public class TeamDAO {
	public static ArrayList<TeamBean> getTeamsFromResult(ResultSet result) throws SQLException {
		ArrayList<TeamBean> list = new ArrayList<TeamBean>();

		while (result.next()) {
			TeamBean team = new TeamBean(result.getInt("id"), result.getString("name"));

			list.add(team);
		}

		return list;
	}
	
	public static TeamBean getTeamFromResult(ResultSet result) throws SQLException {
		TeamBean team = null;

		while (result.next()) {
			team = new TeamBean(result.getInt("id"), result.getString("name"));
		}

		return team;
	}
	
	public static TeamBean getTeamByID(long id) throws SQLException, ClassNotFoundException
	{
		ConnectionManager manager = ConnectionManager.get();
		
		PreparedStatement query = manager.prepareQuery("SELECT * FROM team WHERE id = ?");
		query.setLong(1, id);
		
		return getTeamFromResult(query.executeQuery());
	}

	public static ArrayList<TeamBean> getTeams() throws ClassNotFoundException, SQLException {
		ConnectionManager manager = ConnectionManager.get();
		PreparedStatement query = manager.prepareQuery("SELECT * FROM team");

		ArrayList<TeamBean> teams = getTeamsFromResult(query.executeQuery());

		for (TeamBean team : teams) {
			PreparedStatement membersQuery = manager.prepareQuery("SELECT user_id FROM subscriptions WHERE team_id = ?");
			membersQuery.setLong(1, team.id);
			
			ResultSet result = membersQuery.executeQuery();
			
			while(result.next())
			{
				long userID = result.getLong("user_id");
				UserBean user = UserDAO.getUserByID(userID);
				team.members.add(user);
			}
			
			PreparedStatement tournamentQuery = manager.prepareQuery("SELECT tournament_id FROM team_subscriptions WHERE team_id = ?");
			tournamentQuery.setLong(1, team.id);
			
			ResultSet result2 = tournamentQuery.executeQuery();
			
			while(result2.next())
			{
				long tournamentID = result.getLong("tournament_id");
				TournamentBean tournament = TournamentDAO.getTournamentByID(tournamentID);
				team.tournament = tournament;
			}
		}

		return teams;
	}

	public static int insertTeam(TeamBean team) throws SQLException, ClassNotFoundException {
		ConnectionManager manager = ConnectionManager.get();

		PreparedStatement insertQuery = manager.prepareQuery("INSERT INTO team (name) VALUES (?)");
		insertQuery.setString(1, team.name);

		InsertResult result = manager.executeInsert(insertQuery);

		team.id = result.newId;

		return result.rowsAffected;
	}

	public static int addUserToTeam(TeamBean team, UserBean user) throws SQLException, ClassNotFoundException {
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

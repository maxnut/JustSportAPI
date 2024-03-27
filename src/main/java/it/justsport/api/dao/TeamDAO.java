package it.justsport.api.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import it.justsport.api.ConnectionManager;
import it.justsport.api.bean.TeamBean;

public class TeamDAO {
	public static ArrayList<TeamBean> getTeamsFromResult(ResultSet result) throws SQLException {
		ArrayList<TeamBean> list = new ArrayList<TeamBean>();

		while (result.next()) {
			TeamBean team = new TeamBean(result.getInt("id"), result.getString("name"));

			list.add(team);
		}

		return list;
	}

	public static ArrayList<TeamBean> getTeams() throws ClassNotFoundException, SQLException
	{
		ConnectionManager manager = ConnectionManager.get();
		PreparedStatement query = manager.prepareQuery("SELECT * FROM team");
		
		return getTeamsFromResult(query.executeQuery());
	}
}

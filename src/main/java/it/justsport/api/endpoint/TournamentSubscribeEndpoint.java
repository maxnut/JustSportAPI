package it.justsport.api.endpoint;

import java.io.IOException;
import java.sql.SQLException;

import com.google.gson.Gson;

import it.justsport.api.Responses;
import it.justsport.api.Responses.Response;
import it.justsport.api.dao.TeamDAO;
import it.justsport.api.dao.TournamentDAO;
import it.justsport.api.table.Team;
import it.justsport.api.table.Tournament;
import it.justsport.api.table.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/tournament/subscribe")
public class TournamentSubscribeEndpoint extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public TournamentSubscribeEndpoint() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(!request.isRequestedSessionIdValid())
		{
			Responses.respond(response, Response.UNAUTHORIZED);
			return;
		}
		
		String teamID = request.getParameter("team_id");
		String tournamentID = request.getParameter("tournament_id");
		
		if (teamID == null || tournamentID == null)
		{
			Responses.respond(response, Response.EMPTY_DATA);
			return;
		}
		
		try {
			Tournament t = TournamentDAO.getTournamentByID(Long.valueOf(tournamentID));
			Team team = TeamDAO.getTeamByID(Long.valueOf(teamID));
			
			if(t == null || team == null)
			{
				Responses.respond(response, Response.SERVER_ERROR);
				return;
			}
			
			if(t.type == "extrascolastico")
			{
				for(User u : team.members)
				{
					if(u.type != "stu")
					{
						Responses.respond(response, Response.BAD_REQUEST);
						return;
					}
				}
			}
			
			Gson gson = new Gson();
			
			TournamentDAO.addTeamToTournament(t, team);
			
			Responses.respondWithObject(response, Response.OK, gson.toJsonTree(t));
		} catch (NumberFormatException | ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}

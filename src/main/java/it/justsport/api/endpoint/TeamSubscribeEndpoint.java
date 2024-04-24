package it.justsport.api.endpoint;

import java.io.IOException;
import java.sql.SQLException;

import com.google.gson.Gson;

import it.justsport.api.Responses;
import it.justsport.api.Responses.Response;
import it.justsport.api.bean.TeamBean;
import it.justsport.api.bean.UserBean;
import it.justsport.api.dao.TeamDAO;
import it.justsport.api.dao.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/team/subscribe")
public class TeamSubscribeEndpoint extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public TeamSubscribeEndpoint() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if(!request.isRequestedSessionIdValid()) {
			Responses.respond(response, Response.UNAUTHORIZED);
			return;
		}
		
		String teamID = request.getParameter("team_id");
		String userID = request.getParameter("user_id");
		
		if(teamID == null || userID == null)
		{
			Responses.respond(response, Response.EMPTY_DATA);
			return;
		}
		
		try {
			UserBean user = UserDAO.getUserByID(Long.parseLong(userID));
			TeamBean team = TeamDAO.getTeamByID(Long.parseLong(teamID));
			
			if(user == null || team == null)
			{
				Responses.respond(response, Response.NOT_EXIST);
				return;
			}
			
			Gson gson = new Gson();
			
			if(TeamDAO.addUserToTeam(team, user) > 0)
				Responses.respondWithObject(response, Response.OK, gson.toJsonTree(team));
			else
				Responses.respond(response, Response.SERVER_ERROR);
			
		} catch (NumberFormatException | ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}

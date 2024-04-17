package it.justsport.api.endpoint;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import it.justsport.api.Responses;
import it.justsport.api.Responses.Response;

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
		if(request.getSession().getAttribute("user_id") == null)
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
		
		
	}

}

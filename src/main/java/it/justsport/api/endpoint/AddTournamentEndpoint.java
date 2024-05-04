package it.justsport.api.endpoint;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;

import com.google.gson.Gson;

import it.justsport.api.Responses;
import it.justsport.api.Responses.Response;
import it.justsport.api.dao.TournamentDAO;
import it.justsport.api.dao.UserDAO;
import it.justsport.api.table.Tournament;
import it.justsport.api.table.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/tournament/add")
public class AddTournamentEndpoint extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public AddTournamentEndpoint() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		if(!request.isRequestedSessionIdValid())
		{
			Responses.respond(response, Response.UNAUTHORIZED);
			return;
		}
		
		long userID = (long)request.getSession().getAttribute("user_id");
		
		try {
			User user = UserDAO.getUserByID(userID);
			if(user.type.equals("stu"))
			{
				Responses.respond(response, Response.UNAUTHORIZED);
				return;
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String name = request.getParameter("name");
		String sport = request.getParameter("sport");
		String startDate = request.getParameter("start_date");
		String endDate = request.getParameter("end_date");
		String subDate = request.getParameter("subscription_date");
		String type = request.getParameter("type");
		String maxSubscriptions = request.getParameter("max_subscriptions");
		String minSubscriptions = request.getParameter("min_subscriptions");
		String matchCount = request.getParameter("match_count");

		if (name == null || sport == null || startDate == null || endDate == null || subDate == null || type == null
				|| maxSubscriptions == null || minSubscriptions == null || matchCount == null)
		{
			Responses.respond(response, Response.EMPTY_DATA);
			return;
		}
		
		Gson gson = new Gson();

		Tournament tournament = new Tournament(userID, name, sport, Date.valueOf(startDate), Date.valueOf(endDate),
				Date.valueOf(subDate), type, Integer.parseInt(maxSubscriptions), Integer.parseInt(minSubscriptions),
				Integer.parseInt(matchCount));
		
		if(tournament.startDate.after(tournament.endDate) || tournament.subscriptionDate.after(tournament.startDate))
		{
			Responses.respond(response, Response.TOURNAMENT_BAD_DATE);
			return;
		}
		
		if(!tournament.type.equals("amatoriale") && !tournament.type.equals("extrascolastico"))
		{
			Responses.respond(response, Response.TOURNAMENT_BAD_TYPE);
			return;
		}
		
		if(tournament.minSubscriptions > tournament.maxSubscriptions || tournament.minSubscriptions <= 0 || tournament.maxSubscriptions <= 1)
		{
			Responses.respond(response, Response.TOURNAMENT_BAD_SUBSCRIPTION);
			return;
		}
		
		
		if(tournament.matchCount < 1)
		{
			Responses.respond(response, Response.TOURNAMENT_BAD_MATCH);
			return;
		}
			
		try {
			if(TournamentDAO.insertTournament(tournament) > 0)
				Responses.respondWithObject(response, Response.OK, gson.toJsonTree(tournament));
			else
				Responses.respond(response, Response.SERVER_ERROR);
		} catch (ClassNotFoundException | SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}

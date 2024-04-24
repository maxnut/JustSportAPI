package it.justsport.api.endpoint;

import java.io.IOException;
import java.sql.SQLException;

import com.google.gson.Gson;

import it.justsport.api.Responses;
import it.justsport.api.Responses.Response;
import it.justsport.api.bean.TeamBean;
import it.justsport.api.dao.TeamDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/team/add")
public class AddTeamEndpoint extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public AddTeamEndpoint() {
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
		
		String name = request.getParameter("name");
		
		if (name == null)
		{
			Responses.respond(response, Response.EMPTY_DATA);
			return;
		}
		
		Gson gson = new Gson();
		
		TeamBean team = new TeamBean(name);
		
		try {
			if(TeamDAO.insertTeam(team) > 0)
				Responses.respondWithObject(response, Response.OK, gson.toJsonTree(team));
			else
				Responses.respond(response, Response.SERVER_ERROR);
		} catch (ClassNotFoundException | SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

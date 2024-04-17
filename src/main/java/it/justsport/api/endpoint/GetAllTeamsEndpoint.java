package it.justsport.api.endpoint;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

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

@WebServlet("/team/get")
public class GetAllTeamsEndpoint extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public GetAllTeamsEndpoint() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {

			if (request.getSession().getAttribute("user_id") == null) {
				Responses.respond(response, Response.UNAUTHORIZED);
				return;
			}

			ArrayList<TeamBean> teams = TeamDAO.getTeams();
			Gson gson = new Gson();
			Responses.respondWithObject(response, Response.OK, gson.toJsonTree(teams));
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

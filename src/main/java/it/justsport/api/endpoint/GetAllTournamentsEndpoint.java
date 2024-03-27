package it.justsport.api.endpoint;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import com.google.gson.Gson;

import it.justsport.api.bean.TournamentBean;
import it.justsport.api.dao.TournamentDAO;
import it.justsport.api.endpoint.Responses.Response;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/getAllTournaments")
public class GetAllTournamentsEndpoint extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
    public GetAllTournamentsEndpoint() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
    	doPost(request, response);
    }

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String type = request.getParameter("type");
			
			ArrayList<TournamentBean> tournaments = null;
			
			if(type == null)
				tournaments = TournamentDAO.getTournaments();
			else
				tournaments = TournamentDAO.getTournamentsByType(type);
				
			Gson gson = new Gson();
			Responses.respondWithObject(response, Response.OK, gson.toJsonTree(tournaments));
		
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

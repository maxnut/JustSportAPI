package it.justsport.api.endpoint;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import it.justsport.api.Responses;
import it.justsport.api.Responses.Response;

@WebServlet("/user/logout")
public class LogoutEndpoint extends HttpServlet {
	private static final long serialVersionUID = 1L;
     
    public LogoutEndpoint() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getSession().invalidate();
		
		Responses.respond(response, Response.OK);
	}

}

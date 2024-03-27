package it.justsport.api.endpoint;

import java.io.IOException;
import java.sql.SQLException;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import it.justsport.api.dao.UserDAO;
import it.justsport.api.endpoint.Responses.Response;
import it.justsport.api.bean.UserBean;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/login")
public class LoginEndpoint extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public LoginEndpoint() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		if(email == null || password == null)
		{
			Responses.respond(response, Response.EMPTY_DATA);
			return;
		}
		
		try {
			UserBean user = UserDAO.getUserByEmail(email);
			
			if(user == null)
			{
				Responses.respond(response, Response.LOGIN_WRONG);
				return;
			}
			
			Argon2 hasher = Argon2Factory.create();
			
			if(hasher.verify(user.getPassword(), password.getBytes()))
				Responses.respond(response, Response.LOGIN_OK);
			else
				Responses.respond(response, Response.LOGIN_WRONG);
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

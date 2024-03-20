package it.justsport.api;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/register")
public class RegisterEndpoint extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public RegisterEndpoint() {
        super();
        // TODO Auto-generated constructor stub
    }

	
    //TODO spostare nel post
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		if(email == null || password == null)
		{
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.getWriter().append("Richiesta non valida");
			return;
		}
		
		try {
			ConnectionManager manager = ConnectionManager.getConnection("jdbc:mysql://localhost:3306/justsport", "root", "qwertyuiop");
			
			PreparedStatement registerQuery = manager.prepareQuery("INSERT INTO utenti (email, password, tipo) VALUES (?, ?, \"stu\")");
			registerQuery.setString(1, email);
			registerQuery.setString(2, password);
			
			int rowsAffected = registerQuery.executeUpdate();
            
            if (rowsAffected > 0)
            {
            	response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().append("Utente registrato");
            }
            else
            {
            	response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().append("Utente non registrato");
            }
            
            registerQuery.close();
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

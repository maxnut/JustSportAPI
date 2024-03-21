package it.justsport.api.endpoint;

import java.io.IOException;
import java.sql.SQLException;
import java.util.regex.Pattern;

import it.justsport.api.dao.UserDAO;
import it.justsport.api.endpoint.Responses.Response;
import it.justsport.api.table.User;
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

	private static final String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

	private static final Pattern emailPattern = Pattern.compile(EMAIL_REGEX);

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String type = request.getParameter("type");
		
		System.out.println(type + " saidhsiaodhsoadhsoaidh");

		if (email == null || password == null || type == null) {
			Responses.respond(response, Response.EMPTY_DATA);
			return;
		}

		if (!emailPattern.matcher(email).matches()) {
			Responses.respond(response, Response.REGISTER_BAD_EMAIL);
			return;
		}
		
		if(!type.equals("stu") && !type.equals("ama") && !type.equals("doc"))
		{
			Responses.respond(response, Response.REGISTER_BAD_TYPE);
			return;
		}

		try {
			User user = new User(email, password, type, true);

			if (UserDAO.insertUser(user) > 0)
				Responses.respond(response, Response.REGISTER_OK);

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (java.sql.SQLIntegrityConstraintViolationException e) {
			Responses.respond(response, Response.REGISTER_EXISTS);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
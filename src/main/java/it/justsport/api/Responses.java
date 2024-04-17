package it.justsport.api;

import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import jakarta.servlet.http.HttpServletResponse;

public class Responses {

	public enum Response {
		REGISTER_OK("User registgered", 200),
		REGISTER_EXISTS("User with this email already exists", 452),
		REGISTER_BAD_EMAIL("The provided email is invalid", 453),
		REGISTER_BAD_TYPE("The provided type is invalid", 454),
		EMPTY_DATA("One or more fields are empty", 400),
		LOGIN_OK("Login successful", 200),
		LOGIN_WRONG("Wrong username or password", 452),
		TOURNAMENT_BAD_DATE("The provided dates are invalid", 452),
		TOURNAMENT_BAD_TYPE("The provided type is invalid", 453),
		TOURNAMENT_BAD_SUBSCRIPTION("The provided subscription data is invalid", 454),
		TOURNAMENT_BAD_MATCH("The provided matches are invalid", 455),
		NOT_EXIST("The provided entries do not exist", 455),
		SERVER_ERROR("Internal server error", 500),
		OK("", 200),
		UNAUTHORIZED("Unauthorized", 401);

		private String message;
		private int code;

		private Response(String message, int code) {
			this.message = message;
			this.code = code;
		}

		public String getMessage() {
			return message;
		}

		public int getCode() {
			return code;
		}
	}
	
	public static void respond(HttpServletResponse responseObject, Response response) throws IOException
	{
		JsonObject obj = new JsonObject();
		obj.addProperty("response_message", response.getMessage());
		obj.addProperty("response_code", response.getCode());
		
		responseObject.setContentType("application/json");
		responseObject.setStatus(response.getCode());
		responseObject.getWriter().append(obj.toString());
	}
	
	public static void respondWithObject(HttpServletResponse responseObject, Response response, JsonElement object) throws IOException
	{
		JsonObject obj = new JsonObject();
		obj.addProperty("response_message", response.getMessage());
		obj.addProperty("response_code", response.getCode());
		
		obj.add("response_content", object);
		
		responseObject.setContentType("application/json");
		responseObject.setStatus(response.getCode());
		responseObject.getWriter().append(obj.toString());
	}

}

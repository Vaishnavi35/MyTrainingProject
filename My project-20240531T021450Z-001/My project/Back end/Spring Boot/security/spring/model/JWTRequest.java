package com.security.spring.model;
import java.io.Serializable;
public class JWTRequest implements Serializable {
	
	private static final long serialVersionUID = 5926468583005150707L;

	public String username;
	public String password;

	
	public JWTRequest(String username, String password) {
		
		this.setUsername(username);
		this.setPassword(password);
	}

	//default constructor for JSON Parsing
	public JWTRequest()
	{
		
	}

	

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}

package com.makhloufiTerpend.model;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public class User {
	private String email;
	private String firstName;
	private String lastName;
	private String description;
	
	public User(String email, String firstName, String lastName, String description) {
		super();
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.description = description;
	}
	
	public User(String jsonData) throws JSONException {
		super();
		JSONObject jsonObject = new JSONObject(jsonData);
		this.email = jsonObject.getString("email");
		this.firstName = jsonObject.getString("firstname");
		this.lastName = jsonObject.getString("lastname");
		this.description = jsonObject.getString("description");
	}
	
	public void modifyUser(String email, String firstName, String lastName, String description) {
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.description = description;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
}

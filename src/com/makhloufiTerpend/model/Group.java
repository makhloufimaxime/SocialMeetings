package com.makhloufiTerpend.model;

import org.codehaus.jettison.json.JSONObject;

import com.rest.DB.DBRequest;

public class Group {
	private String name;
	private String description;
	private User admin;
	
	public Group(String name, String description, User admin) {
		super();
		this.name = name;
		this.description = description;
		this.admin = admin;
	}
	
	public Group(String jsonData) throws Exception {
		super();
		JSONObject jsonObject = new JSONObject(jsonData);
		this.name = jsonObject.getString("name");
		this.description = jsonObject.getString("description");
		this.admin = new User(DBRequest.getUser(jsonObject.getString("admin")));
	}
	
	public void modifyGroup(String name, String description, User admin) {
		this.name = name;
		this.description = description;
		this.admin = admin;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public User getAdmin() {
		return admin;
	}
	
	public void setAdmin(User admin) {
		this.admin = admin;
	}
}

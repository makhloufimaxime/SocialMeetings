package com.rest.DB;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.codehaus.jettison.json.JSONArray;

import com.makhloufiTerpend.model.Group;
import com.makhloufiTerpend.model.User;
import com.rest.util.ToJSON;

public class DBRequest {

	public static int createUser(User user) throws SQLException{
		Connection conn = DBClass.returnConnection();
		Statement stmt = conn.createStatement();
		stmt.executeUpdate("USE ws_project;");
		String query = "INSERT INTO users (email,firstname,lastname,description) VALUES ('" + user.getEmail() + "', '" + user.getFirstName() + "', '" + user.getLastName() + "', '" + user.getDescription() + "');";
		int i = stmt.executeUpdate(query);
		conn.close();

		if(i > 0){
			return 200;
		}
		else{
			return 400;
		}
	}

	public static String getUser(String email) throws Exception{
		Connection conn = DBClass.returnConnection();
		Statement stmt = conn.createStatement();
		stmt.executeUpdate("USE ws_project;");

		String query = "SELECT email, firstname, lastname, description FROM users WHERE email='" + email + "';";
		ResultSet rs = stmt.executeQuery(query);
		JSONArray jsonArray = (new ToJSON()).toJSONArray(rs);
		conn.close();

		if(rs.first()){
			return jsonArray.getJSONObject(0).toString();
		}
		else{
			return null;
		}
	}

	public static String getUser() throws Exception{
		Connection conn = DBClass.returnConnection();
		Statement stmt = conn.createStatement();
		stmt.executeUpdate("USE ws_project;");

		String query = "SELECT email, firstname, lastname, description FROM users;";
		ResultSet rs = stmt.executeQuery(query);
		JSONArray jsonArray = (new ToJSON()).toJSONArray(rs);
		conn.close();

		if(rs.first()){
			return jsonArray.toString();
		}
		else{
			return null;
		}
	}

	public static int updateUser(User user) throws SQLException{
		Connection conn = DBClass.returnConnection();
		Statement stmt = conn.createStatement();
		stmt.executeUpdate("USE ws_project;");

		String query = "UPDATE users SET firstname='" + user.getFirstName() + "',lastname='" + user.getLastName() + "',description='" + user.getDescription() + "'WHERE email='" + user.getEmail() + "';";
		int i = stmt.executeUpdate(query);
		conn.close();

		if(i > 0){
			return 200;
		}
		else{
			return 400;
		}
	}

	public static int deleteUser(User user) throws SQLException{
		Connection conn = DBClass.returnConnection();
		Statement stmt = conn.createStatement();
		stmt.executeUpdate("USE ws_project;");

		String query = "DELETE FROM users WHERE email='" + user.getEmail() + "';";
		int i = stmt.executeUpdate(query);
		conn.close();

		if(i > 0){
			return 200;
		}
		else{
			return 400;
		}
	}

	public static int createGroup(Group group)throws SQLException{
		Connection conn = DBClass.returnConnection();
		Statement stmt = conn.createStatement();
		stmt.executeUpdate("USE ws_project;");
		String query = "INSERT INTO groups (name,description,admin) VALUES ('" + group.getName() + "', '" + group.getDescription() + "', '" + group.getAdmin().getEmail() + "');";
		int i = stmt.executeUpdate(query);
		conn.close();

		if(i > 0){
			return joinGroup(group.getAdmin(), group);
		}
		else{
			return 400;
		}
	}

	public static String getGroup(String name) throws Exception{
		Connection conn = DBClass.returnConnection();
		Statement stmt = conn.createStatement();
		stmt.executeUpdate("USE ws_project;");

		String query = "SELECT name, description, admin, COUNT(userEmail) AS members FROM affiliations JOIN groups ON affiliations.groupName = groups.name WHERE groupName='" + name + "' GROUP BY groupName ;";
		ResultSet rs = stmt.executeQuery(query);
		JSONArray jsonArray = (new ToJSON()).toJSONArray(rs);
		conn.close();

		if(rs.first()){
			return jsonArray.getJSONObject(0).toString();
		}
		else{
			return null;
		}
	}

	public static String getGroup() throws Exception{
		Connection conn = DBClass.returnConnection();
		Statement stmt = conn.createStatement();
		stmt.executeUpdate("USE ws_project;");

		String query = "SELECT name, description, admin, COUNT(userEmail) AS members FROM affiliations JOIN groups ON affiliations.groupName = groups.name GROUP BY groupName;";
		ResultSet rs = stmt.executeQuery(query);
		JSONArray jsonArray = (new ToJSON()).toJSONArray(rs);
		conn.close();

		if(rs.first()){
			return jsonArray.toString();
		}
		else{
			return null;
		}
	}

	public static int updateGroup(Group group) throws SQLException{
		Connection conn = DBClass.returnConnection();
		Statement stmt = conn.createStatement();
		stmt.executeUpdate("USE ws_project;");

		String query = "UPDATE groups SET description='" + group.getDescription() + "', admin='" + group.getAdmin().getEmail() + "' WHERE name='" + group.getName() + "';";
		int i = stmt.executeUpdate(query);
		conn.close();

		if(i > 0){
			return 200;
		}
		else{
			return 400;
		}
	}

	public static int deleteGroup(Group group) throws SQLException{
		Connection conn = DBClass.returnConnection();
		Statement stmt = conn.createStatement();
		stmt.executeUpdate("USE ws_project;");

		String query = "DELETE FROM groups WHERE name='" + group.getName() + "';";
		int i = stmt.executeUpdate(query);
		conn.close();

		if(i > 0){
			return 200;
		}
		else{
			return 400;
		}
	}

	public static String getUserInGroup(Group group, User user) throws Exception{
		Connection conn = DBClass.returnConnection();
		Statement stmt = conn.createStatement();
		stmt.executeUpdate("USE ws_project;");

		String query = "SELECT email, firstname, lastname, description FROM affiliations JOIN users ON affiliations.userEmail = users.email WHERE groupName='" + group.getName() + "' AND userEmail='" + user.getEmail() + "';";
		ResultSet rs = stmt.executeQuery(query);
		String result = (new ToJSON()).toJSONArray(rs).toString();
		conn.close();

		if(rs.first()){
			return result;
		}
		else{
			return null;
		}
	}

	public static String getUserInGroup(Group group) throws Exception{
		Connection conn = DBClass.returnConnection();
		Statement stmt = conn.createStatement();
		stmt.executeUpdate("USE ws_project;");

		String query = "SELECT email, firstname, lastname, description FROM affiliations JOIN users ON affiliations.userEmail = users.email WHERE groupName='" + group.getName() + "';";
		ResultSet rs = stmt.executeQuery(query);
		String result = (new ToJSON()).toJSONArray(rs).toString();
		conn.close();

		if(rs.first()){
			return result;
		}
		else{
			return null;
		}
	}

	public static int joinGroup(User user, Group group) throws SQLException{
		Connection conn = DBClass.returnConnection();
		Statement stmt = conn.createStatement();
		stmt.executeUpdate("USE ws_project;");

		String query = "INSERT INTO affiliations(groupName, userEmail) VALUES ('" + group.getName() + "', '" + user.getEmail() + "');";
		int i = stmt.executeUpdate(query);
		conn.close();

		if(i > 0){
			return joinBoard(user, group);
		}
		else{
			return 400;
		}
	}

	public static int leaveGroup(User user, Group group) throws SQLException{
		Connection conn = DBClass.returnConnection();
		Statement stmt = conn.createStatement();
		stmt.executeUpdate("USE ws_project;");

		String query = "DELETE FROM affiliations WHERE groupName='" + group.getName() + "' AND userEmail='" + user.getEmail() + "';";
		int i = stmt.executeUpdate(query);
		conn.close();

		if(i > 0){
			return leaveBoard(user, group);
		}
		else{
			return 400;
		}
	}

	public static int joinBoard(User user, Group group) throws SQLException{
		Connection conn = DBClass.returnConnection();
		Statement stmt = conn.createStatement();
		stmt.executeUpdate("USE ws_project;");

		String query = "INSERT INTO boards(groupName, userEmail, message) VALUES ('" + group.getName() + "', '" + user.getEmail() + "', 'The user " + user.getFirstName() + " " + user.getLastName() + " has joined the group " + group.getName() + "!');";
		int i = stmt.executeUpdate(query);
		conn.close();

		if(i > 0){
			return 200;
		}
		else{
			return 400;
		}
	}

	public static int leaveBoard(User user, Group group) throws SQLException{
		Connection conn = DBClass.returnConnection();
		Statement stmt = conn.createStatement();
		stmt.executeUpdate("USE ws_project;");

		String query = "DELETE FROM boards WHERE groupName='" + group.getName() + "' AND userEmail='" + user.getEmail() + "';";
		int i = stmt.executeUpdate(query);
		conn.close();

		if(i > 0){
			return 200;
		}
		else{
			return 400;
		}
	}

	public static int createMessage(User user, Group group, String message) throws SQLException{
		Connection conn = DBClass.returnConnection();
		Statement stmt = conn.createStatement();
		stmt.executeUpdate("USE ws_project;");

		String query = "INSERT INTO boards(groupName, userEmail, message) VALUES ('" + group.getName() + "', '" + user.getEmail() + "', '" + message + "');";
		int i = stmt.executeUpdate(query);
		conn.close();

		if(i > 0){
			return 200;
		}
		else{
			return 400;
		}
	}

	public static String getMessage(Group group) throws Exception{
		Connection conn = DBClass.returnConnection();
		Statement stmt = conn.createStatement();
		stmt.executeUpdate("USE ws_project;");

		String query = "SELECT * FROM boards WHERE groupName='" + group.getName() + "';";
		ResultSet rs = stmt.executeQuery(query);
		JSONArray jsonArray = (new ToJSON()).toJSONArray(rs);
		conn.close();

		if(rs.first()){
			return jsonArray.toString();
		}
		else{
			return null;
		}
	}
}

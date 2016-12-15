package com.makhloufiTerpend.RESTfulService;

import java.sql.SQLException;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONException;

import com.makhloufiTerpend.model.Group;
import com.makhloufiTerpend.model.User;
import com.rest.DB.DBRequest;

@Path("socialMeetings")
public class SocialMeetings {

	@POST
	@Path("user/{email}")
	public Response createUser(
			@PathParam("email") String email,
			@QueryParam("firstname") String firstName,
			@QueryParam("lastname") String lastName,
			@QueryParam("description") String description) throws SQLException
	{
		User user = new User(email, firstName, lastName, description);

		int http_code = DBRequest.createUser(user);
		if (http_code==200) {
			return Response.status(200).entity("The user " + firstName + " " + lastName + " has been created!").build();
		}
		else
		{
			return Response.status(400).entity("This email address is already used.").build();
		}
	}

	@GET
	@Path("user/{email}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUser(@PathParam("email") String email) throws Exception{
		String entity = DBRequest.getUser(email);
		if(entity != null){
			return Response.status(200).entity(entity).build();
		}
		else{
			return Response.status(400).entity("This user doesn't exist.").build();
		}
	}

	@GET
	@Path("user")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUser() throws Exception{
		String entity = DBRequest.getUser();
		if(entity != null){
			return Response.status(200).entity(entity).build();
		}
		else{
			return Response.status(400).entity("There is no user.").build();
		}
	}

	@PUT
	@Path("user/{email}")
	public Response updateUser(
			@PathParam("email") String email, // Retrieve from cookie
			@QueryParam("firstname") String firstName,
			@QueryParam("lastname") String lastName,
			@QueryParam("description") String description) throws SQLException {

		User user = new User(email, firstName, lastName, description);

		int http_code = DBRequest.updateUser(user);
		if (http_code==200) {
			return Response.status(200).entity("User has been updated!").build();
		}
		else {
			return Response.status(400).entity("Failed to update user.").build();
		}
	}

	@DELETE
	@Path("user/{email}")
	public Response deleteUser(@PathParam("email") String email) throws JSONException, Exception {
		User user = new User(DBRequest.getUser(email));

		int http_code = DBRequest.deleteUser(user);
		if (http_code==200) {
			return Response.status(200).entity("User has been deleted!").build();
		}
		else {
			return Response.status(400).entity("Failed to delete user.").build();
		}
	}

	@POST
	@Path("group/{name}")
	public Response createGroup(
			@PathParam("name") String name,
			@QueryParam("description") String description,
			@QueryParam("admin") String admin) throws JSONException, Exception {

		Group group = new Group(name, description, new User(DBRequest.getUser(admin)));

		int http_code = DBRequest.createGroup(group);
		if (http_code==200) {
			return Response.status(200).entity("The group " + name + " has been created!").build();
		}
		else {
			return Response.status(400).entity("This group already exists.").build();
		}
	}

	@GET
	@Path("group/{name}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getGroup(@PathParam("name") String name) throws Exception{
		String entity = DBRequest.getGroup(name);
		if(entity != null){
			return Response.status(200).entity(entity).build();
		}
		else{
			return Response.status(400).entity("This group doesn't exist.").build();
		}
	}

	@GET
	@Path("group")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getGroup() throws Exception{
		String entity = DBRequest.getGroup();
		if(entity != null){
			return Response.status(200).entity(entity).build();
		}
		else{
			return Response.status(400).entity("There is no group.").build();
		}
	}

	@PUT
	@Path("group/{name}")
	public Response updateGroup(
			@PathParam("name") String name,
			@QueryParam("description") String description,
			@QueryParam("email") String email) throws JSONException, Exception {

		User user = new User(DBRequest.getUser(email));
		Group group = new Group(DBRequest.getGroup(name));

		if(user.getEmail().equals(group.getAdmin().getEmail())){
			group.setDescription(description);

			int http_code = DBRequest.updateGroup(group);
			if (http_code==200) {
				return Response.status(200).entity("Your group description has been updated!").build();
			}
			else {
				return Response.status(400).entity("Failed to update this group description.").build();
			}
		}
		else{
			return Response.status(400).entity("You are not allowed to update this group description.").build();
		}
	}

	@DELETE
	@Path("group/{name}")
	public Response deleteGroup(
			@PathParam("name") String name,
			@QueryParam("email") String email) throws JSONException, Exception {

		User user = new User(DBRequest.getUser(email));
		Group group = new Group(DBRequest.getGroup(name));

		if(user.getEmail().equals(group.getAdmin().getEmail())){
			int http_code = DBRequest.deleteGroup(group);
			if (http_code==200) {
				return Response.status(200).entity("Your group " + name + " has been deleted!").build();
			}
			else {
				return Response.status(400).entity("Failed to delete this group.").build();
			}
		}
		else{
			return Response.status(400).entity("You are not allowed to delete this group.").build();
		}
	}

	@GET
	@Path("group/{name}/user/{email}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUserInGroup(
			@PathParam("name") String name,
			@PathParam("email") String email) throws Exception {

		User user = new User(DBRequest.getUser(email));
		Group group = new Group(DBRequest.getGroup(name));

		String entity = DBRequest.getUserInGroup(group, user);
		if (entity != null) {
			return Response.status(200).entity(entity).build();
		}
		else {
			return Response.status(400).entity("This user isn't in this group.").build();
		}
	}

	@GET
	@Path("group/{name}/user")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUserInGroup(
			@PathParam("name") String name) throws Exception {

		Group group = new Group(DBRequest.getGroup(name));

		String entity = DBRequest.getUserInGroup(group);
		if (entity != null) {
			return Response.status(200).entity(entity).build();
		}
		else {
			return Response.status(400).entity("Failed to retrieve the list of the members of this group.").build();
		}
	}

	@POST
	@Path("group/{name}/user/{email}")
	public Response joinGroup(
			@PathParam("name") String name,
			@PathParam("email") String email) throws JSONException, Exception {

		User user = new User(DBRequest.getUser(email));
		Group group = new Group(DBRequest.getGroup(name));

		int http_code = DBRequest.joinGroup(user, group);
		if (http_code==200) {
			return Response.status(200).entity("You have joined the group " + name + "!").build();
		}
		else {
			return Response.status(400).entity("You can't join this group.").build();
		}
	}

	@DELETE
	@Path("group/{name}/user/{email}")
	public Response leaveGroup(
			@PathParam("name") String name,
			@PathParam("email") String email) throws Exception{

		User user = new User(DBRequest.getUser(email));
		Group group = new Group(DBRequest.getGroup(name));

		int http_code = DBRequest.leaveGroup(user, group);
		if (http_code==200) {
			return Response.status(200).entity("You have left the group " + name + "!").build();
		}
		else {
			return Response.status(400).entity("Failed to leave this group.").build();
		}
	}
	
	@GET
	@Path("group/{name}/message")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getMessage(
			@PathParam("name") String name) throws JSONException, Exception {

		Group group = new Group(DBRequest.getGroup(name));
		String entity = DBRequest.getMessage(group);
		if(entity != null){
			return Response.status(200).entity(entity).build();
		}
		else{
			return Response.status(400).entity("There is no message in this group.").build();
		}
	}

	@POST
	@Path("group/{name}/user/{email}/message")
	public Response publishMessage(
			@PathParam("name") String name,
			@PathParam("email") String email,
			@QueryParam("message") String message) throws JSONException, Exception {

		User user = new User(DBRequest.getUser(email));
		Group group = new Group(DBRequest.getGroup(name));

		int http_code = DBRequest.createMessage(user, group, message);
		if (http_code==200) {
			return Response.status(200).entity("Message sent to " + name + " board: " + message).build();
		}
		else {
			return Response.status(400).entity("Failed to publish your message.").build();
		}
	}
}


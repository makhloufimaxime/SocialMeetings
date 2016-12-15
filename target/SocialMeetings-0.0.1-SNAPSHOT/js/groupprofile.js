var user = null;
var group = null;
var members = null;
var messages = null;
var admin = false;
var member = false;
var deleted = false;


$(document).ready(function(){
	render();
});

function updateGroup(){
	console.log("Function updateGroup()");
	var description = document.getElementById("description").value;
	$.ajax({
		async: false,
		url : "http://localhost:8080/SocialMeetings/v1/app/socialMeetings/group/" + group.name + "?description=" + description + "&email=" + user.email,
		type : 'PUT', 
		success : function(data, status){
			console.log("Group updated.");
		},

		error : function(data, status, error){
			console.log("Failed to update group.");
			console.log(data);
			console.log(status);
			console.log(error);
		}
	});
	render();
}

function deleteGroup(){
	console.log("Function createGroup()");
	$.ajax({
		async: false,
		url : "http://localhost:8080/SocialMeetings/v1/app/socialMeetings/group/" + group.name + "?email=" + user.email,
		type : 'DELETE', 
		success : function(data, status){
			console.log("Group deleted.");
			deleted = true;
		},

		error : function(data, status, error){
			console.log("Failed to delete group.");
			console.log(data);
			console.log(status);
			console.log(error);
		}
	});
	render();
}

function joinGroup(){
	console.log("Function joinGroup()");
	$.ajax({
		async: false,
		url : "http://localhost:8080/SocialMeetings/v1/app/socialMeetings/group/" + group.name + "/user/" + user.email,
		type : 'POST', 
		success : function(data, status){
			console.log("Group joined.");
			member = true;
		},

		error : function(data, status, error){
			console.log("Failed to joined group.");
			console.log(data);
			console.log(status);
			console.log(error);
		}
	});
	render();
}

function leaveGroup(){
	console.log("Function leaveGroup()");
	$.ajax({
		async: false,
		url : "http://localhost:8080/SocialMeetings/v1/app/socialMeetings/group/" + group.name + "/user/" + user.email,
		type : 'DELETE',
		success : function(data, status){
			console.log("Group left.");
			member = false;
		},

		error : function(data, status, error){
			console.log("Failed to leave group.");
			console.log(data);
			console.log(status);
			console.log(error);
		}
	});
	render();
}

function getMembers(){
	console.log("Function getMembers()");
	var listMembers = null;
	$.ajax({
		async: false,
		url : "http://localhost:8080/SocialMeetings/v1/app/socialMeetings/group/" + group.name + "/user",
		type : 'GET', 
		success : function(data, status){
			console.log("Members list retrieved.");
			data = JSON.stringify(data);
			obj = JSON.parse(data);
			listMembers = obj;
		},

		error : function(data, status, error){
			console.log("Failed to retrieve members list.");
			console.log(data);
			console.log(status);
			console.log(error);
		}
	});
	return listMembers;
}

function getMessages(){
	console.log("Function getMessages()");
	var listMessages = null;
	$.ajax({
		async: false,
		url : "http://localhost:8080/SocialMeetings/v1/app/socialMeetings/group/" + group.name + "/message",
		type : 'GET', 
		success : function(data, status){
			console.log("Messages list retrieved.");
			data = JSON.stringify(data);
			obj = JSON.parse(data);
			listMessages = obj;
		},

		error : function(data, status, error){
			console.log("Failed to retrieve messages list.");
			console.log(data);
			console.log(status);
			console.log(error);
		}
	});
	return listMessages;
}

function sendMessage(){
	var message = document.getElementById("messageInput").value;
	console.log("Function sendMessage()");
	$.ajax({
		async: false,
		url : "http://localhost:8080/SocialMeetings/v1/app/socialMeetings/group/" + group.name + "/user/" + user.email + "/message?message=" + message,
		type : 'POST', 
		success : function(data, status){
			console.log("Message sent.");
		},

		error : function(data, status, error){
			console.log("Failed to send message.");
			console.log(data);
			console.log(status);
			console.log(error);
		}
	});
	render();
}

function isMember(){
	console.log("Function isMember()");
	$.ajax({
		async: false,
		url : "http://localhost:8080/SocialMeetings/v1/app/socialMeetings/group/" + group.name + "/user/" + user.email,
		type : 'GET', 
		success : function(data, status){
			member = true;
		},

		error : function(data, status, error){
			console.log(data);
			console.log(status);
			console.log(error);
		}
	});
}

function isAdmin(){
	console.log("Function isAdmin()");
	if(group.admin == user.email){
		admin = true
	}
}

function renderInformation(){
	console.log("Function RenderInformation()");
	if(admin){
		document.getElementById("description").disabled = false;
	}
	else{
		document.getElementById("description").disabled = true;
	}
	document.getElementById("name").disabled = true;
	document.getElementById("name").value = group.name;
	document.getElementById("description").value = group.description;
	var admin = getUser(group.admin);
	var html = "<a href=\"http://localhost:8080/SocialMeetings/profile.html?email=" + admin.email + "\">" + admin.email + "</a> " + admin.firstname + " " + admin.lastname;
	$('#admin').html(html);
}

function renderButtons(){
	console.log("Function renderButton()");
	if(member){
		if(admin){
			var html = "<button onclick=\"updateGroup()\">Update group</button>";
			html = html + "<button onclick=\"deleteGroup()\">Delete group</button>";
		}
		else{
			var html = "<button onclick=\"leaveGroup()\">Leave group</button>";
		}
	}
	else{
		var html = "<button onclick=\"joinGroup()\">Join group</button>";
	}
	$('#buttons').html(html);
}

function renderBoards(){
	if(member){
		messages = getMessages();
		console.log("Function renderBoards()");
		var html = "<p><h4>Messages:</h4>";
		console.log(messages.length);
		for(i = 0; i < messages.length; i++){
			var tempUser =  getUser(messages[i].userEmail);
			html = html + "<b>[" + tempUser.firstname + " " + tempUser.lastname + "]</b>: " + messages[i].message + "</br>";

		}
		html = html + "</p>";
		html = html + "<input id='messageInput' type='text' placeholder='Type a message' name='messageInput' onKeyPress=\"if (event.keyCode == 13) sendMessage()\" required>";
		html = html + "<button onclick=\"sendMessage()\">Send message</button>"
	}
	else{
		var html = "<p>You must join the group if you want to see the messages.</p>";
	}
	$('#board').html(html);
}

function renderMembers(){
	console.log("Function renderMembers()");
	var html = "<p>Total members: " + group.members + "</p>";
	html = html + "<p><table><tr><td>Email</td><td>First Name</td><td>Last Name</td></tr>";
	for(i = 0; i < members.length; i++){
		html = html + "<tr><td><a href=\"http://localhost:8080/SocialMeetings/profile.html?email=" + members[i].email + "\">" + members[i].email + "</a></td>"
		html = html + "<td>" + members[i].firstname + "</td>";
		html = html + "<td>" + members[i].lastname + "</td></tr>";
	}
	html = html + "</table><p>";
	$('#listMembers').html(html);
}

function render(){
	if(deleted){
		location.href="groups.html";
	}
	else{
		if(loggedIn){
			user = getUser(readCookie("email"));
			group = getGroup(decodeURI(location.search.substring(location.search.lastIndexOf("=")+1)));
			members = getMembers();
			if(group != null){
				isMember();
				isAdmin();
				renderInformation();
				renderButtons();
				renderBoards();
				renderMembers();
			}
			else{
				location.href="groups.html";
			}
		}
		else{
			location.href="index.html";
		}
	}

}
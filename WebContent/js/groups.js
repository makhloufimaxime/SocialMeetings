var errorGroup = false;
var groups = null;

$(document).ready(function(){
	render();
});

function createGroup(){
	console.log("Function createGroup()");
	var email = readCookie("email");
	var name = document.getElementById("name").value;
	var description = document.getElementById("description").value;
	if(name != ""){
		$.ajax({
			async: false,
			url : "http://localhost:8080/SocialMeetings/v1/app/socialMeetings/group/" + name + "?description=" + description + "&admin=" + email,
			type : 'POST', 
			success : function(data, status){
				console.log("Group created.");
				errorGroup = false;
			},

			error : function(data, status, error){
				console.log("Failed to create group.");
				console.log(data);
				console.log(status);
				console.log(error);
				errorGroup = true;
			}
		});
		render();
	}
}

function getGroups(){
	console.log("Function getGroups()");
	var listGroup = null;
	$.ajax({
		async: false,
		url : "http://localhost:8080/SocialMeetings/v1/app/socialMeetings/group",
		type : 'GET', 
		success : function(data, status){
			console.log("Groups list retrieved.");
			data = JSON.stringify(data);
			obj = JSON.parse(data);
			listGroup = obj;
		},

		error : function(data, status, error){
			console.log("Failed to retrieve groups list.");
			console.log(data);
			console.log(status);
			console.log(error);
		}
	});
	return listGroup;
}

function renderGroups(){
	var html = "<p><table><tr><td>Group name</td><td>Admin</td><td>Members</td><td></td></tr>";
	console.log("number of groups : " + groups.length);
	for(i = 0; i < groups.length; i++){
		html = html + "<tr><td><a href=\"http://localhost:8080/SocialMeetings/groupprofile.html?name=" + groups[i].name + "\">" + groups[i].name + "</a></td>";
		html = html + "<td>" + groups[i].admin + "</td>";
		html = html + "<td>" + groups[i].members + "</td></tr>";
	}
	html = html + "</table><p>";
	$('#listGroup').html(html);
}

function renderError(){
	if(errorGroup){
		var error = "<b>This group already exists!</b>";
	}
	else{
		var error = "";
	}
	$('#error').html(error);
}

function render(){
	console.log("Function render()");
	document.getElementById("name").value = "";
	document.getElementById("description").value = "";
	if(loggedIn){
		groups = getGroups();
		renderError();
		renderGroups();
	}
	else{
		location.href="index.html";
	}
}
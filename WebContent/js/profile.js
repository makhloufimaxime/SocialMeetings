var user = null;
var currentUser = null;

$(document).ready(function(){
	render();
});

function updateUser(){
	console.log("Function updateUser()");
	var email = user.email;
	var lastname = document.getElementById("lastname").value;
	var firstname = document.getElementById("firstname").value;
	var description = document.getElementById("description").value;
	$.ajax({
		async: false,
		url : "http://localhost:8080/SocialMeetings/v1/app/socialMeetings/user/" + email + "?firstname=" + firstname + "&lastname=" + lastname + "&description=" + description,
		type : 'PUT',
		success : function(data, status){
			console.log("User updated.");
		},

		error : function(data, status, error){
			console.log("Failed to update user.");
			console.log(data);
			console.log(status);
			console.log(error);
		}
	});
	renderHeader();
	render();
}

function deleteUser(){
	console.log("Function deleteUser()");
	var email = user.email;
	$.ajax({
		async: false,
		url : "http://localhost:8080/SocialMeetings/v1/app/socialMeetings/user/" + email,
		type : 'DELETE',
		success : function(data, status){
			console.log("User deleted");
			logOut();
		},

		error : function(data, status, error){
			console.log("Failed to delete user.");
			console.log(data);
			console.log(status);
			console.log(error);
		}
	});
	render();
}

function renderOwnProfile(){
	var html = "<h4>Personnal Information:</h4>"
	html = html + "<p><b>First Name: </b><input id='firstname' type='text' placeholder='First Name' name='firstname' value =" + user.firstname + " required></p>";
	html = html + "<p><b>Last Name: </b><input id='lastname' type='text' placeholder='Last Name' name='lastname' value =" + user.lastname + " required></p>";
	html = html + "<p><b>Description: </b><textarea id='description'>" + user.description + "</textarea></p>";
	html = html + "<button onclick=\"updateUser()\">Save modifications</button>";
	html = html + "<button onclick=\"deleteUser()\">Delete your account</button>";
	$('#body').html(html);
}

function renderOtherProfile(){
	var html = "<h4>Profile of " + currentUser.email + "</h4>"
	html = html + "<p><b>First Name: </b><input id='firstname' type='text' placeholder='First Name' name='firstname' value =" + currentUser.firstname + " disabled required></p>";
	html = html + "<p><b>Last Name: </b><input id='lastname' type='text' placeholder='Last Name' name='lastname' value =" + currentUser.lastname + " disabled required></p>";
	html = html + "<p><b>Description: </b><textarea id='description' disabled>" + currentUser.description + "</textarea></p>";
	$('#body').html(html);
}


function render(){
	if(loggedIn){
		user = getUser(readCookie("email"));
		currentUser = getUser(decodeURI(location.search.substring(location.search.lastIndexOf("=")+1)));
		if(user.email != currentUser.email){
			renderOtherProfile();
		}
		else{
			renderOwnProfile();
		}
	}
	else{
		location.href="index.html";
	}
}

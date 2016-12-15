var loggedIn = false;
var currentGroup = null;

$(document).ready(function(){
	isLoggedIn();
	renderHeader();
});

function renderHeader(){
	console.log("Function renderHeader()");
	if(loggedIn){
		var user = getUser(readCookie("email"));
		var header = '<a href=index.html>Home</a> | <a href=profile.html?email=' + user.email + '>Profile</a> | <a href=groups.html>Groups</a> | <label><b>You are connected as ' + user.firstname + ' ' + user.lastname + '</b></label>  <button onclick=\"logOut()\">Log out</button>';
		$('#header').html(header);
	}
	else{
		var header = '<a href=index.html>Home</a> | <a href=signup.html>Sign up</a> | <a href=login.html>Log in</a>';
		$('#header').html(header);
	}
}

function createCookie(name,value,days) {
	console.log("Function createCookie()");
	if (days) {
		var date = new Date();
		date.setTime(date.getTime()+(days*24*60*60*1000));
		var expires = "; expires="+date.toGMTString();
	}
	else var expires = "";
	document.cookie = name+"="+value+expires+"; path=/";
}

function readCookie(name) {
	console.log("Function readCookie()");
	var nameEQ = name + "=";
	var ca = document.cookie.split(';');
	for(var i=0;i < ca.length;i++) {
		var c = ca[i];
		while (c.charAt(0)==' ') c = c.substring(1,c.length);
		if (c.indexOf(nameEQ) == 0) {
			console.log("Read Cookie " + c.substring(nameEQ.length,c.length))
			return c.substring(nameEQ.length,c.length);
		}
	}
	return null;
}

function eraseCookie(name) {
	console.log("Function eraseCookie()");
	createCookie(name,"",-1);
}

function getUser(email){
	console.log("Function getUser()");
	var user = null;
	$.ajax({
		async: false,
		url : "http://localhost:8080/SocialMeetings/v1/app/socialMeetings/user/" + email,
		type : 'GET',
		dataType : 'json',
		success : function(data, status){
			user = JSON.parse(JSON.stringify(data));
		},

		error : function(data, status, error){
			console.log("This user doesn't exist.");
			console.log(data);
			console.log(status);
			console.log(error);
		}
	});
	return user;
}

function getGroup(name){
	console.log("Function getGroup()");
	var group = null;
	$.ajax({
		async: false,
		url : "http://localhost:8080/SocialMeetings/v1/app/socialMeetings/group/" + name,
		type : 'GET',
		dataType : 'json',
		success : function(data, status){
			group = JSON.parse(JSON.stringify(data));
		},

		error : function(data, status, error){
			console.log("This group doesn't exist.");
			console.log(data);
			console.log(status);
			console.log(error);
		}
	});
	return group;
}

function logOut(){
	console.log("Function logOut()");
	eraseCookie("email");
	loggedIn = false;
	location.href="index.html";
}

function isLoggedIn(){
	console.log("Function isLoggedIn");
	var email = readCookie("email");
	if(email != null){
		if(getUser(email) != null){
			console.log("You are logged in.");
			loggedIn = true;
		}
		else{
			console.log("You are not logged in: This user doesn't exist.");
			eraseCookie("email");
			loggedIn = false;
		}
	}
	else{
		console.log("You are not logged in.");
		loggedIn = false;
	}
}
$(document).ready(function(){
	render();
});

function signUp(){
	console.log("Function signUp()");
	var email = document.getElementById("email").value;
	var firstname = document.getElementById("firstname").value;
	var lastname = document.getElementById("lastname").value;
	var description = document.getElementById("description").value;
	if(email != "" && lastname != "" && firstname != ""){
		$.ajax({
			async: false,
			url : "http://localhost:8080/SocialMeetings/v1/app/socialMeetings/user/" + email + "?firstname=" + firstname + "&lastname=" + lastname + "&description=" + description,
			type : 'POST', 
			success : function(data, status){
				console.log("Signed up successfully.");
				createCookie("email",email, 0);
				loggedIn = true;
			},

			error : function(data, status, error){
				console.log("Failed to sign up.");
				console.log(data);
				console.log(status);
				console.log(error);
				loggedIn = false;
			}
		});
		render();
	}
}

function render(){
	console.log("Function render()");
	if(loggedIn){
		location.href="index.html";
	}
}
var incorrectEmail = false;

$(document).ready(function(){
	render();
});

function logIn(){
	console.log("Function logIn()");
	var email = document.getElementById("email").value;
	if(email != ""){
		var user = getUser(email);
		if(user != null){
			createCookie("email", email, 0);
			loggedIn = true;
			incorrectEmail = false;
		}
		else{
			loggedIn = false;
			incorrectEmail = true;
		}
		render();
	}
}

function render(){
	console.log("Function render()");
	if(loggedIn){
		location.href="index.html";
	}
	else{
		if(incorrectEmail){
			var error = "<label><b>This email address is incorrect.</b>";
			$('#error').html(error);
		}
	}
}
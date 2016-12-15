$(document).ready(function(){
	render();
});


function render(){
	console.log("Function render()");
	if(loggedIn){
		var body = "<p><label><b>You are connected!</b></label><p>";
		$('#body').html(body);
	}
	else{
		var body = "<p><label><b>You are not connected!</b></label><p>";
		$('#body').html(body);
	}
}
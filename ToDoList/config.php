<?php 
	$server = "localhost";
	$user = "root";
	$pass = "152431524466aA";
	$database = "todolist";

	$conn = mysqli_connect($server, $user, $pass, $database);

	if(!$conn){
		die("<script> alert('Connection Failed.') </script>");
	}
?>	
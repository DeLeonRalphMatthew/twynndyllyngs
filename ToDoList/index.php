<?php

	include 'config.php'; 

	session_start();

	if(isset($_POST['submit'])){
		$email = $_POST['email'];
		$password = $_POST['password'];
		
		$sql = "SELECT * FROM `accounts` WHERE Email = '$email' AND Password = '$password'";
		$result = mysqli_query($conn, $sql);

		if(mysqli_num_rows($result) > 0){
			$row = mysqli_fetch_assoc($result);
			$_SESSION['username'] = $row['Username'];
			$_SESSION['email'] = $row['Email'];
			header("Location: ./files/index.php");
		}else{
			echo "<script>alert('Credentials doesn\'t match any record.')</script>";
		}
	}

?>

<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	
	<link rel="stylesheet" type="text/css" href="indexStyle.css">

	<title>Login</title>
</head>
<body>

	
	<div class="container">
		<form action="" method="POST" class="login-email">
			<p class="login-text" style="font-size: 2rem; font-weight: 800;">Login</p>
			<div class="input-group">
				<input type="email" placeholder="Email" name="email" required>
			</div>
			<div class="input-group">
				<input type="password" placeholder="Password" name="password" required>
			</div>
			<div class="input-group">
				<button name="submit" class="btn">Login</button>
			</div>
			<p class="login-register-text">Don't have an account? <a href="register.php" class="register-here">Register Here</a>.</p>
		</form>
	</div>
</body>
</html>
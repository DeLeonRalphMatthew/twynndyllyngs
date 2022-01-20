<?php
	include 'config.php'; 

	if(isset($_POST['submit'])){
		$username = $_POST['username'];
		$email = $_POST['email'];
		$password = $_POST['password'];
		$cpassword = $_POST['cpassword'];
		
		$sql = "SELECT `Email` FROM `accounts` WHERE Email LIKE '$email'";
		$result = mysqli_query($conn, $sql);

		if(mysqli_num_rows($result) > 0){
			echo "<script>alert('Email is already registered.')</script>";
		}else if (strlen($username) < 3 || strlen($username) > 18) {
			echo "<script>alert('Username is invalid.')</script>";
		}else{
			if($password == $cpassword){
				if(strlen($password) > 7){
					$sql = "INSERT INTO `accounts`(`Email`, `Username`, `Password`) VALUES ('$email','$username','$password')";

					$result = mysqli_query($conn, $sql);
				
					if($result){
						echo "<script>alert('Success!.')</script>";
						echo '<meta http-equiv="Refresh" content="0; url=index.php">';
					}else{
						echo "<script>alert('Something went wrong.')</script>";
					}
				}else{
					echo "<script>alert('Password Must Have a Minimum Length of 8.')</script>";
				}
			}else{
				echo "<script>alert('Password Not Matched.')</script>";
			}
		}
	}
?>


<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">

	<link rel="stylesheet" type="text/css" href="indexStyle.css">

	<title>Register Form - Pure Coding</title>
</head>
<body>
	<div class="container">
		<form action="" method="POST" class="login-email">
            <p class="login-text" style="font-size: 2rem; font-weight: 800;">Register</p>
			<div class="input-group">
				<input type="email" placeholder="Email" name="email" required>
			</div>
			<div class="input-group">
				<input type="text" placeholder="Username" name="username" required>
			</div>
			<div class="input-group">
				<input type="password" placeholder="Password" name="password" required>
            </div>
            <div class="input-group">
				<input type="password" placeholder="Confirm Password" name="cpassword" required>
			</div>
			<div class="input-group">
				<button name="submit" class="btn">Register</button>
			</div>
			<p class="login-register-text">Have an account? <a href="index.php">Login Here</a>.</p>
		</form>
	</div>
</body>
</html>
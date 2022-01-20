<?php 
	include './config.php';

	session_start(); 
?>


<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta http-equiv="X-UA-Compatible" content="ie=edge">
	<link rel="stylesheet" href="../CSS/style.css">

	<title>Home</title>
</head>
<body>
	<div class="main-section">
		<div class="add-section">
			<form action="add.php" method="POST" autocomplete="off">
				<input type="text" name="title" placeholder="Required" required/>
				<button type="submit"> Add &nbsp; <span>&#43;</span> </button>
			</form>		
		</div>

		<?php 
			$fromEmail = $_SESSION['email'];
			$username = $_SESSION['username'];
			$sql = "SELECT * FROM todoitems WHERE FromEmail = '$fromEmail' ORDER BY ItemNumber DESC";
			$result = mysqli_query($conn, $sql);
		?>

		<div class="show-todo-section">
			<?php
				if(mysqli_num_rows($result) > 0){
					
					while($row = mysqli_fetch_assoc($result)){ ?>

						<div class="todo-item">
						<span class="remove-to-do">x</span>

						<input type="checkbox" class="check-box" <?php if($row['ItemState'] == "Due"){ echo "checked";} ?> />
						<?php		
						echo   '<h2 class="checked"> '.$row['Description'].' </h2>
								<br>
								<small> created: '.$row['DateCreated'].' </small>
								<br>
							  </div>';
					}
				}else{
					echo '<div class="todo-item">
							  <div class="empty">
							 	<img src="../IMG/2.gif" width="100%" /> 
							  </div>	
					</div>';
				}
				
			?>
		</div>
	</div>

</body>
</html>
<?php
	session_start(); 

    if(isset($_POST['title'])){
        include '../config.php';

        $title = $_POST['title'];
        $email = $_SESSION['email'];
        $sql = "INSERT INTO todoitems(FromEmail, Description, DateCreated, DateDue, ItemState)
         VALUES ('$email', '$title', '2022-01-05 00:00:00', '2022-01-03', 'Unchecked')";
		$result = mysqli_query($conn, $sql);
        
        if(!$result){
            echo 'asd';
        }else{
            header("Location: index.php");
        }
    }
?>
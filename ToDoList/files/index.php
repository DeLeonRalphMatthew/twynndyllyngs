<?php 
require 'db_conn.php';

session_start();

if(!isset($_SESSION['username'])){
    header("Location: ../index.php");
}

$username = $_SESSION['username'];
$fromemail = $_SESSION['email'];

if(isset($_POST['logout'])){
    session_destroy();
    
    header("Location: ../index.php");
}
?>


<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>To-Do List</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <div class="main-section">
      
       <div class="user-section">
            <span class="nice"> Welcome, <?php echo "<span class=\"username\">$username</span>" ?>!</span>
            <form method="POST">
                <input class="logout-button" type="submit" name="logout" value="Log Out">
            </form>
       </div>


       <div class="add-section">
          <form action="app/add.php" method="POST" autocomplete="off">
             <?php if(isset($_GET['mess']) && $_GET['mess'] == 'error'){ ?>
                <input type="text" 
                     name="title" 
                     style="border-color: #ff6666"
                     placeholder="This field is required" />
              <button type="submit">Add &nbsp; <span>&#43;</span></button>

             <?php }else{ ?>
              <input type="text" 
                     name="title" 
                     placeholder="What do you need to do?" />
              <button type="submit">Add &nbsp; <span>&#43;</span></button>
             <?php } ?>
          </form>
       </div>
       <?php 
          $todos = $conn->query("SELECT * FROM todos WHERE FromEmail = '$fromemail' ORDER BY id DESC");
       ?>
       <div class="show-todo-section">
            <?php if($todos->rowCount() <= 0){ ?>
                <div class="todo-item">
                    <div class="empty">
                        <img src="img/f.png" width="100%" />
                        <img src="img/Ellipsis.gif" width="80px">
                    </div>
                </div>
            <?php } ?>

            <?php while($todo = $todos->fetch(PDO::FETCH_ASSOC)) { ?>
                <div class="todo-item">
                    <div class="inner">
                        <?php if($todo['checked']){ ?> 
                            <input type="checkbox"
                                class="check-box"
                                data-todo-id ="<?php echo $todo['id']; ?>"
                                checked />
                            <h2 class="checked"><?php echo $todo['title'] ?></h2>
                        <?php }else { ?>
                            <input type="checkbox"
                                data-todo-id ="<?php echo $todo['id']; ?>"
                                class="check-box" />
                            <h2 class="normal"><?php echo $todo['title'] ?></h2>
                        <?php } ?>
                        <span id="<?php echo $todo['id']; ?>"
                            class="remove-to-do">x</span>
                        <br>
                    </div>
                    <small><?php echo $todo['date_time'] ?></small> 
                    <p class="date-created">Date Created</p>
                </div>
            <?php } ?>
       </div>
    </div>

    <script src="js/jquery-3.2.1.min.js"></script>

    <script>
        $(document).ready(function(){
            $('.remove-to-do').click(function(){
                const id = $(this).attr('id');
                
                $.post("app/remove.php", 
                      {
                          id: id
                      },
                      (data)  => {
                         if(data){
                             $(this).parent().parent().hide(600);
                         }
                      }
                );
            });

            $(".check-box").click(function(e){
                const id = $(this).attr('data-todo-id');
                
                $.post('app/check.php', 
                      {
                          id: id
                      },
                      (data) => {
                          if(data != 'error'){
                              const h2 = $(this).next();
                              if(data === '1'){
                                  h2.removeClass('checked');
                                  h2.addClass('normal');
                              }else {
                                  h2.addClass('checked');
                                  h2.removeClass('normal');
                              }
                          }
                      }
                );
            });
        });
    </script>
</body>
</html>
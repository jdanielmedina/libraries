<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Login Example</title>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
        <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>

        <style>
            .form-horizontal { 
                margin-top: 10px; 
            }
        </style>

    </head>

    <body>

        <div class="container">            

            <form action="verify.php" class="form-horizontal" role="form" method="post">
                <?php if (isset($_GET['alert'])) { ?>
                    <div class = "alert alert-danger">
                        <a href = "#" class = "close" data-dismiss = "alert" aria-label = "close">&times;
                        </a>
                        <strong>Login failed!</strong> <?= $_GET['alert'] ?> 
                    </div>
                <?php }
                ?>
                <div class="form-group">
                    <label class="control-label col-sm-2" for="usr">Username:</label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control" id="usr" placeholder="Enter username" name="user">
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-sm-2" for="pwd">Password:</label>
                    <div class="col-sm-10">          
                        <input type="password" class="form-control" id="pwd" placeholder="Enter password" name="pass">
                    </div>
                </div>

                <div class="form-group">
                    <label class="control-label col-sm-2" for="phone">Phone:</label>
                    <div class="col-sm-10">          
                        <input type="text" class="form-control" id="phone" placeholder="Enter phone number (optional)" name="phone">
                    </div>
                </div>

                <div class="form-group">        
                    <div class="col-sm-offset-2 col-sm-10">
                        <button type="submit" class="btn btn-default">Login</button>
                    </div>
                </div>
            </form> 

        </div>

    </body>
</html>


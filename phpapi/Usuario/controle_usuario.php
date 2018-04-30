<?php

if($_SERVER["REQUEST_METHOD"]=="POST") {
    include '../connection.php';

    if (isset($_POST['EMAIL'], $_POST['SENHA'])) {
        $email = $_POST['EMAIL'];
        $senha = $_POST['SENHA'];

        if (!empty($email) && !empty($senha)) {
            logIn($email, $senha);
        } else {
            echo json_encode("Email ou senha nao foram preenchidos!");
        }
    }

}



function logIn($email,$senha){

    global $connect;


    $query = "SELECT *FROM USUARIO WHERE EMAIL = '$email' AND SENHA = '$senha'";


    $result = mysqli_query($connect, $query);
    $number_of_rows = mysqli_num_rows($result);

    if($number_of_rows > 0)
    {
        $json['success'] = 'Ola :)';
        echo json_encode($json);
        mysqli_close($connect);
    }
    else
    {
        $json['error'] = 'Email ou senha estao incorretos';
        echo json_encode($json);
    }
}


?>
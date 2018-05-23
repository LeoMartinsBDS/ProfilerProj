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

    $senha_md5 = md5($senha);

    $query = "SELECT *FROM USUARIO WHERE EMAIL = '$email' AND SENHA = '$senha_md5'";


    $result = mysqli_query($connect, $query);
    $number_of_rows = mysqli_num_rows($result);


    if($number_of_rows > 0)
    {
        while ($row = mysqli_fetch_assoc($result)) {
            $temp_array[] = $row;
        }
        header('Content-Type: application/json');
        echo json_encode(array("usuario"=>$temp_array));

    }
    else
    {
        $json['error'] = 'Email ou senha estao incorretos';
        echo json_encode($json);
    }

    mysqli_close($connect);
}


?>
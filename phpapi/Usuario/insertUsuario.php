<?php

if($_SERVER["REQUEST_METHOD"]=="POST"){
	require '../connection.php';
    createUsuario();
}


function createUsuario()
{
	global $connect;


	if(isset($_POST["IDADE"]))
    {
	    $idade = $_POST["IDADE"];
    }

    if(isset($_POST["EMAIL"]))
    {
	    $email = $_POST["EMAIL"];
    }

    if(isset($_POST["FOTO"]))
    {
        $foto = $_POST["FOTO"];
    }

    if(isset($_POST["NOME"]))
    {
        $nome = $_POST["NOME"];
    }

    if(isset($_POST["REPUTACAO"]))
    {
        $reputacao = $_POST["REPUTACAO"];
    }

    if(isset($_POST["SENHA"]))
    {
        $senha = $_POST["SENHA"];
        $senha_md5 = md5($senha);
    }


	if(isset($_POST["IDADE"]) && isset($_POST["EMAIL"]) && isset($_POST["SENHA"]) && isset($_POST["NOME"]) )
    {
        $query = " INSERT INTO USUARIO(idade,email,foto,nome,reputacao,senha) VALUES ('$idade','$email','$foto','$nome','$reputacao','$senha_md5');";

    }
    else
    {
        $query = " INSERT INTO USUARIO(idade) VALUES ('''');";
    }

    $inserted = mysqli_query($connect, $query);

    if($inserted == 1){

        $querySelect = "SELECT LAST_INSERT_ID();";

        $result = mysqli_query($connect, $querySelect);
        $number_of_rows = mysqli_num_rows($result);

        $temp_array  = array();

        if($number_of_rows > 0) {
            while ($row = mysqli_fetch_assoc($result)) {
                $temp_array[] = $row;
            }
        }

        header('Content-Type: application/json');
        echo json_encode(array("codUsuario"=>$temp_array));
    }
    else{
        $json['error'] = "Houve um erro ao salvar usuario!";
        echo json_encode($json);
    }

    mysqli_close($connect);
	
}
?>

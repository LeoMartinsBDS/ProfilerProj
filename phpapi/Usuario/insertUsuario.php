<?php

if($_SERVER["REQUEST_METHOD"]=="POST"){
	require '../connection.php';
    createUsuario();
}


function createUsuario()
{
	global $connect;
	
	$data_nascimento = $_POST["DATA_NASCIMENTO"];
	$email = $_POST["EMAIL"];
	$foto = $_POST["FOTO"];
    $nome = $_POST["NOME"];
    $reputacao = $_POST["REPUTACAO"];
    $senha = $_POST["SENHA"];
	
	$query = " INSERT INTO USUARIO(data_nascimento,email,foto,nome,reputacao,senha) VALUES ('$data_nascimento','$email','$foto','$nome','$reputacao','$senha');";

    $inserted = mysqli_query($connect, $query);

    if($inserted == 1){
        $json['success'] = "Usuario inserido com sucesso!";
    }
    else{
        $json['error'] = "Houve um erro ao salvar usuario!";

    }
    echo json_encode($json);
    mysqli_close($connect);
	
}
?>

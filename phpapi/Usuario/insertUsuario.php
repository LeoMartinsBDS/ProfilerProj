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

    $senha_md5 = md5($senha);
	
	$query = " INSERT INTO USUARIO(data_nascimento,email,foto,nome,reputacao,senha) VALUES ('$data_nascimento','$email','$foto','$nome','$reputacao','$senha_md5');";

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

    }
    echo json_encode($json);
    mysqli_close($connect);
	
}
?>

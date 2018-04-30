<?php

if($_SERVER["REQUEST_METHOD"]=="POST"){
	require '../connection.php';
    createOcorrencia();
}


function createOcorrencia()
{
	global $connect;
	
	$localidade = $_POST["LOCALIDADE"];
	$data_hora = $_POST["DATA_HORA"];
	$descricao = $_POST["DESCRICAO"];
    $status_cod_status = $_POST["COD_STATUS"];
    $tipo_cod_tipo = $_POST["COD_TIPO"];
    $usuario = $_POST["COD_USUARIO"];
    $foto = $_POST["FOTO"];
	
	$query = " INSERT INTO OCORRENCIA (LOCALIDADE,DATA_HORA,DESCRICAO,FOTO,STATUS_COD_STATUS,TIPO_COD_TIPO,USUARIO_COD_USUARIO) 
              VALUES ('$localidade','$data_hora','$descricao','$foto','$status_cod_status','$tipo_cod_tipo','$usuario');";

	$inserted = mysqli_query($connect, $query);


	if($inserted == 1){
		$json['success'] = "Ocorrência inserida com sucesso!";
	}
	else{
		$json['error'] = "Houve um erro ao salvar a ocorrencia!";

	}
	echo json_encode($json);
	mysqli_close($connect);
	
}
?>

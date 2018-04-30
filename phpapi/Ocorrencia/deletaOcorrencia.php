<?php

if($_SERVER["REQUEST_METHOD"]=="POST"){
	include '../connection.php';


    if(isset($_POST['COD_OCORRENCIA']) && ($_POST['COD_USUARIO']))
    {
        $cod_ocorrencia = $_POST['COD_OCORRENCIA'];
		$cod_usuario = $_POST['COD_USUARIO'];

        if(!empty($cod_ocorrencia) && !empty($cod_usuario))
        {
            deletaOcorrencia($cod_ocorrencia, $cod_usuario);
        }
        else
        {
            echo json_encode("Codigo da ocorrencia e de usuario nao pode ser nulo!");
        }
    }



}
function deletaOcorrencia($cod_ocorrencia, $cod_usuario)
{
	global $connect;
	
	$querySelect = " SELECT COD_OCORRENCIA, USUARIO_COD_USUARIO FROM OCORRENCIA	
					 WHERE USUARIO_COD_USUARIO = '$cod_usuario' AND COD_OCORRENCIA = '$cod_ocorrencia'";
	
	$select = mysqli_query($connect, $querySelect);
	$number_of_rows = mysqli_num_rows($select);
	
	
	$ocorrencia_array  = array();
	
	if($number_of_rows > 0) {
		$query = " DELETE FROM OCORRENCIA where COD_OCORRENCIA = '$cod_ocorrencia'; ";

		$deleted = mysqli_query($connect, $query);

		if($deleted == 1){
			$json['success'] = "Ocorrencia deletada com sucesso!";
		}
		else{
			$json['error'] = "Houve um erro ao deletar ocorrencia!";
		}
	}
    echo json_encode($json);
	
    mysqli_close($connect);
	
}
?>
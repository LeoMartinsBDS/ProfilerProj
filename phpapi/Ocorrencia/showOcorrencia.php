<?php

if($_SERVER["REQUEST_METHOD"]=="POST"){
    require '../connection.php';


    if(isset($_POST['COD_STATUS']))
    {
        $cod_status = $_POST['COD_STATUS'];
    }
	else
	{
		$cod_status = "";
	}
	
	if(isset($_POST['COD_TIPO']))
	{
		$cod_tipo = $_POST['COD_TIPO'];   
	}
	else
	{
		$cod_tipo = "";
	}
	showOcorrencia($cod_status,$cod_tipo);
	
}
function showOcorrencia($cod_status, $cod_tipo)
{
	global $connect;
	
	if($cod_status != "")
	{
		$strWHERE =  " AND S.COD_STATUS = '$cod_status'";
	}
	else{
		$strWHERE = "";
	}
	
	if($cod_tipo != "")
	{
		$strWHERE = $strWHERE . " AND T.COD_TIPO = '$cod_tipo'";
	}
	else
	{
		$strWHERE = "";
	}
	
	$query = " SELECT OCO.DESCRICAO AS DESCRICAO_OCORRENCIA, OCO.DATA_HORA, OCO.FOTO, OCO.LOCALIDADE, S.DESCRICAO AS DESCRICAO_STATUS, T.DESCRICAO AS DESCRICAO_TIPO
			   FROM OCORRENCIA OCO 
			   INNER JOIN STATUS S ON S.COD_STATUS = OCO.STATUS_COD_STATUS
			   INNER JOIN TIPO T ON T.COD_TIPO = OCO.TIPO_COD_TIPO 
			   WHERE 1=1 $strWHERE";
	
	$result = mysqli_query($connect, $query);
	$number_of_rows = mysqli_num_rows($result);
	
	$ocorrencia_array  = array();
	
	if($number_of_rows > 0) {
		while ($row = mysqli_fetch_assoc($result)) {
			$ocorrencia_array[] = $row;
		}
	}
	
	header('Content-Type: application/json');
	echo json_encode(array("ocorrencias"=>$ocorrencia_array));
	mysqli_close($connect);
	
}
?>
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

	$query = " SELECT OCO.COD_OCORRENCIA, OCO.DESCRICAO AS DESCRICAO_OCORRENCIA, OCO.DATA_HORA, OCO.FOTO, OCO.LOCALIDADE, S.DESCRICAO AS DESCRICAO_STATUS,
		   T.DESCRICAO AS DESCRICAO_TIPO, COUNT(CO.OCORRENCIA_COD_OCORRENCIA) AS QTD_CO,  COUNT(CL.COMENTARIO_OCORRENCIA_COD_CO) AS QTD_LIKE, 
		   COUNT(CD.COMENTARIO_OCORRENCIA_COD_CO) AS QTD_DISLIKE 
		   FROM OCORRENCIA OCO 
		   INNER JOIN STATUS S ON S.COD_STATUS = OCO.STATUS_COD_STATUS
		   INNER JOIN TIPO T ON T.COD_TIPO = OCO.TIPO_COD_TIPO 
		   LEFT JOIN comentario_ocorrencia CO ON CO.OCORRENCIA_COD_OCORRENCIA = OCO.COD_OCORRENCIA
		   LEFT JOIN comentario_like CL ON CL.COMENTARIO_OCORRENCIA_COD_CO = OCO.COD_OCORRENCIA
		   LEFT JOIN comentario_dislike CD ON CD.COMENTARIO_OCORRENCIA_COD_CO = OCO.COD_OCORRENCIA
		   WHERE 1=1 $strWHERE GROUP BY(OCO.COD_OCORRENCIA)";
	
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

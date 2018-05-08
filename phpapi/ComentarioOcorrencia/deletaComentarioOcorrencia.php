<?php

if($_SERVER["REQUEST_METHOD"]=="POST"){
	include '../connection.php';


    if(isset($_POST['COD_COMENTARIO']))
    {
        $codComentario = $_POST['COD_COMENTARIO'];

        if(!empty($codComentario))
        {
            deletaComentario($codComentario);
        }
        else
        {
            echo json_encode("Codigo do comentario e de usuario nao pode ser nulo!");
        }
    }



}
function deletaComentario($codComentario)
{
	global $connect;
		
	$deleteDislikes = " DELETE FROM COMENTARIO_DISLIKE WHERE COMENTARIO_COD_COMENTARIO = $codComentario;"; 
	
	$deldislike = mysqli_query($connect, $deleteDislikes);
	
	$deleteLikes = " DELETE FROM COMENTARIO_LIKE WHERE COMENTARIO_COD_COMENTARIO = $codComentario;";
	
	$dellike = mysqli_query($connect, $deleteLikes);

	$query = " DELETE FROM COMENTARIO_OCORRENCIA where COD_CO = $codComentario;";

	$deleted = mysqli_query($connect, $query);

	if($deleted == 1){
		$json['success'] = "Comentario deletado com sucesso!";
	}
	else{
		$json['error'] = "Houve um erro ao deletar comentario!";
	}

    echo json_encode($json);
	
    mysqli_close($connect);
	
}
?>
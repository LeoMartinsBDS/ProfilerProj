<?php

if($_SERVER["REQUEST_METHOD"]=="POST"){
    require '../connection.php';
    createComentario();
}


function createComentario()
{
    global $connect;

    $comentario = $_POST["COMENTARIO"];
    $codOcorrencia = $_POST["COD_OCORRENCIA"];
    $codUsuario = $_POST["COD_USUARIO"];


    $query = " INSERT INTO COMENTARIO_OCORRENCIA (COMENTARIO, OCORRENCIA_COD_OCORRENCIA, USUARIO_COD_USUARIO) 
              VALUES ('$comentario',$codOcorrencia,$codUsuario);";

    $inserted = mysqli_query($connect, $query);


    if($inserted == 1){
        $json['success'] = "Comentario inserida com sucesso!";
    }
    else{
        $json['error'] = "Houve um erro ao salvar o comentario!";

    }
    echo json_encode($json);
    mysqli_close($connect);

}
?>

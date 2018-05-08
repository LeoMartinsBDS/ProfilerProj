<?php

if($_SERVER["REQUEST_METHOD"]=="POST"){
    require '../connection.php';
    createInteracaoComentarioLike();
}


function createInteracaoComentarioLike()
{
    global $connect;

    $codComentario = $_POST["COD_COMENTARIO"];
    $codUsuario = $_POST["COD_USUARIO"];


    //verificar se existe na tabela de dislike alguma interacao
    $querySelectDislike = "SELECT *FROM COMENTARIO_LIKE 
                            WHERE USUARIO_COD_USUARIO = $codUsuario AND COMENTARIO_COD_COMENTARIO = $codComentario";
    $selectDislike = mysqli_query($connect, $querySelectDislike);
    $number_of_rows_Dislike = mysqli_num_rows($selectDislike);


    if($number_of_rows_Dislike > 0)
    {
        //COLOCO TUDO EM UM VETOR PARA PODER PEGAR O CÓDIGO DA INTERACAO
        while($registro = mysqli_fetch_array($selectDislike, MYSQLI_ASSOC)){

            $codComentarioDislike = $registro['COD_COMENTARIO_DISLIKE'];

            $query = " DELETE FROM COMENTARIO_DISLIKE WHERE COD_COMENTARIO_DISLIKE = $codComentarioDislike; ";


            $deleted = mysqli_query($connect, $query);

            if($deleted == 1){
                $json['success'] = "COMENTARIO DISLIKE deletada com sucesso!";
            }
            else{
                $json['error'] = "Houve um erro ao deletar COMENTARIO DISLIKE!";
            }

        }
    }


    $querySelect = " SELECT *FROM COMENTARIO_LIKE
                         WHERE USUARIO_COD_USUARIO = $codUsuario AND COMENTARIO_COD_COMENTARIO = $codComentario";

    $select = mysqli_query($connect, $querySelect);
    $number_of_rows = mysqli_num_rows($select);

    //caso o usuario já tenha interagido na ocorrência, devo deletar a mesma
    if($number_of_rows > 0) {

        while($registro = mysqli_fetch_array($select, MYSQLI_ASSOC)){

            $codComentarioLike = $registro['COD_COMENTARIO_LIKE'];

            $query = " DELETE FROM INTERACAO_OCORRENCIA_LIKE WHERE COD_INTERACAO = $codComentarioLike; ";

            $deleted = mysqli_query($connect, $query);

            if($deleted == 1){
                $json['success'] = "Comentario like deletada com sucesso!";
            }
            else{
                $json['error'] = "Houve um erro ao deletar comentario like!";
            }

        }
    }


    $query = " INSERT INTO COMENTARIO_LIKE (LIKE, COMENTARIO_COD_COMENTARIO, USUARIO_COD_USUARIO) 
               VALUES (1,$codComentario,$codUsuario);";

    //SE NÃO ACHAR NADA NA BASE, ENTAO POSSO REALIZAR O INSERT NA BASE DE DADOS
    if($number_of_rows == 0){
         $inserted = mysqli_query($connect, $query);


    if($inserted == 1){
        $json['success'] = "COMENTARIO LIKE inserida com sucesso!";
    }
    else{
        $json['error'] = "Houve um erro ao salvar a COMENTARIO LIKE!";

    }
    }

    echo json_encode($json);
    mysqli_close($connect);

}
?>

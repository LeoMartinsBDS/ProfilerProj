<?php

if($_SERVER["REQUEST_METHOD"]=="POST"){
    require '../connection.php';
    createInteracaoComentarioDislike();
}


function createInteracaoComentarioDislike()
{
    global $connect;

    $codComentario = $_POST["COD_COMENTARIO"];
    $codUsuario = $_POST["COD_USUARIO"];


    //verificar se existe na tabela de like alguma interacao
    $querySelectLike = "SELECT *FROM COMENTARIO_LIKE 
                            WHERE USUARIO_COD_USUARIO = $codUsuario AND COMENTARIO_COD_COMENTARIO = $codComentario";
    $selectLike = mysqli_query($connect, $querySelectLike);
    $number_of_rows_Like = mysqli_num_rows($selectLike);


    if($number_of_rows_Like > 0)
    {
        //COLOCO TUDO EM UM VETOR PARA PODER PEGAR O CÓDIGO DA INTERACAO
        while($registro = mysqli_fetch_array($selectLike, MYSQLI_ASSOC)){

            $codComentarioLike = $registro['COD_COMENTARIO_LIKE'];

            $query = " DELETE FROM COMENTARIO_LIKE WHERE COD_COMENTARIO_LIKE = $codComentarioLike; ";


            $deleted = mysqli_query($connect, $query);

            if($deleted == 1){
                $json['success'] = "Comentario LIKE deletada com sucesso!";
            }
            else{
                $json['error'] = "Houve um erro ao deletar comentario LIKE!";
            }

        }
    }


    $querySelect = " SELECT *FROM COMENTARIO_DISLIKE
                         WHERE USUARIO_COD_USUARIO = $codUsuario AND COD_COMENTARIO_DISLIKE = $codComentario";

    $select = mysqli_query($connect, $querySelect);
    $number_of_rows = mysqli_num_rows($select);

    //caso o usuario já tenha interagido na ocorrência, devo deletar a mesma
    if($number_of_rows > 0) {

        while($registro = mysqli_fetch_array($select, MYSQLI_ASSOC)){

            $codComentarioDislike = $registro['COD_COMENTARIO_DISLIKE'];

            $query = " DELETE FROM COMENTARIO_DISLIKE WHERE COD_INTERACAO = $codComentarioDislike; ";

            $deleted = mysqli_query($connect, $query);

            if($deleted == 1){
                $json['success'] = "Comentario dislike deletada com sucesso!";
            }
            else{
                $json['error'] = "Houve um erro ao deletar Comentario dislike!";
            }

        }
    }


    $query = " INSERT INTO COMENTARIO_LIKE (LIKE, COMENTARIO_COD_COMENTARIO, USUARIO_COD_USUARIO) 
               VALUES (1,$codComentario,$codUsuario);";

    //SE NÃO ACHAR NADA NA BASE, ENTAO POSSO REALIZAR O INSERT NA BASE DE DADOS
    if($number_of_rows == 0){
        $inserted = mysqli_query($connect, $query);


        if($inserted == 1){
            $json['success'] = "Comentario like inserida com sucesso!";
        }
        else{
            $json['error'] = "Houve um erro ao salvar a Comentario like!";

        }
    }

    echo json_encode($json);
    mysqli_close($connect);

}
?>

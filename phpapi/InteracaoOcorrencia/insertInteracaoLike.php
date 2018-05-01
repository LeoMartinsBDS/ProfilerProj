<?php

if($_SERVER["REQUEST_METHOD"]=="POST"){
    require '../connection.php';
    createInteracaoLike();
}


function createInteracaoLike()
{
    global $connect;

    $codOcorrencia = $_POST["COD_OCORRENCIA"];
    $codUsuario = $_POST["COD_USUARIO"];


    //verificar se existe na tabela de dislike alguma interacao
    $querySelectDislike = "SELECT *FROM INTERACAO_OCORRENCIA_DISLIKE 
                            WHERE USUARIO_COD_USUARIO = $codUsuario AND OCORRENCIA_COD_OCORRENCIA = $codOcorrencia";
    $selectDislike = mysqli_query($connect, $querySelectDislike);
    $number_of_rows_Dislike = mysqli_num_rows($selectDislike);


    if($number_of_rows_Dislike > 0)
    {
        //COLOCO TUDO EM UM VETOR PARA PODER PEGAR O CÓDIGO DA INTERACAO
        while($registro = mysqli_fetch_array($selectDislike, MYSQLI_ASSOC)){

            $codInteracao = $registro['COD_INTERACAO_DISLIKE'];

            $query = " DELETE FROM INTERACAO_OCORRENCIA_DISLIKE WHERE COD_INTERACAO_DISLIKE = $codInteracao; ";


            $deleted = mysqli_query($connect, $query);

            if($deleted == 1){
                $json['success'] = "Interacao DISLIKE deletada com sucesso!";
            }
            else{
                $json['error'] = "Houve um erro ao deletar interacao DISLIKE!";
            }

        }
    }


    $querySelect = " SELECT *FROM INTERACAO_OCORRENCIA_LIKE
                         WHERE USUARIO_COD_USUARIO = $codUsuario AND OCORRENCIA_COD_OCORRENCIA = $codOcorrencia";

    $select = mysqli_query($connect, $querySelect);
    $number_of_rows = mysqli_num_rows($select);

    //caso o usuario já tenha interagido na ocorrência, devo deletar a mesma
    if($number_of_rows > 0) {

        while($registro = mysqli_fetch_array($select, MYSQLI_ASSOC)){

            $codInteracao = $registro['COD_INTERACAO'];

            $query = " DELETE FROM INTERACAO_OCORRENCIA_LIKE WHERE COD_INTERACAO = $codInteracao; ";

            $deleted = mysqli_query($connect, $query);

            if($deleted == 1){
                $json['success'] = "Interacao deletada com sucesso!";
            }
            else{
                $json['error'] = "Houve um erro ao deletar interacao!";
            }

        }
    }


    $query = " INSERT INTO INTERACAO_OCORRENCIA_LIKE (LIKES, OCORRENCIA_COD_OCORRENCIA, USUARIO_COD_USUARIO) 
               VALUES (1,$codOcorrencia,$codUsuario);";

    //SE NÃO ACHAR NADA NA BASE, ENTAO POSSO REALIZAR O INSERT NA BASE DE DADOS
    if($number_of_rows == 0){
         $inserted = mysqli_query($connect, $query);


    if($inserted == 1){
        $json['success'] = "Interacao inserida com sucesso!";
    }
    else{
        $json['error'] = "Houve um erro ao salvar a interacao!";

    }
    }

    echo json_encode($json);
    mysqli_close($connect);

}
?>

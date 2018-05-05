<?php

if($_SERVER["REQUEST_METHOD"]=="POST"){
    require '../connection.php';
    showInteracaoLike();
}

function showInteracaoLike()
{
    global $connect;

    $_SESSION["CodOcorrencia_LIKE"] = "";

    $query = " Select * FROM INTERACAO_OCORRENCIA_LIKE ORDER  BY OCORRENCIA_COD_OCORRENCIA";

    $result = mysqli_query($connect, $query);
    $number_of_rows = mysqli_num_rows($result);

    $temp_array  = array();

    if($number_of_rows > 0) {


        while($registro = mysqli_fetch_array($result, MYSQLI_ASSOC)){

            $codOcorrencia = $registro['OCORRENCIA_COD_OCORRENCIA'];


            if($codOcorrencia <> $_SESSION["CodOcorrencia_LIKE"]){



                $select = " SELECT OCORRENCIA_COD_OCORRENCIA, COUNT(OCORRENCIA_COD_OCORRENCIA) AS QTD_LIKE FROM INTERACAO_OCORRENCIA_LIKE WHERE OCORRENCIA_COD_OCORRENCIA = $codOcorrencia; ";

                $resultCount = mysqli_query($connect, $select);
                $number_of_rows_count = mysqli_num_rows($resultCount);


                if($number_of_rows_count > 0) {

                    //alimento uma session para salvar o código e não realizar o select dela novamente
                    $_SESSION["CodOcorrencia_LIKE"] = $codOcorrencia;


                    while ($row = mysqli_fetch_assoc($resultCount)) {
                        $temp_array[] = $row;
                    }
                }
            }

        }


    }

    header('Content-Type: application/json');
    echo json_encode(array("likes"=>$temp_array));
    mysqli_close($connect);
}
?>
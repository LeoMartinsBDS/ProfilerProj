<?php

if($_SERVER["REQUEST_METHOD"]=="POST"){
    require '../connection.php';
    showComentarioDislike();
}

function showComentarioDislike()
{
    global $connect;

    $_SESSION["CodComentario_DISLIKE"] = "";

    $query = " Select * FROM COMENTARIO_DISLIKE ORDER  BY COMENTARIO_OCORRENCIA_COD_CO";

    $result = mysqli_query($connect, $query);
    $number_of_rows = mysqli_num_rows($result);

    $temp_array  = array();

    if($number_of_rows > 0) {


        while($registro = mysqli_fetch_array($result, MYSQLI_ASSOC)){

            $codComentario = $registro['COMENTARIO_OCORRENCIA_COD_CO'];


            if($codComentario <> $_SESSION["CodComentario_DISLIKE"]){



                $select = " SELECT COMENTARIO_OCORRENCIA_COD_CO, COUNT(COMENTARIO_OCORRENCIA_COD_CO) AS QTD_DISLIKE FROM COMENTARIO_DISLIKE WHERE COMENTARIO_COD_COMENTARIO = $codComentario; ";

                $resultCount = mysqli_query($connect, $select);
                $number_of_rows_count = mysqli_num_rows($resultCount);


                if($number_of_rows_count > 0) {

                    //alimento uma session para salvar o código e não realizar o select dela novamente
                    $_SESSION["CodComentario_DISLIKE"] = $codComentario;


                    while ($row = mysqli_fetch_assoc($resultCount)) {
                        $temp_array[] = $row;
                    }
                }
            }

        }


    }

    header('Content-Type: application/json');
    echo json_encode(array("ComentariosDislikes"=>$temp_array));
    mysqli_close($connect);
}
?>

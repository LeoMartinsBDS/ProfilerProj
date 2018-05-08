<?php

if($_SERVER["REQUEST_METHOD"]=="POST"){
    require '../connection.php';
    showComentarioLike();
}

function showComentarioLike()
{
    global $connect;

    $_SESSION["CodComentario_LIKE"] = "";

    $query = " Select * FROM COMENTARIO_LIKE ORDER  BY COMENTARIO_COD_COMENTARIO";

    $result = mysqli_query($connect, $query);
    $number_of_rows = mysqli_num_rows($result);

    $temp_array  = array();

    if($number_of_rows > 0) {


        while($registro = mysqli_fetch_array($result, MYSQLI_ASSOC)){

            $codComentarioLike = $registro['COMENTARIO_COD_COMENTARIO'];


            if($codComentarioLike <> $_SESSION["CodComentario_LIKE"]){



                $select = " SELECT COMENTARIO_COD_COMENTARIO, COUNT(COMENTARIO_COD_COMENTARIO) AS QTD_LIKE FROM COMENTARIO_LIKE WHERE COMENTARIO_COD_COMENTARIO = $codComentarioLike; ";

                $resultCount = mysqli_query($connect, $select);
                $number_of_rows_count = mysqli_num_rows($resultCount);


                if($number_of_rows_count > 0) {

                    //alimento uma session para salvar o código e não realizar o select dela novamente
                    $_SESSION["CodComentario_LIKE"] = $codComentarioLike;


                    while ($row = mysqli_fetch_assoc($resultCount)) {
                        $temp_array[] = $row;
                    }
                }
            }

        }


    }

    header('Content-Type: application/json');
    echo json_encode(array("ComentariosLikes"=>$temp_array));
    mysqli_close($connect);
}
?>
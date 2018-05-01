<?php

if($_SERVER["REQUEST_METHOD"]=="POST"){
    require '../connection.php';
    showComentario();
}

function showComentario()
{
    global $connect;

    $query = " Select * FROM COMENTARIO_OCORRENCIA";

    $result = mysqli_query($connect, $query);
    $number_of_rows = mysqli_num_rows($result);

    $temp_array  = array();

    if($number_of_rows > 0) {
        while ($row = mysqli_fetch_assoc($result)) {
            $temp_array[] = $row;
        }
    }

    header('Content-Type: application/json');
    echo json_encode(array("comentarios"=>$temp_array));
    mysqli_close($connect);
}
?>
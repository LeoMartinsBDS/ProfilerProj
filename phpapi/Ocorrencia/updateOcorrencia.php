<?php

if($_SERVER["REQUEST_METHOD"]=="POST"){
    require '../connection.php';

    if(isset($_POST['COD_OCORRENCIA']))
    {
        $cod_ocorrencia = $_POST['COD_OCORRENCIA'];

        if(!empty($cod_ocorrencia))
        {
            updateOcorrencia($cod_ocorrencia);
        }
        else
        {
            echo json_encode("Codigo de ocorrencia nao pode ser nulo!");
        }
    }

}

function updateOcorrencia($cod_ocorrencia)
{
    global $connect;

    if(isset($_POST['COD_STATUS']))
    {
        $cod_status = $_POST["COD_STATUS"];
    }


    $query = " UPDATE OCORRENCIA SET STATUS_COD_STATUS = $cod_status WHERE COD_OCORRENCIA = $cod_ocorrencia";


    $updated = mysqli_query($connect, $query);

    if($updated == 1){
        $json['success'] = "Ocorrencia atualizado com sucesso!";
    }
    else{
        $json['error'] = "Houve um erro ao atualizar ocorrencia!";
    }

    echo json_encode($json);
    mysqli_close($connect);

}
?>

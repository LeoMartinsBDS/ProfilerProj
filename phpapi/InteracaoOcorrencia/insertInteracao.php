<?php

if($_SERVER["REQUEST_METHOD"]=="POST"){
    require '../connection.php';
    createInteracao();
}


function createInteracao()
{
    global $connect;

    $obj_clicado = $_POST["OBJ"];
    $codOcorrencia = $_POST["COD_OCORRENCIA"];
    $codUsuario = $_POST["COD_USUARIO"];

    $RegistroJaExiste = "NAO";


    $querySelect = " SELECT *FROM INTERACAO_OCORRENCIA
                         WHERE USUARIO_COD_USUARIO = $codUsuario AND OCORRENCIA_COD_OCORRENCIA = $codOcorrencia";

    $select = mysqli_query($connect, $querySelect);
    $number_of_rows = mysqli_num_rows($select);

    //caso o usuario já tenha interagido na ocorrência, devo deletar a mesma
    if($number_of_rows > 0) {

        $RegistroJaExiste = "SIM";

        while($registro = mysqli_fetch_array($select, MYSQLI_ASSOC)){

            $codInteracao = $registro['COD_INTERACAO'];

            $query = " DELETE FROM INTERACAO_OCORRENCIA WHERE COD_INTERACAO = $codInteracao; ";

            $deleted = mysqli_query($connect, $query);

            if($deleted == 1){
                $json['success'] = "Interacao deletada com sucesso!";
            }
            else{
                $json['error'] = "Houve um erro ao deletar interacao!";
            }

        }
    }

    if($obj_clicado == "LIKE" && $RegistroJaExiste != "SIM" )
    {
        $query = " INSERT INTO INTERACAO_OCORRENCIA (LIKES, OCORRENCIA_COD_OCORRENCIA, USUARIO_COD_USUARIO) 
                  VALUES (1,$codOcorrencia,$codUsuario);";
    }
    elseif($obj_clicado == "DISLIKE" && $RegistroJaExiste != "SIM" )
    {
        $query = " INSERT INTO INTERACAO_OCORRENCIA (DISLIKES, OCORRENCIA_COD_OCORRENCIA, USUARIO_COD_USUARIO) 
                  VALUES (1,$codOcorrencia,$codUsuario);";
    }


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

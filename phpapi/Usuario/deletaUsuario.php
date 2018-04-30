<?php

if($_SERVER["REQUEST_METHOD"]=="POST"){
	include '../connection.php';


    if(isset($_POST['COD_USUARIO']))
    {
        $cod_user = $_POST['COD_USUARIO'];

        if(!empty($cod_user))
        {
            deletaUsuario($cod_user);
        }
        else
        {
            echo json_encode("Codigo de usuario nao pode ser nulo!");
        }
    }



}
function deletaUsuario($cod_user)
{
	global $connect;
	
	$query = " DELETE FROM USUARIO where COD_USUARIO = '$cod_user'; ";

    $deleted = mysqli_query($connect, $query);

    if($deleted == 1){
        $json['success'] = "Usuario deletado com sucesso!";
    }
    else{
        $json['error'] = "Houve um erro ao deletar usuario!";
    }
    echo json_encode($json);
    mysqli_close($connect);

}
?>
<?php

if($_SERVER["REQUEST_METHOD"]=="POST"){
	require '../connection.php';
    
	 if(isset($_POST['COD_USUARIO']))
	 {
        $cod_user = $_POST['COD_USUARIO'];

        if(!empty($cod_user))
        {
            updateUsuario($cod_user);
        }
        else
        {
            echo json_encode("Codigo de usuario nao pode ser nulo!");
        }
     } 
	
}

function updateUsuario($cod_user)
{
	global $connect;
	
	if(isset($_POST['IDADE']))
	{
		$idade = $_POST["IDADE"];
	}
	
	if(isset($_POST['EMAIL']))
	{
		$email = $_POST["EMAIL"];
	}
	
	if(isset($_POST['FOTO']))
	{
		$foto = $_POST["FOTO"];
	}
	
	if(isset($_POST['NOME']))
	{
		$nome = $_POST["NOME"];
	}
	
	if(isset($_POST['SENHA']))
	{
		$senha = $_POST["SENHA"];
	}
	
	
	$senha = md5($senha);
	
	$query = " UPDATE USUARIO SET IDADE= '$idade'
			   ,EMAIL = '$email'
			   ,FOTO = '$foto'
			   ,NOME = '$nome'
			   ,SENHA = '$senha'
			   WHERE COD_USUARIO = $cod_user
	";

    echo $query;

    $updated = mysqli_query($connect, $query);

    if($updated == 1){
        $json['success'] = "Usuario atualizado com sucesso!";
    }
    else{
        $json['error'] = "Houve um erro ao atualizar usuario!";

    }
    echo json_encode($json);
    mysqli_close($connect);
	
}
?>

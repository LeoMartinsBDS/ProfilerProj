<?php

if($_SERVER["REQUEST_METHOD"]=="POST"){
	include '../connection.php';


    if(isset($_POST['COD_USUARIO']))
    {
        $cod_user = $_POST['COD_USUARIO'];

        if(!empty($cod_user))
        {
            showUsuario($cod_user);
        }
        else
        {
            echo json_encode("Codigo de usuario nao pode ser nulo!");
        }
    }

}
function showUsuario($cod_user)
{
	global $connect;
	
	$query = " Select * FROM USUARIO where COD_USUARIO = '$cod_user'; ";
	
	$result = mysqli_query($connect, $query);
	$number_of_rows = mysqli_num_rows($result);
	
	$temp_array  = array();
	
	if($number_of_rows > 0) {
		while ($row = mysqli_fetch_assoc($result)) {
			$temp_array[] = $row;
		}
	}
	
	header('Content-Type: application/json');
	echo json_encode(array("usuarios"=>$temp_array));
	mysqli_close($connect);
	
}
?>
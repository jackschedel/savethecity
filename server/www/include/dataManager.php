<?php
		

	$conn = new mysqli("127.0.0.1", "cityuser", "password", 'savethecity');
	
	
	function error($message = "An internal or external error occurred") {
		exit(json_encode(array('error' => $message)));
	}
	
	
	if (isset($_POST['Reason'])) {
		switch ($_POST['Reason']) {
			default:
				error("Malformed Request: Invalid Reason");
				break;
		}
	} else if (isset($_GET['Reason'])) {
		switch ($_GET['Reason']) {
			case "waterWins":
				tempTest();
				break;
			default:
				error("Malformed Request: Invalid Reason");
				break;
		}
	} else {
		error("Malformed Request: No Reason Given");
	}
	
	
	function tempTest() {
		global $conn;
		
		if (!$conn || $conn->connect_error) error("Could not connect to database.");

		$statement = $conn->prepare("SELECT * FROM accounts WHERE water_wins = 1");
		
		if (!$statement) error();
		if (!$statement->execute()) error();
		$resultSet = $statement->get_result();



		exit(json_encode($resultSet));
		
		mysql_close($conn);

	}
	
	
	

?>
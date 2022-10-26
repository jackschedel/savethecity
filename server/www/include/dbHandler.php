<?php
	
	
	
	$dbServername = "//oracle.cise.ufl.edu/orcl";
	$dbUsername = "jschedel";
	$dbPassword = 																	"YvlckrJou5LvVgMR0wlzBlbY";
	$dbName = "orcl";
	
	
	$conn = oci_connect($dbUsername, $dbPassword, $dbServername);
	
	
	
	
	
	
	
	if (!$conn) {
		$e = oci_error();
		trigger_error(htmlentities($e['message'], ENT_QUOTES), E_USER_ERROR);
	}	

	$statement = oci_parse($conn, 'SELECT * FROM STOCK WHERE Ticker < \'ABC\'');
	oci_execute($statement);

	while (($row = oci_fetch_object($statement))) {
		var_dump($row);
	}





	oci_free_statement($statement);
	oci_close($conn);
	
	
	
	//Handles HTTP Requests for the file
	if (isset($_POST['Reason'])) {
		switch ($_POST['Reason']) {
			default:
				error("Malformed Request: Invalid Reason");
				break;
		}
	} else if (isset($_GET['Reason'])) {
		switch ($_GET['Reason']) {
			case "StockData":
				getStockData();         //Get the meta data of every story the user has access to
				break;
			default:
				error("Malformed Request: Invalid Reason");
				break;
		}
	} else {
		error("Malformed Request: No Reason Given");
	}

?>

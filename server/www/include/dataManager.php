<?php
	
	/*TODO: 
	DONE:
	- time frame (have both ends regaradless of mod)
	
	
	
	TODO:
	- multi-database support
	- tuplecount
	- more complex stats
	- group computations? (ideally frontend)
	- averaging rather than pulling? (def not worth, just use higher res)
	*/
	
	$dbServername = "//oracle.cise.ufl.edu/orcl";
	$dbUsername = "jschedel";
	$dbPassword ="YvlckrJou5LvVgMR0wlzBlbY";
	$dbName = "orcl";
	
	
	$conn = oci_connect($dbUsername, $dbPassword, $dbServername);
	
	
	

function error($message = "An internal or external error occurred") {
    exit(json_encode(array('error' => $message)));
}

function tupleCount() {
	global $conn;
	
    // Create Connection to Datbase
    if (!$conn) error("Could not connect to database.");
	
	$countResponse = 0;

	
	$userDB = "jschedel";
	
	
	
	$countStatement = oci_parse($conn, "SELECT COUNT(*) AS TOTAL FROM {$userDB}.stockReport");
	
	oci_execute($countStatement);
	
	$countResponse = $countResponse + oci_fetch_assoc($countStatement)['TOTAL'];
	
	oci_free_statement($countStatement);
	
	
	
	
	
	
}



function tempTest() {
	global $conn;
	
    // Create Connection to Datbase
    if (!$conn) error("Could not connect to database.");
	
	$statement = oci_parse($conn, 'SELECT Ticker FROM jschedel.STOCK WHERE Ticker < \'ABC\'');
	oci_execute($statement);

	$response = array();
	$category["ticker"] = "temp";

	while (($row = oci_fetch_object($statement))) {
		$response[] = $row;
	}
	
	exit(json_encode($response));
	
	oci_free_statement($statement);
	oci_close($conn);

}
	
function getStockData($ticker, $statistic, $resolution, $startDate, $endDate) {
	global $conn;
	
	if($resolution < 2) {
		error("Resolution must be 2 or more!");
	}
	
    // Create Connection to Datbase
    if (!$conn) error("Could not connect to database.");
	
	$statistic = strtolower($statistic);
	
	switch ($statistic) {
		case "adjustedclose":			
			break;
		case "open":			
			break;
		case "close":			
			break;
		case "low":			
			break;
		case "high":			
			break;
		case "volume":			
			break;
		default:
			error("Malformed Stock Data Request: Invalid Statistic");
			break;
	}
	
	$userDB = "jschedel";



	if($startDate == NULL) {
		
		$dateStatement = oci_parse($conn, "SELECT reportDate FROM {$userDB}.stockReport WHERE stockTicker = '{$ticker}' ORDER BY reportDate ASC FETCH FIRST 1 ROW ONLY");
		
		oci_execute($dateStatement);
		
		$startDate = oci_fetch_assoc($dateStatement)['REPORTDATE'];
				
		oci_free_statement($dateStatement);
		
	} else {
		
		$dateStatement = oci_parse($conn, "SELECT reportDate, ABS(reportDate - to_date('{$startDate}', 'YYYY-MM-DD')) AS dateDiff FROM {$userDB}.stockReport WHERE stockTicker = '{$ticker}' ORDER BY dateDiff ASC FETCH FIRST 1 ROWS ONLY");
		
		oci_execute($dateStatement);
		
		$startDate = oci_fetch_assoc($dateStatement)['REPORTDATE'];
				
		oci_free_statement($dateStatement);
		
	}
	
	if($endDate == NULL) {
		
		$dateStatement2 = oci_parse($conn, "SELECT reportDate FROM {$userDB}.stockReport WHERE stockTicker = '{$ticker}' ORDER BY reportDate DESC FETCH FIRST 1 ROW ONLY");
		
		oci_execute($dateStatement2);
			
		$endDate = oci_fetch_assoc($dateStatement2)['REPORTDATE'];
				
		oci_free_statement($dateStatement2);
		
	} else {
		
		$dateStatement = oci_parse($conn, "SELECT reportDate, ABS(reportDate - to_date('{$endDate}', 'YYYY-MM-DD')) AS dateDiff FROM {$userDB}.stockReport WHERE stockTicker = '{$ticker}' ORDER BY dateDiff ASC FETCH FIRST 1 ROWS ONLY");
		
		oci_execute($dateStatement);
		
		$endDate = oci_fetch_assoc($dateStatement)['REPORTDATE'];
				
		oci_free_statement($dateStatement);
		
	}
	
	$IDstatement1 = oci_parse($conn, "SELECT id FROM {$userDB}.stockReport WHERE stockTicker = '{$ticker}' AND reportDate = '{$startDate}'");
	
	oci_execute($IDstatement1);
	
	$startID = oci_fetch_assoc($IDstatement1)['ID'];
	
	oci_free_statement($IDstatement1);
	
	
	$IDstatement2 = oci_parse($conn, "SELECT id FROM {$userDB}.stockReport WHERE stockTicker = '{$ticker}' AND reportDate = '{$endDate}'");
	
	oci_execute($IDstatement2);
	
	$endID = oci_fetch_assoc($IDstatement2)['ID'];
	
	oci_free_statement($IDstatement2);
	
	//error("{$startID}   {$endID}");
		
	
	$countDiff = (int)$endID - (int)$startID;
	
	$resolution = $resolution - 1;
	
	$modResolution = $countDiff/$resolution;
	
	
	$statement = oci_parse($conn, "SELECT reportDate, {$statistic} FROM {$userDB}.stockReport WHERE stockTicker = '{$ticker}' AND (((reportDate >= '{$startDate}') AND (reportDate <= '{$endDate}') AND (   MOD(ID, CAST({$modResolution} AS INT)) - MOD({$startID}, CAST({$modResolution} AS INT)) = '0'   )) OR reportDate = '{$endDate}') ORDER BY reportDate ASC");
	
	//exit("SELECT reportDate, {$statistic} FROM {$userDB}.stockReport WHERE stockTicker = '{$ticker}' AND (((reportDate >= '{$startDate}') AND (reportDate <= '{$endDate}') AND (   MOD(ID, CAST({$modResolution} AS INT)) - MOD({$startID}, CAST({$modResolution} AS INT)) = '0'   )) OR reportDate = '{$endDate}') ORDER BY reportDate ASC");
	
	oci_execute($statement);
	

	$response = array();

	while (($row = oci_fetch_object($statement))) {
		$response[] = $row;
	}
		
	oci_free_statement($statement);
	oci_close($conn);
	
	exit(json_encode($response));

	
}
	
	//Handles HTTP Requests for the file
	if (isset($_POST['Reason'])) {
		switch ($_POST['Reason']) {
			default:
				error("Malformed Request: Invalid Reason");
				break;
		}
	} else if (isset($_GET['Reason'])) {
		switch ($_GET['Reason']) {
			case "tupleCount":
				tupleCount();
				break;
			case "tempTest":
				tempTest();
				break;
			case "stockData":			
				getStockData($_GET['Ticker'], $_GET['Statistic'], $_GET['Resolution'], $_GET['StartDate'], $_GET['EndDate']);         //Get the meta data of every story the user has access to
				break;
			default:
				error("Malformed Request: Invalid Reason");
				break;
		}
	} else {
		error("Malformed Request: No Reason Given");
	}

?>

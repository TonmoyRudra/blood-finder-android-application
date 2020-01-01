<?php 

// array for JSON response
$response = array();


// include db connect class
require_once ('/home/a3064074/public_html/db_connect.php');
 
// connecting to db
$db = new DB_CONNECT();


$result = mysql_query("SELECT * FROM doner_info");
///////////////////////////////////////////////////////////////////////////////////
if (mysql_num_rows($result) > 0) {
	$response["donerInfo"] = array();

	while ($row = mysql_fetch_array($result)) {
		$spot = array();

		$spot["name"] = $row["name"];
		$spot["age"] = $row["age"];
		$spot["height"] = $row["height"];
		$spot["weight"] = $row["weight"];
		$spot["gender"] = $row["gender"];
		$spot["bloodgroup"] = $row["bloodgroup"];
		$spot["mobile"] = $row["mobile"];
		$spot["area"] = $row["area"];
		

        array_push($response["donerInfo"], $spot);
	}
	// success
    $response["success"] = 1;
 
    // echoing JSON response
    echo json_encode($response);

} else {
    // no products found
    $response["success"] = 0;
    $response["message"] = "No spots found";
 
    // echo no users JSON
    echo json_encode($response);
}

?>

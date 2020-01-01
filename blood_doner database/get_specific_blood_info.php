<?php 

// array for JSON response
$response = array();

// include db connect class
require_once __DIR__ . '/db_connect.php';
 
// connecting to db
$db = new DB_CONNECT();

if (isset($_GET["area"]) && isset($_GET["bloodgroup"])) {
	$area = $_GET["area"];
    $bloodgroup = $_GET["bloodgroup"];

	$result = mysql_query("SELECT * FROM doner_info WHERE area = '$area' AND bloodgroup = '$bloodgroup'");
    
	if (!empty($result)) {
        if (mysql_num_rows($result) > 0) {
            $response["donerInfo"] = array();
            while ($row = mysql_fetch_array($result)) {
                $info = array();

                $info["name"] = $row["name"];
                $info["age"] = $row["age"];
                $info["height"] = $row["height"];
                $info["weight"] = $row["weight"];
                $info["gender"] = $row["gender"];
                $info["bloodgroup"] = $row["bloodgroup"];
                $info["mobile"] = $row["mobile"];
                $info["area"] = $row["area"];
               

                array_push($response["donerInfo"], $info);
            }

            // success
            $response["success"] = 1;
            echo json_encode($response);
         } else {
            $response["success"] = 0;
            $response["message"] = "No spot found";
 
            // echo no users JSON
            echo json_encode($response);
         }

	} else {
        // no spot found
        $response["success"] = 0;
        $response["message"] = "No spot found";
 
        // echo no users JSON
        echo json_encode($response);
    }

} else {
	// required field is missing
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";
 
    // echoing JSON response
    echo json_encode($response);
}

?>
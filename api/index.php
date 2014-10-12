<?php
  
$app = new \Phalcon\Mvc\Micro();

//Set up the database service
$app['db'] =  function(){
    return new \Phalcon\Db\Adapter\Pdo\Postgresql(array(
        "host" => "localhost",
		"port" => "5432",
        "username" => "cdp",
        "password" => "cdp",
        "dbname" => "cdp"
    ));
};

$app->notFound(function () use ($app) {
    $app->response->setStatusCode(404, "Not Found")->sendHeaders();
    echo 'This is crazy, but this page was not found!';
});

$app->get('/infrastructures/{schoolid}', function ($schoolid) use ($app) {
  	$result = $app['db']->query('SELECT * FROM cdp_infrastructure where school_id='.$schoolid);
	$infrastructures = $result->fetchAll();
	echo json_encode($infrastructures);
});

$app->get('/staffs/{schoolid}', function ($schoolid) use ($app) {
	$result = $app['db']->query('SELECT * FROM cdp_staff where school_id='.$schoolid);
	$staffs = $result->fetchAll();
	echo json_encode($staffs);
});

$app->get('/classes/{schoolid}', function ($schoolid) use ($app) {
	$result = $app['db']->query('SELECT DISTINCT st.student_class FROM cdp_school sch join cdp_student st on sch.school_id = st.school_id where st.school_id='.$schoolid);
	$classes = $result->fetchArray();
	echo json_encode($classes);
});

$app->get('/students/{schoolid}/{classname}', function ($schoolid, $classname) use ($app) {
	$result = $app['db']->query('SELECT * FROM cdp_student where school_id='.$schoolid.' AND student_class= \''.$classname.'\'');
	$students = $result->fetchArray();
	echo json_encode($students);
});

$app->get('/students/attendance/{schoolid}/{classname}', function ($schoolid, $classname) use ($app) {
	$result = $app['db']->query('SELECT * FROM cdp_student_turnout WHERE school_id='.$schoolid.' AND class=\''.$classname.'\'');
	$attendance = $result->fetchAll();
	echo json_encode($attendance);
});

$app->get('/staffs/attendance/{schoolid}', function ($schoolid) use ($app) {
	$result = $app['db']->query('SELECT * FROM cdp_staff_turnout WHERE school_id='.$schoolid);
	$attendance = $result->fetchAll();
	echo json_encode($attendance);
});

$app->get('/schools/list', function () use ($app) {
	$result = $app['db']->query('SELECT * FROM cdp_school');
	$schools = $result->fetchAll();
	echo json_encode($schools);
});

$app->get('/schools/geodata', function () use ($app) {
  	$result = $app['db']->query('SELECT geo_latitude, geo_longitude FROM cdp_school');
	$geo = $result->fetchAll();
	echo json_encode($geo);
});


$app->post('/student/attendance/add', function() use ($app) {
	$data = $app->request->getJsonRawBody();
	$phql = "INSERT INTO cdp_student_turnout (student_id, school_id, turnout_dt, student_name, contact_number, class, turnout_yn, abs_rsn) VALUES (?, ?, ?,?,?,?,?,?)";
	
	$result = $app['db']->query('SELECT * FROM cdp_student WHERE student_id='.$data->student_id);
	$student = json_decode(json_encode($result->fetch()));
	
	echo $data->turnout_dt;
	$status = $app['db']->execute($phql, array(
        $student->{'student_id'},
        $student->{'school_id'},
        $data->turnout_dt,
		$student->{'student_name'},
		$student->{'contact_number'},
		$student->{'student_class'},
		$data->ispresent,
		$data->absent_reason
    ));
	
	
	//Create a response
    $response = new Phalcon\Http\Response();
	
	if($status)
	{
		$response->setStatusCode(201, "Created");
        $response->setJsonContent(array('status' => 'OK', 'data' => $student));
	} else {
	       //Change the HTTP status
        $response->setStatusCode(409, "Conflict");

        //Send errors to the client
        $errors = array();
        foreach ($status->getMessages() as $message) {
            $errors[] = $message->getMessage();
        }

        $response->setJsonContent(array('status' => 'ERROR', 'messages' => $errors));
	}
});

$app->post('/staff/attendance/add', function() use ($app) {
	$data = $app->request->getJsonRawBody();
	$phql = "INSERT INTO cdp_staff_turnout (staff_id, school_id, turnout_dt, staff_name, contact_number, turnout_yn, abs_rsn) VALUES (?,?,?,?,?,?,?)";
	
	$result = $app['db']->query('SELECT * FROM cdp_staff WHERE staff_id='.$data->staff_id);
	$staff = json_decode(json_encode($result->fetch()));
	
	$status = $app['db']->execute($phql, array(
        $staff->{'staff_id'},
        $staff->{'school_id'},
        $data->turnout_dt,
		$staff->{'staff_name'},
		$staff->{'contact_number'},
		$data->ispresent,
		$data->absent_reason
    ));
	
	//Create a response
    $response = new Phalcon\Http\Response();
	
	if($status)
	{
		$response->setStatusCode(201, "Created");
        $response->setJsonContent(array('status' => 'OK', 'data' => $staff));
	} else {
	       //Change the HTTP status
        $response->setStatusCode(409, "Conflict");

        //Send errors to the client
        $errors = array();
        foreach ($status->getMessages() as $message) {
            $errors[] = $message->getMessage();
        }

        $response->setJsonContent(array('status' => 'ERROR', 'messages' => $errors));
	}
});



$app->handle();
 

?>
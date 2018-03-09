<?php
  $app->post('/send_notification_app', function($request,$response,$args) {
    $user_data = $request->getParsedBody();
        require_once 'gcm_helper.php';
        $gcm = new GCM();
        $registoer_id = [];
        // $message = array("message"=>"hello","title"=>"hello","image"=>"https://res.cloudinary.com/teepublic/image/private/s--lJF4iM-v--/t_Preview/b_rgb:c62b29,c_limit,f_jpg,h_630,q_90,w_630/v1459457505/production/designs/464682_1.jpg",'created_at'=>date('Y-m-d G:i:s'));
        $message = array("message"=>$user_data['msg'],"title"=>$user_data['title'],"Title"=>$user_data['Title'],"image"=>$user_data['image'],"video"=>$user_data['video'],"packege"=>$user_data['packege'],"video_id"=>$user_data['video_id'],'created_at'=>date('Y-m-d G:i:s'));
        $result = $gcm->send_notification($registoer_id, $message,$user_data['google_key']);
        $msg = array("success" => 1);
        return $response->withJson($msg);

   });
  $app->post('/get_apps_keys', function($request,$response,$args) {

    $conn=sqlConnection();
    if($result=$conn->query("SELECT * from appskyes")){
      while($row = $result->fetch_assoc()) {
          $appskeys[] =$row;
      }
      $row = $result->fetch_assoc();
      $msg = array("success" => 1,"data"=>$appskeys);
      return $response->withJson($msg);
    }
    $msg = array("success" => 0);
    return $response->withJson($msg);

  });
  $app->post('/add_apps_keys', function($request,$response,$args) {
    $user_data = $request->getParsedBody();
    $conn=sqlConnection();
    if($conn->query("INSERT INTO appskyes(app_name,api_key) VALUES('".$user_data['appname']."','".$user_data['appkey']."')")){
      $msg = array("success" => 1);
      return $response->withJson($msg);
    }
    $msg = array("success" => 0);
    return $response->withJson($msg);

  });
?>


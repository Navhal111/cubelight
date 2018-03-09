<?php
session_start();
error_reporting();
ini_set('display_errors', 'On');
set_error_handler("var_dump");
// extension=php_mbstring.dll
require_once 'vendor/autoload.php';
require_once 'config.php';
use Slim\LogFileWriter;
use Slim\Middleware\SessionCookie;
use Psr\Http\Message\ServerRequestInterface;
use Psr\Http\Message\ResponseInterface;
use Slim\Http\Request;
use Slim\Http\Response;
use Tracy\Debugger;
date_default_timezone_set("Asia/Kolkata");
header('Access-Control-Allow-Origin: *');
header('Access-Control-Allow-Methods: GET, POST');
header('Access-Control-Allow-Headers: Content-Type, Authorization, X-Requested-With');


$app = new \Slim\App(['settings' => [ 'determineRouteBeforeAppMiddleware' => true,
    'displayErrorDetails' => true,
    'addContentLengthHeader' => false,]]);

$app->add(new Silalahi\Slim\Logger(
  [
  'path' => 'log/'
  ]
));

$app->get('/', function($request, $response, $args){
  $tokenExpiration = date('Y-m-d');
  $session =date('Y-m-d');
  $enddate = "10/25/2017";
  $day = date("l", strtotime($tokenExpiration));
  // $json = file_get_contents('https://maps.googleapis.com/maps/api/geocode/json?address=1004,%20White%20Orchid,%20near%20Shell%20petrol%20pump,L.P%20Savani%20road%20,Adajan,Surat,%20Gujarat%20395009%20&key=AIzaSyDFtLiPr8vHgsFqeOuhuVw_XNeJE7WPT7Y');
  // $obj = json_decode($json,true);
  // $obj["results"][0]["geometry"]["location"]
  $diff=strtotime($enddate)-strtotime($session);
  $main= $diff/86400;
  $msg = array('success' => 1,'day'=>$day,'Time'=>$tokenExpiration,"days left"=>$main);
  return $response->withJson($msg);

});
$server_path ="http://192.168.0.19:8000/api/";
require_once 'gcm_send_notification.php';

$app->run();
?>

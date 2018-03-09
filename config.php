<?php

function getConnection(){
    $username = 'root';
    $password = 'noti123';
    $host = '192.168.0.7';
    $db = 'noti_master';
    $port = 3310;
    $connection = new PDO("mysql:dbname=$db;host=$host", $username, $password);
    return $connection;
}

function sqlConnection(){
  $username = 'root';
  $password = 'noti123';
  $host = '192.168.0.7:3310';
  $db = 'noti_master';
    $con = mysqli_connect($host,$username,$password,$db) or die('not conection');
    return $con;
}

?>


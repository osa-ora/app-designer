<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="shortcut icon" href="favicon.ico"/>
        <title>Cloud Native Application Builder - Configuration</title>
        <style>
.switch {
  position: relative;
  display: inline-block;
  width: 60px;
  height: 34px;
}

.switch input { 
  opacity: 0;
  width: 0;
  height: 0;
}

.slider {
  position: absolute;
  cursor: pointer;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: #ccc;
  -webkit-transition: .4s;
  transition: .4s;
}

.slider:before {
  position: absolute;
  content: "";
  height: 26px;
  width: 26px;
  left: 4px;
  bottom: 4px;
  background-color: white;
  -webkit-transition: .4s;
  transition: .4s;
}

input:checked + .slider {
  background-color: #2196F3;
}

input:focus + .slider {
  box-shadow: 0 0 1px #2196F3;
}

input:checked + .slider:before {
  -webkit-transform: translateX(26px);
  -ms-transform: translateX(26px);
  transform: translateX(26px);
}

/* Rounded sliders */
.slider.round {
  border-radius: 34px;
}

.slider.round:before {
  border-radius: 50%;
}
</style>
<style>
.button {
  background-color: #4CAF50; /* Green */
  border: none;
  color: white;
  padding: 15px 32px;
  text-align: center;
  text-decoration: none;
  display: inline-block;
  font-size: 16px;
  margin: 4px 2px;
  cursor: pointer;
  -webkit-transition-duration: 0.4s; /* Safari */
  transition-duration: 0.4s;
}
.button1 {
  box-shadow: 0 8px 16px 0 rgba(0,0,0,0.2), 0 6px 20px 0 rgba(0,0,0,0.19);
}

.button2:hover {
  box-shadow: 0 12px 16px 0 rgba(0,0,0,0.24),0 17px 50px 0 rgba(0,0,0,0.19);
}
.button span {
  cursor: pointer;
  display: inline-block;
  position: relative;
  transition: 0.5s;
}

.button span:after {
  content: '\00bb';
  position: absolute;
  opacity: 0;
  top: 0;
  right: -20px;
  transition: 0.5s;
}

.button:hover span {
  padding-right: 25px;
}

.button:hover span:after {
  opacity: 1;
  right: 0;
}
</style>
    </head>
    <body>
        <center>
            <h2>Build Your Cloud Native Application</h2>
        <table>
            <tr height="120pt;"><td>
            <label class="switch" style="vertical-align:button">
              <input type="checkbox">
              <span class="slider"></span><br><br><br>
              <img src="images/quarkus.png" style="vertical-align:button" alt="Quarkus" id="drag1" width="50" height="50">
            </label>
            </td><td>
            <label class="switch" style="vertical-align:button">
              <input type="checkbox">
              <span class="slider"></span><br><br><br>
              <img src="images/java_logo.png" style="vertical-align:button" alt="Java" id="drag2" width="50" height="50">
            </label>
            </td><td>
            <label class="switch" style="vertical-align:button">
              <input type="checkbox">
              <span class="slider"></span><br><br><br>
              <img src="images/spring-boot.png" style="vertical-align:button" alt="SpringBoot" id="drag3" width="50" height="50">
            </label>
            </td><td>
            <label class="switch" style="vertical-align:button">
              <input type="checkbox">
              <span class="slider"></span><br><br><br>
              <img src="images/nodejs.png" style="vertical-align:button" alt="NodeJS" id="drag4" width="50" height="50">
            </label>
            </td></tr>
            <tr height="120pt;"><td>
            <label class="switch" style="vertical-align:button">
              <input type="checkbox">
              <span class="slider"></span><br><br><br>
              <img src="images/mysql.png" style="vertical-align:button" alt="MySQL" id="drag5" width="50" height="50">
            </label>
            </td><td>
            <label class="switch" style="vertical-align:button">
              <input type="checkbox">
              <span class="slider"></span><br><br><br>
              <img src="images/mongodb.png" style="vertical-align:button" alt="MongoDB" id="drag6" width="50" height="50">
            </label>
            </td><td>
            <label class="switch" style="vertical-align:button">
              <input type="checkbox">
              <span class="slider"></span><br><br><br>
              <img src="images/postgresql.png" style="vertical-align:button" alt="PostgresSQL" id="drag7" width="50" height="50">
            </label>
            </td><td>
            </td></tr>
            <tr height="120pt;"><td>
            <label class="switch" style="vertical-align:button">
              <input type="checkbox">
              <span class="slider"></span><br><br><br>
              <img src="images/activemq.png" style="vertical-align:button" alt="ActiveMQ" id="drag6" width="50" height="50">
            </label>
            </td><td>
            <label class="switch" style="vertical-align:button">
              <input type="checkbox">
              <span class="slider"></span><br><br><br>
              <img src="images/kafka.png" style="vertical-align:button" alt="Kafka" id="drag7" width="50" height="50">
            </label>
            </td><td>
            <label class="switch" style="vertical-align:button">
              <input type="checkbox">
              <span class="slider"></span><br><br><br>
              <img src="images/redis.png" style="vertical-align:button" alt="Redis" id="drag8" width="50" height="50">
            </label>
            </td><td>
            </td></tr>
            <tr height="120pt;"><td>
            <label class="switch" style="vertical-align:button">
              <input type="checkbox" disabled>
              <span class="slider"></span><br><br><br>
              <img src="images/net_core.png" style="vertical-align:button" alt="DotNet" id="drag9" width="50" height="50">
            </label>
            </td><td>
            <label class="switch" style="vertical-align:button">
              <input type="checkbox" disabled>
              <span class="slider"></span><br><br><br>
              <img src="images/vertx.png" style="vertical-align:button" alt="VertX" id="drag9" width="50" height="50">
            </label>
            </td><td>
            <label class="switch" style="vertical-align:button">
              <input type="checkbox" disabled>
              <span class="slider"></span><br><br><br>
              <img src="images/knative.png" style="vertical-align:button" alt="KNative" id="drag9" width="50" height="50">
            </label>
            </td><td>
            <label class="switch" style="vertical-align:button">
              <input type="checkbox">
              <span class="slider"></span><br><br><br>
              <img src="images/3scale.png" style="vertical-align:button" alt="3Scale" id="drag9" width="50" height="50">
            </label>
            </td></tr>
            <tr height="120pt;"><td colspan="4" align="center"><br><br>
            <button class="button button2" style="vertical-align:middle"><span>Next </span></button>
            </td></tr>
            </table>
            </center>
            
    <%@include file="footer.jsp" %>
    </body>
</html>

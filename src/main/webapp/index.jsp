<%@page import="java.util.HashMap"%>
<%@page import="java.util.Set"%>
<%@page import="osa.ora.appbuilder.config.GenConfig"%>
<%@page import="osa.ora.appbuilder.config.IGenerator"%>
<%@page import="java.util.Map"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <link rel="shortcut icon" href="favicon.ico"/>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="css/domarrow.css">
        <link rel="stylesheet" href="css/builder.css">
        <script type="text/javascript" src="js/domarrow.js"></script>
        <script type="text/javascript" src="js/builder.js"></script>
        <title>Cloud Native Application Builder</title>
    </head>
    <body>
        <center>
            <h2>Build Your Cloud Native Application <div id="fileName">[No-Name]</div></h2>
            <div id="snackbar">Hello</div>
        </center>
        <table width="800pt;">
        <tr><td style="vertical-align:top;" height="10%">
        <div class="box" width="100%" id="drag1_div" ondrop="drop_back(event)" ondragover="allowDrop(event)" style="vertical-align:top;">
            <h2>Components</h2>
                <table>
                    <tr>
                    <% Map<String, IGenerator> genList=GenConfig.getInstance().getGeneratorList(); 
                       Set<String> keys=genList.keySet();
                       int count=0;
                       int i=0;
                       for(String key:keys){
                           IGenerator gen=genList.get(key);
                           count++;
                           i++;
                           if(count>2) {
                               count=1;
                               out.println("</tr><tr>");
                           }
                           %>
                            <td style="border:1px solid black;">
                                <img src="images/<%=gen.getIcon()%>" alt="<%=gen.getName()%>" title="<%=gen.getName()%>" action="<%=gen.getDefaultProducerAction()%>" draggable="true" ondragstart="drag(event)" id="drag<%=i%>" 
                                <%
                                    HashMap<String, String> params=(HashMap)gen.getParamList();
                                    Set<String> paramkeys=params.keySet();
                                    int n=0;
                                    for(String paramKey:paramkeys){
                                        n++;
                                        out.print("param"+n+"=\""+paramKey+"\"");
                                        out.print("param"+n+"_value=\""+params.get(paramKey)+"\"");
                                    }
                                %>     
                                width="50" height="50">
                            </td>
                       <%}
                    %>
                </tr>
                <tr>
                    <td colspan="2"><hr></td>
                </tr>
                <tr><td style="border:1px solid black;">
                <img src="images/spring-boot.png" alt="SpringBoot" action="REST" title="SpringBoot" draggable="true" ondragstart="drag(event)" id="drag13" 
                     param1="External" param2="Replica Count"
                     param1_value="FALSE" param2_value="2"
                     width="50" height="50">
                </td><td style="border:1px solid black;">
                <img src="images/nodejs.png" alt="NodeJS" title="NodeJS" action="REST" draggable="true" ondragstart="drag(event)" id="drag14" 
                     param1="External" param2="Replica Count"
                     param1_value="FALSE" param2_value="2"
                     width="50" height="50">
                </td ></tr>
                <tr><td style="border:1px solid black;">
                <img src="images/activemq.png" alt="ActiveMQ" action="Pub/Sub" title="ActiveMQ" draggable="true" ondragstart="drag(event)" id="drag16" width="50" height="50">
                </td><td style="border:1px solid black;">
                <img src="images/knative.png" alt="KNative" action="REST" title="KNative" draggable="true" ondragstart="drag(event)" id="drag21" width="50" height="50">
                </td></tr>
                <tr><td style="border:1px solid black;">
                <img src="images/kafka.png" alt="Kafka" action="Pub/Sub" title="Kafka" draggable="true" ondragstart="drag(event)" id="drag17" width="50" height="50">
                </td><td style="border:1px solid black;">
                <img src="images/redis.png" alt="Redis" action="Cache" title="Redis" draggable="true" ondragstart="drag(event)" id="drag18" width="50" height="50">
                </td></tr>
                <tr><td style="border:1px solid black;">
                <img src="images/net_core.png" alt=".Net" action="REST" title=".NetCore" draggable="true" ondragstart="drag(event)" id="drag19" width="50" height="50">
                </td><td style="border:1px solid black;">
                <img src="images/vertx.png" alt="VertX" action="REST" title="VertX" draggable="true" ondragstart="drag(event)" id="drag20" width="50" height="50">
                </td></tr>
                <tr><td style="border:1px solid black;">
                <img src="images/3scale.png" alt="3Scale" action="REST" title="3Scale" draggable="true" ondragstart="drag(event)" id="drag22" width="50" height="50">
                </td></tr>
                </table>
        </div>
        </td>
        <td style="vertical-align:top;">
        <div id="pannel" ondrop="drop(event)" ondragover="allowDrop(event)" class="pannel1"></div>
        </td>
        <td style="vertical-align:top;display:flex;" height="100%" width="200pt;">
        <table width="200pt;"><tr><td>
        <div class="box" height="40%" width="200ptx;">
            <h2>Component Properties</h2>
            <p id="prop" align="center" height="40%"><br><br><br>
                <label>No Component Selected</label>
                </p>
        </div>
        </td><tr><td>
        <div class="box" height="60%" width="200pt;">
            <h2>Controls</h2>
                <p align="center">
                <label class="switch"> 
                  <input type="checkbox" onclick="connect(this.checked);">
                  <span class="slider round"></span>
                </label><label><br> Connect</label><br>
                <label class="switch"> 
                  <input type="checkbox" onclick="grid(this.checked);" checked>
                  <span class="slider round"></span>
                </label><label><br> Gridlines</label>
                </p>
        </div>
        </td></tr>
        <tr><td style="vertical-align:top;display:flex;">
        <div class="box" height="20%" width="200pt;" style="vertical-align:top;align:center;">
            <h2>Configurations</h2>
            <form action="SaveServlet" method="post">
                  <label for="package"> Group Id:</label>
                  <input type="text" id="groupId" name="groupId" placeholder="Enter Group Id" value="osa.ora">
                  <label for="package"> Artifact Id:</label>
                  <input type="text" id="artifactId" name="artifactId" disabled="true" value="{service_name}">
                  <label for="package"> Version:</label>
                  <input type="text" id="version" name="version" placeholder="Enter Version" value="1.0.0-SNAPSHOT">
                  <label for="package"> Build:</label>
                  <input type="text" id="build" name="build" disabled="true" value="MAVEN">
                  <input type="text" hidden="true" id="type" name="type" value="1">
                  <textarea id="data" hidden="true" rows=1 cols=100 name="data"></textarea>
            </form>
            <script>
            <% if(session.getAttribute("DATA")!=null){
                    String name="No Name";
                    if(session.getAttribute("NAME")!=null){
                        name=(String)session.getAttribute("NAME");
                    }
                    out.println("document.getElementById('fileName').innerHTML='["+name+"]';");
               // out.println("document.getElementById('data').value="+session.getAttribute("DATA")+";");
                    out.println("loadJSON('"+session.getAttribute("DATA")+"');");
                    session.removeAttribute("DATA");
                }
            %>
            </script>
        </div>
        </td></tr>
        </table>
        </td>
        </tr>
        <tr><td></td><td colspan="2">
            <button class="button button2" onclick="reset();">Reset Board</button>
            <button class="button button2" onclick="viewContent();">View Content</button>
            <button class="button button2" onclick="save();">Save Content</button>
            <button class="button button2" style="vertical-align:middle" onclick="next()"><span>Generate </span></button>            
            <form action="LoadServlet" method="post" enctype="multipart/form-data">
                <div><input id="file" name="file" type="file"> 
                    <input type="text" hidden="true" id="type2" name="type2" value="1">
                <button class="button button2" type="submit">Load Saved</button>
                </div>
            </form>
        <div><textarea id="save" rows=10 cols=100 name="text"></textarea></div>
        </td></tr>
        </table>
            
    <%@include file="footer.jsp" %>
    </body>
</html>

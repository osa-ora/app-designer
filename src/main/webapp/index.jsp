<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="css/domarrow.css">
        <link rel="stylesheet" href="css/builder.css">
        <script type="text/javascript" src="js/domarrow.js"></script>
        <title>Cloud Native Application Builder</title>
    <script>
    var startDraw=false;
    var start='';
    var connectFlag=true;
    var counter=10;
    var icon_width=50;
    var icon_height=50;
    var canvas_width=1000;
    var canvas_height=500;

    function startLine(source){
	//display the selected item details
	document.getElementById("prop").innerHTML='<img src="'+source.src+'" width="50" height="50"><br>'+
	'<label>'+document.getElementById(source.id+'_l').innerHTML+'<br>['+source.alt+']</label>';
	//check if connection is switched on (between elements)
	if(!connectFlag) return;
	//check wether to draw or not?
	source=source.id;
	if(!startDraw){
            startDraw=true;
            start=source;
            //store the connection start point and wait for the target
	}else{
            if(start==source){
                startDraw=false;
                //source and target the same, cancel connection
            }else{
                startDraw=false;
                drawLine(start, source);
            }
	}
    }
    function drawLine(obj1, obj2){
	//check if connection already in place
	if(document.getElementById(obj1+'_'+obj2)) {
            alert("A connection already in place!");
            return;
	}
	//capture the connection caption
	var caption = prompt("Please enter name", "Uses");
	if(caption==null) return;
	//adjust connection location ..
	var ylocation=' fromY="0" toY="1" ';
	var xlocation=' fromX="0.5" toX="0.5" ';
	if(document.getElementById(obj1).style.top<document.getElementById(obj2).style.top){
            ylocation=' fromY="1" toY="0" ';
	}	
	//add connection element
	document.getElementById("pannel").innerHTML+=createConnection(obj1+'_'+obj2,'#'+obj1,'#'+obj2,"red",caption,xlocation,ylocation);
}
    //function to create connection
    function createConnection(id,obj1,obj2,color,caption,ylocation,xlocation){
        var connection= '<connection id="'+id+'" from="'+obj1+'" to="'+obj2+'" color="darkblue" text="'+caption+'" onclick="removeConnect('+id+',\''+caption+'\');"'+ylocation+xlocation+' tail></connection>';
        return connection;
    }
    //function to edit caption/remove connection
    function removeConnect(obj1,caption){
	//first edit the caption
	var caption = prompt("Please enter name", caption);
	if(caption==null) return;
	obj1.setAttribute('text',caption);
	obj1.setAttribute('onclick','removeConnect('+obj1.id+',\''+caption+'\');');
	//check if user need to delete it
	if(confirm("Delete this Connection?")){
		obj1.remove();
	}
    }
    function allowDrop(ev) {
        ev.preventDefault();
    }

    function drag(ev) {
        ev.dataTransfer.setData("text", ev.target.id);
    }
    function getTextWidth(text, font) {
        // re-use canvas object for better performance
        var canvas = getTextWidth.canvas || (getTextWidth.canvas = document.createElement("canvas"));
        var context = canvas.getContext("2d");
        context.font = font;
        var metrics = context.measureText(text);
        return metrics.width;
    }
    function drop(ev) {
        ev.preventDefault();
        var data = ev.dataTransfer.getData("text");
        var x=ev.clientX;
        var y=ev.clientY;
        if(x+icon_width>(canvas_width+document.getElementById("pannel").getBoundingClientRect().left) || y+icon_height>(canvas_height+document.getElementById("pannel").getBoundingClientRect().top)) {
            alert("Invalid component location!");
            return;
        }
        var my_element = document.getElementById(data);
        if(my_element==null) return;
        if(ev.target.id=='pannel'){
        if(my_element.id.indexOf("_")==-1) {
            var caption = prompt("Please enter name", "Service");
            counter++;
            yy=y+icon_height+2;
            xx=x-((getTextWidth(caption)-icon_width)/2);
            key='drag_'+counter+'_l';
            document.getElementById("pannel").innerHTML=document.getElementById("pannel").innerHTML+
                    createComponent(my_element.src,my_element.alt,'drag_'+counter,x+'px',y+'px',key,xx,yy,caption);
            counter++;
        }else{
            var caption=document.getElementById(my_element.id+'_l').textContent;
            document.getElementById(data).remove();
            document.getElementById(data+'_l').remove();		
            yy=y+icon_height+2;
            xx=x-((getTextWidth(caption)-icon_width)/2);
            key=my_element.id+'_l';
            document.getElementById("pannel").innerHTML=createComponent(my_element.src,my_element.alt,my_element.id,x+'px',y+'px',key,xx,yy,caption)+
                    document.getElementById("pannel").innerHTML; 
	}
    }
    }
    function createComponent(imgSrc,alt,id,x,y,key,xx,yy,caption){
	var content='<img src="'+imgSrc+'" alt="'+alt+'" draggable="true" ondragstart="drag(event)" id="'+id+'" onclick="startLine('+id+');" width="'+icon_width+'" height="'+icon_height+'" style="position:absolute; left:'+
		x+'; top:'+y+';" class="component">'+
		'<label id="'+key+'" style="position:absolute; left:'+xx+'px; top:'+yy+'px; font-size:8pt;font-family:arial;font-weight:bold;" onclick="updateCaption('+key+','+parseInt(x)+')">'+caption+'</label>';
	return content;
    }
    function updateCaption(key,x){
        //alert(key);
        var caption = prompt("Please enter name", key.textContent);
        if(caption==null) return;
        key.textContent=caption;
        //yy=y+icon_height+2;
        xx=x-((getTextWidth(caption)-icon_width)/2);
        key.style.left=xx+'px';
        //key.setAttribute("style","position:absolute; left:'+xx+'px; top:'+yy+'px; font-size:8pt;font-family:arial")
    }
    function drop_back(ev) {
        ev.preventDefault();
        var data = ev.dataTransfer.getData("text");
        var my_element = document.getElementById(data);
        if(my_element.id.indexOf("_")!=-1) {
            if(confirm("Delete Component?")){
                //component must not connected using connection
                if(validateConnections(data)==true) {alert('Delete its connection(s) first!');return;}
                document.getElementById(data).remove();
                document.getElementById(data+'_l').remove();	
            }	
      }
    }
    //validate if component already connected to a connection
    function validateConnections(data){
	var allConnections = document.getElementsByTagName("connection");
	for (var i=0; i < allConnections.length; i++) {
            if(allConnections[i].id.indexOf(data)!=-1) return true;
	}
	return false;
    }
    //switch on/off grid in the pannel
    function grid(yes){
        if(yes) document.getElementById("pannel").className ="pannel1";
        else  document.getElementById("pannel").className ="pannel2";
    }
    function save(){
	document.getElementById("save").value=generateJSON();
    }
    function generateHTML(){
	var content='';
	var allImages = document.getElementsByTagName("img");
	for (var i=0; i < allImages.length; i++) {
            if(allImages[i].id.indexOf("_")!=-1) {
                content+=allImages[i].outerHTML;
                content+=document.getElementById(allImages[i].id+"_l").outerHTML;
            }
	}
	var allConnections = document.getElementsByTagName("connection");
	for (var i=0; i < allConnections.length; i++) {
            content+=allConnections[i].outerHTML;
	}
	return content;
    }
    function generateJSON(){
	var content='{\n"groupId":"'+document.getElementById("groupId").value+'",\n';
	content+='"version":"'+document.getElementById("version").value+'",\n';
	var allImages = document.getElementsByTagName("img");
	content+='"images":[\n';
	var found=false;
	for (var i=0; i < allImages.length; i++) {
            if(allImages[i].id.indexOf("_")!=-1) {
                if(found) content+=',\n';
                found=true;
                content+='{\n';
                    content+='"type":"'+allImages[i].alt+'",\n';
                    content+='"id":"'+allImages[i].id+'",\n';
                    content+='"src":"'+allImages[i].src+'",\n';
                    content+='"x":"'+allImages[i].style.left+'",\n';
                    content+='"y":"'+allImages[i].style.top+'",\n';
                    content+='"caption":"'+document.getElementById(allImages[i].id+"_l").innerHTML+'"\n';
                    content+='}\n';
            }
	}
	content+='],\n';
	var allConnections = document.getElementsByTagName("connection");
	content+='"connections":[\n';
	var found=false;
	for (var i=0; i < allConnections.length; i++) {
            if(found) content+=',\n';
            found=true;
            content+='{\n';
            content+='"from":"'+allConnections[i].getAttribute('from')+'",\n';
            content+='"to":"'+allConnections[i].getAttribute('to')+'",\n';
            content+='"text":"'+allConnections[i].getAttribute('text')+'",\n';
            content+='"id":"'+allConnections[i].id+'",\n';
            content+='"fromX":"'+allConnections[i].getAttribute('fromX')+'",\n';
            content+='"toX":"'+allConnections[i].getAttribute('toX')+'",\n';		
            content+='"fromY":"'+allConnections[i].getAttribute('fromY')+'",\n';
            content+='"toY":"'+allConnections[i].getAttribute('toY')+'"\n';		
            content+='}\n';
	}
	content+=']\n';
	content+='}\n';
	return content;
    }
    function loadContent(){
	loadJSON(document.getElementById("save").value);
    }
    function loadJSON(mydata){
	try {
            var mydata=JSON.parse(mydata);
        } catch (e) {
            alert("Invalid Input, reason: "+e);
            return;
	}
	document.getElementById("groupId").value=mydata.groupId;
	document.getElementById("version").value=mydata.version;
	var content='';
	var key="";
	for (var i=0; i < mydata.images.length; i++) {
            yy=parseInt(mydata.images[i].y)+icon_height+2;
            xx=parseInt(mydata.images[i].x)-((getTextWidth(mydata.images[i].caption)-icon_width)/2);
            key=mydata.images[i].id+'_l';
            content+=createComponent(mydata.images[i].src,mydata.images[i].type,mydata.images[i].id,mydata.images[i].x,mydata.images[i].y,key,xx,yy,mydata.images[i].caption);
	}
	for (var i=0; i < mydata.connections.length; i++) {
            content+=createConnection(mydata.connections[i].id,mydata.connections[i].from,mydata.connections[i].to,"red",mydata.connections[i].text,' fromX='+mydata.connections[i].fromX+' toX='+mydata.connections[i].toX,' fromY='+mydata.connections[i].fromY+' toY='+mydata.connections[i].toY);
	}
	document.getElementById("pannel").innerHTML=content;
	create();
	alert("Successfully load "+mydata.images.length+" component(s) and "+mydata.connections.length+" connection(s)");
    }
    function load(){
        document.getElementById("pannel").innerHTML=document.getElementById("save").value;
        create();
    }
    function connect(yes){
        if(yes) connectFlag=true;
        else connectFlag=false;
        startDraw=false;
    }
    function save(){
        document.getElementById("type").value="1";
        document.getElementById("data").value=generateJSON();
        document.forms[0].submit();
    }
    function next(){
        document.getElementById("type").value="2";
        document.getElementById("data").value=generateJSON();
        document.forms[0].submit();
    }
    function viewContent(){
        document.getElementById("save").value=generateHTML();
    }
    </script>
    </head>
    <body>
        <center>
            <h2>Build Your Cloud Native Application <div id="fileName">[No-Name]</div></h2>
        </center>
        <table width="800pt;">
        <tr><td style="vertical-align:top;" height="10%">
        <div class="box" width="100%" id="drag1_div" ondrop="drop_back(event)" ondragover="allowDrop(event)" style="vertical-align:top;">
            <h2>Components</h2>
                <table><tr><td style="border:1px solid black;">
                <img src="images/quarkus.png" alt="Quarkus" title="Quarkus" draggable="true" ondragstart="drag(event)" id="drag1" width="50" height="50">
                </td><td style="border:1px solid black;">
                <img src="images/java_logo.png" alt="Java" title="Java" draggable="true" ondragstart="drag(event)" id="drag2" width="50" height="50">
                </td></tr>
                <tr><td style="border:1px solid black;">
                <img src="images/spring-boot.png" alt="SpringBoot" title="SpringBoot" draggable="true" ondragstart="drag(event)" id="drag3" width="50" height="50">
                </td><td style="border:1px solid black;">
                <img src="images/nodejs.png" alt="NodeJS" title="NodeJS" draggable="true" ondragstart="drag(event)" id="drag4" width="50" height="50">
                </td ></tr>
                <tr><td style="border:1px solid black;">
                <img src="images/mysql.png" alt="MySQL" title="MySQL" draggable="true" ondragstart="drag(event)" id="drag5" width="50" height="50">
                </td><td style="border:1px solid black;">
                <img src="images/postgresql.png" alt="PostgreSQL" title="PostgreSQL" draggable="true" ondragstart="drag(event)" id="drag6" width="50" height="50">
                </td></tr>
                <tr><td style="border:1px solid black;">
                <img src="images/mongodb.png" alt="MongoDB" title="MongoDB" draggable="true" ondragstart="drag(event)" id="drag7" width="50" height="50">
                </td><td style="border:1px solid black;">
                <img src="images/activemq.png" alt="ActiveMQ" title="ActiveMQ" draggable="true" ondragstart="drag(event)" id="drag8" width="50" height="50">
                </td></tr>
                <tr><td style="border:1px solid black;">
                <img src="images/kafka.png" alt="Kafka" title="Kafka" draggable="true" ondragstart="drag(event)" id="drag9" width="50" height="50">
                </td><td style="border:1px solid black;">
                <img src="images/redis.png" alt="Redis" title="Redis" draggable="true" ondragstart="drag(event)" id="drag10" width="50" height="50">
                </td></tr>
                <tr><td style="border:1px solid black;">
                <img src="images/net_core.png" alt=".Net" title=".NetCore"draggable="true" ondragstart="drag(event)" id="drag11" width="50" height="50">
                </td><td style="border:1px solid black;">
                <img src="images/vertx.png" alt="VertX" title="VertX" draggable="true" ondragstart="drag(event)" id="drag12" width="50" height="50">
                </td></tr>
                <tr><td style="border:1px solid black;">
                <img src="images/knative.png" alt="KNative" title="KNative" draggable="true" ondragstart="drag(event)" id="drag13" width="50" height="50">
                </td><td style="border:1px solid black;">
                <img src="images/3scale.png" alt="3Scale" title="3Scale" draggable="true" ondragstart="drag(event)" id="drag14" width="50" height="50">
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
                  <input type="checkbox" onclick="connect(this.checked);" checked>
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
                  <input type="text" hidden="true" id="type" name="type" value="1">
                  <textarea id="data" hidden="true" rows=1 cols=100 name="data"></textarea>
            </form>
            <script>
            <% if(session.getAttribute("DATA")!=null){
                    out.println("document.getElementById('fileName').innerHTML='["+session.getAttribute("NAME")+"]';");
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
            <button class="button button2" onclick="viewContent();">View Content</button>
            <button class="button button2" onclick="save();">Save Content</button>
            <button class="button button2" style="vertical-align:middle" onclick="next()"><span>Generate </span></button>            
            <form action="LoadServlet" method="post" enctype="multipart/form-data">
                <div><input id="file" name="file" type="file"> 
                <button class="button button2" type="submit">Load Saved</button>
                </div>
            </form>
        <div><textarea id="save" rows=10 cols=100 name="text"></textarea></div>
        </td></tr>
        </table>
            
    <%@include file="footer.jsp" %>
    </body>
</html>

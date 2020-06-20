 var startDraw=false;
var start='';
var connectFlag=false;
var counter=10;
var icon_width=50;
var icon_height=50;
var canvas_width=1000;
var canvas_height=600;
var currentFormatVersion=1.0;
var max_params=10;
var withStorage=false;
//function to start/end the connection between 2 components
function startLine(source){
    //display the selected item details
    var object_props='<img src="'+source.src+'" width="50" height="50"><br>'+
    '<label>'+document.getElementById(source.id+'_l').innerHTML+'<br></label>'+
    ' <div>Service Name</div><input id="srv_name" value="'+document.getElementById(source.id+'_l').innerHTML+'">';
    object_props+=generateDynamicParametersFields(source,false);
    object_props+='<img src="images/save.png" width="50" height="50" onclick="updateParams('+source.id+',false,false);">';
    object_props+=' <img src="images/trash.png" width="50" height="50" onclick="deleteComponentObj('+source.id+');">';
    document.getElementById("prop").innerHTML=object_props;
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
//storage details
function showStorageDetails(source){
    //display the selected item details
    var object_props='<img src="'+source.src+'" width="50" height="50"><br>'+
    '<label>'+document.getElementById(source.id+'_l').innerHTML+' Storage Volume<br></label>';
    object_props+=generateDynamicParametersFields(source,true);
    object_props+='<img src="images/save.png" width="50" height="50" onclick="updateParams('+source.id+',true,false);">';
    object_props+=' <img src="images/trash.png" width="50" height="50" onclick="updateParams('+source.id+',true,true);">';
    document.getElementById("prop").innerHTML=object_props;
}
//function to draw a connection between 2 components
function drawLine(obj1, obj2){
    //check if connection already in place
    if(document.getElementById(obj1+'_'+obj2)) {
        alert("A connection already in place!");
        return;
    }
    //capture the connection caption
    var caption = prompt("Please enter name", document.getElementById(obj2).getAttribute("action"));
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
//function to get text width on the browser
function getTextWidth(text, font) {
    // re-use canvas object for better performance
    var canvas = getTextWidth.canvas || (getTextWidth.canvas = document.createElement("canvas"));
    var context = canvas.getContext("2d");
    context.font = font;
    var metrics = context.measureText(text);
    return metrics.width;
}
//function to drop components
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
            //make sure no other existing element has the same id
            while(document.getElementById('drag_'+counter)!=null){
                counter++;
            }
            yy=y+icon_height+2;
            xx=x-((getTextWidth(caption)-icon_width)/2);
            key='drag_'+counter+'_l';
            withStorage=false;
            var dynamicParams=generateDynamicParameters(my_element);
            document.getElementById("pannel").innerHTML=document.getElementById("pannel").innerHTML+
                    createComponent(my_element.src,my_element.alt,'drag_'+counter,x+'px',y+'px',key,xx,yy,caption,my_element.getAttribute("action"),dynamicParams);
            counter++;
        }else{
            var caption=document.getElementById(my_element.id+'_l').textContent;
            document.getElementById(data).remove();
            document.getElementById(data+'_l').remove();
            if(document.getElementById(data+'@s')){
                document.getElementById(data+'@s').remove();
            }
            yy=y+icon_height+2;
            xx=x-((getTextWidth(caption)-icon_width)/2);
            key=my_element.id+'_l';
            withStorage=false;
            var dynamicParams=generateDynamicParameters(my_element);
            document.getElementById("pannel").innerHTML=createComponent(my_element.src,my_element.alt,my_element.id,x+'px',y+'px',key,xx,yy,caption,my_element.getAttribute("action"),dynamicParams)+
                    document.getElementById("pannel").innerHTML; 
        }
    }
}
//this method generate the custom parameters for each component
function generateDynamicParameters(element){
    var dynamic_parameters="";
    var itemNo=1;
    while(element.getAttribute("param"+itemNo)!=null){
        dynamic_parameters+=' param'+itemNo+'="'+element.getAttribute("param"+itemNo)+'"'+
                ' param'+itemNo+'_value="'+element.getAttribute('param'+itemNo+'_value')+'"';
        if(element.getAttribute("param"+itemNo)=='Storage Size'){
            if(element.getAttribute('param'+itemNo+'_value')>0){
                withStorage=true;
            }
        }
        itemNo++;
        //max 10 parameters
        if(itemNo>=max_params) break;
    }
    return dynamic_parameters;
}
//this function displays editable fields for a component
function generateDynamicParametersFields(element,onlyStorage){
    var dynamic_parameters="";
    var itemNo=1;
    if(onlyStorage){
         while(element.getAttribute("param"+itemNo)!=null){
            if(element.getAttribute("param"+itemNo)=='Storage Size'){  
                dynamic_parameters+=' <div class="slidecontainer"><p>'+element.getAttribute("param"+itemNo)+' <span id="storage">'+element.getAttribute('param'+itemNo+'_value')+'Mi</span></p>'+
                    ' <input id="param'+itemNo+'" value="'+element.getAttribute('param'+itemNo+'_value')+'" class="slider2" type="range" min="0" max="4096" step="256" onInput="document.getElementById(\'storage\').innerHTML = this.value+\'Mi\';"></div>';   
            }
            itemNo++;
            //max 10 parameters
            if(itemNo>=max_params) break;
         }
    }else {
        while(element.getAttribute("param"+itemNo)!=null){
            //special case for replica and storage/memory
            if(element.getAttribute("param"+itemNo)=='Replica Count'){  
                dynamic_parameters+=' <div class="slidecontainer"><p>'+element.getAttribute("param"+itemNo)+' <span id="replica">'+element.getAttribute('param'+itemNo+'_value')+'</span></p>'+
                    ' <input id="param'+itemNo+'" value="'+element.getAttribute('param'+itemNo+'_value')+'" class="slider2" type="range" min="1" max="12" step="1" onInput="document.getElementById(\'replica\').innerHTML = this.value;"></div>';   
            }else if(element.getAttribute("param"+itemNo)=='Storage Size'){  
                dynamic_parameters+=' <div class="slidecontainer"><p>'+element.getAttribute("param"+itemNo)+' <span id="storage">'+element.getAttribute('param'+itemNo+'_value')+'Mi</span></p>'+
                    ' <input id="param'+itemNo+'" value="'+element.getAttribute('param'+itemNo+'_value')+'" class="slider2" type="range" min="0" max="4096" step="256" onInput="document.getElementById(\'storage\').innerHTML = this.value+\'Mi\';"></div>';   
            }else if(element.getAttribute("param"+itemNo)=='Memory Size'){  
                dynamic_parameters+=' <div class="slidecontainer"><p>'+element.getAttribute("param"+itemNo)+' <span id="memory">'+element.getAttribute('param'+itemNo+'_value')+'Mi</span></p>'+
                    ' <input id="param'+itemNo+'" value="'+element.getAttribute('param'+itemNo+'_value')+'" class="slider2" type="range" min="256" max="4096" step="256" onInput="document.getElementById(\'memory\').innerHTML = this.value+\'Mi\';"></div>';   
            }else if(element.getAttribute("param"+itemNo)=='External' || element.getAttribute("param"+itemNo)=='Native'){  
                dynamic_parameters+=' <div>'+element.getAttribute("param"+itemNo)+'<br>'+
                    ' <input type="checkbox" id="param'+itemNo+'" ';
                if(element.getAttribute('param'+itemNo+'_value')=='TRUE'){
                    dynamic_parameters+='checked></div>';
                }else{
                    dynamic_parameters+='></div>';
                }
            } else{
                dynamic_parameters+=' <div>'+element.getAttribute("param"+itemNo)+'</div>'+
                    ' <input id="param'+itemNo+'" value="'+element.getAttribute('param'+itemNo+'_value')+'">';
            }
            itemNo++;
            //max 10 parameters
            if(itemNo>=max_params) break;
        }
    }
    return dynamic_parameters;
}
//this function updates the custome paramters of a component plus its caption
//from the component properties
function updateParams(element,onlyStorage,removeStorage){
    var itemNo=1;
    if(onlyStorage){
        while(element.getAttribute("param"+itemNo)!=null){
            if(element.getAttribute("param"+itemNo)=='Storage Size'){
                //update storage size or delete it 
                if(removeStorage){
                    element.setAttribute("param"+itemNo+"_value",0);
                }else{
                    element.setAttribute("param"+itemNo+"_value",document.getElementById("param"+itemNo).value);
                }
                //delete it if value is zero
                if(element.getAttribute("param"+itemNo+"_value")==0){
                    if(document.getElementById(element.id+'@s')!=null){
                        document.getElementById(element.id+'@s').remove();
                        resetPropes();
                    }
                }
            }
            itemNo++;
            //max 10 parameters
            if(itemNo>=max_params) break;
        }
    }else{
        while(element.getAttribute("param"+itemNo)!=null){
            if(element.getAttribute("param"+itemNo)=='External' || element.getAttribute("param"+itemNo)=='Native'){ 
                if(document.getElementById("param"+itemNo).checked){
                    element.setAttribute("param"+itemNo+"_value","TRUE");
                }else{
                    element.setAttribute("param"+itemNo+"_value","FALSE");                
                }
            }else {
                element.setAttribute("param"+itemNo+"_value",document.getElementById("param"+itemNo).value);
                if(element.getAttribute("param"+itemNo)=='Storage Size'){
                    if(element.getAttribute("param"+itemNo+"_value")>0){
                        if(document.getElementById(element.id+'@s')==null){
                            //add storage icon
                            var content='<img src="images/storage.png" id="'+element.id+'@s" width="'+icon_width+'" height="'+icon_height+'" onclick="showStorageDetails('+element.id+');" style="position:absolute; left:'+
                                element.style.left+'; top:'+parseInt(parseInt(element.style.top)+70)+'px;" class="component">';
                            document.getElementById("pannel").innerHTML+=content;
                        }
                    }else{
                        if(document.getElementById(element.id+'@s')!=null){
                            document.getElementById(element.id+'@s').remove();
                        }
                    }
                }
            }
            itemNo++;
            //max 10 parameters
            if(itemNo>=max_params) break;
        }
        newCaption(document.getElementById("srv_name").value,document.getElementById(element.id+'_l'),parseInt(document.getElementById(element.id).style.left));
    }
}
//this function creata a component
function createComponent(imgSrc,alt,id,x,y,key,xx,yy,caption,action,dynamicParams){
    //check if same service name is used
    var similar=validateUniqueCaption(caption);
    while(similar>0){
        similar++;
        caption+=''+similar;
        similar=validateUniqueCaption(caption);
    }
    xx=parseInt(x)-((getTextWidth(caption)-icon_width)/2);
    //create the component
    var content='<img src="'+imgSrc+'" alt="'+alt+'" action="'+action+'" draggable="true" ondragstart="drag(event)" id="'+id+'" onclick="startLine('+id+');" width="'+icon_width+'" height="'+icon_height+'" style="position:absolute; left:'+
            x+'; top:'+y+';" class="component" '+dynamicParams+' >'+
            '<label id="'+key+'" style="position:absolute; left:'+xx+'px; top:'+yy+'px; font-size:8pt;font-family:arial;font-weight:bold;" onclick="updateCaption('+key+','+parseInt(x)+')">'+caption+'</label>';
    if(withStorage){
        withStorage=false;
        //add storage icon
        content+='<img src="images/storage.png" id="'+id+'@s" width="'+icon_width+'" height="'+icon_height+'" onclick="showStorageDetails('+id+');" style="position:absolute; left:'+
            x+'; top:'+parseInt(parseInt(y)+70)+'px;" class="component">';
    }
    return content;
}
//update the component caption
function newCaption(caption,element,x){
    if(caption==null || caption=='') return;
    //check if same service name is used
    element.textContent='';
    var similar=validateUniqueCaption(caption);
    while(similar>0){
        similar++;
        caption+=''+similar;
        similar=validateUniqueCaption(caption);
    }
    element.textContent=caption;
    //calculate and adjust the new caption location
    xx=x-((getTextWidth(caption)-icon_width)/2);
    element.style.left=xx+'px';
}
//method to check if a service name/caption is unique
function validateUniqueCaption(caption){
    var allImages = document.getElementsByTagName("img");
    var similarCount=0;
    for (var i=0; i < allImages.length; i++) {
        if(allImages[i].id.indexOf("_")!=-1 && allImages[i].src.indexOf('storage')==-1) {
            if(document.getElementById(allImages[i].id+"_l")!=null){
                if(document.getElementById(allImages[i].id+"_l").textContent==caption) {
                    similarCount++;
                }
            }
        }
    }
    return similarCount;
}
//this function update the component caption
function updateCaption(key,x){
    //alert(key);
    var caption = prompt("Please enter name", key.textContent);
    newCaption(caption,key,x);
}
//function to delete the components by dropping it outside the pannel
function drop_back(ev) {
    ev.preventDefault();
    var data = ev.dataTransfer.getData("text");
    var my_element = document.getElementById(data);
    if(my_element.id.indexOf("_")!=-1) {
        deleteComponent(data);
  }
}
//this methid deletes component using its id
function deleteComponent(data){
    if(confirm("Delete Component?")){
        //component must not connected using connection
        if(validateConnections(data)==true) {alert('Delete its connection(s) first!');return;}
        document.getElementById(data).remove();
        document.getElementById(data+'_l').remove();
        if(document.getElementById(element.id+'@s')!=null){
            document.getElementById(element.id+'@s').remove();
        }
        resetPropes();
    }
}
function resetPropes(){
    document.getElementById("prop").innerHTML='<br><br><br><label>No Component Selected</label>';
    startDraw=false;
}
//this component deletes component using the object itself
function deleteComponentObj(data){
    if(confirm("Delete Component?")){
        //component must not connected using connection
        if(validateConnections(data.id)==true) {alert('Delete its connection(s) first!');return;}
        data.remove();
        document.getElementById(data.id+'_l').remove();
        if(document.getElementById(element.id+'@s')!=null){
            document.getElementById(element.id+'@s').remove();
        }
        resetPropes();
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
//save function that generate JSON
function save(){
    document.getElementById("save").value=generateJSON();
}
//function to generate the HTML content of the pannel
//not really required, we can just throw the innerHTML of the pannel
function generateHTML(){
    var content='';
    var allImages = document.getElementsByTagName("img");
    for (var i=0; i < allImages.length; i++) {
        if(allImages[i].id.indexOf("_")!=-1 && allImages[i].src.indexOf('storage')==-1) {
            content+=allImages[i].outerHTML;
            if(document.getElementById(allImages[i].id+"_l")!=null)
                content+=document.getElementById(allImages[i].id+"_l").outerHTML;
        }
    }
    var allConnections = document.getElementsByTagName("connection");
    for (var i=0; i < allConnections.length; i++) {
        content+=allConnections[i].outerHTML;
    }
    return content;
}
//function to generate the JSON representation of the pannel
function generateJSON(){
    var content='{\n"format":"'+currentFormatVersion+'",\n';
    content+='"groupId":"'+document.getElementById("groupId").value+'",\n';
    content+='"version":"'+document.getElementById("version").value+'",\n';
    content+='"build":"'+document.getElementById("build").value+'",\n';
    var allImages = document.getElementsByTagName("img");
    var allConnections = document.getElementsByTagName("connection");
    content+='"components":[\n';
    var found=false;
    for (var i=0; i < allImages.length; i++) {
        if(allImages[i].id.indexOf("_")!=-1 && allImages[i].src.indexOf('storage')==-1) {
            if(found) content+=',\n';
            found=true;
            content+='{\n';
                content+='"type":"'+allImages[i].alt+'",\n';
                content+='"id":"'+allImages[i].id+'",\n';
                content+='"src":"'+allImages[i].src+'",\n';
                content+='"action":"'+allImages[i].getAttribute("action")+'",\n';
                content+='"x":"'+allImages[i].style.left+'",\n';
                content+='"y":"'+allImages[i].style.top+'",\n';
                content+='"caption":"'+document.getElementById(allImages[i].id+"_l").innerHTML+'",\n';
                content+='"params":[\n';
                var itemNo=1;
                while(allImages[i].getAttribute("param"+itemNo)!=null){
                    if(itemNo!=1) content+=',\n';
                    content+='{"key":"'+allImages[i].getAttribute("param"+itemNo)+'",\n';
                    content+='"value":"'+allImages[i].getAttribute("param"+itemNo+"_value")+'"}\n';
                    itemNo++;
                    //max 10 parameters
                    if(itemNo>=max_params) break;
                }                    
                content+='],\n';
                content+='"dependencies":[\n';                    
                var depdenceyFound=false;
                for (var n=0; n < allConnections.length; n++) {
                    //only "to" depenencies 
                    if(allConnections[n].getAttribute('from')=="#"+allImages[i].id){
                        if(depdenceyFound) content+=',\n';
                        depdenceyFound=true;
                        var key=allConnections[n].getAttribute('to').substring(1);                            
                        content+='{"type":"'+document.getElementById(key).alt+'",\n';
                        content+='"name":"'+document.getElementById(key+"_l").innerHTML+'",\n';
                        content+='"text":"'+allConnections[n].getAttribute('text')+'"}\n';
                    }
                }
                content+=']\n}\n';
        }
    }
    content+='],\n';
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
//function to laod a content from the save textarea
function loadContent(){
    loadJSON(document.getElementById("save").value);
}
//function to load a content into the pannel (JSON format expected)
function loadJSON(mydata){
    try {
        var mydata=JSON.parse(mydata);
    } catch (e) {
        //if not JSON alert error
        alert("Invalid Input, reason: "+e);
        return;
    }
    document.getElementById("groupId").value=mydata.groupId;
    document.getElementById("version").value=mydata.version;
    var content='';
    var key="";
    for (var i=0; i < mydata.components.length; i++) {
        yy=parseInt(mydata.components[i].y)+icon_height+2;
        xx=parseInt(mydata.components[i].x)-((getTextWidth(mydata.components[i].caption)-icon_width)/2);
        key=mydata.components[i].id+'_l';
        var dynamicParams='';
        withStorage=false;
        for(var s=1;s<=mydata.components[i].params.length;s++){
            dynamicParams+=' param'+s+'="'+mydata.components[i].params[s-1].key+'"'+
                ' param'+s+'_value="'+mydata.components[i].params[s-1].value+'"';
            if(mydata.components[i].params[s-1].key=='Storage Size'){
                if(mydata.components[i].params[s-1].value>0){
                    withStorage=true;
                }
            }
        }
        content+=createComponent(mydata.components[i].src,mydata.components[i].type,mydata.components[i].id,mydata.components[i].x,mydata.components[i].y,key,xx,yy,mydata.components[i].caption,mydata.components[i].action,dynamicParams);
    }
    for (var i=0; i < mydata.connections.length; i++) {
        content+=createConnection(mydata.connections[i].id,mydata.connections[i].from,mydata.connections[i].to,"red",mydata.connections[i].text,' fromX='+mydata.connections[i].fromX+' toX='+mydata.connections[i].toX,' fromY='+mydata.connections[i].fromY+' toY='+mydata.connections[i].toY);
    }
    document.getElementById("pannel").innerHTML=content;
    create();
    showMessage("Successfully load "+mydata.components.length+" component(s) and "+mydata.connections.length+" connection(s)");
}
//load a content from the save text area
function load(){
    document.getElementById("pannel").innerHTML=document.getElementById("save").value;
    create();
}
//swich connectivity between components on/off
function connect(yes){
    if(yes) connectFlag=true;
    else connectFlag=false;
    startDraw=false;
}
//save function
function save(){
    if(!atLEastOneComponent()){
        showMessage('You must have at least one component!');
        return;
    }
    document.getElementById("type").value="1";
    document.getElementById("data").value=generateJSON();
    document.forms[0].submit();
}
//generate function
function next(){
    if(!atLEastOneComponent()){
        showMessage('You must have at least one component!');
        return;
    }
    document.getElementById("type").value="2";
    document.getElementById("data").value=generateJSON();
    document.forms[0].submit();
}
//view the content in save area
function viewContent(){
    document.getElementById("save").value=generateHTML();
}
//function to reset the drawing pannel
function reset(){
    document.getElementById("save").value='';
    document.getElementById("data").value='';
    document.getElementById("pannel").innerHTML='';
    document.getElementById("prop").innerHTML='<br><br><br><label>No Component Selected</label>';
    startDraw=false;
    counter=10;
}
//function to validate at least one component is there so save can make sense
function atLEastOneComponent(){
    var allImages = document.getElementsByTagName("img");
    for (var i=0; i < allImages.length; i++) {
        if(allImages[i].id.indexOf("_")!=-1) {
            return true;
        }
    }
    return false;
}
//function to show text message in the snackbar div (as a toast)
function showMessage(message) {
    document.getElementById("snackbar").innerHTML=message;
    var x = document.getElementById("snackbar");
    x.className = "show";
    setTimeout(function(){ x.className = x.className.replace("show", ""); }, 3000);
}

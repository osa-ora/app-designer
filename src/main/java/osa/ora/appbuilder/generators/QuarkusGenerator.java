/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package osa.ora.appbuilder.generators;

import java.util.HashMap;
import java.util.Map;
import osa.ora.appbuilder.beans.Dependency;
import osa.ora.appbuilder.config.IGenerator;

/**
 *
 * @author Osama Oransa
 */
public class QuarkusGenerator implements IGenerator{
    private Map<String,String> mapList=null;

    @Override
    public String getName() {
        return "Quarkus";
    }

    @Override
    public String getIcon() {
        return "quarkus.png";
    }

    @Override
    public String getDefaultProducerAction() {
        return "REST";
    }

    @Override
    public Map<String, String> getParamList() {
        if(mapList==null){
            mapList=new HashMap<String,String>();
            //mapList.put(VERSION, null);
            mapList.put(EXTERNAL, "FALSE");
            mapList.put(REPLICA, "1");
            mapList.put(NATIVE, "TRUE");
        }
        return mapList;
    }
    
    @Override 
    public String generateDeployment(String caption, Map<String,String> params,Dependency[] dependencies) {
        String command="cd "+caption+"\n";
        //check wether native or java is required
        if(params.get(NATIVE)==null || Boolean.parseBoolean(params.get(NATIVE))){
            //adjust version to the latest version if native
            params.put(VERSION, "ubi-quarkus-native-s2i:19.3.1-java11");
            command+="oc new-app quay.io/quarkus/"+params.get(VERSION)+" --name="+caption+" . \n";
        }else{
            //adjust to the latest version of jdk
            params.put("Version", "redhat-openjdk-18/openjdk18-openshift");
            command+="oc new-app registry.access.redhat.com/"+params.get(VERSION)+" --name="+caption+" . \n";
        }
        //add all service bindings as secrets or config maps
        command+=addBindings("oc set env dc/"+caption+" --from ",dependencies);
        //expose if external service is required
        if(params.get(EXTERNAL)!=null && Boolean.parseBoolean(params.get(EXTERNAL))){
            command+="oc expose svc/"+caption+"\n";
        }
        if(params.get(REPLICA)!=null && Integer.parseInt(params.get(REPLICA))>1){
            command+="oc scale dc/"+caption+" --replicas="+Integer.parseInt(params.get(REPLICA))+"\n";
        }
        command+="cd ..\n";
        //System.out.println("Command="+command);
        return command;
    }

    @Override
    public String generateArtifact(String groupId, String version, String build, String caption, Dependency[] dependencies) {
        //basic maven command to generate the project
        String command="mvn io.quarkus:quarkus-maven-plugin:1.5.0.Final:create"
                + " -DprojectGroupId="+groupId
                + " -DprojectArtifactId="+caption
                + " -DprojectVersion="+version
                + " -DclassName=\""+groupId+"."+caption+"\"\n";
        command+="cd "+caption+"\n";
        //execute add all extentions according to service dependencies
        command+=addDependencies("./mvnw quarkus:add-extension -Dextensions=",dependencies);
        command+="# S2I: Ad.s2i folder content for non-native build ..\n";
        command+="cd ..\n";
        return command;
    }
    private String addDependencies(String command, Dependency[] dependencies){
        String allDep="";
        //generic dependencies
        allDep+=command+"\"SmallRye-Health\"\n";
        allDep+=command+"\"JSON-B\"\n";
        //user specific depdenencies
        for(Dependency dep:dependencies){
            dep.setType(dep.getType().toUpperCase());
            switch(dep.getType()){
                case "MYSQL":
                    allDep+=command+"\"jdbc-mysql\"\n";
                    break;
                case "POSTGRESQL":
                    allDep+=command+"\"jdbc-postgres\"\n";
                    break;
                case "MARIADB":
                    allDep+=command+"\"jdbc-mariadb\"\n";
                    break;
                case "MONGODB":
                    allDep+=command+"\"mongodb-client\"\n";
                    break;
                case "ACTIVEMQ":
                    allDep+=command+"\"activemq\"\n";
                    break;
                case "KAFKA":
                    allDep+=command+"\"kafka-streams\"\n";
                    allDep+=command+"\"kafka-client\"\n";
                    break;
                default:
                    break;
            }
        }
        return allDep;
    }
    private String addBindings(String command, Dependency[] dependencies){
        String allDep="";
        //user specific depdenencies
        for(Dependency dep:dependencies){
            dep.setType(dep.getType().toUpperCase());
            switch(dep.getType()){
                case "MYSQL":
                    allDep+=command+"secrets\\"+dep.getName()+"\n";
                    break;
                case "POSTGRESQL":
                    allDep+=command+"secrets\\"+dep.getName()+"\n";
                    break;
                case "MARIADB":
                    allDep+=command+"secrets\\"+dep.getName()+"\n";
                    break;                    
                case "MONGODB":
                    allDep+=command+"secrets\\"+dep.getName()+"\n";
                    break;
                case "ACTIVEMQ":
                    allDep+=command+"secrets\\"+dep.getName()+"\n";
                    break;
                case "KAFKA":
                    allDep+=command+"secrets\\"+dep.getName()+"\n";
                    break;
                default:
                    break;
            }
        }
        return allDep;
    }
}

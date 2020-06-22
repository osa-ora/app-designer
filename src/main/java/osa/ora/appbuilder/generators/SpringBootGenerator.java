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
public class SpringBootGenerator implements IGenerator{
    private Map<String,String> mapList=null;

    @Override
    public String getName() {
        return "SpringBoot";
    }

    @Override
    public String getIcon() {
        return "spring-boot.png";
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
        }
        return mapList;
    }
    
    @Override 
    public String generateDeployment(String caption, Map<String,String> params,Dependency[] dependencies) {
        String command="cd "+caption+"\n";
        //adjust to the latest version of jdk
        params.put(VERSION, "redhat-openjdk-18/openjdk18-openshift");
        command+="oc new-app registry.access.redhat.com/"+params.get(VERSION)+" --name="+caption+" . \n";
        //add all service bindings as secrets or config maps
        command+=addBindings("oc set env dc/"+caption+" --from ",dependencies);
        //expose if external service is required
        if(params.get(EXTERNAL)!=null && Boolean.parseBoolean(params.get(EXTERNAL))){
            command+="oc expose svc/"+caption+"\n";
        }
        command+="cd ..\n";
        //System.out.println("Command="+command);
        return command;
    }

    @Override
    public String generateArtifact(String groupId, String version, String build, String caption, Dependency[] dependencies) {
        //basic maven command to generate the project
        String command="curl https://start.spring.io/starter.zip"
                + " -d packageName="+groupId
                + " -d groupId="+groupId
                + " -d name="+caption
                + " -d artifactId="+caption
                + " -d version="+version
                + " -d type=maven-project -o \""+caption+".zip\""
                + " -d dependencies="+addDependencies(dependencies)+"\n";
        return command;
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
                case "MONGODB":
                    allDep+=command+"secrets\\"+dep.getName()+"\n";
                    break;
                case "REDIS":
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
    private String addDependencies(Dependency[] dependencies){
        String allDep="";
        //generic dependencies
        allDep+="jersey,actuator,web,jpa";
        //user specific depdenencies
        for(Dependency dep:dependencies){
            dep.setType(dep.getType().toUpperCase());
            switch(dep.getType()){
                case "MYSQL":
                    allDep+=",mysql";
                    break;
                case "POSTGRESQL":
                    allDep+=",postgresql";
                    break;
                /*case "MONGODB":
                    allDep+=",data-mongodb";
                    break;*/
                case "REDIS":
                    allDep+=",redis";
                    break;
                case "ACTIVEMQ":
                    allDep+=",artemis";
                    break;
                case "KAFKA":
                    allDep+=",kafka";
                    break;
                default:
                    break;
            }
        }
        return allDep;
    }
}

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
public class JBossGenerator implements IGenerator{
    private Map<String,String> mapList=null;

    @Override
    public String getName() {
        return "JBoss";
    }

    @Override
    public String getIcon() {
        return "jboss.png";
    }

    @Override
    public String getDefaultProducerAction() {
        return "WEB";
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
        //use latest version
        //params.put(VERSION, "8");
        //create command using the required parameters
        command+="oc new-app --name="+caption+" jboss-webserver30-tomcat8-openshift:1.3 .\n";
        //add all service bindings as secrets or config maps
        command+=addBindings("oc set env dc/"+caption+" --from ",dependencies);
        //expose the service in case it is external service
        if(params.get(EXTERNAL)!=null && Boolean.parseBoolean(params.get(EXTERNAL))){
            command+="oc expose svc/"+caption+"\n";
        }
        command+="cd ..\n";
        //System.out.println("Command="+command);
        return command;
    }

    @Override
    public String generateArtifact(String groupId, String version, String build, String caption, Dependency[] dependencies) {
        return null;
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

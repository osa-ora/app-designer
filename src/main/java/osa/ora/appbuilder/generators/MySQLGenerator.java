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
public class MySQLGenerator implements IGenerator{
    private Map<String,String> mapList=null;

    @Override
    public String getName() {
        return "MySQL";
    }

    @Override
    public String getIcon() {
        return "mysql.png";
    }

    @Override
    public String getDefaultProducerAction() {
        return "REST";
    }

    @Override
    public Map<String, String> getParamList() {
        if(mapList==null){
            mapList=new HashMap<String,String>();
            mapList.put(DB_NAME, "db_name");
            mapList.put(DB_ROOT_PASSWORD, "root_password");
            mapList.put(DB_USER, "db_user");
            mapList.put(DB_USER_PASSWORD, "db_user_password");
            mapList.put(VERSION, null);
            mapList.put(EXTERNAL, null);
        }
        return mapList;
    }

    @Override 
    public String generateDeployment(String caption, Map<String,String> params,Dependency[] dependencies) {
        //use latest version
        params.put(VERSION, "8.0");
        //create command using the required parameters
        String command="oc new-app mysql-persistent -p DATABASE_SERVICE_NAME="+caption+" -p  MYSQL_ROOT_PASSWORD="+params.get(DB_ROOT_PASSWORD)+" -p MYSQL_DATABASE="+params.get(DB_NAME)
                + " -p MYSQL_USER="+params.get(DB_USER)+" -p MYSQL_PASSWORD="+params.get(DB_USER_PASSWORD)+" -p MEMORY_LIMIT=512Mi -p VOLUME_CAPACITY=512Mi -p MYSQL_VERSION="+params.get(VERSION)+"\n";
        //expose the service in case it is external service
        if(params.get(EXTERNAL)!=null && Boolean.parseBoolean(params.get(EXTERNAL))){
            command+="oc expose svc/"+caption+"\n";
        }
        //System.out.println("Command="+command);
        return command;
    }

    @Override
    public String generateArtifact(String groupId, String version, String build, String caption, Dependency[] dependencies) {
        return null;
    }    

}

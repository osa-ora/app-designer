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
public class MongoDBGenerator implements IGenerator{
    private Map<String,String> mapList=null;

    @Override
    public String getName() {
        return "MongoDB";
    }

    @Override
    public String getIcon() {
        return "mongodb.png";
    }

    @Override
    public String getDefaultProducerAction() {
        return "Persist";
    }

    @Override
    public Map<String, String> getParamList() {
        if(mapList==null){
            mapList=new HashMap<String,String>();
            mapList.put(DB_NAME, "db_name");
            mapList.put(DB_ROOT_PASSWORD, "admin_password");
            mapList.put(DB_USER, "db_user");
            mapList.put(DB_USER_PASSWORD, "db_user_password");
            //mapList.put(VERSION, null);
            mapList.put(EXTERNAL, "FALSE");
            mapList.put(STORAGE_SIZE,"512");
            mapList.put(MEMORY_SIZE,"512");
        }
        return mapList;
    }

    @Override 
    public String generateDeployment(String caption, Map<String,String> params,Dependency[] dependencies) {
        //use latest version
        params.put(VERSION, "3.6");
        //check MongoDB template type if storage is required or not?
        String mongoDB="mongodb-persistent";
        if(params.get(STORAGE_SIZE)==null || "0".equals(params.get(STORAGE_SIZE))){
            mongoDB="mongodb-ephemeral";
        }
        //create command using the required parameters
        String command="oc new-app "+mongoDB+" -p DATABASE_SERVICE_NAME="+caption+" -p  MONGODB_ADMIN_PASSWORD="+params.get(DB_ROOT_PASSWORD)+" -p MONGODB_DATABASE="+params.get(DB_NAME)
                + " -p MONGODB_USER="+params.get(DB_USER)+" -p MONGODB_PASSWORD="+params.get(DB_USER_PASSWORD)+" -p MEMORY_LIMIT="+params.get(MEMORY_SIZE)+"Mi -p MONGODB_VERSION="+params.get(VERSION);
        if(mongoDB.equals("mongodb-persistent")){
            command+=" -p VOLUME_CAPACITY="+params.get(STORAGE_SIZE)+"Mi\n";
        }else{
            command+="\n";
        }
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

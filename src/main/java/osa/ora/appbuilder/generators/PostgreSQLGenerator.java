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
public class PostgreSQLGenerator implements IGenerator{
    private Map<String,String> mapList=null;

    @Override
    public String getName() {
        return "PostgreSQL";
    }

    @Override
    public String getIcon() {
        return "postgresql.png";
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
            mapList.put(DB_USER, "db_user");
            mapList.put(DB_USER_PASSWORD, "db_user_password");
            mapList.put(STORAGE_SIZE,"512");
            mapList.put(MEMORY_SIZE,"512");
            mapList.put(VERSION, null);
            mapList.put(EXTERNAL, null);
        }
        return mapList;
    }

    @Override 
    public String generateDeployment(String caption, Map<String,String> params,Dependency[] dependencies) {
        //use latest version
        params.put(VERSION, "10");
        //check MySQL type if storage is required or not?
        String postgreSQL="postgresql-persistent";
        if(params.get(STORAGE_SIZE)==null || "0".equals(params.get(STORAGE_SIZE))){
            postgreSQL="postgresql-ephemeral";
        }
        //create command using the required parameters
        String command="oc new-app "+postgreSQL+" -p DATABASE_SERVICE_NAME="+caption+" -p POSTGRESQL_DATABASE="+params.get(DB_NAME)
                + " -p POSTGRESQL_USER="+params.get(DB_USER)+" -p POSTGRESQL_PASSWORD="+params.get(DB_USER_PASSWORD)+" -p MEMORY_LIMIT="+params.get(MEMORY_SIZE)+"Mi -p POSTGRESQL_VERSION="+params.get(VERSION);
        if(postgreSQL.equals("postgresql-persistent")){
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

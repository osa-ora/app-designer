/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package osa.ora.appbuilder.generators;

import osa.ora.appbuilder.beans.Dependency;
import osa.ora.appbuilder.config.IGenerator;

/**
 *
 * @author Osama Oransa
 */
public class QuarkusGenerator implements IGenerator{


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
    public String generateDeployment() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
    
}

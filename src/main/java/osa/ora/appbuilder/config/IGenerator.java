/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package osa.ora.appbuilder.config;

import java.util.Map;
import osa.ora.appbuilder.beans.Dependency;

/**
 *
 * @author Osama Oransa
 */
public interface IGenerator {
    //constant for parameters used in different generators
    public static String EXTERNAL="External";
    public static String VERSION="Version";
    public static String NATIVE="Native";
    public static String DB_NAME="DB Name";
    public static String STORAGE_SIZE="Storage Size";
    public static String MEMORY_SIZE="Memory Size";
    public static String DB_ROOT_PASSWORD="DB Root Password";
    public static String DB_USER="DB User";
    public static String DB_USER_PASSWORD="DB User Password";
    public static String REDIS_USER_PASSWORD="Redis User Password";
    public static String REPLICA="Replica Count";
    
    //generator methods
    /**
     * Get component name
     * @return 
     */
    public String getName();
    /**
     * Get component icon, need to be added to the images folder
     * @return 
     */
    public String getIcon();
    /**
     * Get component default action when other components connect to it
     * @return 
     */
    public String getDefaultProducerAction();
    /**
     * This method return the list of parameters that is required for this component.
     * @return Map including the list of the parameters for this component
     */
    public Map<String,String> getParamList();
    /**
     * This method generate the actual artifact i.e. project/code
     * @param groupId
     * @param version
     * @param build
     * @param caption
     * @param dependencies
     * @return String of the generation commands
     */
    public String generateArtifact(String groupId, String version, String build, String caption, Dependency[] dependencies);
    /**
     * This method generate the OpenShift deployment commands i.e. oc command list
     * @param caption
     * @param params
     * @param dependencies
     * @return String of the deployment commands
     */
    public String generateDeployment(String caption, Map<String,String> params,Dependency[] dependencies);
}

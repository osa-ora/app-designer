/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package osa.ora.appbuilder.beans;

/**
 *
 * @author Osama Redhat
 */
public class Application {
    private String groupId;
    private String version;
    private String build;
    private Component[] components;
    private Connection[] connections;
    public Application(){
    }

    /**
     * @return the groupId
     */
    public String getGroupId() {
        return groupId;
    }

    /**
     * @param groupId the groupId to set
     */
    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    /**
     * @return the version
     */
    public String getVersion() {
        return version;
    }

    /**
     * @param version the version to set
     */
    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * @return the components
     */
    public Component[] getComponents() {
        return components;
    }

    /**
     * @param components the components to set
     */
    public void setComponents(Component[] components) {
        this.components = components;
    }

    /**
     * @return the connections
     */
    public Connection[] getConnections() {
        return connections;
    }

    /**
     * @param connections the connections to set
     */
    public void setConnections(Connection[] connections) {
        this.connections = connections;
    }

    /**
     * @return the build
     */
    public String getBuild() {
        return build;
    }

    /**
     * @param build the build to set
     */
    public void setBuild(String build) {
        this.build = build;
    }
    
}

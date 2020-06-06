/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package osa.ora.appbuilder.config;

import osa.ora.appbuilder.beans.Dependency;

/**
 *
 * @author Osama Oransa
 */
public interface IGenerator {
    public String getName();
    public String getIcon();
    public String getDefaultProducerAction();
    public String generateArtifact(String groupId, String version, String build, String caption, Dependency[] dependencies);
    public String generateDeployment();
}

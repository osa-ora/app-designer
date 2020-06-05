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
    public String generateDeployment() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void generateArtifact(String groupId, String version, String build, String caption, Dependency[] dependencies) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
}

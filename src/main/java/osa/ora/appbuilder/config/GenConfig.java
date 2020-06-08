/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package osa.ora.appbuilder.config;

import java.util.HashMap;
import java.util.Map;
import osa.ora.appbuilder.generators.JBossGenerator;
import osa.ora.appbuilder.generators.JavaGenerator;
import osa.ora.appbuilder.generators.MySQLGenerator;
import osa.ora.appbuilder.generators.PostgreSQLGenerator;
import osa.ora.appbuilder.generators.QuarkusGenerator;
import osa.ora.appbuilder.generators.TomcatGenerator;

/**
 *
 * @author Osama Oransa
 */
public class GenConfig {
    //List of all supported generators
    private final Map<String, IGenerator> generatorList=new HashMap<String, IGenerator>();
    //singleton class
    private static GenConfig genConfig;
    public static GenConfig getInstance(){
        if(genConfig==null) genConfig=new GenConfig();
        return genConfig;
    }
    /**
     * private constructor
     */
    private GenConfig(){
        //add different generators
       IGenerator quarkusGenerator=new QuarkusGenerator();
       generatorList.put(quarkusGenerator.getName().toUpperCase(), quarkusGenerator);
       IGenerator mySQLGenerator=new MySQLGenerator();
       generatorList.put(mySQLGenerator.getName().toUpperCase(), mySQLGenerator);
       IGenerator postgreSQLGenerator=new PostgreSQLGenerator();
       generatorList.put(postgreSQLGenerator.getName().toUpperCase(), postgreSQLGenerator);
       IGenerator tomcatGenerator=new TomcatGenerator();
       generatorList.put(tomcatGenerator.getName().toUpperCase(), tomcatGenerator);
       IGenerator jbossGenerator=new JBossGenerator();
       generatorList.put(jbossGenerator.getName().toUpperCase(), jbossGenerator);
       IGenerator javeGenerator=new JavaGenerator();
       generatorList.put(javeGenerator.getName().toUpperCase(), javeGenerator);    
    }
    public IGenerator getGeneratorForType(String type){
        if(generatorList.get(type)!=null){
            return generatorList.get(type);
        }
        return null;
    } 
}

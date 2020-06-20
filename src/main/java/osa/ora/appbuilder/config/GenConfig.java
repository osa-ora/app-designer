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
import osa.ora.appbuilder.generators.MariaDBGenerator;
import osa.ora.appbuilder.generators.MongoDBGenerator;
import osa.ora.appbuilder.generators.MySQLGenerator;
import osa.ora.appbuilder.generators.PostgreSQLGenerator;
import osa.ora.appbuilder.generators.QuarkusGenerator;
import osa.ora.appbuilder.generators.RedisGenerator;
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
    /**
     * Method to return the singleton instance 
     * @return 
     */
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
       IGenerator mariaDBGenerator=new MariaDBGenerator();
       generatorList.put(mariaDBGenerator.getName().toUpperCase(), mariaDBGenerator);
       IGenerator mongoDBGenerator=new MongoDBGenerator();
       generatorList.put(mongoDBGenerator.getName().toUpperCase(), mongoDBGenerator);
       IGenerator tomcatGenerator=new TomcatGenerator();
       generatorList.put(tomcatGenerator.getName().toUpperCase(), tomcatGenerator);
       IGenerator jbossGenerator=new JBossGenerator();
       generatorList.put(jbossGenerator.getName().toUpperCase(), jbossGenerator);
       IGenerator javeGenerator=new JavaGenerator();
       generatorList.put(javeGenerator.getName().toUpperCase(), javeGenerator);    
       IGenerator redisGenerator=new RedisGenerator();
       generatorList.put(redisGenerator.getName().toUpperCase(), redisGenerator);     
    }
    /**
     * Method to return the generator for a type
     * @param type
     * @return IGenerator of that type
     */
    public IGenerator getGeneratorForType(String type){
        if(getGeneratorList().get(type)!=null){
            return getGeneratorList().get(type);
        }
        return null;
    } 

    /**
     * Method to return Map of all available generators
     * @return the generatorList
     */
    public Map<String, IGenerator> getGeneratorList() {
        return generatorList;
    }
}

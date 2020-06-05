/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package osa.ora.appbuilder.config;

import java.util.HashMap;
import java.util.Map;
import osa.ora.appbuilder.generators.QuarkusGenerator;

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
       IGenerator quarkusGenerator=new QuarkusGenerator();
       generatorList.put(quarkusGenerator.getName(), quarkusGenerator);
    }
    public IGenerator getGeneratorForType(String type){
        if(generatorList.get(type)!=null){
            return generatorList.get(type);
        }
        return null;
    } 
}

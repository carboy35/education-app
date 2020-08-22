package com.people.util;

import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Objetivo: Clase que customiza la estrategia de ingenieria reversa usada para la generación de entities.
 * Fecha: 14-09-2018
 * @author Alexis Tapia
 * @version 1.0
 */

import org.hibernate.cfg.reveng.DelegatingReverseEngineeringStrategy;
import org.hibernate.cfg.reveng.ReverseEngineeringStrategy;
import org.hibernate.cfg.reveng.TableIdentifier;

public class PeopleReverseEngineeringStrategy extends DelegatingReverseEngineeringStrategy {
	private static final Log LOG = LogFactory.getLog(PeopleReverseEngineeringStrategy.class);
    public PeopleReverseEngineeringStrategy(ReverseEngineeringStrategy delegate) {
        super(delegate);
    }

    @Override 

    public String getTableIdentifierStrategyName(TableIdentifier identifier) { 

        return "sequence"; 

    } 

      


    @Override 

    public Properties getTableIdentifierProperties(TableIdentifier identifier) { 

         String nombretabla=identifier.getName();
        //String prefijo=nombretabla.substring(0, nombretabla.indexOf('_'));
       
//         if(prefijo.equals("tpo")) {
//        	 prefijo=nombretabla;
//         }
         
         String secuencia=nombretabla +"_seq";
        Properties properties = super.getTableIdentifierProperties(identifier); 

        if(properties == null){ 

            properties = new Properties(); 

        } 

        properties.put(org.hibernate.id.enhanced.SequenceStyleGenerator.SEQUENCE_PARAM, secuencia); 


        return properties; 

    } 
}

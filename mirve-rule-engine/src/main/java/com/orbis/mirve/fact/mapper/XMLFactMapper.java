package com.orbis.mirve.fact.mapper;

import java.util.List;

import javax.naming.ConfigurationException;

import org.w3c.dom.Document;

/**
 * Interface to map XML to a list of objects T so that they can be inserted 
 * into {@link com.orbis.mirve.engine.MirveRuleEngine} as facts.
 * @author jbaker
 *
 * @param <T> Object to be mapped from XML format.
 */
public interface XMLFactMapper<T> {

	public List<T> readXML(Document xmlDoc) throws ConfigurationException;
}
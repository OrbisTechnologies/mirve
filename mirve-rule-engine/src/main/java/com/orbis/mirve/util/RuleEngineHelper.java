/*
 * Copyright 2017 by Orbis Technologies, Inc.  All rights reserved.
 */
package com.orbis.mirve.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

import javax.naming.ConfigurationException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieRepository;
import org.kie.api.builder.Message.Level;
import org.kie.api.io.KieResources;
import org.kie.api.io.Resource;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.orbis.mirve.fact.mapper.EntityTypeWeightMapper;
import com.orbis.mirve.fact.mapper.ExtractorEntityTypeWeightMapper;
import com.orbis.mirve.fact.mapper.XMLFactMapper;
import com.orbis.utils.etc.PathPropertiesUtil;
import java.io.StringReader;
import org.xml.sax.InputSource;

/**
 * This class contains helper methods that will be used in the
 * {@link com.orbis.mirve.engine.MirveRuleEngine} class.
 * 
 * @author jbaker
 *
 */
public class RuleEngineHelper {

	private final static Logger LOG = LoggerFactory
			.getLogger(RuleEngineHelper.class);

	/**
	 * Returns a KieSession containing the rule file collection provided. Note
	 * that once the KieSession is no longer needed, the dispose() function
	 * should be called to release all current session resources, setting up the
	 * session for garbage collection.
	 * 
	 * @param ruleFileCollection
	 *            A collection of rule (drl) files that should be used in the
	 *            session.
	 * 
	 * @return A KieSession where the provided rules can be fired and facts can
	 *         be inserted.
	 */
	public static KieSession getKieSessionWithRuleFiles(
			Collection<File> ruleFileCollection) {
		
		KieContainer kContainer = getKieContainerFromRuleFiles(ruleFileCollection);

		return kContainer.newKieSession();
	}
	
	public static KieContainer getKieContainerFromRuleFiles(
			Collection<File> ruleFileCollection) {
		KieServices ks = KieServices.Factory.get();
		KieRepository kr = ks.getRepository();
		KieFileSystem kfs = ks.newKieFileSystem();
		KieResources kieResources = ks.getResources();

		for (File ruleFile : ruleFileCollection) {

			// Create new KieFileSystem resource using the current rule file and
			// add it to "src/main/resources"

			Resource resource = kieResources.newFileSystemResource(ruleFile);
			kfs.write("src/main/resources/" + ruleFile.getName(), resource);
		}

		KieBuilder kb = ks.newKieBuilder(kfs);

		kb.buildAll(); // kieModule is automatically deployed to
						// KieRepository if successfully built.
		if (kb.getResults().hasMessages(Level.ERROR)) {
			throw new RuntimeException("Build Errors:\n"
					+ kb.getResults().toString());
		}

		KieContainer kContainer = ks.newKieContainer(kr.getDefaultReleaseId());

		return kContainer;
	}

	/**
	 * Returns a KieSession containing the rule file collection provided. Note
	 * that once the KieSession is no longer needed, the dispose() function
	 * should be called to release all current session resources, setting up the
	 * session for garbage collection.
	 * 
	 * @param ruleFileCollection
	 *            A collection of rule (drl) files that should be used in the
	 *            session.
	 * 
	 * @return A KieSession where the provided rules can be fired and facts can
	 *         be inserted.
	 */
	public static KieSession getKieSessionWithRule_WeightFiles(
			Collection<File> ruleFileCollection, File entityTypeWeightFile,
			File extractorEntityTypeWeight) {
		KieServices ks = KieServices.Factory.get();
		KieRepository kr = ks.getRepository();
		KieFileSystem kfs = ks.newKieFileSystem();
		KieResources kieResources = ks.getResources();

		for (File ruleFile : ruleFileCollection) {

			// Create new KieFileSystem resource using the current rule file and
			// add it to "src/main/resources"

			Resource resource = kieResources.newFileSystemResource(ruleFile);
			kfs.write("src/main/resources/" + ruleFile.getName(), resource);
		}

		KieBuilder kb = ks.newKieBuilder(kfs);

		kb.buildAll(); // kieModule is automatically deployed to
						// KieRepository if successfully built.
		if (kb.getResults().hasMessages(Level.ERROR)) {
			throw new RuntimeException("Build Errors:\n"
					+ kb.getResults().toString());
		}

		KieContainer kContainer = ks.newKieContainer(kr.getDefaultReleaseId());

		return kContainer.newKieSession();
	}

	/**
	 * Validates and expands path-properties that match specific path prefixes.
	 * Mostly used here to find the full path of unpackaged items from the
	 * mirve-config project. Review the
	 * {@link com.orbis.utils.io.properties.PathPropertiesUtil#validateAndExpandPaths}
	 * method for specific details.
	 * 
	 * @param propertiesFilePath
	 *            The path to the property file whose paths are to be expanded.
	 * @return Properties object containing expanded paths.
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ConfigurationException
	 * @throws URISyntaxException
	 */
	public static Properties getExpandedProperties(String propertiesFilePath)
			throws FileNotFoundException, IOException, ConfigurationException,
			URISyntaxException {
		Properties serviceTestProps = new Properties();
		serviceTestProps.load(new FileInputStream(propertiesFilePath));

		Properties expandedProps = PathPropertiesUtil
				.validateAndExpandPaths(serviceTestProps);

		return expandedProps;
	}
	
	public static Properties getExpandedProperties(Properties prop)
			throws FileNotFoundException, IOException, ConfigurationException,
			URISyntaxException {
	
		Properties expandedProps = PathPropertiesUtil
				.validateAndExpandPaths(prop);

		return expandedProps;
	}

	/**
	 * Returns a list of rule files designated in the "drools.rule.files"
	 * section of the property file located at the given path.
	 * 
	 * @param propertyFilePath
	 *            The path to the Mirve property file containing the
	 *            "drools.rule.files" property.
	 * @return A list of File objects which will be used as rule files (drl
	 *         files).
	 * @throws URISyntaxException
	 * @throws IOException
	 * @throws ConfigurationException
	 * @throws FileNotFoundException
	 * @throws Exception
	 */
	/*public static List<File> getRuleFilesFromPropertyFile(
			String propertyFilePath) throws FileNotFoundException,
			ConfigurationException, IOException, URISyntaxException {

		Properties expandedProps = null;

		expandedProps = getExpandedProperties(propertyFilePath);

		return getRuleFilesFromProperty(expandedProps);
	}*/

	public static List<File> getRuleFilesFromProperty(Properties prop) {
		String propertyValue = prop
				.getProperty(RuleEngineProp.RULE_FILES_PROPERTY_NAME);

		if (propertyValue == null) {
			throw new RuntimeException("Need property value: "
					+ RuleEngineProp.RULE_FILES_PROPERTY_NAME);
		}

		List<File> ruleFiles = new ArrayList<>();

		String[] values = propertyValue.split(",");
		for (String value : values) {
			File file = new File(value.trim());
			addRules(ruleFiles, file);
		}

		return ruleFiles;
	}
	
	private static void addRules(List<File> ruleFileList, File file){
		if (file.isDirectory()) {
			// handle if value is directory
			File[] files = file.listFiles();
			for (File f : files) {
				if(!f.isDirectory()){ // Do not recursively search
					ruleFileList.add(f);
				}
			}
		} else {
			// handle if value is a file
			ruleFileList.add(file);
		}
	}

	public static Collection<ExtractorEntityTypeWeight> loadWeightsFromFile(
			String weightXMLFile) throws ConfigurationException {
		try{
			return (Collection<ExtractorEntityTypeWeight>) loadWeights(new InputSource(new FileInputStream(weightXMLFile)), ExtractorEntityTypeWeight.class);
		}catch (IOException e) {
			LOG.error("XML File didn't exist");
			ConfigurationException confException= new ConfigurationException("XML File didn't exist");
			confException.setRootCause(e);
			throw confException;
		}
		
	}
	
	
	public static Collection<ExtractorEntityTypeWeight> loadWeightsFromFile(
			File weightXMLFile) throws ConfigurationException {
		try{
			return (Collection<ExtractorEntityTypeWeight>) loadWeights(new InputSource(new FileInputStream(weightXMLFile)), ExtractorEntityTypeWeight.class);
		}catch (IOException e) {
			LOG.error("XML File didn't exist");
			ConfigurationException confException= new ConfigurationException("XML File didn't exist");
			confException.setRootCause(e);
			throw confException;
		}
	}
	

	public static Collection<EntityTypeWeight> loadEntityWeightsFromFile(
			File weightXMLFile) throws ConfigurationException {
		try{
			return (Collection<EntityTypeWeight>) loadWeights(new InputSource(new FileInputStream(weightXMLFile)), EntityTypeWeight.class);
		}catch (IOException e) {
			LOG.error("XML File didn't exist");
			ConfigurationException confException= new ConfigurationException("XML File didn't exist");
			confException.setRootCause(e);
			throw confException;
		}
	}
	
	public static Collection<EntityTypeWeight> loadEntityWeightsFromPropertyFile(
			String weightXMLFile) throws ConfigurationException {
		try{
			return (Collection<EntityTypeWeight>) loadWeights(new InputSource(new FileInputStream(weightXMLFile)), EntityTypeWeight.class);
		}catch (IOException e) {
			LOG.error("XML File didn't exist");
			ConfigurationException confException= new ConfigurationException("XML File didn't exist");
			confException.setRootCause(e);
			throw confException;
		}
	}

	public static Collection<? extends ExtractorEntityTypeWeight> loadExtractorEntityTypeWeightFromProperty(
			Properties prop) throws ConfigurationException {
		String propertyValue_EXTRACTOR_ENTITY_TYPE_WEIGHT = prop
				.getProperty(RuleEngineProp.EXTRACTOR_ENTITY_TYPE_WEIGHT);
		if (!"".equals(propertyValue_EXTRACTOR_ENTITY_TYPE_WEIGHT) && propertyValue_EXTRACTOR_ENTITY_TYPE_WEIGHT != null){
			return loadWeightsFromFile(propertyValue_EXTRACTOR_ENTITY_TYPE_WEIGHT);
		}
		return new ArrayList<>();
	}
	
	public static Collection<? extends EntityTypeWeight> loadEntityTypeWeightFromProperty(
			Properties prop) throws ConfigurationException {
		String propertyValue_ENTITY_TYPE_WEIGHT = prop
				.getProperty(RuleEngineProp.ENTITY_TYPE_WEIGHT);
		if (!"".equals(propertyValue_ENTITY_TYPE_WEIGHT) && propertyValue_ENTITY_TYPE_WEIGHT != null){
			return loadEntityWeightsFromPropertyFile(propertyValue_ENTITY_TYPE_WEIGHT);
		}
		return new ArrayList<>();
	}
	
	/**
	 * Returns a {@link Collection} of {@code type} from the input string..
	 * @param weightXML A string with the content to unmarshal into a {@link Collection}.
	 * @param type the type of object to be parsed. 
	 * @return a {@link Collection} of {@code type}. 
	 * @throws ConfigurationException if there was an error in configuration or parsing of the source.
	 */
	public static Collection<? extends EntityTypeWeight> loadWeightsFromString(
			String weightXML, Class<? extends EntityTypeWeight> type) throws ConfigurationException {
		return loadWeights(new InputSource(new StringReader(weightXML)), type);
	}

	/**
	 * Returns a {@link Collection} of {@code type} from the input {@code source}.
	 * @param source an {@link InputSource} of the content to unmarshal into a {@link Collection}.
	 * @param type the type of object to be parsed.
	 * @return a {@link Collection} of {@code type}. 
	 * @throws ConfigurationException if there was an error in configuration or parsing of the source.
	 */
	public static Collection<? extends EntityTypeWeight> loadWeights(
			InputSource source, Class<? extends EntityTypeWeight> type) throws ConfigurationException {
		Collection<? extends EntityTypeWeight> typeWeights = new ArrayList<>();

		// Default if not ExtractorEntityTypeWeight, including if null
		XMLFactMapper weightMapper = (type == ExtractorEntityTypeWeight.class) ? 
				new ExtractorEntityTypeWeightMapper() : new EntityTypeWeightMapper();

		DocumentBuilderFactory builderFactory = DocumentBuilderFactory
				.newInstance();

		DocumentBuilder builder;
		try {
			builder = builderFactory.newDocumentBuilder();

			Document document;

			document = builder.parse(source);

			typeWeights.addAll(weightMapper.readXML(document));

		} catch (ParserConfigurationException e) {
			LOG.error("ParserConfigurationException");
			ConfigurationException confException= new ConfigurationException("ParserConfigurationException");
			confException.setRootCause(e);
			throw confException;
		} catch (SAXException e) {
			LOG.error("Error parsing xml document");
			ConfigurationException confException= new ConfigurationException("Error parsing xml document");
			confException.setRootCause(e);
			throw confException;
		} catch (IOException e) {
			LOG.error("Error loading from source");
			ConfigurationException confException= new ConfigurationException("Error loading from source");
			confException.setRootCause(e);
			throw confException;
		} catch (ConfigurationException e) {
			throw e;
		}
		return typeWeights;
	}
}

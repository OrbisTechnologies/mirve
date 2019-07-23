package com.orbis.mirve.engine;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;

import javax.naming.ConfigurationException;

import org.kie.api.runtime.KieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.orbis.mirve.util.EntityTypeWeight;
import com.orbis.mirve.util.ExtractorEntityTypeWeight;
import com.orbis.mirve.util.RuleEngineHelper;
import com.orbis.utils.etc.PathPropertiesUtil;


/**
 * This class allows users to run the Mirve Rule Engine.
 * 
 * First, the Mirve Rule Engine must be initialized using the 
 * {@link #initialize()} method. 
 * 
 * Next, the user must add any rule files and facts that
 * should be used for adjudication. 
 * 
 * Once all necessary rule files and facts have been added,
 *  the {@link #createKieSession()} method should be called
 * to create the Drools KieSession that the Mirve Rule Engine
 * will use.
 * 
 * Finally, the {@link #run()} method can be called to start
 * the adjudication process. Updated facts will be returned.
 * 
 * 
 * @author jbaker
 *
 */
public class MirveRuleEngine {

	private final static Logger LOG = LoggerFactory
			.getLogger(MirveRuleEngine.class);
	private boolean initialized = false;
	private boolean clearFactsAfterRun = true;
	private Collection<File> ruleFileCollection;
	private Collection<Object> factCollection;
	private Collection<ExtractorEntityTypeWeight> extractorEntityTypeWeights;
	private Collection<EntityTypeWeight> entityTypeWeights;
	private KieSession kieSession = null;
	
	/**
	 * Initializes the rule engine. 
	 * Creates empty collections for rule files and facts that
	 * will be used when running the rule engine. 
	 * 
	 * After this method is called, you should then insert any
	 * rule files and facts, then run the {@link #createKieSession()} method.
	 * 
	 * Once these steps are done, the {@link #run()} method can be called.
	 */
	public void initialize(){

		ruleFileCollection = new ArrayList<>();
		factCollection = new ArrayList<>();
		extractorEntityTypeWeights = new ArrayList<>();
		entityTypeWeights = new ArrayList<>();
		initialized = true;

	}

	/**
	 * Initializes the rule engine. 
	 * Creates empty collections for rule files and facts that
	 * will be used when running the rule engine. Will then try
	 * to load rule files if they are provided in the given
	 * property file.
	 * 
	 * After this method is called, you should then insert any
	 * additional rule files and facts, then run the 
	 * {@link #createKieSession()} method.
	 * 
	 * Once these steps are done, the {@link #run()} method can be called.
	 * 
	 * @param propertyFilePath The path of the Mirve property file
	 * containing the "drools.rule.files" property.
	 * @throws URISyntaxException 
	 * @throws FileNotFoundException 
	 * @throws ConfigurationException 
	 */
	public void initialize(Properties prop) throws ConfigurationException, FileNotFoundException, URISyntaxException{

		ruleFileCollection = new ArrayList<>();
		factCollection = new ArrayList<>();
		Properties expandedProps = PathPropertiesUtil.validateAndExpandPaths(prop);
		//Attempt to get rule files from the property file at the given path

		ruleFileCollection.addAll(
					RuleEngineHelper.getRuleFilesFromProperty(expandedProps));
		extractorEntityTypeWeights.addAll(RuleEngineHelper.loadExtractorEntityTypeWeightFromProperty(expandedProps));
		entityTypeWeights.addAll(RuleEngineHelper.loadEntityTypeWeightFromProperty(expandedProps));
		
		initialized = true;
	}
	
	
	
	/**
	 * Initializes the rule engine. 
	 * Creates empty collections for rule files and facts that
	 * will be used when running the rule engine. Will then try
	 * to load rule files if they are provided in the given
	 * property file.
	 * 
	 * After this method is called, you should then insert any
	 * additional rule files and facts, then run the 
	 * {@link #createKieSession()} method.
	 * 
	 * Once these steps are done, the {@link #run()} method can be called.
	 * 
	 * @param propertyFilePath The path of the Mirve property file
	 * containing the "drools.rule.files" property.
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 * @throws URISyntaxException 
	 * @throws ConfigurationException 
	*/
	public void initialize(String propertyFilePath) throws FileNotFoundException, IOException, ConfigurationException, URISyntaxException{

		Properties prop = new Properties();
		prop.load(new FileInputStream(propertyFilePath));
		
		
			initialize(prop);
	
	} 

	public void insertRuleFile(File ruleFile) {
		ruleFileCollection.add(ruleFile);
	}

	public void insertRuleFiles(Collection<File> ruleFiles) {
		ruleFileCollection.addAll(ruleFiles);
	}
	
	public void clearAllRuleFiles(){
		ruleFileCollection.clear();
	}

	public void insertFact(Object fact) {
		factCollection.add(fact);
	}

	public void insertFacts(Collection<Object> facts) {
		factCollection.addAll(facts);
	}
	
	public void clearAllFacts(){
		factCollection.clear();
	}

	
	/**
	 * Creates the KieSession to be used in the rule engine.
	 * 
	 * This session is based on the rule files provided to the class
	 * through a property file (loaded in the {@link #initialize(String)}
	 * method) and/or added directly with the {@link #insertRuleFile(File)}/
	 * {@link #insertRuleFiles(Collection)} methods.
	 * 
	 * @return The KieSession that was created for this rule engine.
	 */
	public KieSession createKieSession(){
		
		if (initialized == false){
			throw new RuntimeException("Must run the initialize() method and insert rule files and facts "
					+ "before the KieSession can be created.");
		}
		
		// Create KieSession
		kieSession = RuleEngineHelper.getKieSessionWithRuleFiles(ruleFileCollection);
		return kieSession;
	}
	
	/**
	 * Runs the rule engine given the current collection of facts and rule
	 * files.
	 * 
	 * Before this method can be run, the {@link #initialize()} function must be run
	 * first. Then any rule files, weight files and facts must be added. Then the 
	 * {@link #createKieSession()} must be run. Otherwise, this method will not work
	 * as expected.
	 * 
	 * @return Returns the updated facts after the rule engine is run.
	 */
	public Collection<Object> run() {
		
		// Create KieSession if not already created.
		if (kieSession == null){
			createKieSession();
		}
		
		
		// Insert into KieSession
		for (Object fact : factCollection) {
			kieSession.insert(fact);
		}

		for (Object weight : extractorEntityTypeWeights) {
			kieSession.insert(weight);
		}

		for (Object weight : entityTypeWeights) {
			kieSession.insert(weight);
		}

		
		
		// Fire Rules
		kieSession.fireAllRules();

		// Check if we should be clearing the current collection of facts
		// after each run. (This default behavior can be changed in the
		// properties file)
		if (clearFactsAfterRun) {

			// Use this to return facts since we will be clearing the current
			// fact
			// collection
			Collection<Object> factsToReturn = new ArrayList<>();
			factsToReturn.addAll(factCollection);

			// Clear the current collection of facts
			factCollection.clear();

			return factsToReturn;
		}

		return factCollection;
	}

}

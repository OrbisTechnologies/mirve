/*
 * Copyright 2017 by Orbis Technologies, Inc.  All rights reserved.
 */
package com.orbis.mirve.rule;

import com.orbis.mirve.core.ClassHierarchy;
import com.orbis.mirve.core.EntityMention;
import com.orbis.mirve.core.Event;
import com.orbis.mirve.core.ExtractionMentionItem;
import com.orbis.mirve.fact.mapper.EntityTypeWeightMapper;
import com.orbis.mirve.fact.mapper.ExtractorEntityTypeWeightMapper;
import com.orbis.mirve.util.EntityTypeWeight;
import com.orbis.mirve.util.ExtractorEntityTypeWeight;
import com.orbis.mirve.util.RuleEngineHelper;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentLinkedQueue;
import javax.naming.ConfigurationException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 *
 * @author kbrown-walker
 */
public class Util {
	private static final Logger LOGGER = LoggerFactory.getLogger(Util.class);
	
	public static void run(Collection<ExtractionMentionItem> factCollection, KieSession kieSession, 
			Collection<ExtractorEntityTypeWeight> extractorEntityTypeWeights, Collection<EntityTypeWeight> entityTypeWeights) {

		// Insert entities into KieSession
		for (Object fact : factCollection) {
			kieSession.insert(fact);
		}

		// Insert weights into KieSession
		for (Object weight : extractorEntityTypeWeights) {
			kieSession.insert(weight);
		}

		for (Object weight : entityTypeWeights) {
			kieSession.insert(weight);
		}

		// Fire Rules
		kieSession.fireAllRules();
		Collection<ExtractionMentionItem> filteredMentions = new ConcurrentLinkedQueue<>();
		for (ExtractionMentionItem fact : factCollection) {

			if (fact.isAdjudicated()) {

				filteredMentions.add(fact);
			}
		}

		// Clear facts from session
		for (FactHandle fact : kieSession.getFactHandles()) {
			kieSession.delete(fact);
		}

		factCollection.removeAll(filteredMentions);
	}

	public static KieSession createKieSession(Properties prop) {

		Collection<File> ruleFileCollection = RuleEngineHelper.getRuleFilesFromProperty(prop);

		// Create KieSession
		KieSession kieSession = RuleEngineHelper.getKieSessionWithRuleFiles(ruleFileCollection);
		return kieSession;
	}

	public static void loadWeightsFromFile(File weightXMLFile, Collection<ExtractorEntityTypeWeight> extractorEntityTypeWeights, 
			Collection<EntityTypeWeight> entityTypeWeights) throws ConfigurationException {
		ExtractorEntityTypeWeightMapper extractorEntityTypeWeightMapper = new ExtractorEntityTypeWeightMapper();
		EntityTypeWeightMapper entityTypeWeightMapper = new EntityTypeWeightMapper();

		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();

		DocumentBuilder builder;
		try {
			builder = builderFactory.newDocumentBuilder();

			Document document;

			document = builder.parse(new FileInputStream(weightXMLFile));

			extractorEntityTypeWeights.addAll(extractorEntityTypeWeightMapper.readXML(document));
			entityTypeWeights.addAll(entityTypeWeightMapper.readXML(document));

		} catch (ParserConfigurationException ex) {
			LOGGER.error("Parser Configuration Exception occured while parsing weights", ex);
		} catch (SAXException ex) {
			LOGGER.error("SAX Exception occured while parsing weights", ex);
		} catch (IOException ex) {
			LOGGER.error("IO Exception occured while loading XML", ex);
		}
	}
	
	public static void addGeneratedMentionIds(Collection<EntityMention> mentions) {
		for (EntityMention mention : mentions) {
			Map<String, Object> att = new LinkedHashMap<>();
			att.put("enrichment_mention_id", mention.getExtractionEngine() + "_" + mention.hashCode());
			mention.setExtractionAttributes(att);
		}
	}

	public static ExtractorEntityTypeWeight getWeight(String extractorName, ClassHierarchy extractorType, 
			Collection<ExtractorEntityTypeWeight> extractorEntityTypeWeights) {
		for (ExtractorEntityTypeWeight weight : extractorEntityTypeWeights) {
			if (weight.getExtractionEngine().equalsIgnoreCase(extractorName)) {
				if (weight.getEntityType().equalsIgnoreCase(extractorType.toString())) {
					return weight;
				} else {
					ClassHierarchy fromFile = new ClassHierarchy(weight.getEntityType());
					if (extractorType.isSubClassOf(fromFile) || extractorType.equals(fromFile)) {
						return weight;
					}
				}
			}
		}
		return null;
	}

	public static EntityTypeWeight getWeight(ClassHierarchy extractorType, Collection<EntityTypeWeight> entityTypeWeights) {
		for (EntityTypeWeight weight : entityTypeWeights) {
			if (weight.getEntityType().equalsIgnoreCase(extractorType.toString())) {
				return weight;
			} else {
				ClassHierarchy fromFile = new ClassHierarchy(weight.getEntityType());
				if (extractorType.isSubClassOf(fromFile) || extractorType.equals(fromFile)) {
					return weight;
				}
			}
		}
		return null;
	}

	public static boolean containsWinner(Collection<ExtractionMentionItem> adjEntities, EntityMention winner) {
		for (ExtractionMentionItem found : adjEntities) {
			if (winner.compareTo(found) == 0)
				return true;
		}
		return false;
	}

	public static EntityMention extractorTypeWeightWinner(EntityMention e1, EntityMention e2, 
			Collection<ExtractorEntityTypeWeight> extractorEntityTypeWeights) {
		ExtractorEntityTypeWeight weight1 = getWeight(e1.getExtractionEngine(), e1.getType(), extractorEntityTypeWeights);
		ExtractorEntityTypeWeight weight2 = getWeight(e2.getExtractionEngine(), e2.getType(), extractorEntityTypeWeights);
		if (weight1 != null && weight2 != null){
			if (weight1.getWeight() > weight2.getWeight()){
				return e1;
			}else if (weight2.getWeight() > weight1.getWeight()){
				return e2;
			}
		}
		return null;
	}
	
	public static EntityMention typeWeightWinner(EntityMention e1, EntityMention e2, 
			Collection<EntityTypeWeight> entityTypeWeights) {
		EntityTypeWeight weight1 = getWeight(e1.getType(), entityTypeWeights);
		EntityTypeWeight weight2 = getWeight(e2.getType(), entityTypeWeights);
		if (weight1 != null && weight2 != null){
			if (weight1.getWeight() > weight2.getWeight()){
				return e1;
			}else if (weight2.getWeight() > weight1.getWeight()){
				return e2;
			}
		}
		return null;
	}
	
	public static void compareEntityMentions(EntityMention entityMention1, EntityMention entityMention2){
		assertEquals("Comparing text", entityMention1.getText(), entityMention2.getText());
		assertEquals("Comparing normalized text", entityMention1.getNormalizedText(), entityMention2.getNormalizedText());
		assertEquals("Comparing extractor", entityMention1.getExtractionEngine(), entityMention2.getExtractionEngine());
		assertEquals("Comparing type", entityMention1.getType(), entityMention2.getType());
		assertEquals("Comparing start offset", entityMention1.getItemOffset().getStartOffset(), entityMention2.getItemOffset().getStartOffset());
		assertEquals("Comparing end offset", entityMention1.getItemOffset().getEndOffset(), entityMention2.getItemOffset().getEndOffset());
		for(Map.Entry<String, Object> entry : entityMention1.getExtractionAttributes().entrySet()){
			assertTrue(entityMention2.getExtractionAttributes().containsKey(entry.getKey()));
			assertTrue(entityMention2.getExtractionAttributes().get(entry.getKey()).equals(entry.getValue()));
		}
		for(Map.Entry<String, Object> entry : entityMention2.getExtractionAttributes().entrySet()){
			assertTrue(entityMention1.getExtractionAttributes().containsKey(entry.getKey()));
			assertTrue(entityMention1.getExtractionAttributes().get(entry.getKey()).equals(entry.getValue()));
		}
	}
		
	public static void compareEvents(Event event1, Event event2){
		assertEquals("Comparing text", event1.getText(), event2.getText());
		assertEquals("Comparing extractor", event1.getExtractionEngine(), event2.getExtractionEngine());
		assertEquals("Comparing type", event1.getType(), event2.getType());
		assertEquals("Comparing start offset", event1.getItemOffset().getStartOffset(), event2.getItemOffset().getStartOffset());
		assertEquals("Comparing end offset", event1.getItemOffset().getEndOffset(), event2.getItemOffset().getEndOffset());
		assertEquals("Comparing trigger start offset", event1.getTriggerPredicateOffset().getStartOffset(), event2.getTriggerPredicateOffset().getStartOffset());
		assertEquals("Comparing trigger end offset", event1.getTriggerPredicateOffset().getEndOffset(), event2.getTriggerPredicateOffset().getEndOffset());
		for(Map.Entry<String, Object> entry : event1.getExtractionAttributes().entrySet()){
			assertTrue(event2.getExtractionAttributes().containsKey(entry.getKey()));
			assertTrue(event2.getExtractionAttributes().get(entry.getKey()).equals(entry.getValue()));
		}
		for(Map.Entry<String, Object> entry : event2.getExtractionAttributes().entrySet()){
			assertTrue(event1.getExtractionAttributes().containsKey(entry.getKey()));
			assertTrue(event1.getExtractionAttributes().get(entry.getKey()).equals(entry.getValue()));
		}
	}
}

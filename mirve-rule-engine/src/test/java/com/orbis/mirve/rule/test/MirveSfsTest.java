/*
 * Copyright 2017 by Orbis Technologies, Inc.  All rights reserved.
 */
package com.orbis.mirve.rule.test;

import com.orbis.mirve.core.EntityMention;
import com.orbis.mirve.core.Event;
import com.orbis.mirve.core.ExtractionMentionItem;
import com.orbis.mirve.nlp.ExtractionResults;
import com.orbis.mirve.nlp.mapper.ExtractionResultsMapper;
import com.orbis.mirve.rule.Util;
import com.orbis.mirve.util.EntityTypeWeight;
import com.orbis.mirve.util.ExtractorEntityTypeWeight;
import com.orbis.utils.etc.PathPropertiesUtil;
import com.orbis.utils.io.FileUtil;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;
import javax.naming.ConfigurationException;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import org.junit.BeforeClass;
import org.junit.Test;
import org.kie.api.runtime.KieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author kbrown-walker
 */
public class MirveSfsTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(MirveSfsTest.class);
	private static KieSession kieSession;
	private static Collection<ExtractorEntityTypeWeight> extractorEntityTypeWeights = new ArrayList<>();
	private static Collection<EntityTypeWeight> entityTypeWeights = new ArrayList<>();
	private static Properties expandedProps = new Properties();

	@BeforeClass
	public static void init() throws IOException, ConfigurationException, URISyntaxException {
		Properties props = new Properties();
		props.load(new StringReader(FileUtil.loadResourceTextFile("sfs/mirve.properties")));
		expandedProps = PathPropertiesUtil.validateAndExpandPaths(props);

		Util.loadWeightsFromFile(new File(expandedProps.getProperty("extractor.entityType.weights.path")), extractorEntityTypeWeights, entityTypeWeights);
		Util.loadWeightsFromFile(new File(expandedProps.getProperty("entityType.weights.path")), extractorEntityTypeWeights, entityTypeWeights);

		kieSession = Util.createKieSession(expandedProps);
	}
	
	@Test
	public void testMatchingSuperClassMatchingSuperOffset(){
		String inputFile = "sfs/testdata/serialized1.json";
		String outputFile = "sfs/testdata/serialized2.json";
		ExtractionResultsMapper mapper = new ExtractionResultsMapper();
		ExtractionResults extractionResults1;
		ExtractionResults expected;
		try {
			extractionResults1 = mapper.deserialize(FileUtil.readResourceTextFile(inputFile).trim());
		} catch (IOException ex) {
			LOGGER.error("Error occured while reading resource file [{}]", inputFile, ex);
			Assert.fail();
			return; // For compiler 
		}
		try {
			expected = mapper.deserialize(FileUtil.readResourceTextFile(outputFile).trim());
		} catch (IOException ex) {
			LOGGER.error("Error occured while reading resource file [{}]", outputFile, ex);
			Assert.fail();
			return; //for compiler
		}
		List<ExtractionMentionItem> factCollection = new ArrayList<>();
		factCollection.addAll(extractionResults1.getEntities());
		factCollection.addAll(extractionResults1.getEvents());
		Util.run(factCollection, kieSession, extractorEntityTypeWeights, entityTypeWeights);
		ExtractionResults results = createExtractionResults(factCollection);
		assertEquals("Comparing sizes of expected entity collection with results collection", expected.getEntities().size(), results.getEntities().size());
		for(int i = 0; i < expected.getEntities().size(); ++i){
			Util.compareEntityMentions(expected.getEntities().get(i), results.getEntities().get(i));
		}
	}
	
	private static ExtractionResults createExtractionResults(Collection<ExtractionMentionItem> facts){
		ExtractionResults extractionResults = new ExtractionResults();
		extractionResults.setEntities(new ArrayList<EntityMention>());
		extractionResults.setEvents(new ArrayList<Event>());
		for(ExtractionMentionItem fact : facts){
			if(fact instanceof EntityMention){
				extractionResults.getEntities().add((EntityMention)fact);
			}
		}
		for(ExtractionMentionItem fact : facts){
			if(fact instanceof Event){
				extractionResults.getEvents().add((Event)fact);
			}
		}
		return extractionResults;
	}
}

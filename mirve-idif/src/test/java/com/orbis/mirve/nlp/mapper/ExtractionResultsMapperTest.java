/*
 * Copyright 2017 by Orbis Technologies, Inc.  All rights reserved.
 */
package com.orbis.mirve.nlp.mapper;

import com.orbis.mirve.core.ClassHierarchy;
import com.orbis.mirve.core.EntityMention;
import com.orbis.mirve.core.Event;
import com.orbis.mirve.core.Offset;
import com.orbis.mirve.nlp.ExtractionResults;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.json.JSONException;
import org.junit.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.skyscreamer.jsonassert.JSONAssert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * This class validates the {@link ExtractionResultsMapper} class serialized and deserialize.
 * @author kbrown-walker
 */
public class ExtractionResultsMapperTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(ExtractionResultsMapperTest.class);
	
	private static final ExtractionResults EXTRACTION_RESULTS = new ExtractionResults();
	
	@Before
	public void setup(){
		EntityMention cta1 = new EntityMention("orbisnlp", new ClassHierarchy("entity.place"), new Offset(0, 10),
				"China", "China", new HashMap<String, Object>(),1);
		EntityMention netowl1 = new EntityMention("netowl", new ClassHierarchy("entity.place.country"),
				new Offset(0, 10), "China", "China", new HashMap<String, Object>(),2);
		EntityMention geotagger1 = new EntityMention("geotagger", new ClassHierarchy("entity.place"), new Offset(0, 10),
				"China", "China", new HashMap<String, Object>(),3);
		cta1.getExtractionAttributes().put("sourceUri", "www.google.com");
		ArrayList<EntityMention> entityList = new ArrayList<>();
		ArrayList<Event> eventList = new ArrayList<>();
		entityList.add(cta1);
		entityList.add(netowl1);
		entityList.add(geotagger1);
		Event orbisnlp1 = new Event("orbisnlp", new ClassHierarchy("Event:TransferOwnership"), new Offset(0, 10), "urges member states",
			cta1, netowl1, "provided", new Offset(3,5),1);
		eventList.add(orbisnlp1);
		EXTRACTION_RESULTS.setEntities(entityList);
		EXTRACTION_RESULTS.setEvents(eventList);
	}
	
	@Test
	public void testSerialize(){
		ExtractionResultsMapper mapper = new ExtractionResultsMapper();
		try {
			
			String result = mapper.serialize(EXTRACTION_RESULTS).trim();
			String expected = 	FileUtils.readFileToString(new File("src/test/resources/serialized1.json"), StandardCharsets.UTF_8);
			
			//String expected = FileUtil.readResourceTextFile("serialized1.json").trim();
			JSONAssert.assertEquals(expected, result, false);
		} catch (IOException ex) {
			LOGGER.error("Error occurred while serializing JSON", ex);
			Assert.fail();
		} catch (JSONException ex) {
			LOGGER.error("Error occurred while comparing JSON", ex);
			Assert.fail();
		}
	}
	
	@Test
	public void testDeserialize() {
		ExtractionResultsMapper mapper = new ExtractionResultsMapper();
		try {
			String input =  FileUtils.readFileToString(new File("src/test/resources/serialized1.json"), StandardCharsets.UTF_8);
			
			//String input = FileUtil.readResourceTextFile("serialized1.json").trim();
			ExtractionResults result = mapper.deserialize(input);
			for(int i = 0; i < EXTRACTION_RESULTS.getEntities().size(); ++i){
				compareEntityMentions(result.getEntities().get(i), EXTRACTION_RESULTS.getEntities().get(i));
			}
			for(int i = 0; i < EXTRACTION_RESULTS.getEvents().size(); ++i){
				compareEvents(result.getEvents().get(i), EXTRACTION_RESULTS.getEvents().get(i));
			}
		} catch (IOException ex) {
			LOGGER.error("Error occurred while deserializing JSON", ex);
			Assert.fail();
		}
	}
	
	@Test
	public void testIntegrity(){
		try {
			String expected =  FileUtils.readFileToString(new File("src/test/resources/serialized2.json"), StandardCharsets.UTF_8);
			//String expected = FilenameUtils.normalize("serialized2.json");
			//String expected = FileUtil.readResourceTextFile("serialized2.json").trim();
			ExtractionResultsMapper mapper = new ExtractionResultsMapper();
			String result = mapper.serialize(mapper.deserialize(expected));
			JSONAssert.assertEquals(expected, result, false);
		}catch (IOException ex) {
			LOGGER.error("Error occurred while deserializing/serializing JSON", ex);
			Assert.fail();
		} catch (JSONException ex) {
			LOGGER.error("Error occurred while comparing JSON", ex);
			Assert.fail();
		}
		try {
			ExtractionResultsMapper mapper = new ExtractionResultsMapper();
			ExtractionResults result = mapper.deserialize(mapper.serialize(EXTRACTION_RESULTS));
			for(int i = 0; i < EXTRACTION_RESULTS.getEntities().size(); ++i){
				compareEntityMentions(result.getEntities().get(i), EXTRACTION_RESULTS.getEntities().get(i));
			}
			for(int i = 0; i < EXTRACTION_RESULTS.getEvents().size(); ++i){
				compareEvents(result.getEvents().get(i), EXTRACTION_RESULTS.getEvents().get(i));
			}
		} catch (IOException ex) {
			LOGGER.error("Error occurred while deserializing/serializing JSON", ex);
			Assert.fail();
		}
	}
	
	private void compareEntityMentions(EntityMention entityMention1, EntityMention entityMention2){
		assertEquals("Comparing text", entityMention1.getText(), entityMention2.getText());
		assertEquals("Comparing normalized text", entityMention1.getNormalizedText(), entityMention2.getNormalizedText());
		assertEquals("Comparing extractor", entityMention1.getExtractionEngine(), entityMention2.getExtractionEngine());
		assertEquals("Comparing type", entityMention1.getType(), entityMention2.getType());
		assertEquals("Comparing start offset", entityMention1.getItemOffset().getStartOffset(), entityMention2.getItemOffset().getStartOffset());
		assertEquals("Comparing end offset", entityMention1.getItemOffset().getEndOffset(), entityMention2.getItemOffset().getEndOffset());
		for(Entry<String, Object> entry : entityMention1.getExtractionAttributes().entrySet()){
			assertTrue(entityMention2.getExtractionAttributes().containsKey(entry.getKey()));
			assertTrue(entityMention2.getExtractionAttributes().get(entry.getKey()).equals(entry.getValue()));
		}
		for(Entry<String, Object> entry : entityMention2.getExtractionAttributes().entrySet()){
			assertTrue(entityMention1.getExtractionAttributes().containsKey(entry.getKey()));
			assertTrue(entityMention1.getExtractionAttributes().get(entry.getKey()).equals(entry.getValue()));
		}
	}
		
	private void compareEvents(Event event1, Event event2){
		assertEquals("Comparing text", event1.getText(), event2.getText());
		assertEquals("Comparing extractor", event1.getExtractionEngine(), event2.getExtractionEngine());
		assertEquals("Comparing type", event1.getType(), event2.getType());
		assertEquals("Comparing start offset", event1.getItemOffset().getStartOffset(), event2.getItemOffset().getStartOffset());
		assertEquals("Comparing end offset", event1.getItemOffset().getEndOffset(), event2.getItemOffset().getEndOffset());
		assertEquals("Comparing trigger start offset", event1.getTriggerPredicateOffset().getStartOffset(), event2.getTriggerPredicateOffset().getStartOffset());
		assertEquals("Comparing trigger end offset", event1.getTriggerPredicateOffset().getEndOffset(), event2.getTriggerPredicateOffset().getEndOffset());
		for(Entry<String, Object> entry : event1.getExtractionAttributes().entrySet()){
			assertTrue(event2.getExtractionAttributes().containsKey(entry.getKey()));
			assertTrue(event2.getExtractionAttributes().get(entry.getKey()).equals(entry.getValue()));
		}
		for(Entry<String, Object> entry : event2.getExtractionAttributes().entrySet()){
			assertTrue(event1.getExtractionAttributes().containsKey(entry.getKey()));
			assertTrue(event1.getExtractionAttributes().get(entry.getKey()).equals(entry.getValue()));
		}
	}
}

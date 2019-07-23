package com.orbis.mirve.rule.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import javax.naming.ConfigurationException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.orbis.mirve.fact.mapper.EntityTypeWeightMapper;
import com.orbis.mirve.fact.mapper.ExtractorEntityTypeWeightMapper;
import com.orbis.mirve.util.EntityTypeWeight;
import com.orbis.mirve.util.ExtractorEntityTypeWeight;


public class XMLMapperTest {
	private static ExtractorEntityTypeWeightMapper extractorEntityTypeWeightMapper;
	private static EntityTypeWeightMapper entityTypeWeightMapper;

	@BeforeClass
	public static void setup() {
		extractorEntityTypeWeightMapper = new ExtractorEntityTypeWeightMapper();
		entityTypeWeightMapper = new EntityTypeWeightMapper();
	}

	@Test
	public void testWithUnnecessaryEntries() throws ParserConfigurationException, FileNotFoundException, SAXException, IOException, ConfigurationException {

		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();

		DocumentBuilder builder = builderFactory.newDocumentBuilder();

		// Read file for weights
		File xmlFile = new File("src/test/resources/xmlMapperTest/fact-list1.xml");
		Document document = builder.parse(new FileInputStream(xmlFile));
		Collection<ExtractorEntityTypeWeight> weightsWithExtractor = extractorEntityTypeWeightMapper.readXML(document);
		Collection<EntityTypeWeight> weightsWithNoExtractor = entityTypeWeightMapper.readXML(document);
		
		// Expected results
		Collection<ExtractorEntityTypeWeight> expectedWeightsWithExtractor = new ArrayList<ExtractorEntityTypeWeight>();
		expectedWeightsWithExtractor.add(new ExtractorEntityTypeWeight("location", "extractorA", 3));
		expectedWeightsWithExtractor.add(new ExtractorEntityTypeWeight("person", "extractorB", 5));
		expectedWeightsWithExtractor.add(new ExtractorEntityTypeWeight("organization", "extractorC", 9));
		
		Collection<EntityTypeWeight> expectedWeightsWithNoExtractor = new ArrayList<EntityTypeWeight>();
		expectedWeightsWithNoExtractor.add(new EntityTypeWeight("person", 10));
		expectedWeightsWithNoExtractor.add(new EntityTypeWeight("location", 9));
		expectedWeightsWithNoExtractor.add(new EntityTypeWeight("organization", 8));
		
		Assert.assertTrue(weightsWithExtractor.containsAll(expectedWeightsWithExtractor));
		Assert.assertTrue(expectedWeightsWithExtractor.containsAll(weightsWithExtractor));
		
		Assert.assertTrue(weightsWithNoExtractor.containsAll(expectedWeightsWithNoExtractor));
		Assert.assertTrue(expectedWeightsWithNoExtractor.containsAll(weightsWithNoExtractor));
	}
	
	@Test
	public void testWithInvalidWeight() throws ParserConfigurationException, FileNotFoundException, SAXException, IOException, ConfigurationException {

		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();

		DocumentBuilder builder = builderFactory.newDocumentBuilder();

		File xmlFile = new File("src/test/resources/xmlMapperTest/fact-list2.xml");
		Document document = builder.parse(new FileInputStream(xmlFile));
		Collection<ExtractorEntityTypeWeight> weightsWithExtractor = extractorEntityTypeWeightMapper.readXML(document);
		
		// Expected results
		Collection<ExtractorEntityTypeWeight> expectedWeightsWithExtractor = new ArrayList<ExtractorEntityTypeWeight>();
		expectedWeightsWithExtractor.add(new ExtractorEntityTypeWeight("organization", "extractorB", 10));
		
		
		Assert.assertTrue(weightsWithExtractor.containsAll(expectedWeightsWithExtractor));
		Assert.assertTrue(expectedWeightsWithExtractor.containsAll(weightsWithExtractor));
		
	}

}
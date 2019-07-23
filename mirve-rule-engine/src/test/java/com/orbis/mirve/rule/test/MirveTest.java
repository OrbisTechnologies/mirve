package com.orbis.mirve.rule.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.naming.ConfigurationException;

import org.junit.BeforeClass;
import org.junit.Test;
import org.kie.api.runtime.KieSession;

import com.orbis.mirve.core.ClassHierarchy;
import com.orbis.mirve.core.EntityMention;
import com.orbis.mirve.core.ExtractionMentionItem;
import com.orbis.mirve.core.Offset;
import com.orbis.mirve.rule.Util;
import com.orbis.mirve.util.EntityTypeWeight;
import com.orbis.mirve.util.ExtractorEntityTypeWeight;
import com.orbis.utils.etc.PathPropertiesUtil;

import org.junit.Assert;

public class MirveTest {

	private static KieSession kieSession;
	private static Collection<ExtractorEntityTypeWeight> extractorEntityTypeWeights = new ArrayList<>();
	private static Collection<EntityTypeWeight> entityTypeWeights = new ArrayList<>();
	private static Properties expandedProps = new Properties();

	@BeforeClass
	public static void init() throws IOException, ConfigurationException, URISyntaxException {
		Properties props = new Properties();
		props.load(new FileInputStream(new File("src/test/resources/mirveTest/mirve.properties")));
		expandedProps = PathPropertiesUtil.validateAndExpandPaths(props);

		Util.loadWeightsFromFile(new File(expandedProps.getProperty("extractor.entityType.weights.path")), extractorEntityTypeWeights, entityTypeWeights);
		Util.loadWeightsFromFile(new File(expandedProps.getProperty("entityType.weights.path")), extractorEntityTypeWeights, entityTypeWeights);

		kieSession = Util.createKieSession(expandedProps);
	}
	
	
	/////////////////////////////////	Tests	/////////////////////////////////////////
	
	@Test
	public void testInvolvedMentions() {
		EntityMention cta1 = new EntityMention("orbisnlp", new ClassHierarchy("entity.place"), new Offset(0, 10),
				"China");
		EntityMention netowl1 = new EntityMention("netowl", new ClassHierarchy("entity.place.country"),
				new Offset(0, 10), "China");
		EntityMention geotagger1 = new EntityMention("geotagger", new ClassHierarchy("entity.place"), new Offset(0, 10),
				"China");

		List<EntityMention> entities = new ArrayList<>();
		entities.add(cta1);
		entities.add(netowl1);
		entities.add(geotagger1);

		Util.addGeneratedMentionIds(entities);
		List<ExtractionMentionItem> factCollection = new ArrayList<>();
		factCollection.addAll(entities);
		Util.run(factCollection, kieSession, extractorEntityTypeWeights, entityTypeWeights);
		Assert.assertEquals(1, factCollection.size());
		Assert.assertTrue(geotagger1.getExtractionAttributes().containsKey("involved_mentions"));
		Object value = geotagger1.getExtractionAttributes().get("involved_mentions");
		String netowlID = (String) netowl1.getExtractionAttributes().get("enrichment_mention_id");
		String ctaID = (String) cta1.getExtractionAttributes().get("enrichment_mention_id");
		if (value instanceof Set<?>){
			Assert.assertTrue(((Set<?>) value).contains(netowlID));
			Assert.assertTrue(((Set<?>) value).contains(ctaID));
		}else{
			Assert.fail();
		}
	}

	// Matching Offset Tests:
	

	@Test
	public void testMatchingOffsetMoreSpecificType() {
		EntityMention cta1 = new EntityMention("orbisnlp", new ClassHierarchy("entity.person"), new Offset(0, 10),
				"Bob Smith");
		EntityMention netowl1 = new EntityMention("netowl", new ClassHierarchy("entity.person.terrorist"),
				new Offset(0, 10), "Bob Smith");

		List<EntityMention> entities = new ArrayList<>();
		entities.add(cta1);
		entities.add(netowl1);

		Util.addGeneratedMentionIds(entities);
		List<ExtractionMentionItem> factCollection = new ArrayList<>();
		factCollection.addAll(entities);
		Util.run(factCollection, kieSession, extractorEntityTypeWeights, entityTypeWeights);
		Assert.assertEquals(1, factCollection.size());
		Assert.assertTrue(Util.containsWinner(factCollection, netowl1));
	}

	@Test
	public void testMatchingOffsetMatchingSupertype() {
		EntityMention cta1 = new EntityMention("orbisnlp", new ClassHierarchy("entity.person.insurgent"),
				new Offset(0, 10), "Bob Smith");
		EntityMention netowl1 = new EntityMention("netowl", new ClassHierarchy("entity.person.terrorist"),
				new Offset(0, 10), "Bob Smith");

		List<EntityMention> entities = new ArrayList<>();
		entities.add(cta1);
		entities.add(netowl1);

		Util.addGeneratedMentionIds(entities);
		List<ExtractionMentionItem> factCollection = new ArrayList<>();
		factCollection.addAll(entities);
		Util.run(factCollection, kieSession, extractorEntityTypeWeights, entityTypeWeights);
		EntityMention winner = Util.extractorTypeWeightWinner(cta1, netowl1, extractorEntityTypeWeights);
		if (winner != null){
			Assert.assertEquals(1, factCollection.size());
			Assert.assertTrue(Util.containsWinner(factCollection, winner));
		}
	}

	@Test
	public void testMatchingOffsetNonMatchingSupertype() {

		EntityMention cta1 = new EntityMention("orbisnlp", new ClassHierarchy("entity.place"), new Offset(0, 10),
				"Chad");
		EntityMention netowl1 = new EntityMention("netowl", new ClassHierarchy("entity.person"), new Offset(0, 10),
				"Chad");

		List<EntityMention> entities = new ArrayList<>();
		entities.add(cta1);
		entities.add(netowl1);

		Util.addGeneratedMentionIds(entities);

		List<ExtractionMentionItem> factCollection = new ArrayList<>();
		factCollection.addAll(entities);
		Util.run(factCollection, kieSession, extractorEntityTypeWeights, entityTypeWeights);
		EntityMention winner = Util.typeWeightWinner(cta1, netowl1, entityTypeWeights);
		if (winner != null){
			Assert.assertEquals(1, factCollection.size());
			Assert.assertTrue(Util.containsWinner(factCollection, winner));
		}
	}
	
	// Overlapping Offset Tests:
	
	@Test
	public void testContainsOffsetMatchingType() {
		EntityMention cta1 = new EntityMention("orbisnlp", new ClassHierarchy("entity.person"), new Offset(0, 15),
				"Bob Smith");
		EntityMention netowl1 = new EntityMention("netowl", new ClassHierarchy("entity.person"),
				new Offset(0, 10), "Bob Smith");

		List<EntityMention> entities = new ArrayList<>();
		entities.add(cta1);
		entities.add(netowl1);

		Util.addGeneratedMentionIds(entities);

		List<ExtractionMentionItem> factCollection = new ArrayList<>();
		factCollection.addAll(entities);
		Util.run(factCollection, kieSession, extractorEntityTypeWeights, entityTypeWeights);
		Assert.assertEquals(1, factCollection.size());
		Assert.assertTrue(Util.containsWinner(factCollection, cta1));
	}

	@Test
	public void testOverlappingOffsetMoreSpecificType() {
		EntityMention cta1 = new EntityMention("orbisnlp", new ClassHierarchy("entity.person"), new Offset(5, 15),
				"Bob Smith");
		EntityMention netowl1 = new EntityMention("netowl", new ClassHierarchy("entity.person.terrorist"),
				new Offset(0, 10), "Bob Smith");

		List<EntityMention> entities = new ArrayList<>();
		entities.add(cta1);
		entities.add(netowl1);

		Util.addGeneratedMentionIds(entities);

		List<ExtractionMentionItem> factCollection = new ArrayList<>();
		factCollection.addAll(entities);
		Util.run(factCollection, kieSession, extractorEntityTypeWeights, entityTypeWeights);
		Assert.assertEquals(1, factCollection.size());
		Assert.assertTrue(Util.containsWinner(factCollection, netowl1));
	}

	@Test
	public void testOverlappingOffsetMatchingSupertype() {
		EntityMention cta1 = new EntityMention("orbisnlp", new ClassHierarchy("entity.person.insurgent"),
				new Offset(5, 15), "Bob Smith");
		EntityMention netowl1 = new EntityMention("netowl", new ClassHierarchy("entity.person.terrorist"),
				new Offset(0, 10), "Bob Smith");

		List<EntityMention> entities = new ArrayList<>();
		entities.add(cta1);
		entities.add(netowl1);

		Util.addGeneratedMentionIds(entities);

		List<ExtractionMentionItem> factCollection = new ArrayList<>();
		factCollection.addAll(entities);
		Util.run(factCollection, kieSession, extractorEntityTypeWeights, entityTypeWeights);
		EntityMention winner = Util.extractorTypeWeightWinner(cta1, netowl1, extractorEntityTypeWeights);
		if (winner != null){
			Assert.assertEquals(1, factCollection.size());
			Assert.assertTrue(Util.containsWinner(factCollection, winner));
		}
	}

	@Test
	public void testOverlappingOffsetNonMatchingSupertype() {

		EntityMention cta1 = new EntityMention("orbisnlp", new ClassHierarchy("entity.place"), new Offset(5, 15),
				"Chad");
		EntityMention netowl1 = new EntityMention("netowl", new ClassHierarchy("entity.person"), new Offset(0, 10),
				"Chad");

		List<EntityMention> entities = new ArrayList<>();
		entities.add(cta1);
		entities.add(netowl1);

		Util.addGeneratedMentionIds(entities);

		List<ExtractionMentionItem> factCollection = new ArrayList<>();
		factCollection.addAll(entities);
		Util.run(factCollection, kieSession, extractorEntityTypeWeights, entityTypeWeights);
		EntityMention winner = Util.typeWeightWinner(cta1, netowl1, entityTypeWeights);
		if (winner != null){
			Assert.assertEquals(1, factCollection.size());
			Assert.assertTrue(Util.containsWinner(factCollection, winner));
		}
	}
}
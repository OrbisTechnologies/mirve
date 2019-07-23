package com.orbis.mirve.rule.test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.junit.Assert;
import org.junit.Test;

import com.orbis.mirve.util.RuleEngineHelper;
import com.orbis.mirve.util.RuleEngineProp;


public class PropertyTest {
	
	
	
	
	@Test
	public void testGetRuleFilesFromPropertyValue1(){
		List<File> expected = new ArrayList<File>();
		expected.add(new File("src/test/resources/propertyTest/empty-rules1.drl"));
		
		Properties prop = new Properties();
		prop.put(RuleEngineProp.RULE_FILES_PROPERTY_NAME, "src/test/resources/propertyTest/empty-rules1.drl");
		
		try {
			List<File> ruleFiles = RuleEngineHelper.getRuleFilesFromProperty(prop);
			
			Assert.assertTrue(ruleFiles.containsAll(expected));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Assert.fail();
		}
	}
	
	@Test
	public void testGetRuleFilesFromPropertyValue2(){
		List<File> expected = new ArrayList<File>();
		expected.add(new File("src/test/resources/propertyTest/empty-rules1.drl"));
		expected.add(new File("src/test/resources/propertyTest/empty-rules2.drl"));
		expected.add(new File("src/test/resources/propertyTest/empty-rules3.drl"));
		
		Properties prop = new Properties();
		prop.put(RuleEngineProp.RULE_FILES_PROPERTY_NAME, "src/test/resources/propertyTest/empty-rules1.drl,"
				+ " src/test/resources/propertyTest/empty-rules2.drl, src/test/resources/propertyTest/empty-rules3.drl");
		
		try {
			List<File> ruleFiles = RuleEngineHelper.getRuleFilesFromProperty(prop);
			
			Assert.assertTrue(ruleFiles.containsAll(expected));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Assert.fail();
		}
	}
	
	@Test
	public void testGetRuleFilesFromPropertyValue3(){
		List<File> expected = new ArrayList<File>();
		expected.add(new File("src/test/resources/propertyTest/testRuleDir/empty-rules4.drl"));
		expected.add(new File("src/test/resources/propertyTest/testRuleDir/empty-rules5.drl"));
		
		Properties prop = new Properties();
		prop.put(RuleEngineProp.RULE_FILES_PROPERTY_NAME, "src/test/resources/propertyTest/testRuleDir");
		
		try {
			List<File> ruleFiles = RuleEngineHelper.getRuleFilesFromProperty(prop);
			
			Assert.assertTrue(ruleFiles.containsAll(expected));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Assert.fail();
		}
	}
	
	
	@Test
	public void testGetRuleFilesFromPropertyValue4(){
		
		List<File> expected = new ArrayList<File>();
		expected.add(new File("src/test/resources/propertyTest/empty-rules1.drl"));
		expected.add(new File("src/test/resources/propertyTest/empty-rules2.drl"));
		expected.add(new File("src/test/resources/propertyTest/empty-rules3.drl"));
		expected.add(new File("src/test/resources/propertyTest/testRuleDir/empty-rules4.drl"));
		expected.add(new File("src/test/resources/propertyTest/testRuleDir/empty-rules5.drl"));
		
		Properties prop = new Properties();
		prop.put(RuleEngineProp.RULE_FILES_PROPERTY_NAME, "src/test/resources/propertyTest/testRuleDir,"
					+ "src/test/resources/propertyTest/empty-rules1.drl,"
					+ " src/test/resources/propertyTest/empty-rules2.drl, "
					+ "src/test/resources/propertyTest/empty-rules3.drl");
		
		try {
			List<File> ruleFiles = RuleEngineHelper.getRuleFilesFromProperty(prop);
			
			Assert.assertTrue(ruleFiles.containsAll(expected));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Assert.fail();
		}
	}
	
	

}

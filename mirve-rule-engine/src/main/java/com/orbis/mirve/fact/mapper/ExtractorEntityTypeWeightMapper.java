package com.orbis.mirve.fact.mapper;


import java.util.ArrayList;
import java.util.List;

import javax.naming.ConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.orbis.mirve.util.ExtractorEntityTypeWeight;


/**
 * This class can be used to read in {@link com.orbis.mirve.util.ExtractorEntityTypeWeight} objects
 * from an XML document. 
 * 
 * The form should look like the following example:
 * {@code
 * <ExtractorEntityTypeWeight>
 *       <extractionEngine>extractorA</extractionEngine>
 *       <entityType>location</entityType>
 *       <weight>3</weight>
 * </ExtractorEntityTypeWeight>
 * }
 * 
 * The {@link #readXML(Document)} method can be used to read ExtractorEntityTypeWeight
 * objects so they can be inserted into the {@link com.orbis.mirve.engine.MirveRuleEngine} 
 * class as facts, using the {@link com.orbis.mirve.engine.MirveRuleEngine#insertFacts(java.util.Collection)} 
 * method.
 * 
 * @author jbaker
 *
 */
public class ExtractorEntityTypeWeightMapper implements XMLFactMapper<ExtractorEntityTypeWeight>{

	private final static Logger LOG = LoggerFactory
			.getLogger(ExtractorEntityTypeWeightMapper.class);
	/**
	 * Reads {@link com.orbis.mirve.util.ExtractorEntityTypeWeight} objects from a
	 * {@link org.w3c.dom.Document}.
	 * 
	 * @param xmlDoc A {@link org.w3c.dom.Document} object which should be formed like
	 * the following example:
	 * 
	 * {@code
	 * <ExtractorEntityTypeWeight>
	 *       <extractionEngine>extractorA</extractionEngine>
	 *       <entityType>location</entityType>
	 *       <weight>3</weight>
	 * </ExtractorEntityTypeWeight>
	 * }
	 * 
	 * @return A list of {@link com.orbis.mirve.util.ExtractorEntityTypeWeight} objects
	 * read from the given xml file. Returns an empty list if no weights were found.
	 * 
	 */
	public List<ExtractorEntityTypeWeight> readXML(Document xmlDoc) throws ConfigurationException{
		
		List<ExtractorEntityTypeWeight> weights = new ArrayList<ExtractorEntityTypeWeight>();
		try {
			
			XPath xPath =  XPathFactory.newInstance().newXPath();
			
			String factExpression = "//ExtractorEntityTypeWeight";
			
			NodeList factNodes = (NodeList) xPath.compile(factExpression).evaluate(xmlDoc, XPathConstants.NODESET);
			
			
			
			for (int i = 0; i < factNodes.getLength(); i++) {
                //System.out.println(factNodes.item(i).getNodeName());
                
                ExtractorEntityTypeWeight weightObj = new ExtractorEntityTypeWeight();
                boolean validWeight = true;
                
                Node fact = factNodes.item(i);
                
                if (fact.getNodeType() == Node.ELEMENT_NODE){
                	
                	String extractorExpression = "./extractionEngine";
                	Node extractorNameNode = (Node) xPath.compile(extractorExpression).evaluate(fact, XPathConstants.NODE);
                	//System.out.println("extractor: " + extractorNameNode.getFirstChild().getNodeValue());
                	String extractorNameValue = extractorNameNode.getFirstChild().getNodeValue();
                	weightObj.setExtractionEngine(extractorNameValue);
                	
                	String typeExpression = "./entityType";
                	Node typeNode = (Node) xPath.compile(typeExpression).evaluate(fact, XPathConstants.NODE);
                	//System.out.println("type: " + type.getFirstChild().getNodeValue());
                	String typeValue = typeNode.getFirstChild().getNodeValue();
                	weightObj.setEntityType(typeValue);
                	
                	String weightExpression = "./weight";
                	Node weightNode = (Node) xPath.compile(weightExpression).evaluate(fact, XPathConstants.NODE);
                	//System.out.println("weight: " + weight.getFirstChild().getNodeValue());
                	String weightStringValue = weightNode.getFirstChild().getNodeValue();
                	try{
	                	int weightValue = Integer.parseInt(weightStringValue);
		                weightObj.setWeight(weightValue);
                	}catch(NumberFormatException e){
                		validWeight = false;
                		System.out.println("The value found for weight was not a valid number (expecting integer).");
                	}
                }
                
                if (validWeight == true)
                	weights.add(weightObj);
            }
			
		} catch (XPathExpressionException e) {
			LOG.error("Unable to parse Extractor Entity Type Weight File!");
			ConfigurationException confException= new ConfigurationException("Unable to parse Entity Type Weight File!");
			confException.setRootCause(e);
			throw confException;
		}
		
		return weights;
	}

}
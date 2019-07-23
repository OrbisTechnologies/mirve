package com.orbis.mirve.util;

import org.drools.core.spi.KnowledgeHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.orbis.mirve.core.EntityMention;

/**
 * This class contains helper methods that are used in the Drools rule files (drl files).
 * 
 * @author jbaker
 *
 */
public class Utility {


	

	/**
	 * Helper method to log message from drl file on debug level.
	 * @param drools Current drools session (can use keyword "drools" from the drl file).
	 * @param message Message to be printed.
	 */
	public static void logDebug(final KnowledgeHelper drools, final String message) {
		
		final String category = drools.getRule().getPackageName() + "." + drools.getRule().getName();
		Logger logger = LoggerFactory.getLogger(category);
		if (logger.isDebugEnabled()){
			logger.debug(message);
		}
	}

	
	/**
	 * Helper method to log message from drl file on info level.
	 * @param drools Current drools session (can use keyword "drools" from the drl file).
	 * @param message Message to be printed.
	 */
	public static void logInfo(final KnowledgeHelper drools, final String message) {

		final String category = drools.getRule().getPackageName() + "." + drools.getRule().getName();
		Logger logger = LoggerFactory.getLogger(category);
		if (logger.isInfoEnabled()){
			logger.info(message);
		}
	}

	/**
	 * Returns a String version of an {@link com.orbis.mirve.core.EntityMention} object.
	 * @param e EntityMention object to be printed.
	 * @return String representation of the Entity Mention object.
	 */
	public static String getEntityAsString(final EntityMention e) {
		StringBuilder sb = new StringBuilder();
		sb.append("ENTITY MENTION [extractor: ").append(e.getExtractionEngine()).append("; text:").append(e.getText())
				.append("; type: ").append(e.getType().toString()).append("; start_offset: ")
				.append(e.getItemOffset().getStartOffset()).append("; end_offset: ")
				.append(e.getItemOffset().getEndOffset()).append("; confidence_score: ").append(e.getConfidenceScore());
				
		if (e.getExtractionAttributes() != null){
			sb.append("; attributes: {").append(e.getExtractionAttributes().toString());
			/*for (String key : e.getExtractionAttributes().keySet()){
				sb.append(key).append(": ").append(e.getExtractionAttributes().get(key).to).append("; ");
			}*/
			sb.append("}");
		}
				
		sb.append("]");

		return sb.toString();
	}

	/**
	 * Returns a String version of an {@link com.orbis.mirve.util.ExtractorEntityTypeWeight} object.
	 * @param w ExtractorEntityTypeWeight object to be printed.
	 * @return String representation of the ExtractorEntityTypeWeight object.
	 */
	public static String getWeightAsString(final ExtractorEntityTypeWeight w) {
		StringBuilder sb = new StringBuilder();
		sb.append("WEIGHT [extractor:").append(w.getExtractionEngine()).append("; type: ")
				.append(w.getEntityType()).append("; weight: ").append(w.getWeight()).append("]");

		return sb.toString();
	}
	
	/**
	 * Returns a String version of an {@link com.orbis.mirve.util.EntityTypeWeight} object.
	 * @param w EntityTypeWeight object to be printed.
	 * @return String representation of the EntityTypeWeight object.
	 */
	public static String getWeightAsString(final EntityTypeWeight w) {
		StringBuilder sb = new StringBuilder();
		sb.append("WEIGHT [type: ")
				.append(w.getEntityType()).append("; weight: ").append(w.getWeight()).append("]");

		return sb.toString();
	}

}
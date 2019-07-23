/*
 * Copyright 2017 by Orbis Technologies, Inc.  All rights reserved.
 */
package com.orbis.mirve.nlp.mapper;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.orbis.mirve.nlp.ExtractionResults;
import java.io.IOException;

/**
 * This class implements the {@link JSONMapper} and interchanges JSON format with {@link ExtractionResults}.
 * @author kbrown-walker
 */
public class ExtractionResultsMapper implements JSONMapper<ExtractionResults>{

	/**
	 * Deserialize the {@code jsonString} into {@link ExtractionResults}.
	 * @param jsonString JSON string to deserialize.
	 * @return {@link ExtractionResults} equivalent to the JSON string. 
	 * @throws IOException if unable to parse the input {@code jsonString}.
	 */
	@Override
	public ExtractionResults deserialize(String jsonString) throws IOException {
		ObjectMapper objectMapper = new ObjectMapper()
				.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
				.enableDefaultTyping(ObjectMapper.DefaultTyping.JAVA_LANG_OBJECT);
		return objectMapper.readValue(jsonString, ExtractionResults.class);
	}

	/**
	 * Serialize the {@code extractionResults} into a JSON string.
	 * @param extractionResults {@link ExtractionResults} to deserialize.
	 * @return a JSON string equivalent to the {@code extractionResults}.
	 * @throws IOException the object mapper is unable to write the {@code extractionResults} to a string.
	 */
	@Override
	public String serialize(ExtractionResults extractionResults) throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(extractionResults);
	}
	
}

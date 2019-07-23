/*
 * Copyright 2017 by Orbis Technologies, Inc.  All rights reserved.
 */
package com.orbis.mirve.nlp.mapper;

import java.io.IOException;

/**
 * Interface to map JSON to a list of objects T so that they can be inserted 
 * into {@link com.orbis.mirve.engine.MirveRuleEngine} as facts.
 * @author kbrown-walker
 * @param <T> Object to be mapped from JSON format.
 */
public interface JSONMapper<T> {
	public T deserialize(String jsonString) throws IOException;
	public String serialize(T t) throws IOException;
}

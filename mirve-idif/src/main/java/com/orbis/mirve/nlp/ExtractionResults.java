/*
 * Copyright 2017 by Orbis Technologies, Inc.  All rights reserved.
 */
package com.orbis.mirve.nlp;

import com.orbis.mirve.core.EntityMention;
import com.orbis.mirve.core.Event;
import com.orbis.mirve.core.Relationship;
import com.orbis.mirve.core.Sentence;
import java.io.Serializable;
import java.util.List;

/**
 * This is the wrapper class used for deserializing the SFS output.
 * @author kbrown-walker
 */
public class ExtractionResults implements Serializable{
	private static final long serialVersionUID = 7818718781365429651L;
	private List<EntityMention> entities;
	private List<Event> events;
	private List<Relationship> relationships;
	private List<Sentence> sentences;

	/**
	 * Get the list of {@link entities}.
	 * @return entities
	 */
	public List<EntityMention> getEntities() {
		return entities;
	}

	/**
	 * Set the List of {@code EntitiesMention}s to {@code entities}. 
	 * @param entities
	 */
	public void setEntities(List<EntityMention> entities) {
		this.entities = entities;
	}

	/**
	 * Get the list of {@link events}.
	 * @return events
	 */
	public List<Event> getEvents() {
		return events;
	}

	/**
	 * Set the List of {@code Event}s to {@code events}.
	 * @param events
	 */
	public void setEvents(List<Event> events) {
		this.events = events;
	}

	/**
	 * Get the list of {@link relationships}.
	 * @return relationships
	 */
	public List<Relationship> getRelationships() {
		return relationships;
	}

	/**
	 * Set the list of {@code Relationship}s to {@code relationships}.
	 * @param relationships
	 */
	public void setRelationships(List<Relationship> relationships) {
		this.relationships = relationships;
	}

	/**
	 * Get the list of {@link sentences}.
	 * @return sentences
	 */
	public List<Sentence> getSentences() {
		return sentences;
	}

	/**
	 * Set the list of {@code Sentence}s to {@code sentences}.
	 * @param sentences
	 */
	public void setSentences(List<Sentence> sentences) {
		this.sentences = sentences;
	}
}
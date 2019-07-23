/*
 * Copyright 2017 by Orbis Technologies, Inc.  All rights reserved.
 */
package com.orbis.mirve.core;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.util.Collection;
import java.util.Map;

@JsonIdentityInfo(scope=Event.class, generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@JsonIgnoreProperties(value = {"serialVersionUID", "adjudicatedBy"})
public class Event extends Relationship {

	// handle trace of event to sentence
	private Event adjudicatedBy;
	private static final long serialVersionUID = -637473659627515667L;
	private Offset triggerPredicateOffset;

	@JsonCreator
	public Event(@JsonProperty("type") String type){
		super(type);
	}
	
	public Event(String extractionEngine, ClassHierarchy type, Offset itemOffset, String text,
			Map<String,Object> extractionAttributes, EntityMention sourceEntity, EntityMention targetEntity, String triggerWord,
			Offset triggerPredicateOffset) {
		super(extractionEngine, type, itemOffset, text, extractionAttributes, sourceEntity, targetEntity, triggerWord);
		this.setTriggerPredicateOffset(triggerPredicateOffset);

	}
	
	public Event(String extractionEngine, ClassHierarchy type, Offset itemOffset, String text,
			Map<String,Object> extractionAttributes, EntityMention sourceEntity, EntityMention targetEntity, String triggerWord,
			Offset triggerPredicateOffset, int id) {
		super(extractionEngine, type, itemOffset, text, extractionAttributes, sourceEntity, targetEntity, triggerWord, id);
		this.setTriggerPredicateOffset(triggerPredicateOffset);

	}

	public Event(String extractionEngine, ClassHierarchy type, Offset itemOffset, String text,
			EntityMention sourceEntity, EntityMention targetEntity, String triggerWord, Offset triggerPredicateOffset) {
		super(extractionEngine, type, itemOffset, text, null, sourceEntity, targetEntity, triggerWord);
		this.setTriggerPredicateOffset(triggerPredicateOffset);

	}
	
	public Event(String extractionEngine, ClassHierarchy type, Offset itemOffset, String text,
			EntityMention sourceEntity, EntityMention targetEntity, String triggerWord, Offset triggerPredicateOffset,int id) {
		super(extractionEngine, type, itemOffset, text, null, sourceEntity, targetEntity, triggerWord, id);
		this.setTriggerPredicateOffset(triggerPredicateOffset);

	}

	public Event(String extractionEngine, double confidenceScore, ClassHierarchy type, Offset itemOffset, String text,
			EntityMention sourceEntity, EntityMention targetEntity, String triggerWord, Offset triggerPredicateOffset) {
		super(extractionEngine, confidenceScore, type, itemOffset, text, sourceEntity, targetEntity, triggerWord);
		this.setTriggerPredicateOffset(triggerPredicateOffset);

	}
	
	public Event(String extractionEngine, double confidenceScore, ClassHierarchy type, Offset itemOffset, String text,
			EntityMention sourceEntity, EntityMention targetEntity, String triggerWord, Offset triggerPredicateOffset, int id) {
		super(extractionEngine, confidenceScore, type, itemOffset, text, sourceEntity, targetEntity, triggerWord,id);
		this.setTriggerPredicateOffset(triggerPredicateOffset);

	}

	public Collection<Sentence> getSentences() {
		// TODO
		return null;
	}

	public Offset getTriggerPredicateOffset() {
		return triggerPredicateOffset;
	}

	public void setTriggerPredicateOffset(Offset triggerPredicateOffset) {
		this.triggerPredicateOffset = triggerPredicateOffset;
	}

	@Override
	public Event getAdjudicatedBy() {
		return adjudicatedBy;
	}

	public void setAdjudicatedBy(Event adjudicatedBy) {
		this.adjudicatedBy = adjudicatedBy;
	}

}

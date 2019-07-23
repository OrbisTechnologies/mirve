/*
 * Copyright 2017 by Orbis Technologies, Inc.  All rights reserved.
 */
package com.orbis.mirve.core;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.util.Map;

import com.google.common.collect.ComparisonChain;

@JsonIdentityInfo(scope=Relationship.class, generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@JsonIgnoreProperties(value = {"serialVersionUID", "adjudicatedBy"})
public class Relationship extends ExtractionMentionItem {

	private static final long serialVersionUID = -8144622742867332111L;
	private EntityMention sourceEntity;
	private EntityMention targetEntity;
	private Relationship adjudicatedBy;
	private String triggerWord;
	
	@JsonCreator
	public Relationship(@JsonProperty("type") String type){
		super(type);
	}
	
	
/**
 * 
 * @param extractionEngine
 * @param type
 * @param itemOffset
 * @param text
 * @param sourceEntity
 * @param targetEntity
 * @param triggerWord
 */
	public Relationship(String extractionEngine, ClassHierarchy type, 
			Offset itemOffset, String text, EntityMention sourceEntity, EntityMention targetEntity,
			String triggerWord){
		super(extractionEngine, type, itemOffset, text);
		this.sourceEntity = sourceEntity;
		this.targetEntity = targetEntity;
		this.triggerWord = triggerWord;
	}
	public Relationship(String extractionEngine, ClassHierarchy type, 
			Offset itemOffset, String text, EntityMention sourceEntity, EntityMention targetEntity,
			String triggerWord, int id){
		super(extractionEngine, type, itemOffset, text,id);
		this.sourceEntity = sourceEntity;
		this.targetEntity = targetEntity;
		this.triggerWord = triggerWord;
	}
	
	
	public Relationship(String extractionEngine, ClassHierarchy type, 
			Offset itemOffset, String text,Map<String,Object> extractionAttributes, EntityMention sourceEntity, EntityMention targetEntity,
			String triggerWord){
		super(extractionEngine, type, itemOffset, text);
		this.sourceEntity = sourceEntity;
		this.targetEntity = targetEntity;
		this.triggerWord = triggerWord;
	}
	
	public Relationship(String extractionEngine, ClassHierarchy type, 
			Offset itemOffset, String text,Map<String,Object> extractionAttributes, EntityMention sourceEntity, EntityMention targetEntity,
			String triggerWord, int id){
		super(extractionEngine, type, itemOffset, text,id);
		this.sourceEntity = sourceEntity;
		this.targetEntity = targetEntity;
		this.triggerWord = triggerWord;
	}
	
	public Relationship(String extractionEngine, double confidenceScore,
			ClassHierarchy type, Offset itemOffset, String text, EntityMention sourceEntity, EntityMention targetEntity,
			String triggerWord){
		super(extractionEngine, confidenceScore, type, itemOffset, text, null);
		this.sourceEntity = sourceEntity;
		this.targetEntity = targetEntity;
		this.triggerWord = triggerWord;
	}
	
	public Relationship(String extractionEngine, double confidenceScore,
			ClassHierarchy type, Offset itemOffset, String text, EntityMention sourceEntity, EntityMention targetEntity,
			String triggerWord,int id){
		super(extractionEngine, confidenceScore, type, itemOffset, text, null,id);
		this.sourceEntity = sourceEntity;
		this.targetEntity = targetEntity;
		this.triggerWord = triggerWord;
	}

	
	/**
	 * 
	 * @param extractionEngine
	 * @param confidenceScore : IF extractorEngine does not support confidence then set it to zero
	 * @param type
	 * @param itemOffset
	 * @param text
	 * @param sourceEntity
	 * @param targetEntity
	 * @param triggerWord
	 */
	public Relationship(String extractionEngine, double confidenceScore,
			ClassHierarchy type, Offset itemOffset, String text, Map<String,Object> extractionAttributes, EntityMention sourceEntity, EntityMention targetEntity,
			String triggerWord){
		super(extractionEngine, confidenceScore, type, itemOffset, text, extractionAttributes);
		this.sourceEntity = sourceEntity;
		this.targetEntity = targetEntity;
		this.triggerWord = triggerWord;
	}

	
	public EntityMention getSourceEntity() {
		return sourceEntity;
	}

	public void setSourceEntity(EntityMention sourceEntity) {
		this.sourceEntity = sourceEntity;
	}

	public EntityMention getTargetEntity() {
		
		return targetEntity;
	}

	public void setTargetEntity(EntityMention targetEntity) {
		this.targetEntity = targetEntity;
	}

	public String getTriggerWord() {
		return triggerWord;
	}

	public void setTriggerWord(String triggerWord) {
		this.triggerWord = triggerWord;
	}


	@Override
	public int compareTo(ExtractionItem o) {
		if (o instanceof Relationship) {
			Relationship other = (Relationship) o;
			return ComparisonChain.start()
	                .compare(this.getSourceEntity(), other.getSourceEntity())
	                .compare(this.getType(), other.getType())
	                .compare(this.getTargetEntity(), other.getTargetEntity())
	                .compare(this.getItemOffset(), other.getItemOffset())
	                .result();
	        
		}
		return -1;
	}

	public Relationship getAdjudicatedBy() {
		return adjudicatedBy;
	}

	public void setAdjudicatedBy(Relationship adjudicatedBy) {
		this.adjudicatedBy = adjudicatedBy;
	}

}

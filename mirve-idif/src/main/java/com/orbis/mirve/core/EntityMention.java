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
import java.util.Map.Entry;
import java.util.Properties;

import com.google.common.collect.ComparisonChain;

/*
 * This is the representation of the EntityMention in orbis-nlp		
 */
@JsonIdentityInfo(scope=EntityMention.class, generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@JsonIgnoreProperties(value = {"serialVersionUID", "adjudicatedBy"})
public class EntityMention extends ExtractionMentionItem {

	
	private static final long serialVersionUID = -4949661004388371413L;
	private String normalizedText;
	private EntityMention adjudicatedBy;
	
	@JsonCreator
	public EntityMention(@JsonProperty("type") String type){
		super(type);
	}

	public EntityMention getAdjudicatedBy() {
		return adjudicatedBy;
	}

	public void setAdjudicatedBy(EntityMention adjudicatedBy) {
		this.adjudicatedBy = adjudicatedBy;
	}
	
	
	
	public EntityMention(String extractionEngine, ClassHierarchy type, Offset itemOffset, String text) {
		super(extractionEngine, type, itemOffset, text);
		this.normalizedText = "";
	}
	
	public EntityMention(String extractionEngine, ClassHierarchy type, Offset itemOffset, String text,int id) {
		super(extractionEngine, type, itemOffset, text, id);
		this.normalizedText = "";
	}

	public EntityMention(String extractionEngine, ClassHierarchy type, Offset itemOffset, String text, String normalizedText) {
		super(extractionEngine, type, itemOffset, text);
		if (normalizedText != null) {
			this.normalizedText = normalizedText;
		} else {
			this.normalizedText = "";
		}
	}
	
	public EntityMention(String extractionEngine, ClassHierarchy type, Offset itemOffset, String text, String normalizedText, int id) {
		super(extractionEngine, type, itemOffset, text, id);
		if (normalizedText != null) {
			this.normalizedText = normalizedText;
		} else {
			this.normalizedText = "";
		}
	}
	
	public EntityMention(String extractionEngine, ClassHierarchy type, Offset itemOffset, String text, Map<String,Object> extractionAttributes) {
		super(extractionEngine, type, itemOffset, text, extractionAttributes);
		this.normalizedText = "";
		
	}
	public EntityMention(String extractionEngine, ClassHierarchy type, Offset itemOffset, String text, Map<String,Object> extractionAttributes, int id) {
		super(extractionEngine, type, itemOffset, text, extractionAttributes,id);
		this.normalizedText = "";
		
	}

	public EntityMention(String extractionEngine, ClassHierarchy type, Offset itemOffset, String text, String normalizedText,
			Map<String,Object> extractionAttributes) {
		super(extractionEngine, type, itemOffset, text, extractionAttributes);
		if (normalizedText != null) {
			this.normalizedText = normalizedText;
		} else {
			this.normalizedText = "";
		}
	}
	
	public EntityMention(String extractionEngine, ClassHierarchy type, Offset itemOffset, String text, String normalizedText,
			Map<String,Object> extractionAttributes,int id) {
		super(extractionEngine, type, itemOffset, text, extractionAttributes, id);
		if (normalizedText != null) {
			this.normalizedText = normalizedText;
		} else {
			this.normalizedText = "";
		}
	}

	public EntityMention(String extractionEngine, double confidenceScore, ClassHierarchy type, Offset itemOffset, String text,
			String normalizedText, Map<String,Object> extractionAttributes) {
		super(extractionEngine, confidenceScore, type, itemOffset, text, extractionAttributes);
		if (normalizedText != null) {
			this.normalizedText = normalizedText;
		} else {
			this.normalizedText = "";
		}
	}
	
	public EntityMention(String extractionEngine, double confidenceScore, ClassHierarchy type, Offset itemOffset, String text,
			String normalizedText, Map<String,Object> extractionAttributes, int id) {
		super(extractionEngine, confidenceScore, type, itemOffset, text, extractionAttributes,id);
		if (normalizedText != null) {
			this.normalizedText = normalizedText;
		} else {
			this.normalizedText = "";
		}
	}

	/**
	 * @return the normalizedText
	 */
	public String getNormalizedText() {
		return normalizedText;
	}

	/**
	 * @param normalizedText
	 *            the normalizedText to set
	 */
	public void setNormalizedText(String normalizedText) {
		this.normalizedText = normalizedText;
	}

	public void print() {
		System.out.println("~~Entity~~:");
		System.out.println("\t name: " + this.getText());
		System.out.println("\t type: " + this.getType());
		System.out.println("\t extractor: " + this.getExtractionEngine());
		System.out.println("\t start: " + this.getItemOffset().getStartOffset());
		System.out.println("\t end: " + this.getItemOffset().getEndOffset());
		System.out.println("\t adjudicated: " + this.isAdjudicated());
	}

	@Override
	public int compareTo(ExtractionItem o) {
		if (o instanceof EntityMention) {
			EntityMention other = (EntityMention) o;
			return ComparisonChain.start()
	                .compare(this.getText(), other.getText())
	                .compare(this.getNormalizedText(), other.getNormalizedText())
	                .compare(this.getType(), other.getType())
	                .compare(this.getItemOffset(), other.getItemOffset())
	                .result();
	        
		}
		return -1;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(" name: ").append(this.getText())
		.append("  type: ").append(this.getType())
		.append("  id: ").append(this.id)
		.append("  start: ").append(this.getItemOffset().getStartOffset())
		.append("  end: ").append(this.getItemOffset().getEndOffset());
		for (Entry<String, Object> attribute :this.getExtractionAttributes().entrySet() ) {
			sb.append("   ").append(attribute.getKey()).append(":  ").append(attribute.getValue());
		}
		
		return sb.toString();
	}

}

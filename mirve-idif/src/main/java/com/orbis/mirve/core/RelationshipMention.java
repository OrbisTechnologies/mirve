package com.orbis.mirve.core;

import java.io.Serializable;

import com.google.common.collect.ComparisonChain;
@Deprecated
public class RelationshipMention extends ExtractionMentionItem{

	private static final long serialVersionUID = -7514112277130329019L;
	
	private String sourceEntity;
	private String targetEntity;
	private ClassHierarchy type;
	
	
	public RelationshipMention(String extractionEngine,String sourceEntity, String targetEntity, ClassHierarchy type, String text) {
		super(extractionEngine, type, null, text);
		this.sourceEntity = sourceEntity;
		this.targetEntity = targetEntity;
		this.type = type;
		
	}
	
	
	public RelationshipMention(String sourceEntity, String targetEntity, ClassHierarchy type) {
		super("", type, null, "");
		this.sourceEntity = sourceEntity;
		this.targetEntity = targetEntity;
		this.type = type;
		
	}
	
	public String getSourceEntity() {
		return this.sourceEntity;
	}
	
	public String getTargetEntity() {
		return this.targetEntity;
	}
	
	public ClassHierarchy getType() {
		return this.type;
	}

	public int compareTo(ExtractionItem o) {
		if (o instanceof RelationshipMention) {
			RelationshipMention other = (RelationshipMention) o;
			return ComparisonChain.start()
	                .compare(this.getSourceEntity(), other.getSourceEntity())
	                .compare(this.getType(), other.getType())
	                .compare(this.getTargetEntity(), other.getTargetEntity())
	                .result();
	        
		}
		return -1;
	}

}

/*
 * Copyright 2017 by Orbis Technologies, Inc.  All rights reserved.
 */
package com.orbis.mirve.core;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public abstract class ExtractionItem implements Serializable,Comparable<ExtractionItem>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3257821897577327736L;

	private String extractionEngine;

	private double confidenceScore;

	private ClassHierarchy type;

	private boolean isAdjudicated;
	
	protected int id;

	// private String ruleName; //this would be for testing purpose
	

	private String text;
	
	private double adjudicatedConfidenceScore;
	
//	TODO
	private Map<String, Object> extractionAttributes;
	//private Properties extractionAttributes;

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	protected ExtractionItem(String type){
		extractionAttributes = new HashMap<>();
		this.type = new ClassHierarchy(type);
	}
	
	protected ExtractionItem(String extractionEngine, ClassHierarchy type
			, String text) {
		this(extractionEngine, 0, type, text, new HashMap<String, Object>());
	}
	

	
	protected ExtractionItem(String extractionEngine, ClassHierarchy type
			, String text, Map<String,Object> extractionAttributes) {
		this(extractionEngine, 0, type, text, extractionAttributes);
	
	}

	protected ExtractionItem(String extractionEngine, double confidenceScore,
			ClassHierarchy type,  String text, Map<String,Object> extractionAttributes) {
		this(extractionEngine, confidenceScore, type, false,  text, extractionAttributes);
		
	}
	
	protected ExtractionItem(String extractionEngine, double confidenceScore,
			ClassHierarchy type,  String text, Map<String,Object> extractionAttributes, int id) {
		this(extractionEngine, confidenceScore, type, false,  text, extractionAttributes,id);
		
	}

	protected ExtractionItem(String extractionEngine, double confidenceScore,
			ClassHierarchy type, boolean isAdjudicated,
			String text, Map<String,Object> extractionAttributes) {
		
		this.extractionEngine = extractionEngine;
		this.confidenceScore = confidenceScore;
		this.type = type;
		this.isAdjudicated = isAdjudicated;
		this.extractionAttributes = extractionAttributes;
		this.text = text;
	}
	protected ExtractionItem(String extractionEngine, double confidenceScore,
			ClassHierarchy type, boolean isAdjudicated,
			String text, Map<String,Object> extractionAttributes,int id) {
		
		this.extractionEngine = extractionEngine;
		this.confidenceScore = confidenceScore;
		this.type = type;
		this.isAdjudicated = isAdjudicated;
		this.extractionAttributes = extractionAttributes;
		this.text = text;
		this.id = id;
	}

	/**
	 * @return the extractionEngine
	 */
	public String getExtractionEngine() {
		return extractionEngine;
	}

	/**
	 * @param extractionEngine
	 *            the extractionEngine to set
	 */
	public void setExtractionEngine(String extractionEngine) {
		this.extractionEngine = extractionEngine;
	}

	/**
	 * @return the confidenceScore
	 */
	public double getConfidenceScore() {
		return confidenceScore;
	}

	/**
	 * @param confidenceScore
	 *            the confidenceScore to set
	 */
	public void setConfidenceScore(double confidenceScore) {
		this.confidenceScore = confidenceScore;
	}

	/**
	 * @return the type
	 */
	public ClassHierarchy getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(ClassHierarchy type) {
		this.type = type;
	}

	/**
	 * @return the isAdjudicated
	 */
	public boolean isAdjudicated() {
		return isAdjudicated;
	}

	/**
	 * @param isAdjudicated
	 *            the isAdjudicated to set
	 */
	public void setAdjudicated(boolean isAdjudicated) {
		this.isAdjudicated = isAdjudicated;
	}

	/**
	 * @return the itemOffset
	 */
/*	public Offset getItemOffset() {
		return itemOffset;
	}
*/
	/**
	 * @param itemOffset
	 *            the itemOffset to set
	 */
/*	public void setItemOffset(Offset itemOffset) {
		this.itemOffset = itemOffset;
	}*/

	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * @param text
	 *            the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}

	/*public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}*/

	/**
	 * @return the adjudicatedConfidenceScore
	 */
	public double getAdjudicatedConfidenceScore() {
		return adjudicatedConfidenceScore;
	}

	/**
	 * @param adjudicatedConfidenceScore the adjudicatedConfidenceScore to set
	 */
	public void setAdjudicatedConfidenceScore(double adjudicatedConfidenceScore) {
		this.adjudicatedConfidenceScore = adjudicatedConfidenceScore;
	}

	public Map<String,Object> getExtractionAttributes() {
		return extractionAttributes;
	}

	public void setExtractionAttributes(Map<String,Object> extractionAttributes) {
		this.extractionAttributes = extractionAttributes;
	}
}

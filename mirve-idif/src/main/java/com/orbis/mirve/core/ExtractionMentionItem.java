/*
 * Copyright 2017 by Orbis Technologies, Inc.  All rights reserved.
 */
package com.orbis.mirve.core;

import java.util.HashMap;
import java.util.Map;

public abstract class ExtractionMentionItem extends ExtractionItem {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3257821897577327736L;


	// private String ruleName; //this would be for testing purpose
	private Offset itemOffset;

	protected ExtractionMentionItem(String type){
		super(type);
	}

	protected ExtractionMentionItem(String extractionEngine, ClassHierarchy type,
			Offset itemOffset, String text) {
		this(extractionEngine, 0, type, itemOffset, text, new HashMap<String, Object>());
	
	}
	
	protected ExtractionMentionItem(String extractionEngine, ClassHierarchy type,
			Offset itemOffset, String text, int id) {
		this(extractionEngine, 0, type, itemOffset, text, new HashMap<String, Object>(),id);
	
	}
	
	protected ExtractionMentionItem(String extractionEngine, ClassHierarchy type,
			Offset itemOffset, String text, Map<String,Object> extractionAttributes) {
		this(extractionEngine, 0, type, itemOffset, text, extractionAttributes);
	
	}

	
	
	protected ExtractionMentionItem(String extractionEngine, ClassHierarchy type,
			Offset itemOffset, String text, Map<String,Object> extractionAttributes, int id) {
		this(extractionEngine, 0, type, itemOffset, text, extractionAttributes, id);
	
	}

	protected ExtractionMentionItem(String extractionEngine, double confidenceScore,
			ClassHierarchy type, Offset itemOffset, String text, Map<String,Object> extractionAttributes) {
		super(extractionEngine, confidenceScore, type, false, text, extractionAttributes);
		this.itemOffset = itemOffset;
		
	}
	
	
	protected ExtractionMentionItem(String extractionEngine, double confidenceScore,
			ClassHierarchy type, Offset itemOffset, String text, Map<String,Object> extractionAttributes, int id) {
		super(extractionEngine, confidenceScore, type, false, text, extractionAttributes,id);
		this.itemOffset = itemOffset;
		
	}



	/**
	 * @return the itemOffset
	 */
	public Offset getItemOffset() {
		return itemOffset;
	}

	/**
	 * @param itemOffset
	 *            the itemOffset to set
	 */
	public void setItemOffset(Offset itemOffset) {
		this.itemOffset = itemOffset;
	}

	
	
	
}

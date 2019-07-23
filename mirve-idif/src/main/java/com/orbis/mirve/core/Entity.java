/*
 * Copyright 2017 by Orbis Technologies, Inc.  All rights reserved.
 */
package com.orbis.mirve.core;

import java.util.Collection;
import java.util.HashSet;

import com.google.common.collect.ComparisonChain;


public class Entity extends ExtractionItem{

	
	private static final long serialVersionUID = 7818711383365429651L;
	private Collection<EntityMention> referenceMention;
	
	public Entity(String extractionEngine, ClassHierarchy type, String text) {
		super(extractionEngine, type, text);
		setReferenceMention(new HashSet<EntityMention>());
	}
	
	public Entity(String extractionEngine, ClassHierarchy type, String text, Collection<EntityMention> referenceMention) {
		super(extractionEngine, type, text);
		this.setReferenceMention(referenceMention);
	}

	public Collection<EntityMention> getReferenceMention() {
		return referenceMention;
	}

	public void setReferenceMention(Collection<EntityMention> referenceMention) {
		this.referenceMention = referenceMention;
	}

	@Override
	public int compareTo(ExtractionItem o) {
		if (o instanceof Entity) {
			Entity other = (Entity) o;
			return ComparisonChain.start()
	                .compare(this.getText(), other.getText())
	                .compare(this.getType(), other.getType())
	                .result();
	        
		}
		return -1;
	}

	
	
	
}

/*
 * Copyright 2017 by Orbis Technologies, Inc.  All rights reserved.
 */
package com.orbis.mirve.core;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.io.Serializable;
import java.util.Map;

@JsonIdentityInfo(scope=Sentence.class, generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "id")
@JsonIgnoreProperties(value = {"serialVersionUID"})
public class Sentence implements Serializable{
	

	private static final long serialVersionUID = 7017133496650013000L;

	private String text;
	
	private Offset offset;
	
	//This is to save any properties to this object such as isBlacklisted, or isOriginal.
	private Map<String, String> sentenceProp;
	//this is to decouple

	private String securityMarking;

	public Sentence(){
		super();
	}
	
	public Sentence(String text, Offset offset) {
		super();
		this.text = text;
		this.offset = offset;
	}
	
	public Sentence(String text, Offset offset,String securityMarking) {
		super();
		this.text = text;
		this.offset = offset;
		this.setSecurityMarking(securityMarking);
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Offset getOffset() {
		return offset;
	}

	public void setOffset(Offset offset) {
		this.offset = offset;
	}

	public String getSecurityMarking() {
		return securityMarking;
	}

	public void setSecurityMarking(String securityMarking) {
		this.securityMarking = securityMarking;
	}
	
	/**
	 * @return the sentenceProp
	 */
	public Map<String, String> getSentenceProp() {
		return sentenceProp;
	}

	/**
	 * @param sentenceProp the sentenceProp to set
	 */
	public void setSentenceProp(Map<String, String> sentenceProp) {
		this.sentenceProp = sentenceProp;
	}
	

}

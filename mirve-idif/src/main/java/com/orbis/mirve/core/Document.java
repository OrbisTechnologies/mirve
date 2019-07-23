package com.orbis.mirve.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.google.common.base.Preconditions;

public class Document implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8904081985871300907L;



	//Add document metadata
	private Map<String,String> docMeta;
	//David, We don't need to have the metadata of document at this level 



    private String cleanedText;

    private String docName;

    private String docID;



    private Collection<EntityMention> entities;

    private Collection<RelationshipMention> relationships;

    private Collection<Sentence> sentences;

    private Collection<Event> events;

    public Document() {
        super();
        this.docMeta = new HashMap<>();
    }

    public Document(String docName) {
      
        this.docName = docName;
        this.docMeta = new HashMap<>();
      
    }
    public Document(String docName, String docID) {
 
        this.docName = docName;
        this.docID = docID;
        this.docMeta = new HashMap<>();
    }

    
    public Document addDocMeta(Map map){
    	this.docMeta.putAll(map);
    	return this;
    }
   
    
    /**
	 * @return the docID
	 */
	public String getDocID() {
		return docID;
	}

	public Collection<EntityMention> addEntity(EntityMention entity) {
        Preconditions.checkNotNull(entity, "Passed Entity Object is null!");
        if (entities != null) {
            entities.add(entity);
        } else {
            entities = new ArrayList<>();
            entities.add(entity);
        }
        return entities;
    }

    public Collection<RelationshipMention> addRelationship(RelationshipMention relationship) {
        Preconditions.checkNotNull(relationship, "Passed Relationship object is null!");
        if (relationships != null) {
            relationships.add(relationship);
        } else {
            relationships = new ArrayList<>();
            relationships.add(relationship);
        }
        return relationships;
    }
    
    public Collection<Event> addEvent(Event event) {
        Preconditions.checkNotNull(event, "Passed Event object is null!");
        if (events != null) {
            events.add(event);
        } else {
            events = new ArrayList<>();
            events.add(event);
        }
        return events;
    }

    public Collection<Sentence> addSentence(Sentence sentence) {
        Preconditions.checkNotNull(sentence, "Passed sentence object is null!");
        if (sentences != null) {
            sentences.add(sentence);
        } else {
            sentences = new ArrayList<>();
            sentences.add(sentence);
        }
        return sentences;
    }
    
    
    public Collection<EntityMention> addEntities(Collection<EntityMention> entities){
        Preconditions.checkNotNull(entities, "Passed entity collection object is null!");
        if (this.entities != null) {
            this.entities.addAll(entities);
        } else {
            this.entities = new ArrayList<>();
            this.entities.addAll(entities);
        }
        return this.entities;
    }
    
    public Collection<RelationshipMention> addRelationships(Collection<RelationshipMention> relationships){
        Preconditions.checkNotNull(relationships, "Passed relationship collection object is null!");
        if (this.relationships != null) {
            this.relationships.addAll(relationships);
        } else {
            this.relationships = new ArrayList<>();
            this.relationships.addAll(relationships);
        }
        return this.relationships;
    }
    
    public Collection<Sentence> addSentences(Collection<Sentence> sentences){
        Preconditions.checkNotNull(sentences, "Passed sentence object is null!");
        if (this.sentences != null) {
            this.sentences.addAll(sentences);
        } else {
            this.sentences = new ArrayList<>();
            this.sentences.addAll(sentences);
        }
        return this.sentences;
    }
    
    public Collection<Event> addEvents(Collection<Event> events){
        Preconditions.checkNotNull(events, "Passed event collection object is null!");
        if (this.events != null) {
            this.events.addAll(events);
        } else {
            this.events = new ArrayList<>();
            this.events.addAll(events);
        }
        return this.events;
    }

    /**
     * @return the originalText
     */
  /*  public String getOriginalText() {
        return originalText;
    }
*/
    /**
     * @param originalText the originalText to set
     */
/*    public void setOriginalText(String originalText) {
        this.originalText = originalText;
    }*/

    /**
     * @return the cleanedText
     */
    public String getCleanedText() {
        return cleanedText;
    }

    /**
     * @param cleanedText the cleanedText to set
     */
    public void setCleanedText(String cleanedText) {
        this.cleanedText = cleanedText;
    }

    /**
     * @return the securityMarking
     */
    /*public String getSecurityMarking() {
        return securityMarking;
    }
*/
    /**
     * @param securityMarking the securityMarking to set
     */
 /*   public void setSecurityMarking(String securityMarking) {
        this.securityMarking = securityMarking;
    }*/

    /**
     * @return the sourceLabel
     */
/*    public String getSourceLabel() {
        return sourceLabel;
    }
*/
    /**
     * @param sourceLabel the sourceLabel to set
     */
/*    public void setSourceLabel(String sourceLabel) {
        this.sourceLabel = sourceLabel;
    }
*/
    /**
     * @return the subject
     */
/*    public String getSubject() {
        return subject;
    }*/

    /**
     * @param subject the subject to set
     */
/*    public void setSubject(String subject) {
        this.subject = subject;
    }*/

    /**
     * @return the docType
     */
/*    public String getDocType() {
        return docType;
    }
*/
    /**
     * @param docType the docType to set
     */
/*    public void setDocType(String docType) {
        this.docType = docType;
    }*/

    /**
     * @return the entities
     */
    public Collection<EntityMention> getEntities() {
        return entities;
    }

    /**
     * @return the relationships
     */
    public Collection<RelationshipMention> getRelationships() {
        return relationships;
    }

    /**
     * @return the events
     */
    public Collection<Event> getEvents() {
        return events;
    }

    /**
     * @return the sentences
     */
    public Collection<Sentence> getSentences() {
        return sentences;
    }

	/**
	 * @return the docName
	 */
	public String getDocName() {
		return docName;
	}

	/**
	 * @return the docMeta
	 */
	public Map<String,String> getDocMeta() {
		return docMeta;
	}

	/**
	 * @param docMeta the docMeta to set
	 */
/*	public void setDocMeta(Map docMeta) {
		this.docMeta = docMeta;
	}
    */
    

}

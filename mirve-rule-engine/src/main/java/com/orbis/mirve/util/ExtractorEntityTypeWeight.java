package com.orbis.mirve.util;


/**
 * 
 * This class is used to create preferences for a given extractor based on
 * a given type.
 * 
 * We will insert instantiations of this class into the rule engine as facts. 
 * 
 * 
 * For example:
 * If we know that EXTRACTOR_A extracts Equipment well and EXTRACTOR_B does not,
 * we can create an ExtractorEntityTypeWeight for both EXTRACTOR_A and EXTRACTOR_B
 * where they both have type "Equipment". Then we can add a int value for the weight
 * so that EXTRACTOR_A has a higher weight than EXTRACTOR_B.
 * 
 * Once these are inserted into the rule engine, this weight information will be used to
 * decide which entity should be voted out.
 *
 */

public class ExtractorEntityTypeWeight extends EntityTypeWeight{
	
	private String extractionEngine = null;
	
	
	public ExtractorEntityTypeWeight() {}
	
	public ExtractorEntityTypeWeight(String entityType, String extractionEngine, int weight) {
		super(entityType, weight);
		this.extractionEngine = extractionEngine;
	}
	
	public String getExtractionEngine() {
		return extractionEngine;
	}
	public void setExtractionEngine(String extractionEngine) {
		this.extractionEngine = extractionEngine;
	}
		
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((extractionEngine == null) ? 0 : extractionEngine.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (!(obj instanceof ExtractorEntityTypeWeight))
			return false;
		else {
			ExtractorEntityTypeWeight weight = (ExtractorEntityTypeWeight) obj;
			return this.entityType.equals(weight.getEntityType())
					&& this.extractionEngine.equals(weight.getExtractionEngine()) && this.weight == weight.getWeight();
		}
	}

	@Override
	public void print(){
		System.out.println("ExtractorEntityTypeWeight");
		System.out.println("\t entityType: " + entityType);
		System.out.println("\t extractionEngine: " + extractionEngine);
		System.out.println("\t weight: " + weight);
	}
	
}
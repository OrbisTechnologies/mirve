package com.orbis.mirve.util;

import java.util.Arrays;

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

public class EntityTypeWeight {
	
	protected String entityType = null;
	protected int weight = 0;
	
	
	public EntityTypeWeight() {}
	
	public EntityTypeWeight(String entityType, int weight) {
		this.entityType = entityType;
		this.weight = weight;
	}
	

	public String getEntityType() {
		return entityType;
	}
	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}
	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (!(obj instanceof EntityTypeWeight))
			return false;
		else{
			EntityTypeWeight weight = (EntityTypeWeight) obj;
			return this.entityType.equals(weight.getEntityType())
					&& this.weight == weight.getWeight();
		}
	}
	
	
	public void print(){
		System.out.println("EntityTypeWeight");
		System.out.println("\t entityType: " + entityType);
		System.out.println("\t weight: " + weight);
	}

	@Override
	public int hashCode() {
		return Arrays.hashCode(new int[] { this.getEntityType().hashCode(), this.getWeight() });
	}

}
/*
 * Copyright 2017 by Orbis Technologies, Inc.  All rights reserved.
 */
package com.orbis.mirve.core;

import com.fasterxml.jackson.annotation.JsonValue;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.common.base.Joiner;

public class ClassHierarchy implements Serializable, Comparable<ClassHierarchy> {

	/**
	 *
	 */
	private static final long serialVersionUID = -4704101825129542458L;
	private List<String> typeArray;
	//transient was removed to make delimiter serializable
	private String delimeter = ":";

	public ClassHierarchy() {
		this.typeArray = new ArrayList<>();
	}

	public ClassHierarchy(String fullType) {
		// split by non-word characters
		this.typeArray = Arrays.asList(fullType.split("\\W"));
	}

	/**
	 * 
	 * @param fullType The type string, e.g., "Agent.PERSON"
	 * @param delimiter The text delimiter
	 */
	public ClassHierarchy(String fullType, String delimiter) {
		this.delimeter = delimiter;
		if (fullType == null) {
			fullType = "";
		}
		this.typeArray = Arrays.asList(fullType.split(determineDelimiterRegex(delimiter)));

	}

	public ClassHierarchy(ArrayList<String> typeArray) {
		this.typeArray = typeArray;
	}

	public ClassHierarchy add(String classLevel) {
		typeArray.add(classLevel);
		return this;
	}

	public List<String> getTypeArray() {
		return typeArray;
	}

	public void setTypeArray(List<String> typeArray) {
		this.typeArray = typeArray;
	}

	public String getDelimeter() {
		return delimeter;
	}

	public void setDelimeter(String delimeter) {
		this.delimeter = delimeter;
	}

	/**
	 * This method will return the highest Ontology level
	 *
	 * @return
	 */
	public String getTopSuperClass() {
		if (typeArray.isEmpty())
			return null;
		return typeArray.get(0);
	}

	public String getLevel(int index) {
		if ((0 <= index) && (index <= (typeArray.size() - 1))) {
			return typeArray.get(index);
		}
		return null;
	}

	public boolean isSuperClassOf(ClassHierarchy classHierarchy2) {

		if (classHierarchy2 == null)
			return false;
		// Make sure that the size of this object's type array is smaller than
		// the other object's type array
		if (this.typeArray.size() < classHierarchy2.getTypeArray().size()) {

			for (int i = 0; i < typeArray.size(); i++) {

				// If any of the Strings in the array don't match, return false
				if (!(this.typeArray.get(i).equals(classHierarchy2.getTypeArray().get(i)))) {
					return false;
				}
			}
			// This class hierarchy is a superclass of the other class hierarchy
			return true;
		}

		return false;
	}

	public boolean isSuperClassOf(String newTypeString, String newDelimiter) {
		if (newTypeString == null || newDelimiter == null)
			return false;
		ClassHierarchy classHierarchy2 = new ClassHierarchy(newTypeString, newDelimiter);
		return isSuperClassOf(classHierarchy2);
	}

	public boolean isSubClassOf(ClassHierarchy classHierarchy2) {

		if (classHierarchy2 == null)
			return false;

		// Make sure that the size of this object's type array is greater than
		// the other object's type array
		if (this.typeArray.size() > classHierarchy2.getTypeArray().size()) {

			for (int i = 0; i < classHierarchy2.getTypeArray().size(); i++) {

				// If any of the Strings in the array don't match, return false
				if (!(this.typeArray.get(i).equals(classHierarchy2.getTypeArray().get(i)))) {
					return false;
				}
			}
			// This class hierarchy is a superclass of the other class hierarchy
			return true;
		}

		return false;
	}

	public boolean isSubClassOf(String newTypeString, String newDelimiter) {
		if (newTypeString == null || newDelimiter == null)
			return false;
		ClassHierarchy classHierarchy2 = new ClassHierarchy(newTypeString, newDelimiter);
		return isSubClassOf(classHierarchy2);
	}

	@Override
	public boolean equals(Object classHierarchy2) {

		if (classHierarchy2 == this)
			return true;
		if (!(classHierarchy2 instanceof ClassHierarchy))
			return false;
		return this.typeArray.equals(((ClassHierarchy) classHierarchy2).getTypeArray());
	}

	@JsonValue
	@Override
	public String toString() {
		return Joiner.on(delimeter).join(typeArray);
	}

	public int compareTo(ClassHierarchy other) {
		if (this.equals(other)) {
			return 0;
		}
		return -1;
	}

	@Override
	public int hashCode() {
		return Arrays.hashCode(new int[] { this.getTopSuperClass().hashCode(), this.getTypeArray().hashCode() });
	}
	
	/**
	 * Determines Regular Expression for the delimiter. This is where a regular expression can be added to match special 
	 * delimiters. 
	 * Example: "." delimiter cannot be used without two leading backslashes as "." equates to everything in Regular Expression. 
	 * @param delimiter
	 * @return
	 */
	private String determineDelimiterRegex(String delimiter) {
		
		if(delimiter == null) return delimiter;
		
		if(".".contentEquals(delimiter)) return "\\.";
		
		if("\\".contentEquals(delimiter)) return "\\\\";
		
		return delimiter;
	}

}

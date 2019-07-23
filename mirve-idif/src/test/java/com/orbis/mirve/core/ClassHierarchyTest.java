/*
 * Copyright 2017 by Orbis Technologies, Inc.  All rights reserved.
 */
package com.orbis.mirve.core;

import java.util.ArrayList;

import org.junit.*;

/**
 *
 * @author jbaker
 */
public class ClassHierarchyTest {
	
	@Test
	public void testConstructorForNoDelimiter(){
		ClassHierarchy type1 = new ClassHierarchy("a;b;c");
		ClassHierarchy type2 = new ClassHierarchy("a b c");
		ClassHierarchy type3 = new ClassHierarchy("a;b-c");
		ClassHierarchy type4 = new ClassHierarchy("a.b.c");
		ArrayList<String> typeArray = new ArrayList<>();
		typeArray.add("a");
		typeArray.add("b");
		typeArray.add("c");
		Assert.assertTrue(type1.getTypeArray().equals(typeArray));
		Assert.assertTrue(type2.getTypeArray().equals(typeArray));
		Assert.assertTrue(type3.getTypeArray().equals(typeArray));
		Assert.assertTrue(type4.getTypeArray().equals(typeArray));
		
	}
    
    @Test
    public void testGetTopSuperClass(){
        ClassHierarchy type1 = new ClassHierarchy();
        type1.add("a").add("b").add("c");
        
        ClassHierarchy type2 = new ClassHierarchy();
        type2.add("d").add("e").add("f");
        
        ClassHierarchy type3 = new ClassHierarchy();
        
        
        // Should return true
        Assert.assertTrue(type1.getTopSuperClass().equals("a"));
        Assert.assertTrue(type2.getTopSuperClass().equals("d"));
        
        // Should return null
        Assert.assertNull(type3.getTopSuperClass());
        
    }
            
    @Test
    public void testGetLevel(){
        ClassHierarchy type1 = new ClassHierarchy();
        type1.add("a").add("b").add("c");
        
        ClassHierarchy type2 = new ClassHierarchy();
        
        
        // Should return true
        Assert.assertTrue(type1.getLevel(0).equals("a"));
        Assert.assertTrue(type1.getLevel(1).equals("b"));
        Assert.assertTrue(type1.getLevel(2).equals("c"));
        
        // Should return null
        Assert.assertNull(type1.getLevel(-1));
        Assert.assertNull(type1.getLevel(3));
        Assert.assertNull(type2.getLevel(0));
    }

    @Test
    public void testIsSuperClassOf(){
        ClassHierarchy type1 = new ClassHierarchy();
        
        ClassHierarchy type2 = new ClassHierarchy();
        type2.add("a");
        
        ClassHierarchy type3 = new ClassHierarchy();
        type3.add("a").add("b");
        
        ClassHierarchy type4 = new ClassHierarchy();
        type4.add("a").add("b").add("c");
        
        ClassHierarchy type5 = null;
        
        
        // Should return true
        Assert.assertTrue(type1.isSuperClassOf(type2));
        Assert.assertTrue(type1.isSuperClassOf(type3));
        Assert.assertTrue(type1.isSuperClassOf(type4));
        
        Assert.assertTrue(type2.isSuperClassOf(type3));
        Assert.assertTrue(type2.isSuperClassOf(type4));
        
        Assert.assertTrue(type3.isSuperClassOf(type4));
        
        
        // Should return false
        Assert.assertFalse(type1.isSuperClassOf(type5));
        Assert.assertFalse(type2.isSuperClassOf(type5));
        Assert.assertFalse(type3.isSuperClassOf(type5));
        Assert.assertFalse(type4.isSuperClassOf(type5));
        
        Assert.assertFalse(type2.isSuperClassOf(type1));
        Assert.assertFalse(type3.isSuperClassOf(type1));
        Assert.assertFalse(type4.isSuperClassOf(type1));
        
        Assert.assertFalse(type3.isSuperClassOf(type2));
        Assert.assertFalse(type4.isSuperClassOf(type2));
        
        Assert.assertFalse(type4.isSuperClassOf(type3));
        
        
    }
    
    
    @Test
    public void testIsSuperClassOfString(){
        ClassHierarchy type1 = new ClassHierarchy();
        type1.add("a").add("b").add("c");
        
        String typeString1 = "a:b:c:d";
        
        // Should return true
        Assert.assertTrue(type1.isSuperClassOf(typeString1, ":"));
    }
     
    @Test
    public void testIsSubClassOf(){
        
        ClassHierarchy type1 = new ClassHierarchy();
        
        ClassHierarchy type2 = new ClassHierarchy();
        type2.add("a");
        
        ClassHierarchy type3 = new ClassHierarchy();
        type3.add("a").add("b");
        
        ClassHierarchy type4 = new ClassHierarchy();
        type4.add("a").add("b").add("c");
        
        ClassHierarchy type5 = null;
        
        
        // Should return true
        Assert.assertTrue(type2.isSubClassOf(type1));
        Assert.assertTrue(type3.isSubClassOf(type1));
        Assert.assertTrue(type4.isSubClassOf(type1));
        
        Assert.assertTrue(type3.isSubClassOf(type2));
        Assert.assertTrue(type4.isSubClassOf(type2));
        
        Assert.assertTrue(type4.isSubClassOf(type3));
        
        
        // Should return false
        Assert.assertFalse(type1.isSubClassOf(type5));
        Assert.assertFalse(type2.isSubClassOf(type5));
        Assert.assertFalse(type3.isSubClassOf(type5));
        Assert.assertFalse(type4.isSubClassOf(type5));
        
        Assert.assertFalse(type1.isSubClassOf(type2));
        Assert.assertFalse(type1.isSubClassOf(type3));
        Assert.assertFalse(type1.isSubClassOf(type4));
        
        Assert.assertFalse(type2.isSubClassOf(type3));
        Assert.assertFalse(type2.isSubClassOf(type4));
        
        Assert.assertFalse(type3.isSubClassOf(type4));
    }
    
    @Test
    public void testIsSubClassOfString(){
        ClassHierarchy type1 = new ClassHierarchy();
        type1.add("a").add("b").add("c").add("d");
        
        String typeString1 = "a:b:c";
        
        // Should return true
        Assert.assertTrue(type1.isSubClassOf(typeString1, ":"));
    }
    
    @Test
    public void testEquals(){
        
        ClassHierarchy type1 = new ClassHierarchy();
        type1.add("a").add("b").add("c");
        
        ClassHierarchy type2 = new ClassHierarchy();
        type2.add("a").add("b").add("c");
        
        ClassHierarchy type3 = new ClassHierarchy();
        type3.add("a").add("b").add("x");
        
        ClassHierarchy type4 = new ClassHierarchy();
        type4.add("a").add("b");
        
        ClassHierarchy type5 = new ClassHierarchy();
        type5.add("a").add("b").add("c").add("d");
        
        ClassHierarchy type6 = new ClassHierarchy();
        
        ClassHierarchy type7 = null;
        
        
        // Should return true
        Assert.assertTrue(type1.equals(type1));
        Assert.assertTrue(type1.equals(type2));
        Assert.assertTrue(type2.equals(type1));
        
        // Should return false
        Assert.assertFalse(type1.equals(type3));
        Assert.assertFalse(type1.equals(type4));
        Assert.assertFalse(type1.equals(type5));
        Assert.assertFalse(type1.equals(type6));
        Assert.assertFalse(type1.equals(type7));
        Assert.assertFalse(type3.equals(type1));
        Assert.assertFalse(type4.equals(type1));
        Assert.assertFalse(type5.equals(type1));
        Assert.assertFalse(type6.equals(type1));
    }
    
    @Test
    public void testDelimiter() {
    	
    	ClassHierarchy typeCH1 = new ClassHierarchy("Agent PERSON", " ");
    	Assert.assertEquals(2, typeCH1.getTypeArray().size());
    	Assert.assertEquals("Agent", typeCH1.getTypeArray().get(0));
    	Assert.assertEquals("PERSON", typeCH1.getTypeArray().get(1));
    	
    	ClassHierarchy typeCH2 = new ClassHierarchy("Agent.PERSON", ".");
    	Assert.assertEquals(2, typeCH2.getTypeArray().size());
    	Assert.assertEquals("Agent", typeCH2.getTypeArray().get(0));
    	Assert.assertEquals("PERSON", typeCH2.getTypeArray().get(1));
    	
    	ClassHierarchy typeCH3 = new ClassHierarchy("Agent\\PERSON", "\\");
    	Assert.assertEquals(2, typeCH3.getTypeArray().size());
    	Assert.assertEquals("Agent", typeCH3.getTypeArray().get(0));
    	Assert.assertEquals("PERSON", typeCH3.getTypeArray().get(1));
    		
    }
}

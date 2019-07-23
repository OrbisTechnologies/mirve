/*
 * Copyright 2017 by Orbis Technologies, Inc.  All rights reserved.
 */
package com.orbis.mirve.core;

import org.junit.*;

/**
 *
 * @author jbaker
 */
public class OffsetTest {

    @Test
    public void testContains() {
        Offset o1 = new Offset(5, 10);
        Offset o2 = new Offset(0, 5); // no overlap, edge
        Offset o3 = new Offset(0, 6); // overlap start
        Offset o4 = new Offset(4, 11); // contained in
        Offset o5 = new Offset(6, 9); // contains
        Offset o6 = new Offset(10, 15); // no overlap, edge
        Offset o7 = new Offset(9, 15); // overlap end
        Offset o8 = new Offset(0, 3); // no overlap
        Offset o9 = new Offset(15, 20); // no overlap
        Offset o10 = null;

        // Should return true
        Assert.assertTrue(o1.contains(o5)); // contains
        Assert.assertTrue(o1.contains(o1)); // equals

        // Should return false
        Assert.assertFalse(o1.contains(o3)); // overlap start
        Assert.assertFalse(o1.contains(o4)); // contained in
        Assert.assertFalse(o1.contains(o7)); // overlap end
        Assert.assertFalse(o1.contains(o2));
        Assert.assertFalse(o1.contains(o6));
        Assert.assertFalse(o1.contains(o8));
        Assert.assertFalse(o1.contains(o9));
        Assert.assertFalse(o1.contains(o10));

    }

    @Test
    public void testContainedIn() {
        Offset o1 = new Offset(5, 10);
        Offset o2 = new Offset(0, 5); // no overlap, edge
        Offset o3 = new Offset(0, 6); // overlap start
        Offset o4 = new Offset(4, 11); // contained in
        Offset o5 = new Offset(6, 9); // contains
        Offset o6 = new Offset(10, 15); // no overlap, edge
        Offset o7 = new Offset(9, 15); // overlap end
        Offset o8 = new Offset(0, 3); // no overlap
        Offset o9 = new Offset(15, 20); // no overlap
        Offset o10 = null;

        // Should return true
        Assert.assertTrue(o1.containedIn(o4)); // contained in
        Assert.assertTrue(o1.containedIn(o1)); // equals

        // Should return false
        Assert.assertFalse(o1.containedIn(o3)); // overlap start
        Assert.assertFalse(o1.containedIn(o5)); // contains
        Assert.assertFalse(o1.containedIn(o7)); // overlap end
        Assert.assertFalse(o1.containedIn(o2));
        Assert.assertFalse(o1.containedIn(o6));
        Assert.assertFalse(o1.containedIn(o8));
        Assert.assertFalse(o1.containedIn(o9));
        Assert.assertFalse(o1.contains(o10));

    }

    @Test
    public void testHasOverlap() {
        Offset o1 = new Offset(5, 10);
        Offset o2 = new Offset(0, 5); // no overlap, edge
        Offset o3 = new Offset(0, 6); // overlap start
        Offset o4 = new Offset(4, 11); // contained in
        Offset o5 = new Offset(6, 9); // contains
        Offset o6 = new Offset(10, 15); // no overlap, edge
        Offset o7 = new Offset(9, 15); // overlap end
        Offset o8 = new Offset(0, 3); // no overlap
        Offset o9 = new Offset(15, 20); // no overlap
        Offset o10 = null;

        // Should return true
        Assert.assertTrue(o1.hasOverlap(o1)); // equals
        Assert.assertTrue(o1.hasOverlap(o3)); // overlap start
        Assert.assertTrue(o1.hasOverlap(o4)); // contained in
        Assert.assertTrue(o1.hasOverlap(o5)); // contains
        Assert.assertTrue(o1.hasOverlap(o7)); // overlap end

        // Should return false
        Assert.assertFalse(o1.hasOverlap(o2));
        Assert.assertFalse(o1.hasOverlap(o6));
        Assert.assertFalse(o1.hasOverlap(o8));
        Assert.assertFalse(o1.hasOverlap(o9));
        Assert.assertFalse(o1.contains(o10));

    }

    @Test
    public void testEquals() {
        Offset o1 = new Offset(5, 10);
        Offset o2 = new Offset(0, 5); // no overlap, edge
        Offset o3 = new Offset(0, 6); // overlap start
        Offset o4 = new Offset(4, 11); // contained in
        Offset o5 = new Offset(6, 9); // contains
        Offset o6 = new Offset(10, 15); // no overlap, edge
        Offset o7 = new Offset(9, 15); // overlap end
        Offset o8 = new Offset(0, 3); // no overlap
        Offset o9 = new Offset(15, 20); // no overlap
        Offset o10 = null;

        // Should return true
        Assert.assertTrue(o1.equals(o1)); // equals

        // Should return false
        Assert.assertFalse(o1.equals(o3)); // overlap start
        Assert.assertFalse(o1.equals(o4)); // contained in
        Assert.assertFalse(o1.equals(o5)); // contains
        Assert.assertFalse(o1.equals(o7)); // overlap end
        Assert.assertFalse(o1.equals(o2));
        Assert.assertFalse(o1.equals(o6));
        Assert.assertFalse(o1.equals(o8));
        Assert.assertFalse(o1.equals(o9));
        Assert.assertFalse(o1.equals(o10));

    }

}

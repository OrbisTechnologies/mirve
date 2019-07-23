/*
 * Copyright 2017 by Orbis Technologies, Inc.  All rights reserved.
 */
package com.orbis.mirve.core;

import java.io.Serializable;

public class Offset implements Serializable,Comparable<Offset> {
	
	private int startOffset;
	
	private int endOffset;
	
	public Offset(){
		super();
	}
	
	public Offset(int startOffset, int endOffset) {
		super();
		this.startOffset = startOffset;
		this.endOffset = endOffset;
	}

	public int getStartOffset() {
		return startOffset;
	}

	public void setStartOffset(int startOffset) {
		this.startOffset = startOffset;
	}

	public int getEndOffset() {
		return endOffset;
	}

	public void setEndOffset(int endOffset) {
		this.endOffset = endOffset;
	}
	
	public boolean hasOverlap(Offset offset2){
                if (offset2 == null) return false;
		return  (offset2.getStartOffset() < this.startOffset
				&& this.startOffset < offset2.getEndOffset()) || 
                        (offset2.getStartOffset() < this.endOffset
				&& this.endOffset < offset2.getEndOffset()) ||
                        contains(offset2) ||
                        containedIn(offset2) ||
                        equals(offset2);
	}
        
        
        public boolean contains(Offset offset2){
            if (offset2 == null) return false;
            return (this.startOffset <= offset2.getStartOffset()
				&& offset2.getEndOffset() <= this.endOffset);
        }
        
        public boolean containedIn(Offset offset2){
            if (offset2 == null) return false;
            return (offset2.getStartOffset() <= this.startOffset
				&& this.endOffset <= offset2.getEndOffset());
        }
        
        
        @Override
    	public int hashCode() {
    		final int prime = 31;
    		int result = 1;
    		result = prime * result + endOffset;
    		result = prime * result + startOffset;
    		return result;
    	}

    	@Override
    	public boolean equals(Object offset2) {
    		if (offset2 == this)
    			return true;
    		if (!(offset2 instanceof Offset))
    			return false;
    		return ((this.startOffset == ((Offset) offset2).getStartOffset())
    				&& (this.endOffset == ((Offset) offset2).getEndOffset()));
    	}

		public int compareTo(Offset other) {
			if (this.equals(other)){
				return 0;
			}
			return -1;
		}

}

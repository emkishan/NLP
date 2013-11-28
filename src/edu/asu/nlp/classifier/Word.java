package edu.asu.nlp.classifier;

public class Word {
	private int posCount=0, negCount=0, posDocCount=0, negDocCount=0;

	public int getPosCount() {
		return posCount;
	}

	public void setPosCount(int posCount) {
		this.posCount = posCount;
	}

	public int getNegCount() {
		return negCount;
	}

	public void setNegCount(int negCount) {
		this.negCount = negCount;
	}

	public int getPosDocCount() {
		return posDocCount;
	}

	public void setPosDocCount(int posDocCount) {
		this.posDocCount = posDocCount;
	}

	public int getNegDocCount() {
		return negDocCount;
	}

	public void setNegDocCount(int negDocCount) {
		this.negDocCount = negDocCount;
	}

	public void incrementPosCount() {
		this.posCount++;
		
	}
	
	public void incrementNegCount() {
		this.negCount++;
		
	}
	
	public void incrementPosDocCount() {
		this.posDocCount++;
	}
	
	public void incrementNegDocCount() {
		this.negDocCount++;
	}
}

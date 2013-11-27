package edu.asu.nlp.main;

import edu.asu.nlp.amazon.LabelData;
import edu.asu.nlp.util.Constants;

public class MainClass {
	public static void main(String args[]){
		LabelData.readFileData(Constants.AMAZON_REVIEWS_FILE);
	}
}

package edu.asu.nlp.classifier;

import java.util.HashMap;
import java.util.StringTokenizer;

import edu.asu.nlp.util.Constants;
import edu.asu.nlp.util.WordUtils;

public class ClassifyReview {
	public static float classifyReview(HashMap<String,Double> scores, String review, boolean... debugFlag){
		double sum = 0;
		StringTokenizer tokenizer = new StringTokenizer(review.toLowerCase());
		while(tokenizer.hasMoreTokens()){
			String token = WordUtils.extractWord(tokenizer.nextToken());
			if(!Constants.STOPWORDS.contains(token)){
				if(scores.containsKey(token)){
					if(debugFlag.length>0 && debugFlag[0]){
						System.out.println("Token: " + token + "\tScore" + scores.get(token));
					}
					sum += scores.get(token);
				}
			}
		}
		return (float)sum;
	}
}

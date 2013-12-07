package edu.asu.nlp.amazon;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import edu.asu.nlp.util.Constants;

public class LabelData {
	static int positiveCount, negativeCount;
	public static void readFileData(String filePath){
		try {
			BufferedReader buf = new BufferedReader(new FileReader(filePath));
			for(int i=0;i<Constants.AMAZON_NUMBER_OF_REVIEWS;i++){
				if(i%1000==0)
					System.out.println("Reading Amazon reviews in process (" + (float)i/Constants.AMAZON_NUMBER_OF_REVIEWS*100 + "% done)");
				readAReview(buf);
				if(buf.readLine()==null)
					break;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void readAReview(BufferedReader buf) {
		String[] lines = new String[Constants.AMAZON_REVIEW_LENGTH];
		for(int i=0;i<Constants.AMAZON_REVIEW_LENGTH;i++){
			try {
				String line = buf.readLine();
				lines[i]=line;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		String productId = getValue(lines[0]);
		String userId = getValue(lines[1]);
		float helpfulness = getHelpfulnessScore(getValue(lines[3]));
		float score = Float.parseFloat(getValue(lines[4]));
		String review = getValue(lines[7]);
		if(productId.startsWith("B")){
			if(helpfulness>Constants.AMAZON_HELPFULNESS_THRESHOLD){
				//Valid review

				if(score<Constants.AMAZON_MIN_REVIEW_SCORE){
					if(positiveCount<=Constants.AMAZON_MAX_REVIEWS){
						positiveCount++;
						writeFile(productId,userId,review,false);
					}
				}
				else if(score>Constants.AMAZON_MAX_REVIEW_SCORE){
					if(negativeCount<=Constants.AMAZON_MAX_REVIEWS){
						negativeCount++;
						writeFile(productId,userId,review,true);
					}
				}
			}
		}
		
	}
	
	private static void writeFile(String productId, String userId, String review, boolean isPositive){
		String folderName = Constants.AMAZON_REVIEW_RESULT_PATH;
		if(isPositive){
			folderName = folderName + "pos/";
		}
		else{
			folderName = folderName + "neg/";
		}
		File f = new File(folderName+productId+Constants.SEPARATOR + userId + Constants.EXTENSION);
		try {
			f.createNewFile();
			BufferedWriter buf = new BufferedWriter(new FileWriter(f.getAbsoluteFile()));
			buf.write(review);
			buf.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static String getValue(String line){
		int index = line.indexOf(':');
		return line.substring(index+1).trim();
	}
	
	private static float getHelpfulnessScore(String value){
		String[] tokens = value.split("/");
		int num = 0;
		int den = 0;
		num = Integer.parseInt(tokens[0]);
		den = Integer.parseInt(tokens[1]);
		
		float returnValue = 0;
		if(den!=0)
			returnValue = (float)num/den;
		return returnValue;
	}
}

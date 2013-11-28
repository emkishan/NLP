package edu.asu.nlp.main;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;

import edu.asu.nlp.classifier.Classifier;
import edu.asu.nlp.util.Constants;

public class MainClass {
	public static void main(String args[]){
		//LabelData.readFileData(Constants.AMAZON_REVIEWS_FILE);
		initializeStopWords();
		Classifier classifier = new Classifier();
		classifier.processPath(Constants.AMAZON_REVIEW_RESULT_PATH+"pos/", true);
		classifier.processPath(Constants.AMAZON_REVIEW_RESULT_PATH+"neg/", false);
		classifier.printAllWords();
	}

	private static void initializeStopWords() {
		try {
			BufferedReader buf = new BufferedReader(new FileReader(Constants.STOPWORDS_FILE));
			HashSet<String> stopwords = new HashSet<String>();
			String line = null;
			while((line=buf.readLine())!=null){
				stopwords.add(line);
			}
			Constants.STOPWORDS = stopwords;
			buf.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

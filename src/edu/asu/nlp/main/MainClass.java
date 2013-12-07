package edu.asu.nlp.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import edu.asu.nlp.amazon.LabelData;
import edu.asu.nlp.classifier.Classifier;
import edu.asu.nlp.classifier.ClassifyReview;
import edu.asu.nlp.util.Constants;

public class MainClass {
	public static void main(String args[]){
		LabelData.readFileData(Constants.AMAZON_REVIEWS_FILE);
		initializeStopWords();
		Classifier classifier = new Classifier();
		classifier.processPath(Constants.AMAZON_REVIEW_RESULT_PATH);
		Classifier imdbClassifier = new Classifier();
		imdbClassifier.processPath(Constants.IMDB_REVIEW_PATH);
		//classifier.printAllWords();
		HashMap<String,Double> amazonReviews = classifier.getTfIdfMap();
		HashMap<String,Double> imdbReviews = imdbClassifier.getTfIdfMap();
		Iterator<Entry<String, Double>> iterator = imdbReviews.entrySet().iterator();
		int agreedWords=0, disagreedWords=0;
		double diff = 0;
		while(iterator.hasNext()){
			Map.Entry<String, Double> pair = (Map.Entry<String, Double>) iterator.next();
			String key = pair.getKey();
			if(amazonReviews.containsKey(key)){
				if(amazonReviews.get(key) * imdbReviews.get(key)>0)
					agreedWords++;
				else{
					diff += Math.abs(amazonReviews.get(key)-imdbReviews.get(key));
					disagreedWords++;
				}
			}
		}
		System.out.println("Agreed Words : " +agreedWords );
		System.out.println("Disagreed Words : " + disagreedWords);
		System.out.println("Average Difference : " + (diff/disagreedWords));
		while(true){
			System.out.println("Enter review to process");
			BufferedReader buf = new BufferedReader(new InputStreamReader(System.in));
			
			try {
				String line = buf.readLine();
				File folder = new File(line);
				File[] allFiles = folder.listFiles();
				int agreed=0;
				for(int i=0;i<5000;i++){
					BufferedReader file = new BufferedReader(new FileReader(allFiles[i]));
					String review = file.readLine();
					float amazonScore,imdbScore;
					
					if(allFiles[i].getAbsolutePath().contains("3232_9")){
						System.out.println("Amazon Score");
						amazonScore = ClassifyReview.classifyReview(amazonReviews, review,true);
						System.out.println("IMDB Scores");
						imdbScore = ClassifyReview.classifyReview(imdbReviews, review,true);
					}
					else{
						amazonScore = ClassifyReview.classifyReview(amazonReviews, review);
						imdbScore = ClassifyReview.classifyReview(imdbReviews, review);
					}
					if(imdbScore*amazonScore>0)
						agreed++;
					else{
						System.out.println(allFiles[i].getAbsolutePath());
						System.out.println("IMDB Score : " + imdbScore);
						System.out.println("Amazon Score : " + amazonScore);
					}
					file.close();
				}
				System.out.println("Total agreements : " + agreed);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
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

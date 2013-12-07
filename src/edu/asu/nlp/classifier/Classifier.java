package edu.asu.nlp.classifier;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.StringTokenizer;

import edu.asu.nlp.util.Constants;
import edu.asu.nlp.util.WordUtils;

public class Classifier {
	HashMap<String,Word> allWords = new HashMap<String,Word>();
	HashMap<String,Double> tfidfMap = new HashMap<String,Double>();
	boolean isPositive = true;
	int posWordsCount = 0;
	int negWordsCount = 0;
	int posDocCount = 0;
	int negDocCount = 0;
	
	public void processReview(String review){
		StringTokenizer tokenizer = new StringTokenizer(review.toLowerCase());
		HashSet<String> reviewWords = new HashSet<String>();
		while(tokenizer.hasMoreTokens()){
			String token = tokenizer.nextToken();
			if(!isStopWord(token)){
				token = WordUtils.extractWord(token);
				Word word = new Word();
				if(allWords.containsKey(token))
					word = allWords.get(token);
				if(isPositive){
					posWordsCount++;
					word.incrementPosCount();
					if(!reviewWords.contains(token)){
						word.incrementPosDocCount();
						reviewWords.add(token);
					}
				}
				else{
					negWordsCount++;
					word.incrementNegCount();
					if(!reviewWords.contains(token)){
						word.incrementNegDocCount();
						reviewWords.add(token);
					}
				}
				allWords.put(token, word);
			}
		}
		if(isPositive)
			posDocCount++;
		else
			negDocCount++;
	}
	
	private void calculateTFIDF(HashMap<String, Word> allWords){
		double tfScore = 0;
		double idfScore = 0;
		double wordScore =0;
		for(Map.Entry<String, Word> entry : allWords.entrySet()){
			Word word = allWords.get(entry.getKey());	
			
			tfScore = (double)word.getPosCount()/posWordsCount;
			if(word.getPosDocCount()!=0)
				idfScore = Math.log((double)posDocCount/word.getPosDocCount());
			else
				idfScore = 0;
			wordScore = tfScore*idfScore;


			tfScore = (double)word.getNegCount()/negWordsCount;
			if(word.getNegDocCount()!=0)
				idfScore = Math.log((double)negDocCount/word.getNegDocCount());
			else
				idfScore = 0;
			wordScore = wordScore - tfScore*idfScore;

			tfidfMap.put(entry.getKey(), wordScore);
		}
		
	}
	private boolean isStopWord(String word) {
		if(Constants.STOPWORDS.contains(word))
			return true;
		else
			return false;
	}

	public void processPath(String path){
		this.isPositive = true;
		String newPath;
		for(int j=0;j<2;j++){
			if(isPositive){
				newPath = path + "pos/";
			}
			else{
				newPath = path + "neg/";
			}
			File folder = new File(newPath);
			File[] allFiles = folder.listFiles();
			for(int i=0;i<allFiles.length;i++){
				if(i%1000==0)
					System.out.println((float)i/allFiles.length*100 + "% done");
				BufferedReader buf=null; 
				try {
					buf = new BufferedReader(new FileReader(allFiles[i]));
					String review = buf.readLine();
					processReview(review);
				} 
				catch (FileNotFoundException e) {
					e.printStackTrace();
				} 
				catch (IOException e) {
					e.printStackTrace();
				}
				finally{
					try {
						buf.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}

			}
			if(isPositive)
				isPositive = false;
		}
		calculateTFIDF(allWords);
	}
	
	public void printAllWords(){
		Iterator<Entry<String, Word>> iter = allWords.entrySet().iterator();
		while(iter.hasNext()){
			Map.Entry<String,Word> pair = (Map.Entry<String,Word>) iter.next();
			System.out.println("Word : " + pair.getKey());
			Word w = (Word) pair.getValue();
			System.out.println(w.getNegCount() +"," + w.getNegDocCount() +"," +w.getPosCount()+","+w.getPosDocCount());
		}
	}

	public HashMap<String, Double> getTfIdfMap() {
		return tfidfMap;
	}
	
}

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

public class Classifier {
	HashMap<String,Word> allWords = new HashMap<String,Word>();
	boolean isPositive = true;
	
	public void processReview(String review){
		StringTokenizer tokenizer = new StringTokenizer(review.toLowerCase());
		HashSet<String> reviewWords = new HashSet<String>();
		while(tokenizer.hasMoreTokens()){
			String token = tokenizer.nextToken();
			if(!isStopWord(token)){
				Word word = new Word();
				if(allWords.containsKey(token))
					word = allWords.get(token);
				if(isPositive){
					word.incrementPosCount();
					if(!reviewWords.contains(token)){
						word.incrementPosDocCount();
						reviewWords.add(token);
					}
				}
				else{
					word.incrementNegCount();
					if(!reviewWords.contains(token)){
						word.incrementNegDocCount();
						reviewWords.add(token);
					}
				}
				allWords.put(token, word);
			}
		}
	}
	
	private boolean isStopWord(String word) {
		if(Constants.STOPWORDS.contains(word))
			return true;
		else
			return false;
	}

	public void processPath(String path,boolean isPositive){
		this.isPositive = isPositive;
		File folder = new File(path);
		File[] allFiles = folder.listFiles();
		for(int i=0;i<allFiles.length;i++){
			BufferedReader buf=null; 
			try {
				buf = new BufferedReader(new FileReader(allFiles[i]));
				String review = buf.readLine();
				processReview(review);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
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
		
		System.out.println("Yo bro..yo");
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
	
}

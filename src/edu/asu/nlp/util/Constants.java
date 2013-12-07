package edu.asu.nlp.util;

import java.util.HashSet;

public class Constants {
	public static final int AMAZON_NUMBER_OF_REVIEWS = 500000;
	public static final String AMAZON_REVIEW_RESULT_PATH = "/Users/kishanmaddula/Documents/AmazonReviews/";
	public static final String AMAZON_REVIEWS_FILE = "/Users/kishanmaddula/Downloads/movies.txt";
	public static final String SEPARATOR = "_";
	public static final int AMAZON_MAX_REVIEWS = 11000;
	public static final String EXTENSION = ".txt";
	public static int AMAZON_REVIEW_LENGTH=8;
	public static float AMAZON_MIN_REVIEW_SCORE = 2.0f;
	public static float AMAZON_MAX_REVIEW_SCORE = 3.0f;
	public static float AMAZON_HELPFULNESS_THRESHOLD = 0.50f;
	public static String STOPWORDS_FILE = "stopwords";
	public static String IMDB_REVIEW_PATH = "/Users/kishanmaddula/Downloads/aclImdb/train/";
	
	public static HashSet<String> STOPWORDS;
}

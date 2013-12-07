package edu.asu.nlp.util;

public class WordUtils {

	public static String extractWord(String token) {
		StringBuilder word = new StringBuilder();
		for(int i=0;i<token.length();i++){
			char ch = token.charAt(i);
			if((ch>='A' && ch<='Z')
					|| (ch>='a' && ch<='z')
					|| (ch>='0' && ch<='9') ){
				word.append(ch);
			}
			if(ch=='\'' && token.endsWith("s")){
				break;
			}
		}
		return word.toString();
	}

}

package edu.asu.nlp.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class IOUtils {
	public static String readFile(String path){
		String returnValue = new String();
		try {
			BufferedReader buf = new BufferedReader(new FileReader(path));
			returnValue = buf.readLine();
			buf.close();
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		return returnValue;
	}
}

/**
 * Contains some common constants and helper functions that are used
 * in our application
 * 
 * @author Dan Sanders, 4/29/13
 *
 */

package com.huskysoft.interviewannihilator.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import com.huskysoft.interviewannihilator.model.Likeable;

public class Utility {
	
	/**
	 * The name of the file in which user info is stored
	 */
	public static final String USER_INFO_FILENAME = "userInfo.txt";
	
	/**
	 * Default encoding for File I/O
	 */
	public static final String ASCII_ENCODING = "ASCII";
	
	/** The minimum number of likes needed to have to have a valid positive 
	 * rating */
	private static final int MIN_LIKES = 5;
	
	/** 
	 * For difficulty "All" special-case for slide-in menu
	 */
	public static final String ALL = "All";
	
	/**
	 * Calculates the rank based on the number of likes and
	 * dislikes of the question
	 * 
	 * @return a float representing the rating. This is
	 * calculated based on the ratio of likes to dislikes, as long as the
	 * question has at least a minimum number of likes. If it doesn't, 
	 * then it is given a negative rating, which starts at the negative of
	 * the minimum number and increments for every like it
	 * gets (until it reaches the min. # of likes).
	 */
	public double getRank(Likeable obj) {
		int likes = obj.getLikes();
		int dislikes = obj.getDislikes();

		if (likes < MIN_LIKES) {
			return (-1 * MIN_LIKES) + likes;
		}
		if (dislikes == 0) {
			return likes;
		}
		return ((double) likes / dislikes);
	}
	
	private static String convertStreamToString(InputStream is) 
			throws IOException {
		InputStreamReader isReader = new InputStreamReader(is, ASCII_ENCODING);
		BufferedReader reader = new BufferedReader(isReader);
		StringBuilder sb = new StringBuilder();
		String line = null;
		while ((line = reader.readLine()) != null) {
			sb.append(line).append('\n');
		}
		return sb.toString();
	}

	/**
	 * Read a text file as a String. Code from:
	 * http://stackoverflow.com/questions/12910503/android-read-file-as-string
	 * 
	 * @param filePath
	 * @return String contents of the file
	 * @throws IOException 
	 * @throws Exception
	 */
	public static String readStringFromFile(File file) throws IOException {
		FileInputStream fin = new FileInputStream(file);
		String ret = convertStreamToString(fin);
		fin.close();        
		return ret;
	}

	/**
	 * Write a String to a text file. Will overwrite existing file contents.
	 * Returns true if a new file was created, false if one was already present.
	 * 
	 * @param file
	 * @param string
	 * @throws IOException
	 */
	public static boolean writeStringToFile(File file, String string) 
			throws IOException {
		boolean newFile = file.createNewFile();
		FileOutputStream fout = new FileOutputStream(file);
		OutputStreamWriter out 
			= new OutputStreamWriter(fout, ASCII_ENCODING);
		try {
			out.write(string);
		} finally {
			out.close();
		}
		return newFile;
	}
}

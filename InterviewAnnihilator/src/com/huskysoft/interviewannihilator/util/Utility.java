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

import com.huskysoft.interviewannihilator.model.Likeable;

public class Utility {
	
	/**
	 * The name of the file in which user info is stored
	 */
	public static final String USER_INFO_FILENAME = "userInfo.txt";
	
	/** The minimum number of likes needed to have to have a valid positive 
	 * rating */
	private static final int MIN_LIKES = 5;
	
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
	    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
	    StringBuilder sb = new StringBuilder();
	    String line = null;
	    while ((line = reader.readLine()) != null) {
	      sb.append(line).append("\n");
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
	 * 
	 * @param file
	 * @param string
	 * @throws IOException
	 */
	public static void writeStringToFile(File file, String string) 
			throws IOException {
		if (!file.exists()) {
			file.createNewFile();
		}
		FileOutputStream fout = new FileOutputStream(file);
		fout.write(string.getBytes());
		fout.close();
	}
}

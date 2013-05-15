/**
 * Contains some common constants and helper functions that are used
 * in our application
 * 
 * @author Dan Sanders, 4/29/13
 *
 */

package com.huskysoft.interviewannihilator.util;

import com.huskysoft.interviewannihilator.model.Likeable;

public class Utility {
	
	// The minimum number of likes needed to have to have a
	// a valid positive rating
	private static final int MIN_LIKES = 5;
	
	/*
	 * Used to pass a selected difficulty back to the 
	 * MainActivity class through the slide-menu.
	 */
	public static String DIFFICULTY_MESSAGE = "";
	
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
	
	
}

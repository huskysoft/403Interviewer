package com.huskysoft.interviewannihilator.util;

/**
 * Contains some common constants and helper functions that are used
 * in our application
 * 
 * @author Dan Sanders, 4/29/13
 *
 */
public class Utility {
	
	// The minimum number of likes needed to have to have a
	// a valid positive rating
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
	 * Returns null if an exception occurs.
	 */
	public Double getRank(int likes, int dislikes) {
		try {
			if (likes < MIN_LIKES) {
				return Double.valueOf((-1 * MIN_LIKES) + likes);
			}
			if (dislikes == 0) {
				return Double.valueOf(likes);
			}
			return ((double) likes / dislikes);
		}
		catch (Exception e) {
			return null;
		}
	}
}

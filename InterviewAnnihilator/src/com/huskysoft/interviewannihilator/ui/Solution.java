package com.huskysoft.interviewannihilator.ui;

import java.sql.Date;

/**
 * 
 * Governs the fields and behavior of a solution in our application
 * to a question. The solution "references" the question that it is
 * answering by storing the id of the question
 * 
 * @author Dan Sanders, 4/29/13
 *
 */
public class Solution {

	// The minimum number of likes a question needs to have to have a
	// a valid positive rating
	private static final int MIN_LIKES = 5;
	
	private int id;
	private String text;
	private int questionId;
	private int authorId;
	private Date dateCreated;
	private int likes;
	private int dislikes;
	
	/** 
	 * Called when we have received the data for a solution back from the
	 * database as a String in JSON format. We are creating a Solution object
	 * for our application to use
	 * 
	 * @param jsonSolution the String to parse that contains the property
	 * values for the solution
	 */
	public Solution(String jsonSolution) {
		// parse the String into a JSON object, then get its property values
	}
	
	/** Called when the user of our application wants to add a solution to the
	 *  database. Our database will populate the rest of the fields of the
	 *  solution
	 *  
	 * @param questionId the id of the question that this solution
	 * is "answering"
	 * @param text the solution's text
	 */
	public Solution(int questionId, String text) {
		this.questionId = questionId;
		this.text = text;
	}
	
	/**
	 * Gets the unique identification number of this solution
	 * 
	 * @return the unique id number of the solution
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Gets the text of this solution
	 * 
	 * @return the text of the solution
	 */
	public String getText() {
		return text;
	}
	
	/**
	 * Gets the id of the question that this solution is answering
	 * 
	 * @return the id of the question
	 */
	public int getQuestionId() {
		return questionId;
	}
	
	/**
	 * Gets the id of the author
	 *
	 * @return the id of the author of the solution
	 */
	public int getAuthorId() {
		return authorId;
	}
	
	/**
	 * Gets the date the solution was added to the database
	 * 
	 * @return a Date object representing when this solution was added
	 * to the database
	 */
	public Date getDateCreated() {
		return dateCreated;
	}
	
	/**
	 * Gets the number of likes of the solution
	 * 
	 * @return the number of likes
	 */
	public int getLikes() {
		return likes;
	}
	
	/**
	 * Gets the number of dislikes of the solution
	 * 
	 * @return the number of dislikes
	 */
	public int getDislikes() {
		return dislikes;
	}
	
	/**
	 * Calculates the rank of this solution based on the number of likes and
	 * dislikes of the solution
	 * 
	 * @return a float representing the rating of the solution. This is
	 * calculated based on the ratio of likes to dislikes, as long as the
	 * solution has at least a minimum number of likes. If it doesn't, 
	 * then it is given a negative rating, which starts at the negative of
	 * the minimum number and increments for every like it
	 * gets (until it reaches the min. # of likes)
	 */
	public double getRank() {
		if (likes < MIN_LIKES) {
			return ((-1 * MIN_LIKES) + likes);
		}
		return ((double) likes / dislikes);
	}
}

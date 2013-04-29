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
}

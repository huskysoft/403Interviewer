package com.huskysoft.interviewannihilator.ui;

import java.sql.Date;
import java.util.List;

import com.huskysoft.interviewannihilator.util.CategoryEnum;
import com.huskysoft.interviewannihilator.util.DifficultyEnum;

/**
 * 
 * Governs the fields and behavior of the questions that are created
 * and displayed on our application
 * 
 * @author Dan Sanders, 4/29/13
 *
 */
public class Question {
	
	private int id;
	private String text;
	private String title;
	private int authorId;
	private Date dateCreated;
	private List<CategoryEnum> category;
	private DifficultyEnum difficulty;
	private int likes;
	private int dislikes;
	
	/**
	 * Called when we have received the data for a question back from the
	 * database as a String in JSON format. We are creating a Question object
	 * for our application to use
	 * 
	 * @param jsonQuestion the String to parse that contains the property
	 * values for the question
	 */
	public Question(String jsonQuestion) {
		// parse the String into a JSON object, then get its property values
		
	}
	
	/**
	 * Called when our android application is trying to create a new question
	 * and load it into the database. The database will populate the rest of
	 * the fields and return the fleshed-out Question object back to the
	 * application
	 * 
	 * @param text
	 * @param title
	 * @param category
	 * @param difficulty
	 */
	public Question(String text, String title, List<CategoryEnum> category, 
			DifficultyEnum difficulty) {
		this.text = text;
		this.title = title;
		this.category = category;
		this.difficulty = difficulty;
	}
	
	/**
	 * Gets the unique identification number of this question
	 * 
	 * @return the unique id number of the question
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Gets the text of this question
	 * 
	 * @return the text of the question
	 */
	public String getText() {
		return text;
	}
	
	/**
	 * Gets the title of this question
	 * 
	 * @return the title of the question
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * Gets the id of the author
	 *
	 * @return the id of the author of the question
	 */
	public int getAuthorId() {
		return authorId;
	}
	
	/**
	 * Gets the date the question was added to the database
	 * 
	 * @return a Date object representing when this question was added
	 * to the database
	 */
	public Date getDateCreated() {
		return dateCreated;
	}
	
	/**
	 * Gets a list representing all of the different categories this question
	 * can be classified under
	 * 
	 * @return category of the question
	 */
	public List<CategoryEnum> getCategory() {
		return category;
	}
	
	/**
	 * Gets the difficulty rating of this question
	 * 
	 * @return the value in the difficultyEnum identifying how hard this
	 * question is
	 */
	public DifficultyEnum getDifficulty() {
		return difficulty;
	}
	
	/**
	 * Gets the number of likes of the question
	 * 
	 * @return the number of likes
	 */
	public int getLikes() {
		return likes;
	}
	
	/**
	 * Gets the number of dislikes of the question
	 * 
	 * @return the number of dislikes
	 */
	public int getDislikes() {
		return dislikes;
	}
}

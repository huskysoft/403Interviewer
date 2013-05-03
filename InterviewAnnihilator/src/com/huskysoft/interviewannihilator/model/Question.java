package com.huskysoft.interviewannihilator.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * 
 * Governs the fields and behavior of the questions that are created
 * and displayed on our application
 * 
 * @author Dan Sanders, 4/29/13
 *
 */
public class Question implements Likeable {

	private int id;
	private String text;
	private String title;
	private int authorId;
	private Date dateCreated;
	private List<Integer> category;
	private int difficulty;
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
	 * @param categories
	 * @param difficulty
	 */
	public Question(String text, String title, List<Category> categories, 
			Difficulty difficulty) {
		this.text = text;
		this.title = title;		
		this.difficulty = difficulty.ordinal();
		this.category = new ArrayList<Integer>(categories.size());
		for (Category cat : categories) {
			this.category.add(cat.ordinal());
		}
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
	public List<Category> getCategory() {
		List<Category> categories = new ArrayList<Category>(category.size());
		for (Integer i : category) {
			categories.add(Category.values()[i]);
		}
		return categories;
	}
	
	/**
	 * Gets the difficulty rating of this question
	 * 
	 * @return the value in the difficultyEnum identifying how hard this
	 * question is
	 */
	public Difficulty getDifficulty() {
		return Difficulty.values()[difficulty];
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + authorId;
		result = prime * result
				+ ((category == null) ? 0 : category.hashCode());
		result = prime * result
				+ ((dateCreated == null) ? 0 : dateCreated.hashCode());
		result = prime * result + difficulty;
		result = prime * result + dislikes;
		result = prime * result + id;
		result = prime * result + likes;
		result = prime * result + ((text == null) ? 0 : text.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Question other = (Question) obj;
		if (authorId != other.authorId)
			return false;
		if (category == null) {
			if (other.category != null)
				return false;
		} else if (!category.equals(other.category))
			return false;
		if (dateCreated == null) {
			if (other.dateCreated != null)
				return false;
		} else if (!dateCreated.equals(other.dateCreated))
			return false;
		if (difficulty != other.difficulty)
			return false;
		if (dislikes != other.dislikes)
			return false;
		if (id != other.id)
			return false;
		if (likes != other.likes)
			return false;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}
	
}
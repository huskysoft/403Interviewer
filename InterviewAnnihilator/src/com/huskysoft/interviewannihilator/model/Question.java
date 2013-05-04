package com.huskysoft.interviewannihilator.model;

import java.util.Date;


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
	private Category category;
	private Difficulty difficulty;
	private int likes;
	private int dislikes;
	
	public Question() {}
	
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
	public Question(String text, String title, Category category, 
			Difficulty difficulty) {
		this.text = text;
		this.title = title;		
		this.difficulty = difficulty;
		this.category = category;
	}

	public int getId() {
		return id;
	}
	
	public String getText() {
		return text;
	}
	
	public String getTitle() {
		return title;
	}
	
	public int getAuthorId() {
		return authorId;
	}
	
	public Date getDateCreated() {
		return dateCreated;
	}
	
	public Category getCategory() {
		return category;
	}
	
	public Difficulty getDifficulty() {
		return difficulty;
	}
	
	public int getLikes() {
		return likes;
	}
	
	public int getDislikes() {
		return dislikes;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setAuthorId(int authorId) {
		this.authorId = authorId;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public void setDifficulty(Difficulty difficulty) {
		this.difficulty = difficulty;
	}

	public void setLikes(int likes) {
		this.likes = likes;
	}

	public void setDislikes(int dislikes) {
		this.dislikes = dislikes;
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
		result = prime * result
				+ ((difficulty == null) ? 0 : difficulty.hashCode());
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
		if (category != other.category)
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

	@Override
	public String toString() {
		return "Question [id=" + id + ", text=" + text + ", title=" + title
				+ ", authorId=" + authorId + ", dateCreated=" + dateCreated
				+ ", category=" + category + ", difficulty=" + difficulty
				+ ", likes=" + likes + ", dislikes=" + dislikes + "]";
	}
	
}
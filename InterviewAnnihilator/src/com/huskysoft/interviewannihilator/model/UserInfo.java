/**
 * Local object to keep track of user history, favorites, and upvotes/downvotes
 * 
 * @author Bennett Ng, 5/10/13
 *
 */
package com.huskysoft.interviewannihilator.model;

import android.annotation.SuppressLint;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@SuppressLint("UseSparseArrays")
public class UserInfo {

	public static final Boolean UPVOTE = true;
	public static final Boolean DOWNVOTE = false;

	private String userEmail;
	private Integer userId;
	private Map<Integer, Date> viewedQuestions;
	private Map<Integer, Date> favoriteQuestions;
	private Map<Integer, Boolean> votedQuestions;
	private Map<Integer, Boolean> votedSolutions;

	public UserInfo() {
		// how to sort a map on values:
		// http://stackoverflow.com/questions/2864840/treemap-sort-by-value
		viewedQuestions = new HashMap<Integer, Date>();
		favoriteQuestions = new HashMap<Integer, Date>();
		votedQuestions = new HashMap<Integer, Boolean>();
		votedSolutions = new HashMap<Integer, Boolean>();
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Map<Integer, Date> getViewedQuestions() {
		return viewedQuestions;
	}

	public void setViewedQuestions(Map<Integer, Date> viewedQuestions) {
		this.viewedQuestions = viewedQuestions;
	}

	public Map<Integer, Date> getFavoriteQuestions() {
		return favoriteQuestions;
	}

	public void setFavoriteQuestions(Map<Integer, Date> favoriteQuestions) {
		this.favoriteQuestions = favoriteQuestions;
	}

	public Map<Integer, Boolean> getVotedQuestions() {
		return votedQuestions;
	}

	public void setVotedQuestions(Map<Integer, Boolean> votedQuestions) {
		this.votedQuestions = votedQuestions;
	}

	public Map<Integer, Boolean> getVotedSolutions() {
		return votedSolutions;
	}

	public void setVotedSolutions(Map<Integer, Boolean> votedSolutions) {
		this.votedSolutions = votedSolutions;
	}

	/**
	 * Mark a Question as viewed
	 * 
	 * @param questionId
	 */
	public void markViewedQuestion(int questionId) {
		viewedQuestions.put(questionId, new Date());
	}

	/**
	 * Get the Date at which this Question was marked viewed. Returns null if
	 * Question has never been marked viewed.
	 * 
	 * @param questionId
	 * @return
	 */
	public Date whenViewedQuestion(int questionId) {
		return viewedQuestions.get(questionId);
	}

	/**
	 * Mark a Question as a favorite
	 * 
	 * @param questionId
	 */
	public void markFavoriteQuestion(int questionId) {
		favoriteQuestions.put(questionId, new Date());
	}

	/**
	 * Clear a specific favorite Question
	 * 
	 * @param questionId
	 * @return
	 */
	public boolean clearFavoriteQuestion(int questionId) {
		return (favoriteQuestions.remove(questionId) != null);
	}

	/**
	 * Get the Date at which this Question was marked favorite. Returns null if
	 * Question has never been marked favorite.
	 * 
	 * @param questionId
	 * @return
	 */
	public Date whenFavoriteQuestion(int questionId) {
		return favoriteQuestions.get(questionId);
	}

	/**
	 * Upvote a Question. Will overwrite a downvote.
	 * 
	 * @param questionId
	 */
	public void upvoteQuestion(int questionId) {
		votedQuestions.put(questionId, UPVOTE);
	}

	/**
	 * Downvote a Question. Will overwrite an upvote.
	 * 
	 * @param questionId
	 */
	public void downvoteQuestion(int questionId) {
		votedQuestions.put(questionId, DOWNVOTE);
	}

	/**
	 * Clear any votes for a Question.
	 * 
	 * @param questionId
	 */
	public void novoteQuestion(int questionId) {
		votedQuestions.remove(questionId);
	}
	
	/**
	 * Get the vote status of a given Question. Returns 'true' if upvoted,
	 * 'false' if downvoted, or 'null' if no vote has been submitted.
	 * 
	 * @param questionId
	 * @return
	 */
	public Boolean getQuestionVote(int questionId) {
		return votedQuestions.get(questionId);
	}

	/**
	 * Upvote a Solution. Will overwrite a downvote.
	 * 
	 * @param solutionId
	 */
	public void upvoteSolution(int solutionId) {
		votedSolutions.put(solutionId, UPVOTE);
	}

	/**
	 * Downvote a Solution. Will overwrite an upvote.
	 * 
	 * @param solutionId
	 */
	public void downvoteSolution(int solutionId) {
		votedSolutions.put(solutionId, DOWNVOTE);
	}

	/**
	 * Clear any votes for a Question.
	 * 
	 * @param solutionId
	 */
	public void novoteSolution(int solutionId) {
		votedSolutions.remove(solutionId);
	}
	
	/**
	 * Get the vote status of a given Solution. Returns 'true' if upvoted,
	 * 'false' if downvoted, or 'null' if no vote has been submitted.
	 * 
	 * @param solutionId
	 * @return
	 */
	public Boolean getSolutionVote(int solutionId) {
		return votedSolutions.get(solutionId);
	}

	/**
	 * Clears all user data, preserving the user's email and ID
	 */
	public void clear() {
		viewedQuestions.clear();
		favoriteQuestions.clear();
		votedQuestions.clear();
		votedSolutions.clear();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((favoriteQuestions == null) ? 0 : favoriteQuestions
						.hashCode());
		result = prime * result
				+ ((userEmail == null) ? 0 : userEmail.hashCode());
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		result = prime * result
				+ ((viewedQuestions == null) ? 0 : viewedQuestions.hashCode());
		result = prime * result
				+ ((votedQuestions == null) ? 0 : votedQuestions.hashCode());
		result = prime * result
				+ ((votedSolutions == null) ? 0 : votedSolutions.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!getClass().equals(obj.getClass()))
			return false;
		UserInfo other = (UserInfo) obj;
		if (favoriteQuestions == null) {
			if (other.favoriteQuestions != null)
				return false;
		} else if (!favoriteQuestions.equals(other.favoriteQuestions))
			return false;
		if (userEmail == null) {
			if (other.userEmail != null)
				return false;
		} else if (!userEmail.equals(other.userEmail))
			return false;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		if (viewedQuestions == null) {
			if (other.viewedQuestions != null)
				return false;
		} else if (!viewedQuestions.equals(other.viewedQuestions))
			return false;
		if (votedQuestions == null) {
			if (other.votedQuestions != null)
				return false;
		} else if (!votedQuestions.equals(other.votedQuestions))
			return false;
		if (votedSolutions == null) {
			if (other.votedSolutions != null)
				return false;
		} else if (!votedSolutions.equals(other.votedSolutions))
			return false;
		return true;
	}


}

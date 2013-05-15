package com.huskysoft.interviewannihilator.model;

import android.annotation.SuppressLint;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@SuppressLint("UseSparseArrays")
public class UserInfo {
	
	public static final Boolean UPVOTE = true;
	public static final Boolean DOWNVOTE = false;
	
	private String userEmail;
	private String userId;
	private Set<Integer> viewedQuestions;
	private Set<Integer> favoriteQuestions;
	private Map<Integer, Boolean> votedQuestions;
	private Map<Integer, Boolean> votedSolutions;
	
	public UserInfo() {
		viewedQuestions = new HashSet<Integer>();
		favoriteQuestions = new HashSet<Integer>();
		votedQuestions = new HashMap<Integer, Boolean>();
		votedSolutions = new HashMap<Integer, Boolean>();
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Set<Integer> getViewedQuestions() {
		return viewedQuestions;
	}
	public void setViewedQuestions(Set<Integer> viewedQuestions) {
		this.viewedQuestions = viewedQuestions;
	}
	public Set<Integer> getFavoriteQuestions() {
		return favoriteQuestions;
	}
	public void setFavoriteQuestions(Set<Integer> favoriteQuestions) {
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
	
	public void markViewedQuestion(int questionId) {
		viewedQuestions.add(questionId);
	}
	public boolean haveViewedQuestion(int questionId) {
		return viewedQuestions.contains(questionId);
	}
	public void markFavoriteQuestion(int questionId) {
		favoriteQuestions.add(questionId);
	}
	public boolean clearFavoriteQuestion(int questionId) {
		return favoriteQuestions.remove(questionId);
	}
	public boolean isFavoriteQuestion(int questionId) {
		return favoriteQuestions.contains(questionId);
	}
	public void upvoteQuestion(int questionId) {
		votedQuestions.put(questionId, UPVOTE);
	}
	public void downvoteQuestion(int questionId) {
		votedQuestions.put(questionId, DOWNVOTE);
	}
	public void upvoteSolution(int solutionId) {
		votedSolutions.put(solutionId, UPVOTE);
	}
	public void downvoteSolution(int solutionId) {
		votedSolutions.put(solutionId, DOWNVOTE);
	}
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
		if (getClass() != obj.getClass())
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

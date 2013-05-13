package com.huskysoft.interviewannihilator.service;

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
}

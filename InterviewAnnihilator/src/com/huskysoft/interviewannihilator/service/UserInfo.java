package com.huskysoft.interviewannihilator.service;

import java.util.Map;
import java.util.Set;

public class UserInfo {
	
	private String userId;
	private Set<String> viewedQuestions;
	private Map<String, Boolean> votedQuestions;
	private Map<String, Boolean> votedSolutions;
	
	public UserInfo() {
		
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Set<String> getViewedQuestions() {
		return viewedQuestions;
	}
	public void setViewedQuestions(Set<String> viewedQuestions) {
		this.viewedQuestions = viewedQuestions;
	}
	public Map<String, Boolean> getVotedQuestions() {
		return votedQuestions;
	}
	public void setVotedQuestions(Map<String, Boolean> votedQuestions) {
		this.votedQuestions = votedQuestions;
	}
	public Map<String, Boolean> getVotedSolutions() {
		return votedSolutions;
	}
	public void setVotedSolutions(Map<String, Boolean> votedSolutions) {
		this.votedSolutions = votedSolutions;
	}
}

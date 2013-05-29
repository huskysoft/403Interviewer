package com.huskysoft.interviewannihilator.service;

import java.util.Collection;
import java.util.List;

import com.huskysoft.interviewannihilator.model.Category;
import com.huskysoft.interviewannihilator.model.Difficulty;
import com.huskysoft.interviewannihilator.model.NetworkException;

public interface NetworkServiceInterface {

	public String getQuestions(Difficulty difficulty,
			Collection<Category> categories, int limit, int offset,
			boolean random, Integer authorId) throws NetworkException;

	public String getQuestionsById(List<Integer> questionIds)
			throws NetworkException;

	public String postQuestion(String question) throws NetworkException;

	public boolean deleteQuestion(int questionId, String userEmail)
			throws NetworkException;

	public String getSolutions(int questionId, int limit, int offset)
			throws NetworkException;

	public int getUserId(String userEmail) throws NetworkException;

	public String postSolution(String json) throws NetworkException;

	public boolean deleteSolution(int solutionId, String userEmail)
			throws NetworkException;

	public boolean voteQuestion(int questionId, boolean upvote,
			String userEmail) throws NetworkException;

	public boolean voteSolution(int solutionId, boolean upvote,
			String userEmail) throws NetworkException;

}

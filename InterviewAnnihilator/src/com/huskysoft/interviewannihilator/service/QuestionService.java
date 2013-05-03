package com.huskysoft.interviewannihilator.service;

import java.util.List;

import com.huskysoft.interviewannihilator.model.Category;
import com.huskysoft.interviewannihilator.model.Difficulty;
import com.huskysoft.interviewannihilator.model.Question;
import com.huskysoft.interviewannihilator.model.Solution;
import com.huskysoft.interviewannihilator.util.PaginatedResults;

/**
 * A class to provide services to the front end for getting questions,
 * getting and posting solutions, etc. 
 * 
 * @author Kevin Loh, 5/2/2013
 *
 */
public class QuestionService implements QuestionServiceInterface {
	
	private static QuestionService instance;
	
	/**
	 * Private constructor - QuestionService implements the singleton pattern
	 */
	private QuestionService() {
		
	}
	
	public static QuestionService getInstance() {
		if (instance == null) {
			instance = new QuestionService();
		}
		return instance;
	}

	@Override
	public PaginatedResults<Question> getQuestions(List<Category> category,
			Difficulty difficulty, int limit, int offset) {
		return null;
	}

	@Override
	public PaginatedResults<Solution> getSolutions(int questionId, int limit,
			int offset) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int postQuestion(Question toPost) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int postSolution(Solution toPost) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean upvoteSolution(int solutionId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean downvoteSolution(int solutionId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public PaginatedResults<Question> getFavorites(int limit, int offset) {
		// TODO Auto-generated method stub
		return null;
	}

}

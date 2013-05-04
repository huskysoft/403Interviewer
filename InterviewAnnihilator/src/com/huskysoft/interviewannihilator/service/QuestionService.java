package com.huskysoft.interviewannihilator.service;

import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONException;

import com.huskysoft.interviewannihilator.model.Category;
import com.huskysoft.interviewannihilator.model.Difficulty;
import com.huskysoft.interviewannihilator.model.NetworkException;
import com.huskysoft.interviewannihilator.model.Question;
import com.huskysoft.interviewannihilator.model.Solution;
import com.huskysoft.interviewannihilator.util.PaginatedQuestions;
import com.huskysoft.interviewannihilator.util.PaginatedSolutions;

/**
 * A class to provide services to the front end for getting questions,
 * getting and posting solutions, etc. 
 * 
 * @author Dan Sanders, Bennett Ng, 5/2/2013
 *
 */
public class QuestionService {
	
	private static QuestionService instance;
	private NetworkService networkService;
	private ObjectMapper mapper;

	private QuestionService() {
		networkService = NetworkService.getInstance();
		mapper = new ObjectMapper();
	}
	
	protected QuestionService(NetworkService networkService) {
		this.networkService = networkService;
		mapper = new ObjectMapper();
	}
		
	/**
	 * Get the singleton QuestionService
	 */
	public static QuestionService getInstance() {
		if (instance == null) {
			instance = new QuestionService();
		}
		return instance;
	}

	public PaginatedQuestions getQuestions(List<Category> categories,
			Difficulty difficulty, int limit, int offset) 
			throws NetworkException, JSONException {
		String json = networkService.getQuestions(difficulty, categories,
				limit, offset);
		PaginatedQuestions res;
		try {
			res = mapper.readValue(json, PaginatedQuestions.class);
		} catch (Exception e) {
			throw new JSONException("Failed to deserialize JSON :" + json);
		}
		return res;
	}
	
	public PaginatedSolutions getSolutions(int questionId, int limit,
			int offset)
			throws NetworkException, JSONException {
		String json = networkService.getSolutions(questionId, limit, offset);
		PaginatedSolutions res;
		try {
			res = mapper.readValue(json, PaginatedSolutions.class);
		} catch (Exception e) {
			throw new JSONException("Failed to deserialize JSON :" + json);
		}
		return res;
	}

	public int postQuestion(Question toPost) {
		// TODO Auto-generated method stub
		return 0;
	}

	public int postSolution(Solution toPost) {
		// TODO Auto-generated method stub
		return 0;
	}

	public boolean upvoteSolution(int solutionId) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean downvoteSolution(int solutionId) {
		// TODO Auto-generated method stub
		return false;
	}

	public PaginatedQuestions getFavorites(int limit, int offset) {
		// TODO Auto-generated method stub
		return null;
	}

}

package com.huskysoft.interviewannihilator.service;

import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONException;

import com.huskysoft.interviewannihilator.model.Category;
import com.huskysoft.interviewannihilator.model.Difficulty;
import com.huskysoft.interviewannihilator.model.NetworkException;
import com.huskysoft.interviewannihilator.model.Question;
import com.huskysoft.interviewannihilator.model.Solution;
import com.huskysoft.interviewannihilator.util.PaginatedResults;
import com.huskysoft.interviewannihilator.util.PaginatedResultsDTO;

/**
 * A class to provide services to the front end for getting questions,
 * getting and posting solutions, etc. 
 * 
 * @author Kevin Loh, 5/2/2013
 *
 */
public class QuestionService implements QuestionServiceInterface {
	
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

	@Override
	public PaginatedResults<Question> getQuestions(List<Category> categories,
			Difficulty difficulty, int limit, int offset) 
			throws NetworkException, JSONException {
		String json = networkService.getQuestions(difficulty, categories,
				limit, offset);
		PaginatedResultsDTO dto;
		try {
			dto = mapper.readValue(json, PaginatedResultsDTO.class);
		} catch (Exception e) {
			throw new JSONException("Failed to deserialize JSON :" + json);
		}
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

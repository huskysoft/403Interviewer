package com.huskysoft.interviewannihilator.service;

import java.util.List;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.type.TypeFactory;
import org.codehaus.jackson.type.JavaType;

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
	
	private static final String RESULTS_KEY = "results";
	private static QuestionService instance;
	private NetworkService networkService;
	private ObjectMapper mapper;

	private QuestionService() {
		this(NetworkService.getInstance());
	}
	
	protected QuestionService(NetworkService networkService) {
		this.networkService = networkService;
		mapper = new ObjectMapper();
		mapper.configure(DeserializationConfig.
				Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
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
			// deserialize "flat parameters"
			res = mapper.readValue(json, PaginatedQuestions.class);
			JsonNode node = mapper.readTree(json);
			
			// deserialize nested Questions
			String questionsJson = node.get(RESULTS_KEY).asText();
			JavaType jtype = TypeFactory.defaultInstance().
					constructParametricType(List.class, Question.class);
			List<Question> questions = mapper.readValue(questionsJson, jtype);
			res.setQuestions(questions);
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
			// deserialize "flat parameters"
			res = mapper.readValue(json, PaginatedSolutions.class);
			JsonNode node = mapper.readTree(json);
			
			// deserialize nested Questions
			String questionsJson = node.get(RESULTS_KEY).asText();
			JavaType jtype = TypeFactory.defaultInstance().
					constructParametricType(List.class, Solution.class);
			List<Solution> solutions = mapper.readValue(questionsJson, jtype);
			res.setSolutions(solutions);
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

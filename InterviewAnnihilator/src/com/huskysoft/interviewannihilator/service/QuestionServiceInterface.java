package com.huskysoft.interviewannihilator.service;

import java.util.List;

import com.huskysoft.interviewannihilator.model.CategoryEnum;
import com.huskysoft.interviewannihilator.model.DifficultyEnum;
import com.huskysoft.interviewannihilator.model.Question;
import com.huskysoft.interviewannihilator.model.Solution;
import com.huskysoft.interviewannihilator.util.PaginatedResults;

/**
 * 
 * This interface represents the methods we are providing to the activities
 * of the application that they can call to get information they need from
 * the database
 * 
 * @author Dan Sanders, 4/29/13
 *
 */
public interface QuestionServiceInterface {

	/**
	 * Retrieves a list of questions from the database.
	 * 
	 * @param category - list representing the filters on the categories of
	 * the questions they want back. Can be null
	 * @param difficulty - filter representing how hard they want the questions
	 * they get back to be. Can be null
	 * @param limit - number of questions requested
	 * @param offset - query offset
	 * @return PaginatedResults containing the selected questions
	 */
	public PaginatedResults<Question> getQuestions(List<CategoryEnum> 
		category, DifficultyEnum difficulty, int limit, int offset);
	
	/**
	 * Gets the solutions corresponding to a given question from the database
	 * 
	 * @param questionId the identifier for the question that the user wants
	 * answers to
	 * @param limit - number of solutions requested
	 * @param offset - query offset
	 * @return PaginatedResults containing the selected solutions
	 */
	public PaginatedResults<Solution> getSolutions(int questionId, int limit, int offset);
	
	/**
	 * Called when the application user wants to post a question to the
	 * database.
	 * 
	 * @param toPost the question they want on the database
	 * @return the questionId assigned to the question they posted, a negative
	 * integer if the post fails
	 */
	public int postQuestion(Question toPost);
	
	/**
	 * Called when the user wants to post a solution for a given question on to
	 * the database.
	 * 
	 * @param toPost the Solution the user is posting. Note that they have to
	 * have populated the questionId field for this method to work correctly
	 * @return the id assigned to the solution they posted if the post was
	 * successful. If the post fails, a negative integer is returned
	 */
	public int postSolution(Solution toPost);
	
	/**
	 * Gives a "like" to a given solution
	 * 
	 * @param solutionId the id of the solution to be liked
	 * @return bool representing whether the "like" was successful
	 */
	public boolean upvoteSolution(int solutionId);
	
	/**
	 * Gives a "dislike" to a given solution
	 * 
	 * @param solutionId the id of the solution to be disliked
	 * @return bool representing whether the "dislike" was successful
	 */
	public boolean downvoteSolution(int solutionId);
}

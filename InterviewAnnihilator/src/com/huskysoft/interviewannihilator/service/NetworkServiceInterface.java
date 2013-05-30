/**
 * An interface for the NetworkService class. Necessary for mock testing using
 * EasyMock.
 * 
 * @author Kevin Loh, 5/28/2013
 */

package com.huskysoft.interviewannihilator.service;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import android.accounts.NetworkErrorException;

import com.huskysoft.interviewannihilator.model.Category;
import com.huskysoft.interviewannihilator.model.Difficulty;
import com.huskysoft.interviewannihilator.model.NetworkException;

public interface NetworkServiceInterface {

	/**
	 * Request questions from the server.
	 * 
	 * @return a string with the form [q_1, q_2, ..., q_n] (each q_i is a JSON
	 *         String). Null if an exception occurs or the request fails.
	 * @throws NetworkErrorException
	 */
	public String getQuestions(Difficulty difficulty,
			Collection<Category> categories, int limit, int offset,
			boolean random, Integer authorId) throws NetworkException;

	/**
	 * Get a specific list of Questions from the remote server. NetworkException
	 * if one or more QuestionID does not exist.
	 * 
	 * @param questionIds
	 * @return
	 * @throws IOException 
	 * @throws JsonException 
	 * @throws NetworkException
	 */
	public String getQuestionsById(List<Integer> questionIds)
			throws NetworkException;

	/**
	 * Posts a question to the server. Returns true if the post succeeds.
	 * 
	 * @param question
	 *            a JSON string representing the question
	 * @return a String representing the response from the server
	 * @throws NetworkException
	 * @throws IllegalArgumentException
	 */
	public String postQuestion(String question) throws NetworkException;

	/**
	 * Deletes a question from the remote DB. Returns true on success.
	 * 
	 * @param questionId the id of the question to be deleted
	 * @param userEmail must be the one who posted the question to delete it
	 * @return a bool indicating whether the deletion was successful
	 * @throws NetworkException 
	 */
	public boolean deleteQuestion(int questionId, String userEmail)
			throws NetworkException;

	/**
	 * Request all the solutions on the server for a given question
	 * 
	 * @param questionId
	 *            the id of the question of the solutions we are fetching
	 * @param limit
	 *            the number of solutions wanted. Must be >= 0
	 * @param offset
	 *            the starting offset of the solutions wanted. Must be >= 0
	 * @return a String that can be deserialized into JSON representing the
	 *         answers to a question
	 * @throws NetworkErrorException
	 */
	public String getSolutions(int questionId, int limit, int offset)
			throws NetworkException;

	/**
	 * Gets the userId associated with a given email in the database
	 * 
	 * @param userEmail the email whose id we are getting
	 * @return the id associated with the email. Creates a new entry in the
	 * database and returns the id of the new entry if the email doesn't
	 * exist in the database yet
	 * @throws NetworkException
	 * @throws IOException
	 * @throws IllegalArgumentException
	 */
	public int getUserId(String userEmail) throws NetworkException;

	/**
	 * Posts a solution to the server. Returns true if the post succeeds.
	 * 
	 * @param json
	 *            a JSON string representing the solution
	 * @return a String representing the response from the server
	 * @throws NetworkException
	 * @throws IllegalArgumentException
	 */
	public String postSolution(String json) throws NetworkException;

	/**
	 * Deletes a solution from the remote DB
	 * 
	 * @param solutionId the id of the solution to be deleted
	 * @param userEmail must be the author of the solution they are deleting
	 * @return a bool indicating whether the deletion was successful
	 * @throws NetworkException
	 */
	public boolean deleteSolution(int solutionId, String userEmail)
			throws NetworkException;

	/**
	 * Votes on a question
	 * 
	 * @param questionId the id of the question to be voted on
	 * @param upvote a bool indicating whether the question is to be upvoted
	 * @param userEmail the email of the user, for authentication
	 * @return bool indicating whether the vote was successful
	 * @throws NetworkException
	 */
	public boolean voteQuestion(int questionId, boolean upvote,
			String userEmail) throws NetworkException;

	/**
	 * Votes on a solution
	 * 
	 * @param solutionId the id of the question to be voted on
	 * @param upvote a bool indicating whether the question is to be upvoted
	 * @param userEmail the email of the user, for authentication
	 * @return bool indicating whether the vote was successful
	 * @throws NetworkException
	 */
	public boolean voteSolution(int solutionId, boolean upvote,
			String userEmail) throws NetworkException;

}

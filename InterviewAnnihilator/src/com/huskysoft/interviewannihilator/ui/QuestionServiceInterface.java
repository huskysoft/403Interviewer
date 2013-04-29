package com.huskysoft.interviewannihilator.ui;

import java.util.List;

import com.huskysoft.interviewannihilator.util.CategoryEnum;
import com.huskysoft.interviewannihilator.util.DifficultyEnum;

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
	 * Retrieves a list of questions from the database, where the size of the
	 * list and its contents are dictated by the arguments
	 * 
	 * @param numQuestions The number of questions the user wants
	 * @param category A list representing the filters on the categories of
	 * the questions they want back
	 * @param difficulty A filter representing how hard they want the questions
	 * they get back to be
	 * @return A list of questions, with size less than or equal to the
	 * numQuestions parameter
	 */
	public List<Question> getQuestions(int numQuestions, List<CategoryEnum> 
		category, DifficultyEnum difficulty);
	
	/**
	 * Gets the solutions corresponding to a given question from the database
	 * 
	 * @param questionId the identifier for the question that the user wants
	 * answers to
	 * @return A list of the solutions associated with a given question
	 */
	public List<Solution> getSolutions(int questionId);
	
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

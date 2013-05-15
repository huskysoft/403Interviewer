/**
 * Defines any network constants we use to avoid hard-coding them into the
 * application code
 * 
 * @author Dan Sanders, 05/04/2013
 */

package com.huskysoft.interviewannihilator.util;

public class NetworkConstants {
	/** The URI of our server for both solution and question. */
	public static final String BASE_URI = 
	"http://students.washington.edu/bkng/cse403/403Interviewer-php/";

	/** The 'question' directory */
	public static final String QUESTION = "question/";

	/** The 'solution' directory */
	public static final String SOLUTION = "solution/";

	/** The script for getQuestions */
	public static final String GET_QUESTIONS_URL =
	BASE_URI + QUESTION + "getQuestions.php";

	/** The script for getSolutions */
	public static final String GET_SOLUTIONS_URL = 
	BASE_URI + SOLUTION + "getSolutions.php";

	/** If a request for multiple categories happens, this token
	    separates out the categories */
	public static final String CATEGORY_DELIMITER = 
	"-";
	
	/** Ampersand for get URL requests */
	public static final String AMPERSAND = "&";
	
	/** HTTP parameters */
	public static final String PARAM_LIMIT = "limit";
	public static final String PARAM_OFFSET = "offset";	
	public static final String PARAM_DIFFICULTY = "difficulty";
	public static final String PARAM_CATEGORY = "category";
	public static final String PARAM_QUESTIONID = "questionId";
	public static final String PARAM_RANDOM = "random";
}

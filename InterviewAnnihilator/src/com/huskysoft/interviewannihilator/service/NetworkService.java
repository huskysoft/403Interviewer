/**
 * A class that provides basic functionalities for getting JSON response
 * strings from the server.
 * 
 * @author Kevin Loh, Bennett Ng, 5/3/2013
 * 
 *
 */

package com.huskysoft.interviewannihilator.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;

import android.accounts.NetworkErrorException;

import com.huskysoft.interviewannihilator.model.Category;
import com.huskysoft.interviewannihilator.model.Difficulty;
import com.huskysoft.interviewannihilator.model.NetworkException;
import com.huskysoft.interviewannihilator.util.Utility;

import static com.huskysoft.interviewannihilator.util.NetworkConstants.*;

public class NetworkService {

	/** The currently running instance of the NetworkService */
	private static NetworkService instance;

	/** The client that actually sends data to the PHP script */
	private HttpClient httpClient;

	private NetworkService() {
		httpClient = new DefaultHttpClient();
	}

	/**
	 * Get the singleton NetworkService
	 */
	public static NetworkService getInstance() {
		if (instance == null) {
			instance = new NetworkService();
		}
		return instance;
	}

	/**
	 * Request questions from the server.
	 * 
	 * @return a string with the form [q_1, q_2, ..., q_n] (each q_i is a JSON
	 *         String). Null if an exception occurs or the request fails.
	 * @throws NetworkErrorException
	 */
	public String getQuestions(Difficulty difficulty,
			Collection<Category> categories, int limit, int offset,
			boolean random) throws NetworkException {
		StringBuilder urlToSend = new StringBuilder(GET_QUESTIONS_URL + "?");
		urlToSend.append(Utility.appendParameter
				(PARAM_LIMIT, String.valueOf(limit)));
		urlToSend.append(Utility.appendParameter
				(PARAM_OFFSET, String.valueOf(offset)));
		if (difficulty != null) {
			urlToSend.append(Utility.appendParameter(PARAM_DIFFICULTY,
					difficulty.name()));
		}
		if (categories != null && categories.size() != 0) {
			Object[] categoryObj = categories.toArray();
			StringBuilder categoryList = new StringBuilder();
			for (int i = 0; i < categories.size(); i++) {
				categoryList.append(categoryObj[i].toString());
				// if this is the last element, we don't put a dash in the URL
				if ((i + 1) < categories.size()) {
					categoryList.append(CATEGORY_DELIMITER);
				}
			}
			urlToSend.append(Utility.appendParameter(PARAM_CATEGORY,
					categoryList.toString()));
		}
		if (random) {
			urlToSend.append(Utility.appendParameter(PARAM_RANDOM, ""));
		}

		// delete the trailing ampersand from the url
		urlToSend.deleteCharAt(urlToSend.lastIndexOf(AMPERSAND));

		return dispatchGetRequest(urlToSend.toString());
	}

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
			throws NetworkException, JSONException, IOException {
		StringBuilder urlToSend = new StringBuilder(GET_QUESTIONS_BYID_URL + "?");
		StringBuilder deliminatedQuestions = new StringBuilder();
		for (int i = 0; i < questionIds.size(); i++) {
			deliminatedQuestions.append(questionIds.get(i));
			if (i < questionIds.size() - 1) {
				deliminatedQuestions.append(CATEGORY_DELIMITER);
			}
		}
		urlToSend.append(Utility.appendParameter(PARAM_QUESTIONID,
				deliminatedQuestions.toString()));
		// delete the trailing ampersand from the url
		urlToSend.deleteCharAt(urlToSend.lastIndexOf(AMPERSAND));

		return dispatchGetRequest(urlToSend.toString());
	}
	
	/**
	 * Posts a question to the server. Returns true if the post succeeds.
	 * 
	 * @param question
	 *            a JSON string representing the question
	 * @return a String representing the response from the server
	 * @throws NetworkException
	 * @throws IllegalArgumentException
	 */
	public String postQuestion(String question) throws NetworkException {
		Utility.ensureNotNull(question, "Question");
		return dispatchPostRequest(POST_QUESTION_URL, question);
	}

	/**
	 * Deletes a question from the remote DB. Returns true on success.
	 * 
	 * @param questionId
	 * @param userEmail
	 * @return
	 * @throws NetworkException 
	 */
	public boolean deleteQuestion(int questionId, String userEmail)
			throws NetworkException {
		StringBuilder urlToSend = new StringBuilder(DELETE_QUESTION_URL + "?");
		urlToSend.append(Utility.appendParameter(
				PARAM_QUESTIONID, String.valueOf(questionId)));
		
		// delete the trailing ampersand from the url
		urlToSend.deleteCharAt(urlToSend.lastIndexOf(AMPERSAND));
		dispatchPostRequest(urlToSend.toString(), userEmail);
		
		// TODO: detect failure
		return true;
	}

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
			throws NetworkException {
		StringBuilder urlToSend = new StringBuilder(GET_SOLUTIONS_URL + "?");
		urlToSend.append(Utility.appendParameter(PARAM_QUESTIONID,
				String.valueOf(questionId)));
		urlToSend.append(Utility.appendParameter
				(PARAM_LIMIT, String.valueOf(limit)));
		urlToSend.append(Utility.appendParameter
				(PARAM_OFFSET, String.valueOf(offset)));

		// delete the trailing ampersand from the url
		urlToSend.deleteCharAt(urlToSend.lastIndexOf(AMPERSAND));

		return dispatchGetRequest(urlToSend.toString());
	}

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
	public int getUserId(String userEmail) throws NetworkException {
		Utility.ensureNotNull(userEmail, "User email");
		String res = dispatchPostRequest(GET_USERID_URL, userEmail);
		return Integer.valueOf(res);
	}
	
	/**
	 * Posts a solution to the server. Returns true if the post succeeds.
	 * 
	 * @param json
	 *            a JSON string representing the solution
	 * @return a String representing the response from the server
	 * @throws NetworkException
	 * @throws IllegalArgumentException
	 */
	public String postSolution(String json) throws NetworkException {
		Utility.ensureNotNull(json, "Solution JSON");
		return dispatchPostRequest(POST_SOLUTION_URL, json);
	}
	
	public boolean deleteSolution(int solutionId, String userEmail) {
		// TODO
		return false;
	}

	/**
	 * Dispatches a get request to the remote server
	 * 
	 * @param url the url to send to the server
	 * @return a String representing the response from the server
	 * @throws NetworkException
	 */
	private String dispatchGetRequest(String url) throws NetworkException {
		try {
			// create client and send request
			HttpGet request = new HttpGet(url);
			HttpResponse response = httpClient.execute(request);

			// get response
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != 200) {
				throw new NetworkException("Request to " + url
						+ " failed with response code " + statusCode);
			}
			
			// Return the content
			return getContent(response);
		} catch (Exception e) {
			throw new NetworkException("GET request to " + url + " failed", e);
		}
	}

	/**
	 * Dispatches a post request to the remote server with the given url. The
	 * content of the post request is the given json string.
	 * 
	 * @param url
	 *            the url of the server
	 * @param jsonString
	 *            a JSON string as the content of the post request
	 * @return a String representing the response from the server
	 * @throws NetworkException
	 */
	private String dispatchPostRequest(String url, String jsonString)
			throws NetworkException {
		try {
			// Create and execute the HTTP POST request
			HttpPost request = new HttpPost(url);
			StringEntity requestContent = new StringEntity(jsonString);
			requestContent.setContentType("application/json");
			request.setEntity(requestContent);
			HttpResponse response = httpClient.execute(request);

			// Check the status of the response
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != 200) {
				throw new NetworkException("Request to " + url
						+ " failed with response code " + statusCode);
			}

			// Return the content
			return getContent(response);
			
		} catch (Exception e) {
			throw new NetworkException("POST request to " + url + " failed", e);
		}
	}

	/**
	 * Gets the content of the HTTP response of a GET/POST request.
	 * 
	 * @param response
	 *            the HTTP response after the request
	 * @return a String representing the content of the response
	 * @throws IOException
	 * @throws IllegalStateException
	 * @throws UnsupportedEncodingException
	 */
	private String getContent(HttpResponse response)
			throws UnsupportedEncodingException, IllegalStateException,
			IOException {
		BufferedReader rd = new BufferedReader(new InputStreamReader(response
				.getEntity().getContent(), Utility.ASCII_ENCODING));
		StringBuilder serverString = new StringBuilder();
		String line = rd.readLine();
		while (line != null) {
			serverString.append(line);
			line = rd.readLine();
		}
		rd.close();
		return serverString.toString();
	}

	@Override
	protected void finalize() {
		httpClient.getConnectionManager().shutdown();
	}

}

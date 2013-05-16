/**
 * A class that provides basic functionalities for getting JSON response
 * strings from the server.
 * 
 * @author Bennett Ng, 5/3/2013
 * 
 *
 */

package com.huskysoft.interviewannihilator.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Collection;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import android.accounts.NetworkErrorException;

import com.huskysoft.interviewannihilator.model.Category;
import com.huskysoft.interviewannihilator.model.Difficulty;
import com.huskysoft.interviewannihilator.model.NetworkException;

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
		urlToSend.append(appendParameter(PARAM_LIMIT, String.valueOf(limit)));
		urlToSend.append(appendParameter(PARAM_OFFSET, String.valueOf(offset)));
		if (difficulty != null) {
			urlToSend.append(appendParameter(PARAM_DIFFICULTY,
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
			urlToSend.append(appendParameter(PARAM_CATEGORY,
					categoryList.toString()));
		}
		if (random) {
			urlToSend.append(appendParameter(PARAM_RANDOM, ""));
		}

		// delete the trailing ampersand from the url
		urlToSend.deleteCharAt(urlToSend.lastIndexOf(AMPERSAND));

		return dispatchGetRequest(urlToSend.toString());
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
		urlToSend.append(appendParameter(PARAM_QUESTIONID,
				String.valueOf(questionId)));
		urlToSend.append(appendParameter(PARAM_LIMIT, String.valueOf(limit)));
		urlToSend.append(appendParameter(PARAM_OFFSET, String.valueOf(offset)));

		// delete the trailing ampersand from the url
		urlToSend.deleteCharAt(urlToSend.lastIndexOf(AMPERSAND));

		return dispatchGetRequest(urlToSend.toString());
	}

	/**
	 * Posts a question to the server. Returns true if the post succeeds.
	 * 
	 * @param question
	 *            a JSON string representing the question
	 * @return true if the post succeeds, false otherwise
	 * @throws NetworkException
	 */
	public boolean postQuestion(String question) throws NetworkException {
		return dispatchPostRequest(POST_QUESTION_URL, question);
	}

	/**
	 * Posts a solution to the server. Returns true if the post succeeds.
	 * 
	 * @param solution
	 *            a JSON string representing the solution
	 * @return true if the post succeeds, false otherwise
	 * @throws NetworkException
	 */
	public boolean postSolution(String solution) throws NetworkException {
		return dispatchPostRequest(POST_SOLUTION_URL, solution);
	}

	/**
	 * Append a given parameter to a url string
	 * 
	 * @param paramName
	 *            the name of the parameter appended to the url
	 * @param paramVal
	 *            the value of the parameter appended to the url
	 * @param addAmpersand
	 *            should be set to false if this is the last param that is to be
	 *            appended
	 * @return a new String with the parameter appended. Returns the empty
	 *         String if either of the Strings passed in were null
	 */
	private String appendParameter(String paramName, String paramVal) {
		if (paramName == null || paramVal == null) {
			return "";
		}
		return (paramName + "=" + paramVal + AMPERSAND);
	}

	/**
	 * Dispatches a get request to the remote server
	 * 
	 * @param url
	 *            the url to send to the server
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
			BufferedReader rd = new BufferedReader(new InputStreamReader(
					response.getEntity().getContent()));

			StringBuilder serverString = new StringBuilder();
			String line = rd.readLine();
			while (line != null) {
				serverString.append(line);
				line = rd.readLine();
			}
			return serverString.toString();
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
	 * @param content
	 *            a JSON string as the content of the post request
	 * @return true if the post request is successful, false otherwise
	 * @throws NetworkException
	 */
	private boolean dispatchPostRequest(String url, String content)
			throws NetworkException {
		try {
			// Create and execute the HTTP POST request
			HttpPost request = new HttpPost(url);
			StringEntity requestContent = new StringEntity(content);
			requestContent.setContentType("application/json");
			request.setEntity(requestContent);
			HttpResponse response = httpClient.execute(request);

			// Check the status of the response
			int statusCode = response.getStatusLine().getStatusCode();
			return statusCode == 200;

		} catch (Exception e) {
			throw new NetworkException("POST request to " + url + " failed", e);
		}
	}

	@Override
	public void finalize() {
		httpClient.getConnectionManager().shutdown();
	}

}

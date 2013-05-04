package com.huskysoft.interviewannihilator.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.accounts.NetworkErrorException;

import com.huskysoft.interviewannihilator.model.Category;
import com.huskysoft.interviewannihilator.model.Difficulty;
import com.huskysoft.interviewannihilator.model.NetworkException;

import static com.huskysoft.interviewannihilator.util.NetworkConstants.*;

/**
 * A class that provides basic functionalities for getting JSON response strings
 * from the server.
 * 
 * @author Kevin Loh, 5/3/2013
 * 
 */
/**
 * @author Bennett
 *
 */
public class NetworkService {
	
	private static NetworkService instance;
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
	 * Request all the questions on the server.
	 * 
	 * @return a string with the form [q_1, q_2, ..., q_n]. Each q_i is a JSON
	 *         String; null if an exception occurs or the request fails.
	 * @throws NetworkErrorException 
	 */
	public String getQuestions(Difficulty difficulty, 
			Collection<Category> categories, int limit, int offset) 
			throws NetworkException {
		String urlToSend = GET_QUESTIONS_URL + "?";
		urlToSend = appendParameter(urlToSend, PARAM_LIMIT, 
				String.valueOf(limit));
		urlToSend = appendParameter(urlToSend, PARAM_OFFSET, 
				String.valueOf(offset));
		if (difficulty != null) {
			urlToSend = appendParameter(urlToSend, PARAM_DIFFICULTY, 
					difficulty.name());
		}
		if (categories != null && categories.size() != 0) {
			urlToSend = appendParameter(urlToSend, PARAM_CATEGORY, 
					categories.toString());
		}
		return dispatchGetRequest(urlToSend);
	}

	/**
	 * 
	 * @param questionId the id of the question of the solutions we are
	 * fetching
	 * @param limit the number of solutions wanted
	 * @param offset the starting offset of the solutions wanted
	 * @return a String that can be deserialized into JSON representing the
	 * answers to a question
	 * @throws NetworkErrorException
	 */
	public String getSolutions(int questionId, int limit, int offset)
			throws NetworkException {
		String urlToSend = GET_SOLUTIONS_URL + "?";
		urlToSend = appendParameter(urlToSend, PARAM_LIMIT, 
				String.valueOf(limit));
		urlToSend = appendParameter(urlToSend, PARAM_OFFSET,
				String.valueOf(offset));
		return dispatchGetRequest(urlToSend);
	}
	
	/**
	 * 
	 * @param url the url to which the parameters will be appended to
	 * @param paramName the name of the parameter appended to the url
	 * @param paramVal the value of the parameter appended to the url
	 * @return a new String with the parameter appended
	 */
	private String appendParameter(String url, String paramName,
			String paramVal) {
		String completeUrl = url;
		completeUrl += (paramName + "=" + paramVal + "&");
		return completeUrl;
	}
	
	private String dispatchGetRequest(String url) 
			throws NetworkException {
		try {
			// create client and send request
			HttpGet request = new HttpGet(url);			
			HttpResponse response = httpClient.execute(request);

			// get response
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != 200) {
				throw new NetworkException("Request to " + url +
						" failed with response code " + statusCode);
			}
			BufferedReader rd = new BufferedReader(new InputStreamReader(
					response.getEntity().getContent()));

			StringBuilder ret = new StringBuilder();
			String line = rd.readLine();
			while (line != null) {
				ret.append(line);
				line = rd.readLine();
			}			
			return ret.toString();
		} catch (IOException e) {
			throw new NetworkException("Request to " + url + " failed", e);
		}
	}
	
	@Override
	public void finalize() {
		httpClient.getConnectionManager().shutdown();
	}

}

package com.huskysoft.interviewannihilator.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;

import android.accounts.NetworkErrorException;

import com.huskysoft.interviewannihilator.model.Category;
import com.huskysoft.interviewannihilator.model.Difficulty;
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
			throws NetworkErrorException {
		HttpParams params = new BasicHttpParams();
		params.setIntParameter(PARAM_LIMIT, limit);
		params.setIntParameter(PARAM_OFFSET, offset);
		if (difficulty != null) {
			params.setParameter(PARAM_DIFFICULTY, difficulty.name());
		}
		if (categories != null && categories.size() != 0) {
			params.setParameter(PARAM_CATEGORY, categories.toString());
		}
		return dispatchGetRequest(GET_QUESTIONS_URL, params);
	}

	private String dispatchGetRequest(String url, HttpParams params) 
			throws NetworkErrorException {
		try {
			// create client and send request
			
			HttpGet request = new HttpGet(url);			
			request.setParams(params);
			HttpResponse response = httpClient.execute(request);

			// get response
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != 200) {
				throw new NetworkErrorException("Request to " + url +
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
			throw new NetworkErrorException("Request to " + url + " failed", e);
		}
	}
	
	@Override
	public void finalize() {
		httpClient.getConnectionManager().shutdown();
	}

}

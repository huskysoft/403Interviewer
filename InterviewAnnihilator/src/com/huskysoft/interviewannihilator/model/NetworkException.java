/**
 * An exception our application throws when failing to retrieve data from
 * the external server
 * 
 * @author Dan Sanders, 5/4/2013
 */

package com.huskysoft.interviewannihilator.model;

public class NetworkException extends Exception {

	private static final long serialVersionUID = -7992177400958818519L;
	private String message;
	private Throwable e;
	
	public NetworkException(String message, Throwable e) {
		this.message = message;
		this.e = e;
	}
	
	public NetworkException(String message) {
		this(message, null);
	}
	
	public String getMessage() {
		return message;
	}

	public Throwable getE() {
		return e;
	}

	public NetworkException() {
		this(null, null);
	}

}

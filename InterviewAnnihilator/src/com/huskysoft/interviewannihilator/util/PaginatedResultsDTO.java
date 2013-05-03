package com.huskysoft.interviewannihilator.util;

public class PaginatedResultsDTO {
	
	private String results;
	
	/** total number of objects in the DB matching the request */
	private int totalNumberOfResults;
	
	/** the requested number of objects */
	private int limit;
	
	/** the requested offset */
	private int offset;

	public String getResults() {
		return results;
	}

	public void setResults(String results) {
		this.results = results;
	}

	public int getTotalNumberOfResults() {
		return totalNumberOfResults;
	}

	public void setTotalNumberOfResults(int totalNumberOfResults) {
		this.totalNumberOfResults = totalNumberOfResults;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}
}

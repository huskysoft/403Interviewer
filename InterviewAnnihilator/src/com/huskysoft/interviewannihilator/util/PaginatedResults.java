package com.huskysoft.interviewannihilator.util;

import java.util.List;

/**
 * A generic PaginatedResults object to return paginated data from the DB.
 * Users would get one page of results from the DB, and can then request
 * additional pages (nextOffset = offset + limit) if more results are
 * available (totaNumberOfResults > offset + limit).
 * 
 * @author bkng
 *
 * @param <T>
 */
public class PaginatedResults<T> {

	/** list of objects returned from the DB */
	private List<T> results;
	
	/** total number of objects in the DB matching the request */
	private int totalNumberOfResults;
	
	/** the requested number of objects */
	private int limit;
	
	/** the requested offset */
	private int offset;
	
	/** the type object contained in 'results' */
	private Class<T> clazz;
	
	public PaginatedResults(List<T> results, int totalNumberOfResults, 
			int limit, int offset, Class<T> clazz) {
		this.results = results;
		this.totalNumberOfResults = totalNumberOfResults;
		this.limit = limit;
		this.offset = offset;
		this.clazz = clazz;
	}

	public List<T> getResults() {
		return results;
	}

	public int getTotalNumberOfResults() {
		return totalNumberOfResults;
	}

	public int getLimit() {
		return limit;
	}

	public int getOffset() {
		return offset;
	}

	public Class<T> getClazz() {
		return clazz;
	}


	public void setResults(List<T> results) {
		this.results = results;
	}

	public void setTotalNumberOfResults(int totalNumberOfResults) {
		this.totalNumberOfResults = totalNumberOfResults;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public void setClazz(Class<T> clazz) {
		this.clazz = clazz;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PaginatedResults<?> other = (PaginatedResults<?>) obj;
		if (clazz == null) {
			if (other.clazz != null)
				return false;
		} else if (!clazz.equals(other.clazz))
			return false;
		if (limit != other.limit)
			return false;
		if (offset != other.offset)
			return false;
		if (results == null) {
			if (other.results != null)
				return false;
		} else if (!results.equals(other.results))
			return false;
		if (totalNumberOfResults != other.totalNumberOfResults)
			return false;
		return true;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((clazz == null) ? 0 : clazz.hashCode());
		result = prime * result + limit;
		result = prime * result + offset;
		result = prime * result + ((results == null) ? 0 : results.hashCode());
		result = prime * result + totalNumberOfResults;
		return result;
	}
}

package org.kairos.ibpnh.vo;

/**
 * Value Object for a request that is used with pagination.
 * 
 * @author fgonzalez
 */
public class PaginatedRequestVo {
	
	/**
	 * The page to load
	 */
	private String page;
	
	/**
	 * Flag that indicates if an aditional query is used to get the total number of items.
	 */
	private Boolean fetchTotal;

	/**
	 * @return the page
	 */
	public String getPage() {
		return this.page;
	}

	/**
	 * @param page the page to set
	 */
	public void setPage(String page) {
		this.page = page;
	}

	/**
	 * @return the fetchTotal
	 */
	public Boolean getFetchTotal() {
		return this.fetchTotal;
	}

	/**
	 * @param fetchTotal the fetchTotal to set
	 */
	public void setFetchTotal(Boolean fetchTotal) {
		this.fetchTotal = fetchTotal;
	}
	
}
